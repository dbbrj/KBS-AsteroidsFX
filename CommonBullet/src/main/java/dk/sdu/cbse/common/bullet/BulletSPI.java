package dk.sdu.cbse.common.bullet;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;

/**
 * Service Provider Interface for creating bullets.
 * Implementations define how a bullet is created and
 * positioned relative to the shooter.
 */
public interface BulletSPI {

    /**
     * Creates a new bullet entity fired from the given shooter.
     *
     * <p>Pre-conditions:
     * <ul>
     *   <li>{@code e} must not be null</li>
     *   <li>{@code e} must have a valid position and rotation</li>
     *   <li>{@code gameData} must not be null</li>
     * </ul>
     *
     * <p>Post-conditions:
     * <ul>
     *   <li>Returns a new {@code Bullet} entity, not null</li>
     *   <li>Bullet is positioned ahead of {@code e} based on its rotation</li>
     *   <li>Bullet has the same rotation as {@code e}</li>
     *   <li>Bullet is NOT added to the world by this method —
     *       the caller is responsible for adding it</li>
     * </ul>
     *
     * @param e        the entity firing the bullet
     * @param gameData shared game state
     * @return a new bullet entity ready to be added to the world
     */
    Entity createBullet(Entity e, GameData gameData);
}