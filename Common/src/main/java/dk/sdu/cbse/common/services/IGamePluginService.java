package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

/**
 * Service interface for game plugins.
 * Implementations are responsible for creating and removing 
 * their entities when the game starts and stops, respectively.
 */
public interface IGamePluginService {
    
    /**
     * Called once when the game starts. Responsible for creating
     * and adding the plugin's entities to the world.
     *
     * <p>Pre-conditions:
     * <ul>
     *   <li>{@code gameData} must not be null</li>
     *   <li>{@code world} must not be null</li>
     * </ul>
     *
     * <p>Post-conditions:
     * <ul>
     *   <li>One or more entities have been added to {@code world}</li>
     *   <li>{@code gameData} is unchanged</li>
     * </ul>
     *
     * @param gameData shared game state (display size, key input)
     * @param world    the game world to add entities to
     */
    void start(GameData gameData, World world);

     /**
     * Called once when the game stops. Responsible for removing
     * all entities previously created by this plugin from the world.
     *
     * <p>Pre-conditions:
     * <ul>
     *   <li>{@code gameData} must not be null</li>
     *   <li>{@code world} must not be null</li>
     * </ul>
     *
     * <p>Post-conditions:
     * <ul>
     *   <li>All entities created by this plugin have been removed from {@code world}</li>
     *   <li>{@code gameData} is unchanged</li>
     * </ul>
     *
     * @param gameData shared game state
     * @param world    the game world to remove entities from
     */
    void stop(GameData gameData, World world);
}
