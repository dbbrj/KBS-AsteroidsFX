package dk.sdu.cbse.enemysystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        Entity e = new Enemy();
        e.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        e.setX(gameData.getDisplayWidth() * 0.75);
        e.setY(gameData.getDisplayHeight() * 0.25);
        e.setRadius(8);
        e.setHealth(3); // 3 hits to destroy
        return e;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}