package com.example.demo;

import javafx.scene.image.Image;

/**
 * Implements destruction capabilities, parent of all planes and bullets.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;
	/**
	 * this is used to keep track of how long the destruction explosion image is shown.
	 */
	public int destructionFrames = 0;

	/**
	 * @param imageName The image the object will have.
	 * @param imageHeight The height the image will be resized to, while maintaining aspect ratio.
	 * @param initialXPos initial position on the horizontal axis.
	 * @param initialYPos initial position of the vertical axis.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the object
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * updates actor logic
	 */
	public abstract void updateActor();

	/**
	 * reduces health and calls destroy() if health has run out
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * calls setDestroyed() and replaces the actors image with one of an explosion
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
		this.setImage(new Image(getClass().getResource("/com/example/demo/images/explode.png").toExternalForm()));
	}

	/**
	 * setter for isDestroued
	 * @param isDestroyed value to set isDestroyed
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * getter for isDestroyed
	 * @return value of isDestroyed
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
}
