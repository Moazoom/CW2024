package com.example.demo;

/**
 * Alien UFO plane class. Only used in the second level.
 */
public class AlienPlane extends FighterPlane {

    private static final String IMAGE_NAME = "ufo.png";
    private static final int IMAGE_HEIGHT = 45;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final double PROJECTILE_X_POSITION_OFFSET = -30;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 10;
    private static final int INITIAL_HEALTH = 1;
    private static final double FIRE_RATE = .01;
    private final double RANDOM = Math.random();

    /**
     * @param initialXPos initial position on the horizontal axis.
     * @param initialYPos initial position of the vertical axis.
     */
    public AlienPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }
    /**
     * Updates the position of the object, using Math.sin() to get the cool floaty movement.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        moveVertically(Math.sin((this.getLayoutX() + this.getTranslateX()) / 50 * RANDOM) * 3);
    }

    /**
     * fires a laserProjectile, specific to this enemy.
     * @return return the projectile after it is initialised
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new LaserProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    /**
     * updates the position of the actor and checks if the actor is still alive.
     */
    @Override
    public void updateActor() {
        updatePosition();
        if (this.getHealth() <= 0) this.destroy();
    }

}
