package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
* Main class, does very minimal setup like making the window and setting up the stage.
*/

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "1943";
	private Controller myController;

	/**
	 * Called by JavaFX framework, this is where the stage is set up.
	 * A Controller object is created, and launchGame() is called.
	 *
	 * @param stage
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		myController = new Controller(stage);
		myController.launchGame();
	}

	/**
	 * Minimal main function, calls launch()
	 * @param args
	 */

	public static void main(String[] args) {
		launch();
	}
}