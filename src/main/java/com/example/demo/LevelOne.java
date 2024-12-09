package com.example.demo;

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

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

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

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	protected void updateLevelView() {
		levelView.removeHearts(getUser().getHealth());
		levelView.setTextDisplay("Kills: " + getUser().getNumberOfKills() + "/" + KILLS_TO_ADVANCE);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
	
}
