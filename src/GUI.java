import game.MainGame;
import utills.MenuAPI.GameMode;
import utills.MenuAPI.MenuState;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GUI extends JFrame implements Observer{
    public static final int cellSize = 64;
    private MainGame maingame;
    private List<JPanel> observers;
    private JPanel currentPanel;
    private int menuSize = 1000;
    private MenuGUI menuPanel;
    private Map<String, Object> config;
    private GameGUI game;

    public GUI() {
        setPreferredSize(new Dimension(menuSize, menuSize));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        menuPanel = new MenuGUI(this);
        add(menuPanel);
        pack();
    }

    public void initGame() {
        MainGame mainGame = new MainGame((String) config.get("map"), cellSize, (GameMode) config.get("mode"));
        setPreferredSize(new Dimension((mainGame.getWidth())*64, (mainGame.getHeight()+4)*64));
        game = new GameGUI(mainGame, this, (GameMode) config.get("mode"));
        add(game);
        game.requestFocus();
        pack();
    }

    public void start() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    public static void main(String[] args) {
        GUI GUI = new GUI();
        GUI.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        Map<MenuState, Object> parsel = (Map<MenuState, Object>) arg;
        if (parsel.keySet().toArray()[0] == MenuState.READY_PLAY) {
            config = (Map<String, Object>) parsel.get(MenuState.READY_PLAY);
            menuPanel.setVisible(false);
            initGame();
        }
        if (parsel.keySet().toArray()[0] == MenuState.IN_MENU) {
            menuPanel.setVisible(true);
            setPreferredSize(new Dimension(menuSize, menuSize));
            remove(game);
            pack();
            repaint();
        }

    }
}
