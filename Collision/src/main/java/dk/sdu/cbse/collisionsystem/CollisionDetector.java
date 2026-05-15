package dk.sdu.cbse.collisionsystem;

import java.util.ServiceLoader;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.EntityType;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class CollisionDetector implements IPostEntityProcessingService {

    private static final String SCORING_URL = "http://localhost:8080/score/";

    @Override
    public void process(GameData gameData, World world) {
        outer:
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                if (entity1.getID().equals(entity2.getID())) continue;
                if (entity1.getClass().equals(entity2.getClass())) continue;
                if (!collides(entity1, entity2)) continue;
                if (world.getEntity(entity1.getID()) == null) continue;
                if (world.getEntity(entity2.getID()) == null) continue;

                // Bullet hits asteroid
                if (entity1 instanceof Bullet && entity2 instanceof Asteroid) {
                    world.removeEntity(entity1);
                    entity2.setHealth(entity2.getHealth() - 1);
                    if (entity2.getHealth() <= 0) {
                        world.removeEntity(entity2);
                        gameData.addAsteroidDestroyed();
                        postScore("asteroid");
                        getAsteroidSplitter().createSplitAsteroid(entity2, world);
                    }
                }

                // Asteroid hits bullet (reverse)
                else if (entity2 instanceof Bullet && entity1 instanceof Asteroid) {
                    world.removeEntity(entity2);
                    entity1.setHealth(entity1.getHealth() - 1);
                    if (entity1.getHealth() <= 0) {
                        world.removeEntity(entity1);
                        gameData.addAsteroidDestroyed();
                        postScore("asteroid");
                        getAsteroidSplitter().createSplitAsteroid(entity1, world);
                    }
                }

                // Non-bullet collision (ship vs asteroid — destroy both)
                else if (!(entity1 instanceof Bullet) && !(entity2 instanceof Bullet)) {
                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
                    awardScore(entity1, gameData);
                    awardScore(entity2, gameData);
                }

                // Bullet vs ship — reduce ship health
                else {
                    Entity bullet = entity1 instanceof Bullet ? entity1 : entity2;
                    Entity ship   = entity1 instanceof Bullet ? entity2 : entity1;
                    world.removeEntity(bullet);
                    ship.setHealth(ship.getHealth() - 1);
                    if (ship.getHealth() <= 0) {
                        world.removeEntity(ship);
                        awardScore(ship, gameData);
                    }
                }

                continue outer;
            }
        }
    }

    private void awardScore(Entity entity, GameData gameData) {
        if (entity.getType() == EntityType.ASTEROID) {
            gameData.addAsteroidDestroyed();
            postScore("asteroid");
        } else if (entity.getType() == EntityType.ENEMY) {
            gameData.addEnemyDestroyed();
            postScore("enemy");
        }
    }

    private void postScore(String endpoint) {
        new Thread(() -> {
            try {
                new RestTemplate().postForLocation(SCORING_URL + endpoint, null);
            } catch (RestClientException ignored) {
                // Scoring service not running — game continues normally
            }
        }).start();
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
