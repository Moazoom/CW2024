package com.example.demo;

/**
 * This is the third level, the boss level.
 */
public class LevelBoss extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelBoss levelView;

	public LevelBoss(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
	}

	/**
	 * Initialises User
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks if the boss or user has died, and shows the wingame or losegame image respectively.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	/**
	 * Spawns the boss.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * Created a new LevelViewLevelBoss
	 * @return Returns the new levelview.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelBoss(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	/**
	 * Updates the levelView, including the heart display, the boss's shield as well as the boss's health bar.
	 */
	@Override
	protected void updateLevelView() {
		levelView.removeHearts(getUser().getHealth());

		if (boss.getShielded()){
			levelView.showShield();
			levelView.moveShieldTo(boss.getLayoutY() + boss.getTranslateY());
		}
		else levelView.hideShield();

		levelView.setBossHealth(boss.getHealth());
	}

}
