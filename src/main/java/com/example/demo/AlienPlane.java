package com.example.demo;

public class AlienPlane extends FighterPlane {

    private static final String IMAGE_NAME = "ufo.png";
    private static final int IMAGE_HEIGHT = 45;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final double PROJECTILE_X_POSITION_OFFSET = -30;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 10;
    private static final int INITIAL_HEALTH = 1;
    private static final double FIRE_RATE = .01;
    private final double RANDOM = Math.random();


    public AlienPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        moveVertically(Math.sin((this.getLayoutX() + this.getTranslateX()) / 50 * RANDOM) * 3);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new LaserProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
        if (this.getHealth() <= 0) this.destroy();
    }

}
