package com.example.demo;

import javafx.scene.Group;

import java.util.List;
import java.util.stream.Collectors;

public class CollisionHandler {
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final List<ActiveActorDestructible> exploded;
    private final UserPlane user;
    private final Group root;

    public CollisionHandler(Group root, UserPlane user, List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits, List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> exploded) {
        this.friendlyUnits = friendlyUnits;
        this.enemyUnits = enemyUnits;
        this.userProjectiles = userProjectiles;
        this.enemyProjectiles = enemyProjectiles;
        this.exploded = exploded;
        this.user = user;
        this.root = root;
    }

    public void CalculateCollision() {
        handleEnemyPenetration();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
        removeAllDestroyedActors();
    }

    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    private void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    public void handleCollisions(List<ActiveActorDestructible> actors1,
                                  List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX() + enemy.getLayoutX()) < user.getLayoutX() + user.getTranslateX();
    }

    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
                .collect(Collectors.toList());
        destroyedActors.forEach(destroyedActor -> destroyedActor.destroy());
        for (ActiveActorDestructible actor : destroyedActors) {
            exploded.add(actor);
        }
        actors.removeAll(destroyedActors);
        for (ActiveActorDestructible actor: exploded) {
            if (actor.destructionFrames > 25){
                root.getChildren().remove(actor);
                //exploded.remove(actor);
            }
            else actor.destructionFrames++;
        }
    }
}
