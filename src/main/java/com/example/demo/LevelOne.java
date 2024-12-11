package com.example.demo;

/**
 * This is the class for the first level.
 */
public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 25;
	private static final double ENEMY_SPAWN_PROBABILITY = .10;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static boolean nextLevelStarted = false;
	private static final int MAX_SPAWN_Y = 550;
	private static final int MIN_SPAWN_Y = 50;
	private LevelView levelView;

	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the user is dead, in which case loseCame() is called.
	 * Also checks if the user has reached the kill target for this level, in which case it starts the next one.
	 * There is also a check here preventing the next level from being started several times, this was a bug originally.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget() && !nextLevelStarted) {
			goToNextLevel(NEXT_LEVEL);
			nextLevelStarted = true;
		}
	}

	/**
	 * Initialises the player
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns the enemyPlane enemy. Various values and probabilities have been tweaked by my to ensure a fun spawn rate.
	 * ALso, if there are no more enemies on screen, at least one new one will always spawn.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY || i < (TOTAL_ENEMIES - 1)) {
				double newEnemyInitialYPosition = (Math.random() * MAX_SPAWN_Y) + MIN_SPAWN_Y;
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Creates a new levelView
	 * @return returns the new levelView.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	/**
	 * Updates the levelView. This includes the heart dispaly as well as the on screen kill counter.
	 */
	protected void updateLevelView() {
		levelView.removeHearts(getUser().getHealth());
		levelView.setTextDisplay("Kills: " + getUser().getNumberOfKills() + "/" + KILLS_TO_ADVANCE);
	}

	/**
	 * Checks weather or not the user has reached the kill target for this level.
	 * @return returns true if the user has reached the kill target, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
	
}
