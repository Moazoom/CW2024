package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 950;
	private static final int SHIELD_Y_POSITION = 300;
	private final Group root;
	private final ShieldImage shieldImage;
	private static BossHealthBar healthBar = new BossHealthBar();
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}
	
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
		root.getChildren().addAll(healthBar.getImages());
		System.out.println("shield added to root");
	}
	
	public void showShield() {
		shieldImage.showShield();
		System.out.println("shield is up");
	}

	public void hideShield() {
		shieldImage.hideShield();
		System.out.println("shield down");
	}

	public void moveShieldTo(double y){
		shieldImage.setLayoutY(y - 40);
	}

	public void setBossHealth(int health){
		healthBar.setHealth(health);
	}

}
