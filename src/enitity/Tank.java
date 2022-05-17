package enitity;

import org.w3c.dom.css.Rect;
import utills.HitBlock;
import utills.Direction;
import utills.imageManager.TankImage;
import utills.pool.BulletPool;
import utills.HitBlock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Tank extends Entity{
    private Direction facing;
    private boolean canFire = true;
    private  int count = 10;
    private boolean isMove = false;
    private BulletPool pool;
    private List<Bullet> bullets;
    private int speed = 1;

    public Tank(int x, int y, Direction direction) {
        super(x, y);
        this.pool = new BulletPool();
        this.bullets = new ArrayList<Bullet>();
        this.setDirection(direction);
    }

    @Override
    public void update() {
        if (!isMove) {
            return;
        }
        setX(getX() + facing.getX()*speed);
        setY(getY() + facing.getY()*speed);
    }

    @Override
    public int getHeight() {
        return HitBlock.TANK.getHeight();
    }

    @Override
    public int getWidth() {
        return HitBlock.TANK.getWidth();
    }

    @Override
    public BufferedImage getImage() {
        return TankImage.getImage(facing);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public int getCount() {
        return count;
    }

    public void move() {
        this.isMove = true;
    }

    public void stop(){
        this.isMove = false;
    }

    public void canGo(Entity obs) {
        Rectangle r1 = new Rectangle(obs.getX()-15, obs.getY(), obs.getWidth()+15,obs.getHeight());
        Rectangle r2 = new Rectangle(
                getX()+facing.getX()*speed,
                getY()+facing.getY()*speed,
                getWidth(),
                getHeight()
        );
        if (r2.intersects(r1)) {
            setX((int) (getX() - facing.getX()*speed*2));
            setY((int) (getY() - facing.getY()*speed*2));
            if (obs instanceof Tank) {
                if (((Tank) obs).getFacing() == Direction.LEFT) {
                    ((Tank) obs).setX(((Tank) obs).getX() - ((Tank) obs).getFacing().getX()*5);
                    ((Tank) obs).stop();
                }

            }
            stop();
        }
    }

    public void fireBullet() {

        if (canFire) {
            canFire = false;
            addBullet(
                    pool.requestBullet(
                            getX(),
                            getY(),
                            getFacing(),
                            this
                    )
            );
            Thread counter = new Thread() {
                @Override
                public void run() {
                   count=0;
                    while(count < 10) {

                        try {
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        count += 1;
                    }
                    canFire = true;
                }
            };
            counter.start();
        }

    }


    public BulletPool getPool() {
        return pool;
    }

    public void setDirection(Direction direction) {
        facing = direction;

    }


    public Direction getFacing() {
        return facing;
    }
}
