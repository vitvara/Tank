import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuGUI extends JPanel{
    private JButton startButton = new JButton("Start");
    private JButton settingButton = new JButton("Setting");
    private JButton exitButton = new JButton("Exit");
    private int menuSize = 1000;

    public MenuGUI() {
        initButton();
    }

    public void initButton() {
        add(startButton);
        add(settingButton);
        add(exitButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMap();
                startButton.setVisible(false);
                settingButton.setVisible(false);
                exitButton.setVisible(false);
                repaint();
            }
        });
    }

    public void selectMap() {
        File files = new File("map");
        int count = 0;
        for (File fileEntry: files.listFiles()){
            JButton newButton = new JButton(fileEntry.getName());
            newButton.setBounds(menuSize/2-menuSize/8,menuSize/4+(menuSize/20)*count,menuSize/4,40);
            newButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    repaint();
                }
            });
            add(newButton);
            repaint();
            count++;
        }


    }
}
