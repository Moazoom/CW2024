package com.example.demo;

/**
 * This is the class for the second level.
 */
public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.LevelBoss";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 30;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.1;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static boolean nextLevelStarted = false;
    private static final int MAX_SPAWN_Y = 500;
    private static final int MIN_SPAWN_Y = 150;
    private LevelView levelView;

    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    /**
     * Checks if the user is dead, and if so shows the lose game image.
     * If the user has reached the kill count, the boss level is started.
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
     * adds user plane to root.
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
            if ((Math.random() < ENEMY_SPAWN_PROBABILITY) || i == (TOTAL_ENEMIES - 1)) {
                double newEnemyInitialYPosition = (Math.random() * MAX_SPAWN_Y) + MIN_SPAWN_Y;
                ActiveActorDestructible newEnemy = new AlienPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
        if (currentNumberOfEnemies == 0){
            double newEnemyInitialYPosition = (Math.random() * MAX_SPAWN_Y) + MIN_SPAWN_Y;
            ActiveActorDestructible newEnemy = new AlienPlane(getScreenWidth(), newEnemyInitialYPosition);
            addEnemyUnit(newEnemy);
        }
    }

    /**
     * Creates a new levelView.
     * @return returns the new level view.
     */
    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }

    /**
     * Updates the level view, including the heart display and the kill counter for this level.
     */
    protected void updateLevelView() {
        levelView.removeHearts(getUser().getHealth());
        levelView.setTextDisplay("Kills: " + getUser().getNumberOfKills() + "/" + KILLS_TO_ADVANCE);
    }

    /**
     * checks if the user has reached the kill target for this level.
     * @return true if the user has reached the kill threshold, false otherwise.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }

}
