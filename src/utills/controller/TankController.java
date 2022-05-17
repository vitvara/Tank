package utills.controller;

import enitity.Tank;
import utills.Direction;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankController extends KeyAdapter {
    private Tank tank;
    private int up;
    private int down;
    private int left;
    private int right;
    private int shoot;
    private int currentPress;
    private JPanel panel;
    public TankController(JPanel panel, Tank tank, int up, int down, int right, int left, int shoot) {
        this.tank = tank;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == this.up) {
            tank.move();
            tank.setDirection(Direction.UP);
            currentPress = keyCode;
        }
        else if (keyCode == this.down) {
            tank.move();
            tank.setDirection(Direction.DOWN);
            currentPress = keyCode;
        }
        else if (keyCode == this.left) {
            tank.move();
            tank.setDirection(Direction.RIGHT);
            currentPress = keyCode;
        }
        else if (keyCode == this.right) {
            tank.move();
            tank.setDirection(Direction.LEFT);
            currentPress = keyCode;
        }

        else if (keyCode == this.shoot) {
            tank.fireBullet();
        }
        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if ((keyCode == this.up && currentPress == keyCode)
                || (keyCode == this.down && currentPress == keyCode)
                || (keyCode == this.left && currentPress == keyCode)
                || (keyCode) == this.right && currentPress == keyCode) {
            tank.stop();
        }
    }



}
