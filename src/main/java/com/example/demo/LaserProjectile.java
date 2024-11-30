package com.example.demo;

public class LaserProjectile extends Projectile {

    private static final String IMAGE_NAME = "laser.png";
    private static final int IMAGE_HEIGHT = 16;
    private static final int HORIZONTAL_VELOCITY = -15;

    public LaserProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public void updateActor() {
        updatePosition();
    }


}
