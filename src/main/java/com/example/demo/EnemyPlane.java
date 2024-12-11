package com.example.demo;

/**
 * Enemy plane used in the first level.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 53;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = 30; // originally -100
	private static final double PROJECTILE_Y_POSITION_OFFSET = 40.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	/**
	 * @param initialXPos initial position on the horizontal axis.
	 * @param initialYPos initial position of the vertical axis.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * moves horizontally towards the player.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Randomly fires a projectile.
	 * @return returns the projectile if one is fired, otherwise returns null.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * updates the position and checks if the actor is still alive
	 */
	@Override
	public void updateActor() {
		updatePosition();
		if (this.getHealth() <= 0) this.destroy();
	}

}
