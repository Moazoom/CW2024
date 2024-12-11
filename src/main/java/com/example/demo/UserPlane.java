package com.example.demo;

/**
 * This is the class for the players plane.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 50;
	private static final double Y_LOWER_BOUND = 650.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 38;
	private static final int VERTICAL_VELOCITY = 12; // originally 8
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
    private static final int MOVEMENT_ROTATION = 10;
	private int velocityMultiplier;
	private int numberOfKills;
	public boolean isFiring = false;
	public int fireDelay = 0;

	/**
	 * Initial health value is provided by the level class.
	 * @param initialHealth initial health value input.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		numberOfKills = 0;
	}

	/**
	 * Moves the player up or down depending on the key inputs. Also caps the movement at a maximum and minimum height.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	/**
	 * Calls updatePosition().
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * fires a projectile
	 * @return returns the projectile after it is fired.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	/**
	 * Moves the user plane upwards, also rotates it slightly.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
		setRotate(-MOVEMENT_ROTATION);
	}

	/**
	 * Moves the user plane downwards, also rotates it slightly.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
		setRotate(MOVEMENT_ROTATION);
	}

	/**
	 * Sets the user planes vertical velicoity to 0 and also sets its rotation angle to 0.
	 */
	public void stop() {
		velocityMultiplier = 0;
		setRotate(0);
	}

	/**
	 * gets the number of kills
	 * @return number of kills
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * increases the kill counter by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}


}
