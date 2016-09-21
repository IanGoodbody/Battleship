package BattleShip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Client
{
	final String NEWL = System.getProperty("line.separator");
	
	private String name = "Player";
	PrintWriter out = null;
	BufferedReader in = null;
	GameManager man = null;
	GameBoard board = new GameBoard(10,10);
	GameBoard targets = new GameBoard(10,10);
	
	Client( BufferedReader in, PrintWriter out, GameManager manager )
	{
		this.in = in;
		this.out = out;
		this.man = manager;
	}
	
	public void playGame() throws IOException
	{
		this.out.println( NEWL + NEWL + "   Missiles Away! Game has begun" );
		this.out.println( "   To Launch a missle at your enemy:" );
		this.out.println( "F 2 4" );
		this.out.println( "Fires a missile at coordinate x=2, y=4." );
		
		// put Code Here to process in game commands, after each command, print the target board and game board w/ updated state 
		while(true)
		{
			this.out.println( "------------------------" );
			this.out.println( "Target Board:\n" + this.targets.draw() );
			this.out.println( "Your Ships:\n" + this.board.draw() );
			this.out.println( "   Waiting for Next Command...\n\n" );
			this.out.flush();

			// Process user input
			this.processCommand();
			//Perform test here to see if we have won or lost
			if(this.allMyShipsAreDestroyed()){
				this.out.println(this.man.getOpponent(this).getName() + " has destroyed all your ships; you lose.");
				this.out.println("You may want to try somethime more your speed, like Candyland.");
				this.out.flush();
				break;
			}
			else if(this.allEnemyShipsAreDestroyed()){
				this.out.println("Congratulations you have destroyed all of " +
					this.man.getOpponent(this).getName() + "'s ships.");
				this.out.println("You won!");
				this.out.flush();
				break;
			}
		}
	}
	
	//Returns a bool, true iff all of this client's ships are destroyed
	boolean allMyShipsAreDestroyed()
	{
		boolean destroyed = true;
		for(int i = 0; i < this.board.myShips.size(); i++)
			destroyed = destroyed && !this.board.myShips.get(i).isAlive();
		return destroyed;
	}

	//Returns a bool, true iff all of the opponent's ships are destroyed
	boolean allEnemyShipsAreDestroyed()
	{
		return this.man.getOpponent(this).allMyShipsAreDestroyed();
	}

	//"F 2 4" = Fire command
	//"C Hello world, i am a chat message"
	//"D" - Redraw the latest game and target boards
	boolean processCommand() throws IOException
	{
		String cmd = this.in.readLine();
		if(cmd.length() == 0)
			return false;

		if(cmd.toUpperCase().startsWith("F")){
			// Check and process the fire command
			String[] coords = cmd.split(" ");
			if(coords.length != 3)
				return false;
			if(!coords[1].matches("\\d+"))
				return false;
			if(!coords[2].matches("\\d+"))
				return false;

			String[] loc = {coords[1], coords[2]};
			return this.processFireCmd(loc);
		}
		else if(cmd.toUpperCase().startsWith("C")){
			if(cmd.length() < 3)
				return false;

			return processChatCmd( cmd.substring(2) );
		}
		else if(cmd.toUpperCase().startsWith("D")){
			// The main game loop will do this once this function returns.
			return true;
		}
		else
			return false;

	}
	
	//When a fire command is typed, this method parses the coordinates and launches a missle at the enemy
	boolean processFireCmd( String [] s )
	{
		Position coordinate = new Position(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
		this.targets.fireMissle(coordinate); 
		Ship hit = this.man.getOpponent(this).board.fireMissle(coordinate);
		if(hit != null){
			this.targets.cells.get(coordinate.y).get(coordinate.x).setShip(hit);
			this.out.println("Captain! Direct hit on the " + hit.getName());
			this.out.flush();
		}
		else{
			this.out.println("It's a miss Captain, but I think we at least spooked a shark...");
			this.out.flush();
		}
		return true;
	}
	
	//Send a message to the opponent
	boolean processChatCmd( String s )
	{
		this.man.getOpponent(this).out.println(s);
		this.man.getOpponent(this).out.flush();
		return true;
	}
	
	GameBoard getGameBoard() { return this.board; }
	
	public void initPlayer() throws IOException
	{
		//1.Get player name
		this.out.println("What... is your name?");
		this.out.flush();
		this.name = in.readLine();
		System.out.println(this.name + " is connected.");

		//2.Print out instructions
		
//Here's some nice instructions to show a client		
		this.out.println("   You will now place 2 ships. You may choose from Cruiser (C), " );
		this.out.println("   Lifeboat (L), or Destroyer (D) as the ships in play...");
		this.out.println("   Enter Ship info. An example input looks like:");
		this.out.println("\nD 2 4 S USS MyBoat\n");
		this.out.println("   The above line creates a Destroyer with the stern located at x=2 (col)," );
		this.out.println("   y=4 (row) and the front of the ship will point to the SOUTH (valid" );
		this.out.println("   headings are N, E, S, and W.\n\n" );
		this.out.println("   the name of the ship will be \"USS MyBoat\"");
		this.out.flush();
		
		//Get ship locations from the player for all 2 ships (or more than 2 if you're using more ships)
		for(int i = 1; i <= 2; ){ // iterator at the bottom of the loop, checks for valid sip location
			out.println("Enter Ship " + i + " information:");
			out.flush();
			String[] fields = in.readLine().split(" ", 5);
			System.out.println(this.name + " entered:");
			for(int ii = 0 ; ii < fields.length; ii++)
				System.out.println("\t" + fields[ii]);
			
			if(fields.length < 5){
				this.out.println("Missing one or more arguments, please try again.");
				this.out.flush();
				System.out.println(this.name + " is missing one or more arguments");
				continue;
			}
			
			boolean goodShip = false;
			while(!goodShip){
				// Validate user input, replace if necessary 
				switch(fields[0].toUpperCase()){
					case "L":
						goodShip = true;
						break;
					case "D":
						goodShip = true;
						break;
					case "C":
						goodShip = true;
						break;
					default:
						this.out.println(fields[0] + " is an invalid field please use either");
						this.out.println("	C => Cruiser");
						this.out.println("	D => Destroyer");
						this.out.println("	L => Lifeboat");
						this.out.println("Please re-enter ship type:");
						this.out.flush();
						System.out.println(this.name + " gave a bad ship ID");
						fields[0] = in.readLine();
						break;
				}
			}
			Ship s;
			switch(fields[0].toUpperCase()){
				case "L":
					s = new Lifeboat(fields[4]);
					break;
				case "D":
					s = new Destroyer(fields[4]);
					break;
				default: // Default to cruiser, this should be a C but the compiler is freaking out
					s = new Cruiser(fields[4]);
					break;
			}

			// Parse position and direction
			int x, y;
			while(true){
				if(fields[1].matches("\\d+")){
					x = Integer.parseInt(fields[1]);
					break;
				}
				else{
					this.out.println(fields[1] + " is not an integer value, try again");
					this.out.flush();
					System.out.println(this.name + " gave a bad X coordinate");
					fields[1] = in.readLine();
				}
			}
			while(true){
				if(fields[2].matches("\\d+")){
					y = Integer.parseInt(fields[2]);
					break;
				}
				else{
					this.out.println(fields[2] + " is not an integer value, try again");
					this.out.flush();
					System.out.println(this.name + " gave a bad Y coordinate");
					fields[2] = in.readLine();
				}
			}
			Position sPos = new Position(x, y);
				
			boolean goodHeading = false;
			while(!goodHeading){
				// Validate user input, replace if necessary
				switch(fields[3].toUpperCase()){
					case "N":
						goodHeading = true;
						break;
					case "S":
						goodHeading = true;
						break;
					case "E":
						goodHeading = true;
						break;
					case "W":
						goodHeading = true;
						break;
					default:
						out.println(fields[3] + " is an invalid direction please use ");
						out.println("'N', 'S', 'E', or 'W'");
						out.println("Please re-enter ship heading:");
						out.flush();
						System.out.println(this.name + " gave a bad heading");
						fields[3] = in.readLine();
				}
			}
			HEADING head;
			switch(fields[3].toUpperCase()){
				case "N":
					head = HEADING.NORTH;
					break;
				case "S":
					head = HEADING.SOUTH;
					break;
				case "E":
					head = HEADING.EAST;
					break;
				default: // Default to west, there is a narrow set of values but the compiler needs a default fo the assignment
					head = HEADING.WEST;
					break;
			}

			// Paramters are all type-checked try and add the ship
			if( !this.board.addShip(s, sPos, head) ){
				this.out.println("The ship could not be placed, try again.");
				this.out.flush();
				System.out.println(this.name + "'s ship " + s.getName() + " could not be added.");
				continue;
			}
			else{
				this.out.println(s.getName() + " has been added.");
				this.out.flush();
			}
			i++;
		}
		
		//After all game state is input, draw the game board to the client
		
		
		System.out.println( "Waiting for other player to finish their setup, then war will ensue!" );
	}
	
	String getName() { return this.name; }
	
	public static void main( String [] args )
	{
		// Should work without GameManager Class to mediate.
		// Primarily tests the initialization functions
		Client testPlayer = new Client( new BufferedReader( new InputStreamReader(System.in)), new PrintWriter(System.out), null);
		try{ testPlayer.initPlayer(); }
		catch(IOException e){
			e.printStackTrace();
		}
		System.out.println(testPlayer.board.draw());
	}
}
