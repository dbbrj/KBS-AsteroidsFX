package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        float newSize = e.getRadius() / 2;

        System.out.println("Split called! Radius: " + e.getRadius() + ", New Size: " + newSize);
        
        // if to small, dont split just destroy
        if (newSize < 2) {
            System.out.println("Asteroid too small to split, destroying.");
            return;
        }

        // Two asteroids will split at 45 degree angles from the original rotation
        double rotation1 = e.getRotation() + 45;
        double rotation2 = e.getRotation() - 45;

        // Create 2 smaller asteroids
        for (int i = 0; i < 2; i++) {
            double rotation = (i == 0) ? rotation1 : rotation2;

                        // Calculate offset based on rotation so they fly apart
            double offsetX = Math.cos(Math.toRadians(rotation)) * newSize;
            double offsetY = Math.sin(Math.toRadians(rotation)) * newSize;

            Entity splitAsteroid = new Asteroid();
            float displaySize = newSize * 2; // make polygon bigger than the radius
            splitAsteroid.setPolygonCoordinates(displaySize, -displaySize, -displaySize, -displaySize, -displaySize, displaySize, displaySize, displaySize);
            splitAsteroid.setX(e.getX() + offsetX); // Slightly offset from original
            splitAsteroid.setY(e.getY() + offsetY);
            splitAsteroid.setRadius(newSize);
            splitAsteroid.setRotation(rotation);
            splitAsteroid.setHealth(1); // 1 hit to split/destroy
            world.addEntity(splitAsteroid);
        }
    }   
}
