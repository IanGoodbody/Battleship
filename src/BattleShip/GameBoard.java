package BattleShip;
import java.util.ArrayList;

public class GameBoard
{
	int rowCount = 10;
	int colCount = 10;
	
	final String LINE_END = System.getProperty("line.separator"); 
	
	ArrayList< ArrayList<Cell> > cells;
	ArrayList< Ship > myShips = new ArrayList<Ship>();
	
	public GameBoard( int rowCount, int colCount )
	{
		this.rowCount = rowCount;
		this.colCount = colCount;
		
		//create the 2D array of cells
		cells = new ArrayList<Cell>(rowCount);
		for(int i = 0; i < rowCount; i++)i
		{
			cells.set(i, new ArrayList<Cell>(colCount);
			for(int j = 0; j < colCount; j++)
				cells.get(i).set(j) = new Cell();
		}
	}
	
	public String draw(){
		String outStr = "";

		// Pretty top bar
		outStr.concat("+");
		for(int i = 0; i < colCount; i++)
			outStr.concat("-");
		outStr.concat("+\n");

		// Boats and stuff
		for(int i = 0; i < rowCount; i++){
			outStr.concat("|");
			for(int j = 0; j < colCount; j++)
				ourStr.concat(cells.get(i).get(j).draw());
			outStr.concat("|\n");
		}

		// Pretty bottom bar
		outStr.concat("+");
		for(int i = 0; i < colCount; i++)
			outStr.concat("-");
		outStr.concat("+\n");
		
		return outStr;
	}
	
	//add in a ship if it fully 1) fits on the board and 2) doesn't collide w/
	//an existing ship.
	//Returns true on successful addition; false, otherwise
	public boolean addShip(Ship s, Position sternLocation, HEADING bowDirection){
		// Check that the location is valid
		testLocation = new Position(sternLocation.x, sternLocation.y);
		for(int i = 0; i < s.getLength(); i++){
			// Check that the location is on the game board
			if(testLocation.x < 0 || testLocation.x >= colCount)
				return false;

			// Check if there is alread a ship at that location
			else if(cells.get(testLocation.y).get(testLocation.x).getShip() != 
				null)
				return false;

			// Move the loacation by the heading, assume 0,0 is top left
			switch(bowDirect){
				case HEADING.NORTH:
					testLocation.y--;
					break;
				case HEADING.SOUTH:
					testLocation.y++;
					break;
				case HEADING.EAST:
					testLocation.x++;
					break;
				case HEADING.WEST:
					testLocation.x--;
					break;
			}
		}
		// If the for loop completes, the location is valid, add the ship
		myShips.add(s);
	}
	
	//Returns A reference to a ship, if that ship was struck by a missle.
	//The returned ship can then be used to print the name of the ship which
	//was hit to the player who hit it.
	//Ensure you handle missiles that may fly off the grid
	public Ship fireMissle( Position coordinate )
	{
		
	}
	
	//Here's a simple driver that should work without touching any of the code below this point
	public static void main( String [] args )
	{
		System.out.println( "Hello World" );
		GameBoard b = new GameBoard( 10, 10 );	
		System.out.println( b.draw() );
		
		Ship s = new Cruiser( "Cruiser" );
		if( b.addShip(s, new Position(3,6), HEADING.WEST ) )
			System.out.println( "Added " + s.getName() + "Location is " );
		else
			System.out.println( "Failed to add " + s.getName() );
		
		s = new Destroyer( "Vader" );
		if( b.addShip(s, new Position(3,5), HEADING.NORTH ) )
			System.out.println( "Added " + s.getName() + "Location is " );
		else
			System.out.println( "Failed to add " + s.getName() );
		
		System.out.println( b.draw() );
		
		b.fireMissle( new Position(3,5) );
		System.out.println( b.draw() );
		b.fireMissle( new Position(3,4) );
		System.out.println( b.draw() );
		b.fireMissle( new Position(3,3) );
		System.out.println( b.draw() );
		
		b.fireMissle( new Position(0,6) );
		b.fireMissle( new Position(1,6) );
		b.fireMissle( new Position(2,6) );
		b.fireMissle( new Position(3,6) );
		System.out.println( b.draw() );
		
		b.fireMissle( new Position(6,6) );
		System.out.println( b.draw() );
	}

}
