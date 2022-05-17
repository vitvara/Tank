package game;

import enitity.*;
import utills.Direction;
import utills.MenuAPI.GameMode;
import utills.pool.ExplosionPool;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainGame extends Observable {

    private Thread mainloop;
    private int width;
    private int height;
    private List<List<Integer>> map = new ArrayList<>();
    private List<Explosion> explosions = new ArrayList<>();
    private ExplosionPool explosionPool;
    private List<Tank> players;
    private int borderWidth;
    private int borderHeight;
    private List<Obstruction> construction;
    private String mapName;
    private List<TankAI> aiList;
    int amount = 4;
    private String isWin;
    private GameMode mode;

    public MainGame(String mapName, int cellSize, GameMode mode) {
        this.mode = mode;
        this.mapName = mapName;
        aiList = new ArrayList<>();
        construction = new ArrayList<Obstruction>();
        readMap();
        borderWidth = (width-1)*cellSize;
        borderHeight = (height-1)*cellSize;
        players = new ArrayList<Tank>();
        players.add(new Tank(cellSize,height*cellSize/2, Direction.RIGHT, borderWidth, borderHeight)); // player1
        if (mode == GameMode.AI) {
            int randomNum1;
            int randomNum2;
            for (int i=0; i< amount; i++) {
                while(true) {
                    randomNum1 = ThreadLocalRandom.current().nextInt(0, map.size()-1 + 1);
                    randomNum2 = ThreadLocalRandom.current().nextInt(0, map.get(0).size()-1 + 1);
                    if (map.get(randomNum1).get(randomNum2) == 0) {break;}
                }
                aiList.add(new TankAI(randomNum2*cellSize, randomNum1*cellSize, Direction.RIGHT, borderWidth, borderHeight));
            }

        }
        else {
            players.add(new Tank(width*cellSize-cellSize,height*cellSize/2, Direction.LEFT, borderWidth, borderHeight));
        }

        explosionPool = new ExplosionPool();

        mainloop  = new Thread() {
            @Override
            public void run() {
                while(true) {
                    tick();
                    setChanged();
                    notifyObservers();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mainloop.start();

    }

    public void readMap() {
        try {
            Scanner s = new Scanner(new File(mapName));
            int j = 0;
            while (s.hasNext()){
                String[] strCell = s.next().split(",");
                List<Integer> realCell = new ArrayList<>();
                for (int i=0; i<strCell.length; i++) {
                    if (Integer.parseInt(strCell[i]) == 3) {
                        construction.add(new Rock(i*64, j*64));
                    }
                    realCell.add(Integer.parseInt(strCell[i]));


                }
                map.add(realCell);
                j += 1;
            }
            s.close();
            this.width = map.get(0).size();
            this.height = map.size();

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Map file not found");
        }
    }



    private void tick() {
        update();
        cleanupBullet();
        checkHit();

        cleanupExplode();
        cleanupRock();
        cleanUpTank();
        checkWin();
    }

    public boolean isEnd() {
        return isWin != null;
    }

    public void pause() {
        mainloop.suspend();
    }

    public void resume() {
        mainloop.resume();
    }

    public List<TankAI> getAI() {
        return aiList;
    }

    private void update() {

        for (Tank tank: getALlTank()) {
            for (Obstruction obs: construction) {
                tank.isCollapse(obs);
            }
            List<Tank> othersTank = new ArrayList<>(getALlTank());
            othersTank.remove(othersTank.indexOf(tank));
            for (Tank otherTank: othersTank) {
                tank.isCollapse(otherTank);
            }
            tank.canGo();
            tank.update();
        }



        for (Bullet bullet: getBullets()) {
            bullet.update();
        }
        for (Entity ele: getAllEntity()) {
            ele.update();
        }
    }

    public List<List<Integer>> getMap() {
        return map;
    }
    private List<Tank> getALlTank() {
        return Stream.concat(getAI().stream(), getPlayers().stream()).collect(Collectors.toList());
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getWinner() {
        return  isWin;
    }

    public void checkWin() {
        if (mode == GameMode.AI && getAI().size() == 0) {
            isWin = "YOU WIN";
            pause();
            return;
        }
        for (Entity ex: players) {
            if (ex.getHitState()) {
                if (mode == GameMode.AI && ex == players.get(0)) {
                    isWin = "YOU LOSE";
                    pause();
                    return;
                } else if (mode == GameMode.ONE_ON_ONE ) {
                    if (ex == players.get(0)){
                        isWin = "PLAYER 1 WIN!";
                        pause();
                    }
                    else if(ex == players.get(1)) {
                        isWin = "PLAYER 2 WIN!";
                        pause();
                    }
                    return;
                }
            }
        }
    }

    public List<Entity> getAllEntity() {
        List<Entity> out = Stream.concat(players.stream(), getBullets().stream()).collect(Collectors.toList());
        out = Stream.concat(out.stream(), construction.stream()).collect(Collectors.toList());
        out = Stream.concat(out.stream(), getAI().stream()).collect(Collectors.toList());
        return  Stream.concat(out.stream(), explosions.stream()).collect(Collectors.toList());
    }

    public void checkHit() {
        List<Entity> canHit = new ArrayList<Entity>();
        canHit = Stream.concat(getALlTank().stream(), construction.stream()).collect(Collectors.toList());
        for (Bullet bullet: getBullets()) {
            for (Entity enti: canHit) {
                if (bullet.isHit(enti)){
                    explosions.add(explosionPool.requestExplosion(enti.getX(), enti.getY()));
                }
            }

        }

    }

    public void cleanUpTank() {

        List<Entity> toRemove = new ArrayList<Entity>();
        for (Entity ex: getAI()) {
            if (ex.getHitState()) {
                toRemove.add(ex);
            }
        }
        for (Entity ex: toRemove) {
            getAI().remove(ex);
        }
    }

    public void cleanupRock() {
        List<Entity> toRemove = new ArrayList<Entity>();
        for (Entity ex: construction) {
            if (ex.getHitState()) {
                toRemove.add(ex);
            }
        }
        for (Entity ex: toRemove) {
            construction.remove(ex);
        }
    }

    public List<Tank> getPlayers() {
        return players;
    }

    public void cleanupExplode() {
        List<Explosion> toRemove = new ArrayList<Explosion>();
        for (Explosion ex: explosions) {
            if (ex.getDone()) {
                toRemove.add(ex);
            }
        }
        for (Explosion ex: toRemove) {
            explosions.remove(ex);
            explosionPool.recieveExplosion(ex);
        }
    }


    public List<Bullet> getBullets() {
        List<Bullet> out = new ArrayList<Bullet>();
        for (Tank player: getALlTank()) {
            out = Stream.concat(out.stream(), player.getBullets().stream()).collect(Collectors.toList());
        }
        return out;
    }

    private void cleanupBullet() {
        for (Tank player: getALlTank()) {
            List<Bullet> toRemove = new ArrayList<Bullet>();
            for (Bullet bullet: player.getBullets()) {
                if (bullet.getX() <= 0 || bullet.getX() >= width * 64 || bullet.getY() <= 0 || bullet.getY() >= height*64 || bullet.isHit()) {
                    toRemove.add(bullet);
                }
            }
            for (Bullet bullet: toRemove) {
                player.getBullets().remove(bullet);
                player.getPool().recieveBullet(bullet);
            }
        }
    }


}
