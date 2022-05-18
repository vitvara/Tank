import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.*;

import utills.MenuAPI.GameMode;
import utills.MenuAPI.MainMenuAPI;
import utills.MenuAPI.MenuState;

import java.util.List;

public class MenuGUI extends JPanel{
    private BufferedImage logo;
    private List<JButton> configBtn = new ArrayList<>();
    private JButton startButton = new JButton("Start");
    private JButton settingButton = new JButton("Setting");
    private JButton exitButton = new JButton("Exit");
    private boolean configMenu = false;
    private boolean selectedMapError = false;
    private Map<String, String> configDis;
    private Map<String, Object> config;
    final MainMenuAPI obserable = new MainMenuAPI();
    private int state = 0; // 0 is in menu, 1 is ready to play.

    private int menuSize = 1000;

    public MenuGUI(GUI gui) {
        try {
            System.out.println(getClass().getResourceAsStream("res/logo.png"));
            logo = ImageIO.read(getClass().getResourceAsStream("res/logo.png"));
        } catch (Exception e) {
            System.out.println("Can't find logo image");
        }
        obserable.addObserver(gui);
        config = new HashMap<>();
        configDis = new HashMap<>();
        initButton();
        setLayout(null);
        initConfig();
    }

    public void initConfig() {
        configDis.put("map", "Please Choose Map");
        configDis.put("mode", "Please Choose Mode");
    }

    public void initButton() {
        startButton.setBounds(menuSize/2-menuSize/4,menuSize/4+(menuSize/20)*1,menuSize/2,40);
        exitButton.setBounds(menuSize/2-menuSize/4,menuSize/4+(menuSize/20)*2,menuSize/2,40);
        add(startButton);
        add(exitButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configMenu = true;
                startButton.setVisible(false);
                settingButton.setVisible(false);
                exitButton.setVisible(false);
                selectMap();
                repaint();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (configMenu) {
            int count = 0;
            for (Map.Entry<String, String> entry: configDis.entrySet()) {
                g.drawString(entry.getKey() + ": "+ entry.getValue(),menuSize/2+menuSize/10, menuSize/4+(menuSize/20)*count);
                count ++;
            }
            if (selectedMapError) {
                g.setColor(Color.red);
                g.drawString("Please select an options",menuSize/2+menuSize/10, menuSize/4+(menuSize/20)*count);
            }
        } else {
            g.drawImage(logo, 0,0, null);
        }
    }

    public void removeConfigBtn() {
        for (JButton btn: configBtn) {
            btn.setVisible(false);
            remove(btn);
        }
        configMenu = false;
    }

    public void recoverMainMenu() {
        startButton.setVisible(true);
        settingButton.setVisible(true);
        exitButton.setVisible(true);
        repaint();
    }

    public void selectMap() {
        File files = new File("map");
        int count = 0;
        for (File fileEntry: files.listFiles()){
            JButton newButton = new JButton(fileEntry.getName());
            newButton.setBounds(menuSize/2-menuSize/3,menuSize/4+(menuSize/20)*count,menuSize/4,40);
            newButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    configDis.put("map", fileEntry.getName());
                    config.put("map", fileEntry.getPath());
                    repaint();
                }
            });
            configBtn.add(newButton);
            add(newButton);
            count++;
        }
        JButton newButton = new JButton("Start");
        JButton backButton = new JButton("Back");
        JButton multiplayer = new JButton("One-on-One");
        JButton singleplayer = new JButton("Fight w/ AI");
        multiplayer.setBounds(menuSize/2+menuSize/10,menuSize/4+(menuSize/20)*(configDis.size()+1),menuSize/8,40);
        singleplayer.setBounds(menuSize/2+menuSize/10+ menuSize/8,menuSize/4+(menuSize/20)*(configDis.size()+1),menuSize/8,40);
        newButton.setBounds(menuSize/2+menuSize/10,menuSize/4+(menuSize/20)*(configDis.size()+2),menuSize/4,40);
        backButton.setBounds(menuSize/2+menuSize/10,menuSize/4+(menuSize/20)*(configDis.size()+3),menuSize/4,40);
        newButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (config.get("mode") == null || config.get("map") == null) {
                    selectedMapError = true;
                    repaint();
                    return;
                }
                Map<MenuState, Object> out = new HashMap<>();
                out.put(MenuState.READY_PLAY, config);
                state = 1;
                removeConfigBtn();
                recoverMainMenu();
                obserable.ready();
                obserable.sendData(out);
                repaint();
            }
        });
        multiplayer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                configDis.put("mode", GameMode.ONE_ON_ONE.getDisplayString());
                config.put("mode", GameMode.ONE_ON_ONE);
                repaint();
            }
        });
        singleplayer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                configDis.put("mode", GameMode.AI.getDisplayString());
                config.put("mode", GameMode.AI);
                repaint();
            }
        });
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                removeConfigBtn();
                recoverMainMenu();
                selectedMapError = false;
                repaint();
            }
        });
        configBtn.add(newButton);
        configBtn.add(multiplayer);
        configBtn.add(singleplayer);
        configBtn.add(backButton);
        add(newButton);
        add(backButton);
        add(multiplayer);
        add(singleplayer);
        repaint();


    }

    public int getState() {
        return state;
    }

}
