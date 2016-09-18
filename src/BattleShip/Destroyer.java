package BattleShip;

class Destroyer extends Ship
{
	public Destroyer(String name)
	{
		super(name);
	}

	public char drawShipStatusAtCell(boolean isDamaged)
	{
		return isDamaged ? 'd' : 'D';
	}

	public int getLength()
	{
		return 2;
	}
}
