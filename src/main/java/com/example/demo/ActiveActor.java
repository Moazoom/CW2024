package com.example.demo;

import javafx.scene.image.*;

/**
 * This class is the parent of all the game objects.
 */
public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * @param imageName The image the object will have.
	 * @param imageHeight The height the image will be resized to, while maintaining aspect ratio.
	 * @param initialXPos initial position on the horizontal axis.
	 * @param initialYPos initial position of the vertical axis.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Updates the position of the object
	 */
	public abstract void updatePosition();

	/**
	 * Moves object in the X direction
	 * @param horizontalMove the horizontal movement vector to be applied to the object
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves object in the Y direction
	 * @param verticalMove the vertical movement vector to be applied to the object
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
