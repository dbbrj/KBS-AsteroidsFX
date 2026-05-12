package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

/**
 * Service interface for processing entities each game tick.
 * Implementations update entity state such as position,
 * rotation, and velocity based on input or game logic.
 */
public interface IEntityProcessingService {

    /**
     * Called once per game tick. Updates the state of relevant
     * entities in the world (e.g. movement, rotation, shooting).
     *
     * <p>Pre-conditions:
     * <ul>
     *   <li>{@code gameData} must not be null</li>
     *   <li>{@code world} must not be null</li>
     *   <li>Called after all plugins have been started</li>
     * </ul>
     *
     * <p>Post-conditions:
     * <ul>
     *   <li>Relevant entities in {@code world} have updated state</li>
     *   <li>New entities may have been added (e.g. bullets fired)</li>
     *   <li>{@code gameData} key state is unchanged</li>
     * </ul>
     *
     * @param gameData shared game state (display size, key input)
     * @param world    the game world containing all entities
     */
    void process(GameData gameData, World world);
}