import game.MainGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class GUI extends JFrame implements Observer{
    public static final int cellSize = 64;
    private MainGame maingame;
    private List<JPanel> observers;
    private JPanel currentPanel;
    private int menuSize = 1000;

    public GUI() {
        setPreferredSize(new Dimension(menuSize, menuSize));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        add(new MenuGUI());
        pack();
    }



    public void start() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    public static void main(String[] args) {
        GUI GUI = new GUI();
        GUI.start();
    }




}
