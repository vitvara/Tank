import com.sun.tools.javac.Main;
import enitity.Entity;
import game.MainGame;
import utills.controller.TankController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameGUI extends JPanel implements ActionListener{
    private MainGame maingame;
    private int width;
    private int height;
    private final Image baseTile = new ImageIcon("image/TileFloor.png").getImage();
    private final Image oceanTile = new ImageIcon("image/ocean.png").getImage();
    private final Image treeTile = new ImageIcon("image/TreeTile.png").getImage();
    private final Image rock = new ImageIcon("image/Rock.png").getImage();
    private int cellSize;

    public GameGUI(MainGame game, int cellSize) {
        this.maingame = game;
        this.cellSize = cellSize;
        width = maingame.getWidth() * cellSize;
        height = (maingame.getHeight()+ 2)*cellSize;

        addKeyListener(new TankController(this, maingame.getPlayers().get(1), KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL));
        addKeyListener(new TankController(this, maingame.getPlayers().get(0), KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE));
    }

    @Override
    public void paint(Graphics g) {
        for (int row=0; row<maingame.getHeight(); row++) {
            for (int col=0; col<maingame.getWidth(); col++) {
                paintTile(g, maingame.getMap().get(row).get(col), row, col);
            }
        }
        if (!maingame.getAllEntity().isEmpty()) {
            for (Entity ele: maingame.getAllEntity()) {
                g.drawRect(ele.getX(), ele.getY(), ele.getImage().getWidth(), ele.getImage().getHeight());
                g.drawImage(ele.getImage(), ele.getX(), ele.getY(), null,null);
            }

        }

        g.drawRect(width/20, height - height/20, width-width/280, height-height/20);
        g.drawString(String.valueOf(maingame.getPlayers().get(0).getCount()), width/20, height - height/20);
        g.drawRect(width/20, height - height/20, maingame.getPlayers().get(0).getCount()*width/280, height/80);

        g.drawString(String.valueOf(maingame.getPlayers().get(1).getCount()), width - width/20, height - height/20);
        g.drawRect(width - maingame.getPlayers().get(1).getCount()*width/280 - width/20, height - height/20, maingame.getPlayers().get(1).getCount()*width/280, height/80); // hitbox
    }

    public void paintTile(Graphics g, int id, int row, int col) {
        int x = col * cellSize;
        int y = row * cellSize;
        if (id == 0) {
            g.drawImage(baseTile, x, y, cellSize, cellSize, null);
        }
        else if (id == 1) {
            g.drawImage(oceanTile, x, y, cellSize, cellSize, null);
        }
        else if (id == 2) {
            g.drawImage(treeTile, x, y, cellSize, cellSize, null);
        }
        else if (id == 3) {
            g.drawImage(baseTile, x, y, cellSize, cellSize, null);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
