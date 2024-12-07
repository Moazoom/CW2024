package com.example.demo;

public class BossProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 50;
	private double HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;
	private double ACCELERATION = 1.05;

	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	@Override
	public void updatePosition() {
		HORIZONTAL_VELOCITY = HORIZONTAL_VELOCITY * ACCELERATION;
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
