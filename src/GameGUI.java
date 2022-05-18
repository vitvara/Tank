import enitity.Entity;
import enitity.Tank;
import game.MainGame;
import utills.MenuAPI.GameMode;
import utills.MenuAPI.MainMenuAPI;
import utills.MenuAPI.MenuState;
import utills.controller.TankController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class GameGUI extends JPanel implements ActionListener{
    private MainGame maingame;
    private int width;
    private int height;
    private final Image baseTile = new ImageIcon(getClass().getResource("res/TileFloor.png")).getImage();
    private final Image oceanTile = new ImageIcon(getClass().getResource("res/ocean.png")).getImage();
    private final Image treeTile = new ImageIcon(getClass().getResource("res/TreeTile.png")).getImage();
    final MainMenuAPI obserable = new MainMenuAPI();
    private JButton resumeBtn = new JButton("Resume");
    private JButton exitBtn = new JButton("Exit To Menu");
    private int cellSize = 64;
    private List<Map.Entry<Integer, Integer>> treePosition = new ArrayList<>();
    private GameMode mode;

    public GameGUI(MainGame game, GUI gui, GameMode mode) {
        this.mode = mode;
        setLayout(null);
        obserable.addObserver(gui);
        this.maingame = game;
        width = maingame.getWidth() * cellSize;
        height = (maingame.getHeight()+ 2)*cellSize;
        initPauseMenuBtn();
        addKeyListener(new TankController(this, maingame.getPlayers().get(0), KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE));
        if (mode == GameMode.ONE_ON_ONE) {
            addKeyListener(new TankController(this, maingame.getPlayers().get(1), KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_CONTROL));
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    resumeBtn.setVisible(true);
                    exitBtn.setVisible(true);
                    maingame.pause();
                }
            }
        });
    }

    public void initPauseMenuBtn() {
        resumeBtn.setBounds(width/2-width/4,height/4+(height/20)*1,width/2,40);
        exitBtn.setBounds(width/2-width/4,height/4+(height/20)*2,width/2,40);
        resumeBtn.setVisible(false);
        exitBtn.setVisible(false);
        resumeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeBtn.setVisible(false);
                exitBtn.setVisible(false);
                maingame.resume();
                requestFocus();
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<MenuState, Object> out = new HashMap<>();
                out.put(MenuState.IN_MENU, null);
                obserable.sendData(out);
            }
        });
        add(resumeBtn);
        add(exitBtn);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (maingame.isEnd()) {
            g.drawString(maingame.getWinner(), width/2-width/4,height/4+(height/20)*1);
            exitBtn.setVisible(true);
            return;
        }
        for (int row=0; row<maingame.getHeight(); row++) {
            for (int col=0; col<maingame.getWidth(); col++) {
                paintTile(g, maingame.getMap().get(row).get(col), row, col);
            }
        }
        for (Tank tank: maingame.getPlayers()) {
            g.drawString("P " + (maingame.getPlayers().indexOf(tank)+1), tank.getX(), tank.getY());
        }
        if (!maingame.getAllEntity().isEmpty()) {
            for (Entity ele: maingame.getAllEntity()) {
                g.drawImage(ele.getImage(), ele.getX(), ele.getY(), null,null);
            }

        }

        for (Map.Entry<Integer, Integer> entry: treePosition) {
            g.drawImage(treeTile, entry.getKey(), entry.getValue(), cellSize, cellSize, null);
        }

        g.drawString(String.valueOf(maingame.getPlayers().get(0).getCount()), width/20, height - height/20);
        g.drawRect(width/20, height - height/20, maingame.getPlayers().get(0).getCount()*width/280, height/80);
        if (mode == GameMode.ONE_ON_ONE) {
            g.drawString(String.valueOf(maingame.getPlayers().get(1).getCount()), width - width/20, height - height/20);
            g.drawRect(width - maingame.getPlayers().get(1).getCount()*width/280 - width/20, height - height/20, maingame.getPlayers().get(1).getCount()*width/280, height/80); // hitbox

        }

        repaint();
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
            treePosition.add(new AbstractMap.SimpleEntry<>(x,y));
            g.drawImage(baseTile, x, y, cellSize, cellSize, null);
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
