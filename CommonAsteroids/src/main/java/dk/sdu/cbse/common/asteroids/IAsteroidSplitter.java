package dk.sdu.cbse.common.asteroids;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;


/**
 * Service interface for splitting asteroids.
 * Implementations decide how an asteroid breaks apart
 * when hit by a bullet.
 */
public interface IAsteroidSplitter {

        /**
     * Creates two smaller asteroids from the given asteroid entity.
     * If the asteroid is too small, no new asteroids are created.
     *
     * <p>Pre-conditions:
     * <ul>
     *   <li>{@code e} must not be null</li>
     *   <li>{@code e} must be an Asteroid with radius greater than 0</li>
     *   <li>{@code w} must not be null</li>
     * </ul>
     *
     * <p>Post-conditions:
     * <ul>
     *   <li>If {@code e.getRadius() > 3}, two new smaller asteroids
     *       with radius {@code e.getRadius()/2} are added to {@code w}</li>
     *   <li>If {@code e.getRadius() <= 3}, no new entities are added</li>
     *   <li>The original asteroid {@code e} is not removed by this method</li>
     * </ul>
     *
     * @param e the asteroid entity to split
     * @param w the game world to add split asteroids to
     */
    void createSplitAsteroid(Entity e, World w);
}
