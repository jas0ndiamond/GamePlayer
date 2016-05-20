package org.jason.gameplayer.world.world2d;

import java.util.TreeSet;

import org.jason.gameplayer.world.World;
import org.jason.gameplayer.world.zone.Zone;
import org.jason.gameplayer.world.zone.Zone2D;


public class Hexagons extends World
{
	private Zone2D[][] world;
	private final static int X_DIM_DEFAULT = 10;
	private final static int Y_DIM_DEFAULT = 10;
	
	public static final int EMPTY_SPACE= 0;
	public static final int SPAWNER_SPACE = 1;
	public static final int ALIVE_SPACE = 2;
	
	public Hexagons()
	{
		this(X_DIM_DEFAULT, Y_DIM_DEFAULT);
	}

	public Hexagons(int xDim, int yDim) 
	{
		super();
		world = new Zone2D[xDim][yDim];
		
		resetWorld();
	}
	
	public int getXDim()
	{
		return world.length;
	}
	
	public int getYDim()
	{
		return world[0].length;
	}
	
	public void resetWorld()
	{
		for(int row = 0; row < world.length; row++)
		{
			for(int col = 0; col < world[row].length; col++)
			{
				if(world[row][col] == null)
				{
					world[row][col] = new Zone2D(row,col);
				}
				
				kill(row,col);
			}
		}
	}
	
	public int getSpace(int x, int y)
	{
		return world[x][y].getState();
	}
	
	private void populate(int val, int x, int y)
	{
		
		
		world[x][y].setState(val);
	}
	
	public void kill(int x, int y)
	{
		populate(EMPTY_SPACE,x,y);
	}
	
	public void addSpawner(int x,int y)
	{
		populate(SPAWNER_SPACE, x,y);
	}
	
	public Zone[][] getWorld()
	{
		return world;
	}
	
	public String getWorldString()
	{
		StringBuilder builder = new StringBuilder();
		
		for(int row = 0; row < world.length; row++)
		{
			for(int col = 0; col < world[row].length; col++)
			{
				builder.append(world[row][col]);
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	@Override
	public void setWorld(Zone[][] newWorld) 
	{
		this.world = (Zone2D[][]) newWorld;
	}
	
	@Override
	public World computeNextTransition() 
	{
		return this;
	}
	
	@Override
	public String getWorldState() 
	{
		return "";
	}

	@Override
	public TreeSet<Zone> getNeighbors(Zone target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String render() {
		// TODO Auto-generated method stub
		return null;
	}
}
