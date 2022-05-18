package utills.imageManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public enum ExplosionImage {
    A(ExplosionImage.class.getClassLoader().getResourceAsStream("res/Effect/Explosion_A.png")),
    B(ExplosionImage.class.getClassLoader().getResourceAsStream("res/Effect/Explosion_B.png")),
    C(ExplosionImage.class.getClassLoader().getResourceAsStream("res/Effect/Explosion_C.png")),
    D(ExplosionImage.class.getClassLoader().getResourceAsStream("res/Effect/Explosion_D.png")),
    E(ExplosionImage.class.getClassLoader().getResourceAsStream("res/Effect/Explosion_E.png")),
    F(ExplosionImage.class.getClassLoader().getResourceAsStream("res/Effect/Explosion_F.png")),
    G(ExplosionImage.class.getClassLoader().getResourceAsStream("res/Effect/Explosion_G.png")),
    H(ExplosionImage.class.getClassLoader().getResourceAsStream("res/Effect/Explosion_H.png"))
    ;

    private BufferedImage explodeImage;

    ExplosionImage(InputStream filename) {
        try {
            explodeImage = ImageIO.read(filename);
        } catch (Exception e) {
            System.out.println(filename + " File not found");
        }
    }

    public static BufferedImage getImage(int state) {
        switch (state) {
            case 0:
                return A.explodeImage;
            case 1:
                return B.explodeImage;
            case 2:
                return C.explodeImage;
            case 3:
                return D.explodeImage;
            case 4:
                return E.explodeImage;
            case 5:
                return F.explodeImage;
            case 6:
                return G.explodeImage;
            case 7:
                return H.explodeImage;
        };
        return null;
    }

}
