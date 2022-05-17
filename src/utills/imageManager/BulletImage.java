package utills.imageManager;

import enitity.Bullet;
import utills.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum BulletImage {
    lightBulletUp("image/LightBulletUp.png"),
    lightBulletDown("image/LightBulletDown.png"),
    lightBulletRight("image/LightBulletRight.png"),
    lightBulletLeft("image/LightBulletLeft.png")
    ;
    private String filename;

    private BufferedImage lightBulletImage;


    BulletImage(String filename)  {
        try {
            lightBulletImage = ImageIO.read(new File(filename));
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
