package com.example.demo;

import javafx.scene.Group;

/**
 * Extends the functionality of levelView in order to show things like the boss health bar.
 */
public class LevelViewLevelBoss extends LevelView {

	private static final int SHIELD_X_POSITION = 950;
	private static final int SHIELD_Y_POSITION = 300;
	private final Group root;
	private final ShieldImage shieldImage;
	private static final BossHealthBar healthBar = new BossHealthBar();

	/**
	 * @param root Provided by levelBoss, is used when displaying the HUD
	 * @param heartsToDisplay initial player health.
	 */
	public LevelViewLevelBoss(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds all the hud elements to root, including shield and health bar.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
		healthBar.addToRoot(root);
	}

	/**
	 * shows the shield
	 */
	public void showShield() {
		shieldImage.showShield();
	}

	/**
	 * makes the shield transparent.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

	/**
	 * Moves shield to the bosses Y position.
	 * @param y Vertical position of the boss
	 */
	public void moveShieldTo(double y){
		shieldImage.setLayoutY(y - 40);
	}

	/**
	 * Sets the health bar according to the boss health.
	 * @param health Boss's remaining health value.
	 */
	public void setBossHealth(int health){
		healthBar.setHealth(health);
	}

}
