package dk.sdu.cbse.collisionsystem;

import java.util.ServiceLoader;
import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                if (!collides(entity1, entity2)) {
                    continue;
                }

                // Bullet hits asteroid
                if (entity1 instanceof Bullet && entity2 instanceof Asteroid) {
                    world.removeEntity(entity1); // remove bullet
                    entity2.setHealth(entity2.getHealth() - 1);
                    if (entity2.getHealth() <= 0) {
                        world.removeEntity(entity2);
                        // split the asteroid
                        getAsteroidSplitter().createSplitAsteroid(entity2, world);
                    }
                }

                // Asteroid hits bullet (reverse)
                else if (entity2 instanceof Bullet && entity1 instanceof Asteroid) {
                    world.removeEntity(entity2);
                    entity1.setHealth(entity1.getHealth() - 1);
                    if (entity1.getHealth() <= 0) {
                        world.removeEntity(entity1);
                        getAsteroidSplitter().createSplitAsteroid(entity1, world);
                    }
                }

                // Anything else colliding (ship vs asteroid, bullet vs ship)
                else if (!(entity1 instanceof Bullet) && !(entity2 instanceof Bullet)) {
                    // ship vs asteroid — destroy both
                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
                } else {
                    // bullet vs ship — reduce ship health
                    Entity bullet = entity1 instanceof Bullet ? entity1 : entity2;
                    Entity ship = entity1 instanceof Bullet ? entity2 : entity1;
                    world.removeEntity(bullet);
                    ship.setHealth(ship.getHealth() - 1);
                    if (ship.getHealth() <= 0) {
                        world.removeEntity(ship);
                    }
                }
            }
        }
    }

    private IAsteroidSplitter getAsteroidSplitter() {
        return ServiceLoader.load(IAsteroidSplitter.class)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No IAsteroidSplitter found"));
    }

    public boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }
}