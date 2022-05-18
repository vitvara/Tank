package utills.imageManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public enum SteelImage {
    steelImage(RockImage .class.getClassLoader().getResourceAsStream("res/Steel.png"));
    private BufferedImage rockImages;

    SteelImage(InputStream filename) {
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
