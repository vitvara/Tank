package enitity;
import utills.imageManager.RockImage;
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
        return RockImage.rockImage.getImage();
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
