package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class holds the win image, and shows it when
 */
public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	/**
	 * @param xPosition Position on the horizontal axis.
	 * @param yPosition Position on the vertical axis.
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

}
