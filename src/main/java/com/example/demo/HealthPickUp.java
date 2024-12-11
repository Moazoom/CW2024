package com.example.demo;

/**
 * This is the class for the floating heart pickups.
 */
public class HealthPickUp extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "heart.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final int HORIZONTAL_VELOCITY = -12;

    /**
     * Usually spawns off screen to the right, with a random y position.
     * @param initialXPos initial position on the horizontal axis.
     * @param initialYPos initial position of the vertical axis.
     */
    public HealthPickUp(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * moves the health pickup horizontally towards the player
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * calls updatePosition()
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Once this health pickup collides with the player it is destroyed.
     */
    @Override
    public void takeDamage(){
        this.destroy();
    }

}
