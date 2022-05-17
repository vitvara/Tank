package utills.imageManager;

import enitity.Explosion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ExplosionImage {
    A("image/Effect/Explosion_A.png"),
    B("image/Effect/Explosion_B.png"),
    C("image/Effect/Explosion_C.png"),
    D("image/Effect/Explosion_D.png"),
    E("image/Effect/Explosion_E.png"),
    F("image/Effect/Explosion_F.png"),
    G("image/Effect/Explosion_G.png"),
    H("image/Effect/Explosion_H.png")
    ;

    private BufferedImage explodeImage;

    ExplosionImage(String filename) {
        try {
            explodeImage = ImageIO.read(new File(filename));
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
