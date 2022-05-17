package enitity;
import jdk.jshell.spi.ExecutionControl;
import utills.imageManager.ExplosionImage;
import java.awt.image.BufferedImage;
import utills.HitBlock;

public class Explosion extends Entity {
    private int current = 0;
    private boolean done = false;
    public Explosion(int x, int y) {
        super(x,y);
    }
    @Override
    public void update() {
        if (!done){
            current += 1;
            if (current == 7) {
                done = true;
                current = 0;
            }
        }
    }

    @Override
    public int getHeight() {
        return HitBlock.EXPLOSION.getHeight();
    }

    @Override
    public int getWidth() {
        return HitBlock.EXPLOSION.getWidth();
    }

    @Override
    public BufferedImage getImage() {
        return ExplosionImage.getImage(current);
    }

    public boolean getDone() {
        return done;
    }

    public void refreshState(int x, int y) {
        setX(x);
        setY(y);
        done = false;
    }
}
