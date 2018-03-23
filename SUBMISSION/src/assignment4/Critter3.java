package assignment4;

import assignment4.Critter.TestCritter;

/* This Critter is named Troy. Troy likes to 
   live an uncomplicated life, so he only
   moves eastward.
   
   Troy only makes little Troy's when he has at
   least 100 energy.
   
   Troy is Abed's best friend and would never
   fight Abed. However, since he is very
   protective of his friend Abed, he will fight
   anyone else that runs into him just so Abed
   would be safe.
 */

public class Critter3 extends TestCritter {

    /**
     * String representation of the Critter to display on board
     * @return returns the string representation of Troy
     */
    @Override
    public String toString () { return "T"; }

    /**
     * Does the timestep for this Critter
     */
    @Override
    public void doTimeStep() {
        walk(0);
        run(0);
        if (getEnergy() >= 100) {
            Critter3 child = new Critter3();

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
        if(opponent.equals("A")){
            return false;
        }
        return true;
    }

}
