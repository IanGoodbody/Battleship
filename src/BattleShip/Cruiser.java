package BattleShip;

class Cruiser extends Ship
{
	public Cruiser(String name)
	{
		super(name);
	}

	public char drawShipStatusAtCell(boolean isDamaged)
	{
		return isDamaged ? 'c' : 'C';
	}

	public int getLength()
	{
		return 3;
	}
}
