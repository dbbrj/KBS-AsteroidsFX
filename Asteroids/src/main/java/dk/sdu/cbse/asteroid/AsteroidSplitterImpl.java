package dk.sdu.cbse.asteroid;

import java.util.Random;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;

public class AsteroidSplitterImpl implements IAsteroidSplitter {
    private final Random rnd = new Random();

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        float newSize = e.getRadius() / 2;

        // if to small, dont split just destroy
        if (newSize < 3) {
            return;
        }

        // Create 2 smaller asteroids
        for (int i = 0; i < 2; i++) {
            Entity splitAsteroid = new Asteroid();
            splitAsteroid.setPolygonCoordinates(newSize, -newSize, -newSize, -newSize, -newSize, newSize, newSize, newSize);
            splitAsteroid.setX(e.getX() + rnd.nextInt(10) - 5); // Slightly offset from original
            splitAsteroid.setY(e.getY() + rnd.nextInt(10) - 5);
            splitAsteroid.setRadius(newSize);
            splitAsteroid.setRotation(rnd.nextInt(360));
            splitAsteroid.setHealth(1); // 1 hit to split/destroy
            world.addEntity(splitAsteroid);
        }
    }   
}
