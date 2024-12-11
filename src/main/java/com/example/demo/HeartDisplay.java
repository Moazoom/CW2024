package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Health Display for the player.
 */
public class HeartDisplay {
	
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private HBox container;
	private final double containerXPosition;
	private final double containerYPosition;
	private final int numberOfHeartsToDisplay;

	/**
	 * Spawns the heart display on the top left of the HUD.
	 * @param xPosition Horizontal position
	 * @param yPosition Vertical position
	 * @param heartsToDisplay THe initial health to display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initialises the container used hor arranging the heart images.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);		
	}

	/**
	 * Loads and adds heart images to the container.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * This is called when the player picks up a health pickup. Increases hearts displayed by one.
	 */
	public void addHeart(){
		ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
		heart.setFitHeight(HEART_HEIGHT);
		heart.setPreserveRatio(true);
		container.getChildren().add(heart);
	}

	/**
	 * This is called when the player takes damage. Reduces heart count by one.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Returns the container that holds the hearts.
	 * @return container of type HBox.
	 */
	public HBox getContainer() {
		return container;
	}

}
