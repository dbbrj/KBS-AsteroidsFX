import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IEntityProcessingService;

module enemy {
    requires Common;
    requires CommonBullet;

    uses dk.sdu.cbse.common.bullet.BulletSPI;

    provides IGamePluginService with dk.sdu.cbse.enemysystem.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.cbse.enemysystem.EnemyControlSystem;
}