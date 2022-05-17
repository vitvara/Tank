package utills;

public enum Direction {
        LEFT(-1,0),
        RIGHT(1,0),
        UP(0,-1),
        DOWN(0,1);

        private int x;
        private int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX(){
            return this.x;
        }

        public Direction getReverse() {
            switch (this) {
                case LEFT:
                    return Direction.RIGHT;
                case RIGHT:
                    return Direction.LEFT;
                case UP:
                    return Direction.DOWN;
                case DOWN:
                    return Direction.UP;
            }
            return Direction.UP;
        }

        public int getY() {
            return this.y;
        }
    }

