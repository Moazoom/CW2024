package com.example.demo;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.LevelBoss";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 30 ;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.1;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static boolean nextLevelStarted = false;
    private static final int MAX_SPAWN_Y = 500;
    private static final int MIN_SPAWN_Y = 150;
    private LevelView levelView;

    public LevelTwo(double screenHeight, double screenWidth) {
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
