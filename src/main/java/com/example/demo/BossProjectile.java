package com.example.demo;

/**
 * This is the projectile used by the boss enemy. It is the only projectile in the game to accelerate once fired.
 */
public class BossProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 50;
	private double HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;
	private double ACCELERATION = 1.05;

	/**
	 * @param initialYPos Boss position plus offset
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Moves the projectile to the left by a certain velocity.
	 * THe velocity is gradually increased causing the projectile to accelerate.
	 */
	@Override
	public void updatePosition() {
		HORIZONTAL_VELOCITY = HORIZONTAL_VELOCITY * ACCELERATION;
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * updates the position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
