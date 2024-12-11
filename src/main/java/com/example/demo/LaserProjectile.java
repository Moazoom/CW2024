package com.example.demo;

/**
 * This is the projectile used by the UFO enemy encountered in the second level.
 */
public class LaserProjectile extends Projectile {

    private static final String IMAGE_NAME = "laser.png";
    private static final int IMAGE_HEIGHT = 20;
    private static final int HORIZONTAL_VELOCITY = -15;

    /**
     * @param initialXPos initial position on the horizontal axis.
     * @param initialYPos initial position of the vertical axis.
     */
    public LaserProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Moves the projectile towards the player. Unlike the UFO that fires it, this projectile hase a simple horizontal movement.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Calls updatePosition().
     */
    @Override
    public void updateActor() {
        updatePosition();
    }


}
