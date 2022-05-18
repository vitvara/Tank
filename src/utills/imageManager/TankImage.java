package utills.imageManager;

import utills.Direction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public enum TankImage {
    tankUp(TankImage.class.getClassLoader().getResourceAsStream("res/TankUp.png")),
    tankDown(TankImage.class.getClassLoader().getResourceAsStream("res/TankDown.png")),
    tankLeft(TankImage.class.getClassLoader().getResourceAsStream("res/TankLeft.png")),
    tankRight(TankImage.class.getClassLoader().getResourceAsStream("res/TankRight.png")),
    ;
    private BufferedImage tankImage;
    TankImage(InputStream filename) {
        try {
            tankImage = ImageIO.read(filename);
        } catch (Exception e) {
            System.out.println(filename + " File not found");
        }

    }
    public static BufferedImage getImage(Direction direction) {
        if (direction == Direction.UP)
        {
            return  tankUp.tankImage;
        }
        else if (direction == Direction.DOWN)
        {
            return  tankDown.tankImage;
        }
        else if (direction == Direction.LEFT)
        {
            return tankLeft.tankImage;
        }
        else if (direction == Direction.RIGHT)
        {
            return tankRight.tankImage;
        }
        return null;
    }
}
