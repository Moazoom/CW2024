package com.example.demo;

import javafx.scene.Group;

import java.util.List;

/**
 * A class I made by breaking collision functionality off of the LevelParent class. All collision logic happens here now.
 */
public class CollisionHandler {
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final List<ActiveActorDestructible> exploded;
    private final List<ActiveActorDestructible> healthPickUps;
    private final UserPlane user;
    private final Group root;

    /**
     * Constructor needs lots of lists from the levelParent class.
     * @param root this is uses to remove destroyed enemies from root.
     * @param user this is used to handle damage to the player.
     * @param friendlyUnits list of all user planes.
     * @param enemyUnits list of all enemy planes.
     * @param userProjectiles list of all user fired bullets.
     * @param enemyProjectiles list of all enemy fired bullets.
     * @param exploded list of all destroyed actors currently displaying an explosion.
     * @param healthPickUps list of spawned health pickups.
     */
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

    /**
     * Handles collision between all the different objects and deals damage respectively. This is called in LevelParent.
     */
    public void CalculateCollision() {
        handleEnemyPenetration();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
        handleHealthPickUp();
        removeAllDestroyedActors();
    }

    /**
     * handles collision between the players plane and health pickups
     */
    private void handleHealthPickUp() {
        for (ActiveActorDestructible heart : healthPickUps) {
            if (user.getBoundsInParent().intersects(heart.getBoundsInParent())) {
                heart.takeDamage();
            }
        }
    }

    /**
     * handles collision when the player crashes into an enemy plane
     */
    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    /**
     * handles collision when an enemy plane is shot by the user.
     */
    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    /**
     * handles collision when teh user is shot by the enemy.
     */
    private void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * This method is used to check which objects from two lists are overlapping, and hence colliding.
     * It deals damage to all actors which collide.
     */
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

    /**
     * handles when the enemy plane flies past the user, penetrating its defenses.
     */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    /**
     * checks if an enemy plane has gotten past the user.
     * @param enemy the enemy plane to be checked.
     * @return returns true if the plane has gotten past, false otherwise
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX() + enemy.getLayoutX()) < user.getLayoutX() + user.getTranslateX();
    }

    /**
     * calls removeDestroyedActors with all the lists, except healthPickups since those are handled separately.
     */
    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    /**
     * If the actor has just been destroyed, it is caused to explode and is removed from the list it was in.
     * If the actor is currently exploding, it continues to count its explosion frames.
     * Once an actor runs out of explosion frames, it is removed from root in order to remove it from the screen.
     * @param actors the list of destructible actors to check.
     */
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
