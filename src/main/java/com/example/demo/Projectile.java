package com.example.demo;

/**
 * Parent class to all bullets and lasers.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * @param imageName The image the object will have.
	 * @param imageHeight The height the image will be resized to, while maintaining aspect ratio.
	 * @param initialXPos initial position on the horizontal axis.
	 * @param initialYPos initial position of the vertical axis.
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * destroys the projectile if it collides with anything.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	@Override
	public abstract void updatePosition();

}
