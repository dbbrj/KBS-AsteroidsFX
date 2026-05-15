import dk.sdu.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonAsteroids;
    requires CommonBullet;
    requires spring.web;
    requires spring.core;

    uses dk.sdu.cbse.common.asteroids.IAsteroidSplitter;

    provides IPostEntityProcessingService with dk.sdu.cbse.collisionsystem.CollisionDetector;
}