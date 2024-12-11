package com.example.demo;

/**
 * This is the parent class for all plane actors, including the player and the boss.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	/**
	 * @param imageName The image the object will have.
	 * @param imageHeight The height the image will be resized to, while maintaining aspect ratio.
	 * @param initialXPos initial position on the horizontal axis.
	 * @param initialYPos initial position of the vertical axis.
	 * @param health initial health
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * reduce health, and call destroy() if necessary
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Returns the X positions the projectile should fire from.
	 * @param xPositionOffset Offset on the X axis for the projectile
	 * @return returns the X position coordinate.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}
	/**
	 * Returns the Y positions the projectile should fire from.
	 * @param yPositionOffset Offset on the Y axis for the projectile
	 * @return returns the Y position coordinate.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if health is depleted.
	 * @return returns true if health is gone, false otherwise.
	 */
	private boolean healthAtZero() {
		return health <= 0;
	}

	/**
	 * Gets the amount of health left
	 * @return returns the health variable
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Increases health value by 1.
	 */
	public void incrementHealth() {
		this.health++;
	}
		
}
