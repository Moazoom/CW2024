package com.example.demo;

import javafx.scene.Group;
import javafx.scene.text.Text;

/**
 * This class contains the heart display as well as a kill counter. THis is the levelView used by the first two levels.
 */
public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSITION = -375;
	private static final int TEXT_DISPLAY_X_POSITION = 1180;
	private static final int TEXT_DISPLAY_Y_POSITION = 25;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	private final Text textDisplay;

	/**
	 * @param root provided by the level class, this is used when displaying all the HUD elements.
	 * @param heartsToDisplay initial player health.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
		this.textDisplay = new Text();
		textDisplay.setX(TEXT_DISPLAY_X_POSITION);
		textDisplay.setY(TEXT_DISPLAY_Y_POSITION);
		textDisplay.setScaleX(3);
		textDisplay.setScaleY(3);
		root.getChildren().addAll(textDisplay);
	}

	/**
	 * adds heart display container to root.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * adds win image to root.
	 */
	public void showWinImage() {
		root.getChildren().add(winImage);
	}

	/**
	 * adds game over image to root.
	 */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	/**
	 * Adjusts heart display according to player health.
	 * @param heartsRemaining The health the player has remaining
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Updates the kill counter.
	 * @param text Text to update the text display to.
	 */
	public void setTextDisplay(String text) {
		textDisplay.setText(text);
	}

	/**
	 * Adds a heart to the heart display.
	 * */
	public void addHeart(){
		heartDisplay.addHeart();
	}

}
