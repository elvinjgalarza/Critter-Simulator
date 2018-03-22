package assignment4;

import assignment4.Critter.TestCritter;

/* Abed is Troy's best friend. He likes
   being independent, so he will move
   diagonally to the bottom left.
   
   When Abed has little Abed's, he wants
   them to be born above him, so they
   will be placed towards the north.
   
   Abed would never fight Troy, but if
   he were to meet another Critter besides
   Troy, he will get into a heated debate
   with them about the media and fight them.
   
 */

public class Critter4 extends TestCritter {

    @Override
    public String toString () { return "A"; }

    @Override
    public void doTimeStep() {
        walk(5);
        run(5);
        if (getEnergy() >= Params.min_reproduce_energy) {
            Critter4 child = new Critter4();

            reproduce(child, 2);
        }
    }

    @Override
    public boolean fight(String opponent) {
        if(opponent.equals("T")){
            return false;
        }
        return true;
    }

}
