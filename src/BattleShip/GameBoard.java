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
		this.cells = new ArrayList< ArrayList<Cell> >(this.rowCount);
		for(int i = 0; i < rowCount; i++){
			this.cells.add(new ArrayList<Cell>(this.colCount));
			for(int j = 0; j < colCount; j++)
				this.cells.get(i).add(new Cell());
		}
	}
	
	public String draw()
	{
		String outStr = "";
		String nl = System.getProperty("line.separator");

		// Pretty top bar
		outStr += '+';
		for(int i = 0; i < colCount; i++)
			outStr += '-';
		outStr += '+' + nl;

		// Boats and stuff
		for(int i = 0; i < rowCount; i++){
			outStr += '|';
			for(int j = 0; j < colCount; j++)
				outStr += this.cells.get(i).get(j).draw();
			outStr += '|' + nl;
		}

		// Pretty bottom bar
		outStr += '+';
		for(int i = 0; i < colCount; i++)
			outStr += '-';
		outStr += '+' + nl;
		
		return outStr;
	}
	
	//add in a ship if it fully 1) fits on the board and 2) doesn't collide w/
	//an existing ship.
	//Returns true on successful addition; false, otherwise
	public boolean addShip(Ship s, Position sternLocation, HEADING bowDirection)
	{
		Position testLocation = new Position(sternLocation.x, sternLocation.y);
		ArrayList<Cell> shipCells = new ArrayList<Cell>(s.getLength());

		// Check that the location is valid
		for(int i = 0; i < s.getLength(); i++){
			// Check that the location is on the game board
			if(testLocation.x < 0 || testLocation.x >= colCount)
				return false;
			if(testLocation.y < 0 || testLocation.y >= rowCount)
				return false;

			// Check if there is alread a ship at that location
			else if(this.cells.get(testLocation.y).get(testLocation.x).getShip() != null)
				return false;

			// Move the loacation by the heading, assume 0,0 is top left
			shipCells.add(this.cells.get(testLocation.y).get(testLocation.x));
			switch(bowDirection){
				case NORTH:
					testLocation.y--;
					break;
				case SOUTH:
					testLocation.y++;
					break;
				case EAST:
					testLocation.x++;
					break;
				case WEST:
					testLocation.x--;
					break;
			}
		}
		// If the for loop completes, the location is valid, add the ship
		for(int i = 0; i < s.getLength(); i++)
			shipCells.get(i).setShip(s);
		s.setPosition(shipCells);
		this.myShips.add(s);
		return true;
	}
	
	//Returns A reference to a ship, if that ship was struck by a missle.
	//The returned ship can then be used to print the name of the ship which
	//was hit to the player who hit it.
	//Ensure you handle missiles that may fly off the grid
	public Ship fireMissle( Position coordinate )
	{
		if(coordinate.x < 0 || coordinate.x >= colCount)
			return null;
		else if(coordinate.y < 0 || coordinate.y >= rowCount)
			return null;

		Cell target = this.cells.get(coordinate.y).get(coordinate.x);
		target.hasBeenStruckByMissile(true);
		return target.getShip();
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
		
		s = new Lifeboat( "Diggny" );
		if( b.addShip(s, new Position(7,3), HEADING.NORTH ) )
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

		b.fireMissle( new Position(7,3) );
		
		b.fireMissle( new Position(0,6) );
		b.fireMissle( new Position(1,6) );
		b.fireMissle( new Position(2,6) );
		b.fireMissle( new Position(3,6) );
		System.out.println( b.draw() );
		
		b.fireMissle( new Position(6,6) );
		System.out.println( b.draw() );

		for(int i = 0; i < b.myShips.size(); i++){
			if(b.myShips.get(i).isAlive())
				System.out.println(b.myShips.get(i).getName() + " has survived!");
			else
				System.out.println(b.myShips.get(i).getName() + " is lost.");
		}
	}

}
