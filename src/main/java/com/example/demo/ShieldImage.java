package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class contains the shield image and also shows and hides the shield as necessary.
 */
public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_WIDTH = 400;
	private static final int SHIELD_HEIGHT = 150;

	/**
	 * @param xPosition Position on the horizontal axis.
	 * @param yPosition Position on the vertical axis.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_HEIGHT);
		this.setFitWidth(SHIELD_WIDTH);
	}

	/**
	 * Makes the shield image opaque.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Makes the shield image transparent.
	 */
	public void hideShield() {
		this.setVisible(false);
	}

}
