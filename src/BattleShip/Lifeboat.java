package BattleShip;

class Lifeboat extends Ship
{
	public Lifeboat( String name )
	{
		super(name);
	}

	public char drawShipStatusAtCell( boolean isDamaged )
	{
		return isDamaged ? 'l' : 'L';
	}

	public int getLength()
	{
		return 1;
	}
}
