package utills.imageManager;

import utills.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum TankImage {
    tankUp("image/TankUp.png"),
    tankDown("image/TankDown.png"),
    tankLeft("image/TankLeft.png"),
    tankRight("image/TankRight.png"),
    ;
    private BufferedImage tankImage;
    TankImage(String filename) {
        try {
            tankImage = ImageIO.read(new File(filename));
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
