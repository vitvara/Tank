package enitity;

import java.awt.image.BufferedImage;

public class Obstruction extends Entity{

    public Obstruction(int x, int y) {
        super(x, y);
    }

    @Override
    public BufferedImage getImage() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }
}
