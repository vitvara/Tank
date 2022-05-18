package enitity;

import utills.HitBlock;
import utills.imageManager.RockImage;
import utills.imageManager.SteelImage;

import java.awt.image.BufferedImage;

public class Steel extends Obstruction {
    public Steel(int x, int y) {
        super(x,y);
    }

    @Override
    public BufferedImage getImage() {
        return SteelImage.steelImage.getImage();
    }

    @Override
    public void hit() {

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
