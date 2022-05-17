package enitity;

import jdk.jshell.spi.ExecutionControl;

import java.awt.image.BufferedImage;

public abstract class Entity {
    private int x;
    private int y;
    private boolean hitState = false;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {this.x = x;}

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void hit() {hitState = true;}

    public abstract BufferedImage getImage();

    public boolean getHitState() {
        return hitState;
    }

    public abstract void update();

    public abstract int getHeight();

    public abstract int getWidth();
}
