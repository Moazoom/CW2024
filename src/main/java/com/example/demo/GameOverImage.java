package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Simple class responsible for loading and resizing the Game Over image.
 */
public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
	 * Loads the image and sets its position.
	 * @param xPosition position on the horizontal axis.
	 * @param yPosition position of the vertical axis.
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) );
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}
