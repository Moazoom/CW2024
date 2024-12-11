package com.example.demo;

import java.util.*;

/**
 * Boss fight enemy used in the boss level, aka the third level.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static double BOSS_FIRE_RATE = 0.04;
	private static final double SPECIAL_ATTACK_PROBABILITY = 0.003;
	private static final int IMAGE_HEIGHT = 55;
	private static final int VERTICAL_VELOCITY = 8; // originally 8
	private static final int HEALTH = 100;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = 100;
	private static final int Y_POSITION_LOWER_BOUND = 650;
	private static int specialAttackFrames = 0;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private boolean doSpecialAttack = false;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;

	/**
	 * initialises boss, its shield logic and its move pattern.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		isShielded = false;
		initializeMovePattern();
	}

	/**
	 * Uses a shuffled array of possible moves to give the boss unpredictable up and down movement.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		int move = getNextMove();
		moveVertically(move);
		setRotate(-move);
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * checks if the boss is doing a special attack, and if not, updates positions normally.
	 */
	@Override
	public void updateActor() {
		if (!doSpecialAttack) updatePosition();
		specialAttack();
	}

	/**
	 * fires a BossProjectile specific to this enemy.
	 * @return returns the initialised projectile.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (bossFiresInCurrentFrame()){
			return new BossProjectile(getProjectileInitialPosition());
		}
		else return null;
	}

	/**
	 * takes damage and reduces health if not shielded.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) super.takeDamage();
	}

	/**
	 * gets the move pattern array ready by adding movements and then shuffling the array
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * gets the next move in the move pattern array. Also counts consecutive moves and resuffles the array as necessary.
	 * @return returns a vertical movement vector for the object.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * returns true and false randomly, based on the BOSS_FIRE_RATE variable.
	 * @return returns a boolean deciding weather the boss fires or not in the current frame.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * gets the offset that a projectile should be fired from
	 * @return the offset as a vector.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * activates shield
	 */
	private void activateShield() {
		isShielded = true;
	}

	/**
	 * 	deactivates shield
	 */
	private void deactivateShield() {
		isShielded = false;
	}

	/**
	 * returns true if the boss is shielded, false otherwise
	 * @return value of isShielded boolean.
	 */
	public boolean getShielded(){
		return isShielded;
	}

	/**
	 * This is called every frame and handles the special attack.
	 * Using a probability check, it decides when to initiate the attack, and once initiated, keeps track of what actions the
	 * boss should be doing using a frame count.
	 */
	private void specialAttack(){
		if (Math.random() < SPECIAL_ATTACK_PROBABILITY) doSpecialAttack = true;

		if (doSpecialAttack){
			specialAttackFrames++;
			activateShield();
			if (specialAttackFrames < 40){
				double move = Y_POSITION_UPPER_BOUND - getLayoutY() - getTranslateY();
				if (-5 < move && move < 5) specialAttackFrames = 39;
				else specialAttackFrames--;
				move = move / 10;
				setRotate(-move);
				moveVertically(move);
				BOSS_FIRE_RATE = 0;
			}
			else if (specialAttackFrames < 100){
				if (specialAttackFrames < 60 || 70 < specialAttackFrames) BOSS_FIRE_RATE = 1;
				else BOSS_FIRE_RATE = 0;
				moveVertically((Y_POSITION_LOWER_BOUND - Y_POSITION_UPPER_BOUND) / 60.0);
				setRotate(0);
			}
			else if (specialAttackFrames < 160){
				if (specialAttackFrames < 130 || 140 < specialAttackFrames) BOSS_FIRE_RATE = 1;
				else BOSS_FIRE_RATE = 0;
				moveVertically((Y_POSITION_LOWER_BOUND - Y_POSITION_UPPER_BOUND) / -60.0);
			}
			else{
				doSpecialAttack = false;
				specialAttackFrames = 0;
				deactivateShield();
				BOSS_FIRE_RATE = 0.04;
			}

		}
	}

}
