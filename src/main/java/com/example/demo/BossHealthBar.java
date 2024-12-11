package com.example.demo;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Simple health bar display, used only during the boss fight.
 */
public class BossHealthBar{

    private static final String BAR_IMAGE_NAME = "/com/example/demo/images/healthBar.png";
    private static final String BORDER_IMAGE_NAME = "/com/example/demo/images/healthBorder.png";
    private static ImageView healthBar;
    private static ImageView healthBorder;
    private final Text textDisplay;
    public int xPosition = 85;
    public int yPosition = 85;
    private double originalWidth;

    /**
     * Creates two image views for the health bar and its background, as well as a text label.
     */
    public BossHealthBar() {
        healthBar = new ImageView(new Image(getClass().getResource(BAR_IMAGE_NAME).toExternalForm()));
        healthBorder = new ImageView(new Image(getClass().getResource(BORDER_IMAGE_NAME).toExternalForm()));
        healthBar.setX(xPosition);
        healthBar.setY(yPosition);
        healthBorder.setX(xPosition);
        healthBorder.setY(yPosition);
        originalWidth = healthBar.getBoundsInLocal().getWidth();
        this.textDisplay = new Text("BOSS HEALTH");
        textDisplay.setX(xPosition + 545);
        textDisplay.setY(yPosition - 20);
        textDisplay.setScaleX(3);
        textDisplay.setScaleY(3);
        textDisplay.setFill(Color.WHITE);
    }

    /**
     * Adds the health bar, its black border background and the text display to root.
     * @param root Root, as provided by LevelViewLevelBoss, wherein is the only instance this method is called.
     */
    public void addToRoot(Group root){
        root.getChildren().addAll(healthBorder, healthBar, textDisplay);
    }

    /**
     * sets the health that the bar should display
     * @param health health value input.
     */
    public void setHealth(double health){
        health = health / 100;
        healthBar.setFitWidth(health * originalWidth);
    }
}
