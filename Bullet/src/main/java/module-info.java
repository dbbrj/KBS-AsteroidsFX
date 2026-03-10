import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;

module Bullet {
    requires Common;
    requires CommonBullet;

    provides IGamePluginService with dk.sdu.cbse.bulletsystem.BulletPlugin;
    provides IEntityProcessingService with dk.sdu.cbse.bulletsystem.BulletControlSystem;
    provides BulletSPI with dk.sdu.cbse.bulletsystem.BulletControlSystem;
}
