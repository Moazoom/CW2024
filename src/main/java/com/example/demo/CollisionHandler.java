package com.example.demo;

import javafx.scene.Group;

import java.util.List;

public class CollisionHandler {
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final List<ActiveActorDestructible> exploded;
    private final List<ActiveActorDestructible> healthPickUps;
    private final UserPlane user;
    private final Group root;

    public CollisionHandler(Group root, UserPlane user, List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits, List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> exploded, List<ActiveActorDestructible> healthPickUps) {
        this.friendlyUnits = friendlyUnits;
        this.enemyUnits = enemyUnits;
        this.userProjectiles = userProjectiles;
        this.enemyProjectiles = enemyProjectiles;
        this.exploded = exploded;
        this.healthPickUps = healthPickUps;
        this.user = user;
        this.root = root;
    }

    public void CalculateCollision() {
        handleEnemyPenetration();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
        handleHealthPickUp();
        removeAllDestroyedActors();
    }

    private void handleHealthPickUp() {
        for (ActiveActorDestructible heart : healthPickUps) {
            if (user.getBoundsInParent().intersects(heart.getBoundsInParent())) {
                heart.takeDamage();
            }
        }
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
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed).toList();
        destroyedActors.forEach(ActiveActorDestructible::destroy);
        exploded.addAll(destroyedActors);
        actors.removeAll(destroyedActors);
        for (ActiveActorDestructible actor: exploded) {
            if (actor.destructionFrames > 25) root.getChildren().remove(actor);
            else actor.destructionFrames++;
        }
    }
}
