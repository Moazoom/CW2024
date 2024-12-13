     __  ___  _  _  ____  
    /_ |/ _ \| || ||___ \
     | | (_) | || |_ __) |
     | |\__, |__   _|__ <
     | |  / /   | | ___) |
     |_| /_/    |_||____/ 

Github: https://github.com/Moazoom/CW2024

## Compilation Instructions

Start by downloading the latest versions of the following:
- Intellij IDE: https://www.jetbrains.com/idea/
- Java: https://www.java.com/en/download/

Import the files into Intellij and the Maven build system should have JavaFX ready to go.
Now just open the Main.java file and click "run" in the top right corner.

## Implemented and working properly
### Second level
A second playable level was added, including new enemies with unique wave-like movement and unique projectiles.

### New animations for player and boss
A small rotation animation was given to the player, and to the boss, 
which is the only enemy that moves like the player.

### New logic for player firing
The player can now fire bullets by just holding down the space bar, and is free to move when doing so.

### New special attack for boss
The boss now has a special attack where it will raise its shield and fire a wave of projectiles at the player,
leaving a small gap in the middle for skilled players to escape through.

### New health bar for boss
THe boss fight now has a health bar that will display how much health the boss has left at the top of the screen.

### New health pick-up that can randomly fly across screen
Randomly a heart will fly towards the player. If the player touches the heart, the heart will be destroyed and the 
players health will be increased by one.

### New destruction explosion frames for all destructible actors
All destructible actors, when they are destroyed have special logic which will show a stationary explosion for 
a few frames before the actor is removed from the screen.

### Better collision achieved by trimming sprites
Since the collision logic is based on the overlapping of actor images, by trimming all the transparent backgrounds 
in all the images to be as minimal as possible more visually accurate collision was achieved.
This makes it easier and more reliable to dodge projectiles and makes it harder but more fun to hit enemies.

### New boss shield graphic and logic
The boss now has a more appropriate force-field type shield. The shield logic has also been modified so that the shield
is only activated during the boss's special attack, instead of regular gameplay. Also, the shield will follow the boss's
vertical movements instead of being stationary, so it will cover the boss the whole time its active.

### New kill counter for non-boss levels
Level one and two both have a kill counter in the top right showing how many kills are needed to advance to the next level.

### Gameplay changes
Small changes I made to improve the feel of the game, including:
- Faster movement for player
- Faster bullets for player
- Smaller bullets for some enemies
- Accelerating bullets for the boss
- Spawning logic is changed to provide a smooth stream of enemies, as well as making sure 
there is always at least one enemy on screen
- Adjusted maximum and minimum Y values, for all fighter planes.
- Adjusted projectile offsets for all fighter planes to make them more accurate

## Implemented and not working properly
- Boss health bar returns to 100% when the boss is defeated. I assume this is because I try to set an images width to 0.
- The final enemy on level one or two does not get to show explosion frames because the next level is started immediately,
making it feel as though that enemy was never killed. This also means the player never gets to see the kill counter at
full capacity, since at full capacity the next level is started.

## Features Not implemented
### Main menu, pausing, saving and loading, Sounds etc.
I did not implement these because I felt they were not unique additions, and also I wanted to keep the arcade feel of 
the game intact.

### High Score
I was not sure on how or where to save the data, I don't have enough experience in Java.

### End of level animation
Again it was lack of experience, I did not understand how to control the JavaFX timeline and 
that was enough to scare me off from working on this feature, since I was worried that I would break the game in crazy
ways.

## New Java Classes
### LevelTwo
This is the class for the second level, it has a new background and slightly different spawning logic than level one.

### AlienPlane
This is the new enemy encountered in the second level. It has a unique image and fires unique projectiles, and it also 
unique wave-like movement calculated using the sin() function.

### LaserProjectile
This is the projectile fired by AlienPlane. Besides having a custom image, size and velocity it has the same projectile 
logic as other enemy projectiles.

### BossHealthBar
This is the class that holds the images and text needed to display the boss's health in the third level.

### CollisionHandler
This class contains all collision logic that used to reside in LevelParent. I thought it would be wise to move it all to
its own class. It also has some new collision logic for the health pick-ups, as well as the destruction explosion frames.

### HealthPickUp
This class is for the health pick-up. Once spawned, it will float across the screen, and if and only if it collides with the 
user plane directly, it will be destroyed.

## Modified Java Classes
### ActiveActorDestructible
New logic was added to allow for the destruction frames to appear, as well as to count how long they were displayed.

### Boss
A new special attack was added, and shield logic was changed. The projectile offset was also tweaked to make it more accurate.

### BossProjectile
The velocity is now increased every frame, causing the projectile to accelerate.

### EnemyPlane
Size and speed values were adjusted.

### EnemyProjectile
Size and speed of the projectile was also adjusted.

### HeartDisplay
Added functionality to allow for the heart count to be increased.

### LevelOne
Adjusted spawning logic and vertical range for enemies, and also fixed a bug where the next level was being loaded over
and over again causing the system to run out of memory. Added and overrode updateLevelView() method

### LevelBoss
Renamed from LevelTwo. New logic to allow the shield to show and to move the shield image to the boss's position. 
Overrode the updateLevelView() method  from LevelParent() to allow the class to show and hide the shield.

### LevelParent
All collision logic moved to the CollisionHandler class. New logic added for tbe user continuously firing, as well as for the 
health pick-ups. Changed when levelView is initialised to allow for elements to be added to root after the background 
has been added. Made updateLevelView() into an abstract method so that each level could manage its HUD separately. 
Also removed useless commented code.

### LevelView
New text display added which is used to display the kill count.

### LevelViewLevelBoss
Renamed from LevelViewLevelTwo. New logic added for the boss health bar as well as to move the shield to the boss's position.

### ShieldImage
Fixed a bad filename which was causing an error upon entering the boss level. Removed useless commented code.

### UserPlane
Adjusted speed for more fun gameplay.

### UserProjectile
Adjusted speed and size for more fun gameplay.

### WinImage
Removed useless show() method which was unnecessary.

## Unexpected Problems
Adding the health pick-up was easy, but making it increment the players health as well as the heartDisplay was hard,
especially before I realised how the removeHeart() method worked, and that it was called each frame. I also had
problems where the first levels heartDisplay was invisible but still loaded and any new hearts picked up would be added 
to that instead. I did eventually fix both these issues.