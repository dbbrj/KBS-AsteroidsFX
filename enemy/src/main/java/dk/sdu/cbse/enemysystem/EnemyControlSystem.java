package dk.sdu.cbse.enemysystem;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;

import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

public class EnemyControlSystem implements IEntityProcessingService {

    private final Random random = new Random();
    // fire roughly every 90 frames (~1.5 seconds at 60fps)
    private int shootCooldown = 0;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {

            // Random rotation drift — small chance to turn each frame
            if (random.nextInt(100) < 3) {
                enemy.setRotation(enemy.getRotation() + random.nextInt(91) - 45);
            }

            // Always move forward
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            // Wrap around screen edges
            if (enemy.getX() < 0) enemy.setX(gameData.getDisplayWidth());
            if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(0);
            if (enemy.getY() < 0) enemy.setY(gameData.getDisplayHeight());
            if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(0);

            // Shoot randomly via BulletSPI
            shootCooldown--;
            if (shootCooldown <= 0 && random.nextInt(100) < 5) {
                getBulletSPIs().stream().findFirst().ifPresent(
                    spi -> world.addEntity(spi.createBullet(enemy, gameData))
                );
                shootCooldown = 90;
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }
}