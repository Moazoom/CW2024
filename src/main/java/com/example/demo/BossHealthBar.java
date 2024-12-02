package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class BossHealthBar{

    private static final String BAR_IMAGE_NAME = "/com/example/demo/images/healthBar.png";
    private static final String BORDER_IMAGE_NAME = "/com/example/demo/images/healthBorder.png";
    private static ImageView healthBar;
    private static ImageView healthBorder;
    public int xPosition = 85;
    public int yPosition = 85;
    private double originalWidth;

    public BossHealthBar() {
        healthBar = new ImageView(new Image(getClass().getResource(BAR_IMAGE_NAME).toExternalForm()));
        healthBorder = new ImageView(new Image(getClass().getResource(BORDER_IMAGE_NAME).toExternalForm()));
        healthBar.setX(xPosition);
        healthBar.setY(yPosition);
        healthBorder.setX(xPosition);
        healthBorder.setY(yPosition);
        originalWidth = healthBar.getBoundsInLocal().getWidth();
    }

    public List<ImageView> getImages(){
        return List.of(healthBorder, healthBar);
    }

    public void setHealth(double health){
        health = health / 100;
        healthBar.setFitWidth(health * originalWidth);
        System.out.println("set health to " + health);
    }
}
