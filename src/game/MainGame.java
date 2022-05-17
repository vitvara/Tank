package game;

import enitity.*;
import utills.Direction;
import utills.pool.ExplosionPool;

import java.io.File;
import java.util.*;
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
    private List<Obstruction> construction;
    private String mapName;

    public MainGame(String mapName) {
        this.mapName = mapName;
        construction = new ArrayList<Obstruction>();
        readMap();
        players = new ArrayList<Tank>(Arrays.asList(new Tank(64,height*64/2, Direction.RIGHT), new Tank(width*64-64,height*64/2, Direction.LEFT)));


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
    }

    private void update() {
        for (Tank tank: getPlayers()) {
            for (Obstruction obs: construction) {
                tank.canGo(obs);
            }
            List<Tank> othersTank = new ArrayList<>(getPlayers());
            othersTank.remove(othersTank.indexOf(tank));
            for (Tank otherTank: othersTank) {
                tank.canGo(otherTank);
            }
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public List<Entity> getAllEntity() {
        List<Entity> out = Stream.concat(players.stream(), getBullets().stream()).collect(Collectors.toList());
        out = Stream.concat(out.stream(), construction.stream()).collect(Collectors.toList());
        return  Stream.concat(out.stream(), explosions.stream()).collect(Collectors.toList());
    }

    public void checkHit() {
        List<Entity> canHit = new ArrayList<Entity>();
        canHit = Stream.concat(players.stream(), construction.stream()).collect(Collectors.toList());
        for (Bullet bullet: getBullets()) {
            for (Entity enti: canHit) {
                if (bullet.isHit(enti)){
                    explosions.add(explosionPool.requestExplosion(enti.getX(), enti.getY()));
                }
            }

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
        for (Tank player: players) {
            out = Stream.concat(out.stream(), player.getBullets().stream()).collect(Collectors.toList());
        }
        return out;
    }

    private void cleanupBullet() {
        for (Tank player: players) {
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
