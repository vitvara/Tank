package utills.pool;

import enitity.Bullet;
import enitity.Tank;
import utills.Direction;

import java.util.ArrayList;
import java.util.List;

public class BulletPool {
    private List<Bullet> bullets = new ArrayList<Bullet>();

    public BulletPool() {
        int size = 30;
        for (int i=0; i<size; i++) {
            bullets.add(new Bullet(-999,-999, Direction.UP, null));
        }
    }

    public Bullet requestBullet(int x, int y, Direction direction, Tank host) {
        Bullet bullet = bullets.remove(0);
        bullet.refreshState(x,y, direction, host);
        bullet.resetHit();
        return  bullet;
    }

    public void recieveBullet(Bullet bullet) {
        bullets.add(bullet);
    }

}
