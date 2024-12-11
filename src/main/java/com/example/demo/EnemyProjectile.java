package com.example.demo;

/**
 *Projectile that is specific to the enemyPlane enemy.
 */
public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 20;
	private static final int HORIZONTAL_VELOCITY = -15;

	/**
	 * @param initialXPos initial position on the horizontal axis.
	 * @param initialYPos initial position of the vertical axis.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * moves the projectile towards the player
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


}
