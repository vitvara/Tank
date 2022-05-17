package enitity;

import jdk.jshell.spi.ExecutionControl;
import utills.Direction;
import utills.HitBlock;
import utills.imageManager.BulletImage;

import java.awt.image.BufferedImage;

public class Bullet extends Entity {
    private int dx;
    private int dy;
    private Direction direction;
    private int speed = 10;
    private boolean isHit = false;
    private Entity host;

    public Bullet(int x, int y, Direction direction, Tank host) {
        super(x,y);
        this.direction = direction;
        setDirection();
        this.host = host;
    }
    @Override
    public void update() {
        setX(getX() + dx * speed);
        setY(getY() +dy * speed);
    }

    @Override
    public int getHeight() {
        return HitBlock.LIGHT_BULLET.getHeight();
    }

    @Override
    public int getWidth() {
        return HitBlock.LIGHT_BULLET.getWidth();
    }

    public void setDirection() {
        this.dx = direction.getX();
        this.dy = direction.getY();
    }
    @Override
    public BufferedImage getImage() {
        return BulletImage.getImage(direction);
    }

    public void refreshState(int x, int y, Direction direction, Tank host) {
        setX(x);
        setY(y);
        this.direction = direction;
        setDirection();
        this.host = host;
    }

    public boolean isHit(Entity other) {
        if (host != other) {
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                if (other.getX() - other.getWidth() < getX() - getHeight() &&
                        other.getX() + other.getWidth() > getX() + getHeight() &&
                        other.getY() - other.getHeight()  <  getY() - getWidth() &&
                        other.getY() + other.getHeight() > getY() + getWidth())
                {
                    other.hit();
                    hit();
                    return true;
                }
            } else {
                if (other.getX() - other.getHeight() < getX() - getWidth() &&
                        other.getX() + other.getHeight() > getX() + getWidth() &&
                        other.getY() -  other.getWidth() <  getY() - getHeight() &&
                        other.getY() + other.getWidth() > getY() + getHeight())
                {
                    other.hit();
                    hit();
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isHit() {
        return isHit;
    }

    public void hit() {isHit = true;}

    public void resetHit() {
        isHit = false;
    }
}
