package com.example.demo;

/**
 * an interface used by ActiveActorDestructible.
 */
public interface Destructible {

	/**
	 * takes damage, reduces health.
	 */
	void takeDamage();

	/**
	 * destroys itself.
	 */
	void destroy();
	
}
