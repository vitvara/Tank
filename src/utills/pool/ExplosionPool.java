package utills.pool;

import enitity.Explosion;

import java.util.ArrayList;
import java.util.List;

public class ExplosionPool {
    private List<Explosion> explosions = new ArrayList<Explosion>();

    public ExplosionPool() {
        int size = 10;
        for (int i=0; i<size; i++) {
            explosions.add(new Explosion(-999,-999));
        }
    }

    public Explosion requestExplosion(int x, int y) {
        Explosion explosion = explosions.remove(0);
        explosion.refreshState(x,y);
        return  explosion;
    }

    public void recieveExplosion(Explosion explosion) {
        explosions.add(explosion);
    }

}
