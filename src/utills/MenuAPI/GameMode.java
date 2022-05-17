package utills.MenuAPI;

public enum GameMode {
    ONE_ON_ONE("one-on-one"),
    AI("fight AI");

    private String displayString;

    GameMode(String str) {
        displayString = str;
    }

    public String getDisplayString() {
        return displayString;
    }
}
