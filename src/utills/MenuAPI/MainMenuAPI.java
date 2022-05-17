package utills.MenuAPI;

import java.awt.*;
import java.util.Observable;

public class MainMenuAPI extends Observable {
    private MenuState state = MenuState.IN_MENU;
    public MainMenuAPI() {
        super();
    }

    public void ready() {
        state = MenuState.READY_PLAY;
    }

    public MenuState getState() {
        return state;
    }

    public void sendData(Object data) {
        setChanged(); // the two methods of Observable class
        notifyObservers(data);
    }
}
