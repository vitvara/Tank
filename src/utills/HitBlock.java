package utills;

public enum HitBlock {
    LIGHT_BULLET(12, 24),
    TANK(49,60),
    ROCK(50, 50),
    EXPLOSION(64,64);

    private int width;
    private int height;

    HitBlock(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
