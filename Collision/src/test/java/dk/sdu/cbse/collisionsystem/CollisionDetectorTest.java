package dk.sdu.cbse.collisionsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dk.sdu.cbse.common.asteroids.Asteroid;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

public class CollisionDetectorTest {

    private CollisionDetector collisionDetector;
    private GameData gameData;
    private World world;

    @BeforeEach
    public void setUp() {
        collisionDetector = new CollisionDetector();
        gameData = new GameData();
        world = new World();
    }

    @Test
    void testCollides_WhenEntitiesOverlap_ReturnsTrue() {
        Entity e1 = new Asteroid();
        e1.setX(0);
        e1.setY(0);
        e1.setRadius(10);

        Entity e2 = new Asteroid();
        e2.setX(5);
        e2.setY(0);
        e2.setRadius(10);

        assertTrue(collisionDetector.collides(e1, e2));
    }

    @Test
    void testCollides_WhenEntitiesFarApart_ReturnsFalse() {
        Entity e1 = new Asteroid();
        e1.setX(0);
        e1.setY(0);
        e1.setRadius(5);

        Entity e2 = new Asteroid();
        e2.setX(100);
        e2.setY(100);
        e2.setRadius(5);

        assertFalse(collisionDetector.collides(e1, e2));
    }

    @Test
    void testBulletHitsAsteroid_BulletRemovedAndAsteroidHealthReduced() {
        Bullet bullet = new Bullet();
        bullet.setX(100);
        bullet.setY(100);
        bullet.setRadius(1);
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);

        Asteroid asteroid = new Asteroid();
        asteroid.setX(100);
        asteroid.setY(100);
        asteroid.setRadius(10);
        asteroid.setHealth(1);
        asteroid.setPolygonCoordinates(10, -10, -10, -10, -10, 10, 10, 10);

        world.addEntity(bullet);
        world.addEntity(asteroid);

        collisionDetector.process(gameData, world);

        assertFalse(world.getEntities().contains(bullet), "Bullet should be removed");
        assertFalse(world.getEntities().contains(asteroid), "Asteroid with 1 health should be removed after hit");
    }

    @Test
    void testShipHitByBullet_HealthReducedButNotDestroyed() {
        Bullet bullet = new Bullet();
        bullet.setX(100);
        bullet.setY(100);
        bullet.setRadius(1);
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);

        // Use a plain Entity as a "ship" (not Bullet or Asteroid)
        Entity ship = new Entity() {};
        ship.setX(100);
        ship.setY(100);
        ship.setRadius(8);
        ship.setHealth(5);
        ship.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);

        world.addEntity(bullet);
        world.addEntity(ship);

        collisionDetector.process(gameData, world);

        assertFalse(world.getEntities().contains(bullet), "Bullet should be removed");
        assertTrue(world.getEntities().contains(ship), "Ship with 5 health should survive one hit");
        assertEquals(4, ship.getHealth(), "Ship health should be reduced by 1");
    }

    @Test
    void testAsteroidSplits_WhenHitByBullet_TwoSmallerAsteroidsCreated() {
        Bullet bullet = new Bullet();
        bullet.setX(100);
        bullet.setY(100);
        bullet.setRadius(1);
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);

        Asteroid asteroid = new Asteroid();
        asteroid.setX(100);
        asteroid.setY(100);
        asteroid.setRadius(20);
        asteroid.setHealth(1);
        asteroid.setPolygonCoordinates(20, -20, -20, -20, -20, 20, 20, 20);

        world.addEntity(bullet);
        world.addEntity(asteroid);

        collisionDetector.process(gameData, world);

        assertFalse(world.getEntities().contains(bullet), "Bullet should be removed");
        assertFalse(world.getEntities().contains(asteroid), "Original asteroid should be removed");
        // World should contain only Asteroid type entities after split
        assertTrue(world.getEntities().stream().allMatch(e -> e instanceof Asteroid),
            "Only split asteroids should remain");
        assertFalse(world.getEntities().isEmpty(), "Split asteroids should exist");
    }
    @Test
    void testShipDestroyed_WhenHealthReachesZero() {
        Bullet bullet = new Bullet();
        bullet.setX(100);
        bullet.setY(100);
        bullet.setRadius(1);
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);

        Entity ship = new Entity() {};
        ship.setX(100);
        ship.setY(100);
        ship.setRadius(8);
        ship.setHealth(1); // one hit to destroy
        ship.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);

        world.addEntity(bullet);
        world.addEntity(ship);

        collisionDetector.process(gameData, world);

        assertFalse(world.getEntities().contains(bullet), "Bullet should be removed");
        assertFalse(world.getEntities().contains(ship), "Ship with 1 health should be destroyed");
    }

    @Test
    void testSameTypeEntities_DoNotCollide() {
        Asteroid asteroid1 = new Asteroid();
        asteroid1.setX(100);
        asteroid1.setY(100);
        asteroid1.setRadius(10);
        asteroid1.setHealth(1);
        asteroid1.setPolygonCoordinates(10, -10, -10, -10, -10, 10, 10, 10);

        Asteroid asteroid2 = new Asteroid();
        asteroid2.setX(100);
        asteroid2.setY(100);
        asteroid2.setRadius(10);
        asteroid2.setHealth(1);
        asteroid2.setPolygonCoordinates(10, -10, -10, -10, -10, 10, 10, 10);

        world.addEntity(asteroid1);
        world.addEntity(asteroid2);

        collisionDetector.process(gameData, world);

        assertTrue(world.getEntities().contains(asteroid1), "Asteroid1 should survive");
        assertTrue(world.getEntities().contains(asteroid2), "Asteroid2 should survive");
    }

    @Test
    void testBulletRemovedWhenHittingShip() {
        Bullet bullet = new Bullet();
        bullet.setX(100);
        bullet.setY(100);
        bullet.setRadius(1);
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);

        Entity ship = new Entity() {};
        ship.setX(100);
        ship.setY(100);
        ship.setRadius(8);
        ship.setHealth(5);
        ship.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);

        world.addEntity(bullet);
        world.addEntity(ship);

        collisionDetector.process(gameData, world);

        assertFalse(world.getEntities().contains(bullet), "Bullet should always be removed on hit");
        assertTrue(world.getEntities().contains(ship), "Ship should still be alive");
    }
}