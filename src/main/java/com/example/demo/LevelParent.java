package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

/**
 * This class is the parent for all the level classes. Much of the game logic, as well as the main game loop, is contained within.
 */
public abstract class LevelParent extends Observable {

	private static final int MILLISECOND_DELAY = 50; // originally 50
	private final double screenHeight;
	private final double screenWidth;
	private final double HEALTH_PICKUP_SPAWN_PROBABILITY = 0.002;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;
	private final CollisionHandler collisionHandler;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private final List<ActiveActorDestructible> exploded;
	private final List<ActiveActorDestructible> healthPickUps;

	private int currentNumberOfEnemies;
	private LevelView levelView;

	/**
	 * @param backgroundImageName The background the level will use.
	 * @param screenHeight Height of the screen.
	 * @param screenWidth Width of the screen.
	 * @param playerInitialHealth Initial health value for the player.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);

		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.exploded = new ArrayList<>();
		this.healthPickUps = new ArrayList<>();

		this.collisionHandler = new CollisionHandler(root, user, friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles, exploded, healthPickUps);

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	protected abstract void updateLevelView();

	/**
	 * Initialises the backgorund, the levelView as well as the user and the heart display.
	 * @return returns the scene once everyhting is initialised.
	 */
	public Scene initializeScene() {
		initializeBackground();
		this.levelView = instantiateLevelView();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * Starts the timeline.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Calls the controllers update() method.
	 * @param levelName the nest level to be played
	 */
	public void goToNextLevel(String levelName) {
		setChanged();
		notifyObservers(levelName);
	}

	/**
	 * This is the main game loop. Spawns and updates actors, updates variables and checks for collisions as well as the game ending.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		userFire();
		generateEnemyFire();
		updateNumberOfEnemies();
		spawnHealthPickups();
		updateHealthPickups();
		checkCollisions();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * handles collisions using the collisionHandler class.
	 */
	private void checkCollisions(){
		collisionHandler.CalculateCollision();
	}

	/**
	 * Initialises timeline, establishes the framerate of 20fps and updateScene() as the game loop.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * initialises the background and also handles user input.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) user.isFiring = true;
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
				if (kc == KeyCode.SPACE) user.isFiring = false;
			}
		});
		root.getChildren().add(background);
	}

	/**
	 * counts frames and fires a projectile from the user every 3rd frame.
	 */
	private void userFire(){
		if (user.isFiring) {
			if (user.fireDelay > 1) {
				fireProjectile();
				user.fireDelay = 0;
			}
			else user.fireDelay++;
		}
	}

	/**
	 * generates a projectile and adds it to root, as well as the list of user projectiles.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * generates enemy fire, calls spawnEnemyProjectile.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * checks if the projectile is not null, then adds it to root and the enemyProjectile list if it exits.
	 * @param projectile enemy projectile, can be null depending on weather the enemy fires this frame or not.
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Spaws health pickups for the player.
	 */
	protected void spawnHealthPickups() {
		if (Math.random() < HEALTH_PICKUP_SPAWN_PROBABILITY) {
			double newHeartInitialYPosition = (Math.random() * 550) + 50;
			ActiveActorDestructible newHeart = new HealthPickUp(getScreenWidth(), newHeartInitialYPosition);
			addHealthPickUp(newHeart);
		}
	}

	/**
	 * If the health pickup is destroyed, this means it collided with the player. The destroyed health pickup is removed and the user health is increased.
	 */
	protected void updateHealthPickups(){
		List <ActiveActorDestructible> toRemove = new ArrayList<>();
		for (ActiveActorDestructible heart : healthPickUps){
			if (heart.isDestroyed()){
				toRemove.add(heart);
				root.getChildren().remove(heart);
				levelView.addHeart();
				user.incrementHealth();
			}
		}
		healthPickUps.removeAll(toRemove);
	}

	/**
	 * Updates all actors, in all the lists.
	 */
	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
		healthPickUps.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * updates user kill count based on current number of enemies and size of enemy list.
	 */
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * Stops the timeline and shows the win image.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	/**
	 * Stops the timeline and shows the lose image.
	 */
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	/**
	 * returns user
	 * @return players plane
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * returns root.
	 * @return the Group root.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * returns the size of the enemy units list.
	 * @return amount of enemies in the enemy units list.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * adds an enemy unit to the enemy list, and also root,
	 * @param enemy enemy unit to be added
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * adds a health pickup to the health pickup list as well as root
	 * @param heart health pickup to be added
	 */
	protected void addHealthPickUp(ActiveActorDestructible heart) {
		healthPickUps.add(heart);
		root.getChildren().add(heart);
	}

	/**
	 * gets the width of the screen
	 * @return width of the screen
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * checks if the user is destroyed
	 * @return true if the user is dead, false otherwise.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the number of enemies variable to the size of the enemyUnits list
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

}
