package com.example.demo;

import java.util.*;

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

	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		isShielded = false;
		initializeMovePattern();
	}

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
	
	@Override
	public void updateActor() {
		if (!doSpecialAttack) updatePosition();
		specialAttack();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (bossFiresInCurrentFrame()){
			return new BossProjectile(getProjectileInitialPosition());
		}
		else return null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded) super.takeDamage();
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

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

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private void activateShield() {
		isShielded = true;
	}

	private void deactivateShield() {
		isShielded = false;
	}

	public boolean getShielded(){
		return isShielded;
	}

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
