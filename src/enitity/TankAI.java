package enitity;

import utills.Direction;

import java.util.concurrent.ThreadLocalRandom;

public class TankAI extends Tank{
    private boolean changeDirection = false;

    public TankAI(int x, int y, Direction direction, int width, int height) {
        super(x, y, direction, width, height);
        System.out.println(x + " " + y);
        Thread counter = new Thread() {
            @Override
            public void run() {
                int count=0;
                while(true) {

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count += 1;
                    if (count == 100) {
                        changeDirection = true;
                        count = 0;
                    }
                }

            }
        };
        counter.start();
    }

    public void moveRight() {
        setDirection(Direction.RIGHT);
    }
    public void moveLeft() {
        setDirection(Direction.LEFT);
    }
    public void moveUp() {
        setDirection(Direction.UP);
    }
    public void moveDown() {
        setDirection(Direction.DOWN);
    }

    @Override
    public void update() {
        super.update();
        move();
        if (changeDirection) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
            switch (randomNum) {
                case 1:
                    moveRight();
                    break;
                case 2:
                    moveLeft();
                    break;
                case 3:
                    moveUp();
                    break;
                case 4:
                    moveDown();
                    break;
            }
            changeDirection = false;
            fireBullet();
        }
        }

}
