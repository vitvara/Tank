package enitity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import utills.HitBlock;

public class Rock extends Obstruction{
    public Rock(int x, int y) {
        super(x,y);
    }

    @Override
    public BufferedImage getImage() {
        try {
            return ImageIO.read(new File("image/Rock.png"));
        } catch (Exception e) {
            System.out.println("Rock File not found");
        }
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public int getHeight() {
        return HitBlock.ROCK.getHeight();
    }

    @Override
    public int getWidth() {
        return HitBlock.ROCK.getWidth();
    }

}
