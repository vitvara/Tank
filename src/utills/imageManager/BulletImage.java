package utills.imageManager;
import utills.Direction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public enum BulletImage {
    lightBulletUp(BulletImage.class.getClassLoader().getResourceAsStream("res/LightBulletUp.png")),
    lightBulletDown(BulletImage.class.getClassLoader().getResourceAsStream("res/LightBulletDown.png")),
    lightBulletRight(BulletImage.class.getClassLoader().getResourceAsStream("res/LightBulletRight.png")),
    lightBulletLeft(BulletImage.class.getClassLoader().getResourceAsStream("res/LightBulletLeft.png"))
    ;

    private BufferedImage lightBulletImage;


    BulletImage(InputStream filename)  {
        try {
            lightBulletImage = ImageIO.read(filename);
        } catch (Exception e) {
            System.out.println(filename + " File not found");
        }
    }


    public static BufferedImage getImage(Direction direction) {
        if (direction == Direction.UP)
        {
            return  lightBulletUp.lightBulletImage;
        }
        else if (direction == Direction.DOWN)
        {
            return  lightBulletDown.lightBulletImage;
        }
        else if (direction == Direction.LEFT)
        {
            return lightBulletLeft.lightBulletImage;
        }
        else if (direction == Direction.RIGHT)
        {
            return lightBulletRight.lightBulletImage;
        }
        return null;
    }
}
