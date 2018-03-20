package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 *
 * Elvin J. Galarza
 * ejg2298
 * 15455
 *
 * Bianca Antonio
 * bla774
 * 15510
 *
 * Slip days used: <0>
 * Spring 2018
 *
 */


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


/**
 * Maintains the world's operations and can display it.
 * All objects extend from this abstract class.
 */
public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	private static int time_step = 0;
	private static boolean fighting = false;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();

	/**
	 * Generates a random int from 0 to max - 1
	 * @param max from setSeed
	 * @return An int from 0 to max-1
	 */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	/**
	 * Sets the seed needed for the random number generatro that is used
	 * in getRandomInt
	 * @param new_seed The new seed number for the random number generator
	 */
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() {
		return "";
	}
	
	private int energy = 0;
	protected int getEnergy() {
		return energy;
	}
	
	private int x_coord;
	private int y_coord;
	private boolean moved; /* tells us if the Critter has moved (true) during this time */


	/**
	 * This function moves the Critter in a given direction
	 * and steps. Keep in mind that the map is numbered like
	 * a matrix ( think: Battleship gameboard). So we start
	 * from (0,0) and increase in x as we go right and increase
	 * in y as we go down. Map is a torus (i.e., wraps around like
	 * Pacman)
	 * @param direction direction the Critter moves in
	 *                  Think of a circle and the int represents the radians
	 *                  0 = straight RIGHT(increasing x, no change in y)
	 *                  1 = diagonally UP and to the RIGHT(increasing x, decreasing y)
	 *                  2 = straight UP (unchanged x, decreasing y)
	 *                  3 = diagonally UP and to the LEFT(decreasing x, decreasing y)
	 *                  4 = straight LEFT (decreasing x, unchanged y)
	 *                  5 = diagonally DOWN and to the LEFT (decreasing x, increasing y)
	 *                  6 = straight DOWN (unchanged x, increasing y)
	 *                  7 = diagonally DOWN and to the RIGHT (increasing x, increasing y)
	 * @param steps amount of steps the Critter takes
	 */
	private final void move(int direction, int steps){
		int newX = x_coord;
		int newY = y_coord;
		/* debugging
		System.out.println("Direction: " + direction);
		System.out.println("Old Coordinate: " + newX + " " + newY); */

		switch(direction){

			/* right */
			case 0:
				newX += steps;
				if(newX >= Params.world_width){
					newX = wrapX(newX);
				}
				break;

			/* right-up diagonal */
			case 1:
				newX += steps;
				newY -= steps;
				if(newX >= Params.world_width){
					newX = wrapX(newX);
				}
				if(newY < 0){
					newY = wrapY(newY);
				}
				break;

			/* up */
			case 2:
				newY -= steps;
				if(newY < 0){
					newY = wrapY(newY);
				}
				break;

			/* left-up diagonal */
			case 3:
				newX -= steps;
				newY -= steps;
				if(newX < 0){
					newX = wrapX(newX);
				}
				if(newY < 0){
					newY = wrapY(newY);
				}
				break;

			/* left */
			case 4:
				newX -= steps;
				if(newX < 0){
					newX = wrapX(newX);
				}
				break;

			/* down-left diagonal */
			case 5:
				newX -= steps;
				newY += steps;

				if(newX < 0){
					newX = wrapX(newX);
				}
				if(newY >= Params.world_height){
					newY = wrapY(newY);
				}
				break;

			/* down */
			case 6:
				newY += steps;
				if(newY >= Params.world_height){
					newY = wrapY(newY);
				}
				break;

			/* down-right diagonal */
			case 7:
				newX += steps;
				newY += steps;

				if(newX >= Params.world_width){
					newX = wrapX(newX);
				}
				if(newY >= Params.world_height){
					newY = wrapY(newY);
				}
				break;

			default:
				break;
		}

		if(fighting == false){
			x_coord = newX;
			y_coord = newY;
		}
		/* debugging
		System.out.println("New Coordinate: " + x_coord + " " + y_coord); */
	}
	/* helper functions for move method that wraps
	   the Critter around the map (TORUS)*/
	private int wrapX(int x){
		return ((x % Params.world_width + Params.world_width) % Params.world_width);
	}
	private int wrapY(int y){
		return ((y % Params.world_height + Params.world_height) % Params.world_height);
	}



	/**
	 * Simulates a Critter's walk operation.
	 * Uses specified direction to move a tile.
	 * Reduces energy from Critter.
	 * @param direction int that represents a certain direction
	 */
	protected final void walk(int direction) {
		this.energy = this.energy - Params.walk_energy_cost;
		if(moved == false){
			moved = true;
			move(direction, 1);
		}
	}

	/**
	 * Simulates a Critter's run operation.
	 * Uses specific direction to move two tiles.
	 * Reduces energy from Critter.
	 * @param direction
	 */
	protected final void run(int direction) {
		this.energy = this.energy - Params.run_energy_cost;
		if(moved == false){
			moved = true;
			move(direction, 2);
		}
	}

	/**
	 * Simulates reproducing Critters.
	 * Creates a new Critter object by expending parent's energy
	 * if there is enough energy to use. Child gets half of parent's
	 * original energy (rounded down) and parent is obviously left with
	 * the remaining half (rounded up). Child is placed adjacent to
	 * parent (specified by direction), and is encounter-immune. Babies
	 * are not added to general population until after timestep is finished.
	 * So technically the baby is not existing yet, but is prepped.
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy >= Params.min_reproduce_energy){
			offspring.energy = this.energy/2; /* give offspring energy */
			this.energy -= offspring.energy; /* give parent modified energy */

			/* place offspring adjacent to parent */
			offspring.x_coord = this.x_coord;
			offspring.y_coord = this.y_coord;
			offspring.move(direction, 1);

			/* add offspring to babies */
			babies.add(offspring);
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);



	/* CRITTER COLLECTION: STAGE ONE */
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {

		try{
			/* check input first */
			Critter newCritter = (Critter) Class.forName(myPackage + "." + critter_class_name).newInstance();
			/* add  the newCritter to the world's population */
			Critter.population.add(newCritter);
			/* give the newCritter its starting energy */
			newCritter.energy = Params.start_energy;
			/* give the newCritter a random start position in the world */
			newCritter.x_coord = Critter.getRandomInt(Params.world_width);
			newCritter.y_coord = Critter.getRandomInt(Params.world_height);
		}
		catch(ClassNotFoundException|InstantiationException|IllegalAccessException exception){
			throw new InvalidCritterException(critter_class_name);
		}
	}
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();

		Class<? extends Critter> get_list = null;

		try{
			get_list = Class.forName(myPackage + "." + critter_class_name).asSubclass(Critter.class);
		}
		catch(ClassNotFoundException critter){
			throw new InvalidCritterException(critter_class_name);
		}

		for(Critter critter: population){
			if(get_list.isInstance(critter)){
				result.add(critter);
			}
		}

		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}

	/* Time Steps: STAGE 1 */
	/**
	 * Simulation consists of a sequence of time steps. During
	 * each step, the state of all Critters in simulation is
	 * updated, new critters may be added, and critters may be
	 * removed (births and deaths). All core functionality is
	 * associated with time steps.
	 *
	 * > Simulates one time step for every Critter in the
	 *   critter collection (i.e., entire world).
	 */
	public static void worldTimeStep() {
		/*
		 * 1. increment timestep; timestep++;
		 * 2. doTimeSteps();
		 * 3. Do the fights. doEncounters();
		 * 4. updateRestEnergy();
		 * 5. Generate Algae genAlgae();
		 * 6. Move babies to general population. population.addAll(babies); babies.clear();
		 *
		 */

		/* 1 */
		time_step++;

		/* 2) */
		doTimeSteps();

		/* 3)
		 * First, find fights (encounter).
		 * Then, simulate fighting.
		 */
		doEncounters();

		/* 4*/
		/* checks Critter: allows for removing death critters, energy upkeep, enables Critter to move again */
		updateRestEnergy();

		/* 5 */
		genAlgae();

		/* 6 */
		population.addAll(babies);
		babies.clear();
	}

	private static void doTimeSteps(){
		for(Critter critter : population){
			critter.doTimeStep();
		}
	}
	private static void updateRestEnergy(){
		for(int i = population.size()-1; i >= 0; i--){
			Critter checkCritter = population.get(i);
			checkCritter.energy -= Params.rest_energy_cost;
			/* remove rekt Critters (dead) */
			if(checkCritter.energy <= 0){
				population.remove(i);
			}
			checkCritter.moved = false;
		}
	}
	private static void genAlgae(){
		for(int i = 0; i < Params.refresh_algae_count; i++){
			try{
				makeCritter("Algae");
			}
			catch(InvalidCritterException ice){
				System.out.println("error generating Critter: Algae");
			}
		}
	}
	private static void doEncounters(){
		fighting = true;
		ArrayList<List<Critter>> encounters = findEncounters();
		
	}


	private static ArrayList<List<Critter>> findEncounters(){
		ArrayList<List<Critter>> encounters_list = new ArrayList<>();
		/* HashMap to keep track of positions that Critters are in */
		HashMap<String, Critter> critter_positions = new HashMap<>();

		for(Critter critter : population){
			/* create a coordinate key for the HashMap */
			String key = String.format("%d %d", critter.x_coord, critter.y_coord);

			/* if a battle is about to happen */
			if(critter_positions.containsKey(key)){
				ArrayList<Critter> battle_spot = new ArrayList<>();

				/* add both Critters to the battle ground instance */
				battle_spot.add(critter);
				battle_spot.add(critter_positions.get(key));

				/* add to encounter instance to the list of encounters*/
				encounters_list.add(battle_spot);
			}
			/* if no battle found yet */
			else{
				critter_positions.put(key, critter);
			}
		}
		return encounters_list;
	}


	/**
	 * Helper function for worldTimeStep method. Finds all
	 * the critters that share the same spot in the world.
	 * @return Returns ArrayList of Lists of Critters that
	 * 		   share the same spot.
	 */

	/**
	 * Display board in grid form (LxW).
	 */
	public static void displayWorld() {
		String[][] board = new String[Params.world_height][Params.world_width];

		/* ASCII display sprite of a Critter */
		for(Critter critter : population){
			board[critter.y_coord][critter.x_coord] = critter.toString();
		}

		/* print top border */
		displayBorder();

		/* print the critters/empty spaces */
		for(int columns = 0; columns < Params.world_width; columns++){
			System.out.print('|');
			/* if a space is empty, print a space
			   else print whatever is in it
			 */
			for(int rows = 0; rows < Params.world_height; rows++){
				if(board[columns][rows] == null){
					System.out.print(' ');
				}
				else{
					System.out.print(board[columns][rows]);
				}
			}
			System.out.println('|');
		}

		/* print bottom border */
		displayBorder();

	}

	private static void displayBorder(){
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++) {
			System.out.print("-");
		}
		System.out.println("+");
	}
}
