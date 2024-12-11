package com.example.demo;

/**
 * Bullet that is show by the player in the UserPlane classs.
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 6; // only thing i rounded up and not down, original value 5.2
	private static final int HORIZONTAL_VELOCITY = 32; // original value 15

	/**
	 * @param initialXPos initial position on the horizontal axis.
	 * @param initialYPos initial position of the vertical axis.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Moves the projectile to the right in a straight line.
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
