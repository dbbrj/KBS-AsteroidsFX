package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

/**
 * Service interface for post-processing entities each game tick.
 * Implementations run after all {@link IEntityProcessingService}
 * processors have completed. Typical use cases include collision
 * detection and other cross-entity logic.
 */
public interface IPostEntityProcessingService {

    /**
     * Called once per game tick, after all entity processors have run.
     * Performs cross-entity logic such as collision detection and response.
     *
     * <p>Pre-conditions:
     * <ul>
     *   <li>{@code gameData} must not be null</li>
     *   <li>{@code world} must not be null</li>
     *   <li>All {@link IEntityProcessingService} processors have already
     *       been called this tick</li>
     * </ul>
     *
     * <p>Post-conditions:
     * <ul>
     *   <li>Cross-entity logic has been resolved (e.g. colliding entities removed)</li>
     *   <li>World state is consistent for the next tick</li>
     * </ul>
     *
     * @param gameData shared game state (display size, key input)
     * @param world    the game world containing all entities
     */
    void process(GameData gameData, World world);
}