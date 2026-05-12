import dk.sdu.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonAsteroids;
    requires CommonBullet;

    uses dk.sdu.cbse.common.asteroids.IAsteroidSplitter;

    provides IPostEntityProcessingService with dk.sdu.cbse.collisionsystem.CollisionDetector;
}