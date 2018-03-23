package assignment4;

import assignment4.Critter.TestCritter;

/* The Rick Critter's life has always been up.
   It only knows being up, and so it moves upward.
   It spits out tiny Ricks upwards. It's always
   looking to pick a fight; however, it does not
   fight Morty Critters.
 */

public class Critter2 extends TestCritter {

    /**
     * String representation of the Critter to display on board
     * @return returns the string representation of Rick
     */
    @Override
    public String toString () { return "R"; }

    /**
     * Does the timestep for this Critter
     */
    @Override
    public void doTimeStep() {
        walk(2);
        run(2);
        if (getEnergy() >= Params.min_reproduce_energy) {
            Critter2 child = new Critter2();

            reproduce(child, 2);
        }
    }

    /**
     * Tells if should fight or not
     * @param opponent Opponent Critter
     * @return boolean that says to fight/not to fight
     */
    @Override
    public boolean fight(String opponent) {
        if(opponent.equals("M")){
            return false;
        }
        return true;
    }

}