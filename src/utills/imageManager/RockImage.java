package utills.imageManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public enum RockImage {
    rockImage(RockImage .class.getClassLoader().getResourceAsStream("res/Rock.png"));
    private BufferedImage rockImages;

    RockImage(InputStream filename) {
        try {
            rockImages = ImageIO.read(filename);
        } catch (Exception e) {
            System.out.println(filename + " File not found");
        }
    }

    public BufferedImage getImage() {
        return rockImages;
    }
}
