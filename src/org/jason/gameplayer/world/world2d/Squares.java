package org.jason.gameplayer.world.world2d;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.jason.gameplayer.GamePlayer;
import org.jason.gameplayer.game.conwaygol.world.zone.ZoneStates;
import org.jason.gameplayer.world.World;
import org.jason.gameplayer.world.zone.Zone;
import org.jason.gameplayer.world.zone.Zone2D;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;


public class Squares extends World
{
	private Zone2D[][] world;
	private Random rand;
	private final static int X_DIM_DEFAULT = 20;
	private final static int Y_DIM_DEFAULT = 20;

	public final static HashMap<Integer, String> colors = new HashMap<Integer, String>() 
	{
		private static final long serialVersionUID = -1206511141862245217L;
		
		{
			put(ZoneStates.EMPTY_SPACE, "#FFF");
			put(ZoneStates.SPAWNER_SPACE, "#00F");
			put(ZoneStates.ALIVE_SPACE, "#0F0");
			put(ZoneStates.MURDER_SPACE, "#F00");
			put(ZoneStates.BLOCKED_SPACE, "#000");
		}
	};

	public Squares()
	{
		this(X_DIM_DEFAULT, Y_DIM_DEFAULT);
	}

	public Squares(int xDim, int yDim) 
	{
		super();
		world = new Zone2D[xDim][yDim];

		rand = new Random();
		
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
		if(x >= 0 && x < getXDim() && y >= 0 && y < getYDim())
		{
			world[x][y].setState(val);
		}
	}

	public void kill(int x, int y)
	{
		populate(ZoneStates.EMPTY_SPACE,x,y);
	}

	public void addSpawner(int x,int y)
	{
		populate(ZoneStates.SPAWNER_SPACE, x,y);
	}

	public Zone[][] getWorld()
	{
		return world;
	}

	@Override
	public void setWorld(Zone[][] newWorld) 
	{
		this.world = (Zone2D[][]) newWorld;
	}

	@Override
	public World computeNextTransition() 
	{
		//	    Any live cell with fewer than two live neighbours dies, as if caused by under-population.
		//	    Any live cell with two or three live neighbours lives on to the next generation.
		//	    Any live cell with more than three live neighbours dies, as if by overcrowding.
		//	    Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

		Squares nextTransition = new Squares(getXDim(), getYDim());
		
		Zone[][] nextWorldState = new Zone2D[getXDim()][getYDim()];
		int aliveNeighbors;
		int zoneState;
		int neighborState;
		
		for(int row =0; row < world.length; row++)
		{
			for(int col =0; col < world[row].length; col++)
			{
				aliveNeighbors = 0;
				for(Zone zone : getNeighbors(world[row][col]))
				{
					neighborState = zone.getState();
					if(neighborState == ZoneStates.ALIVE_SPACE || neighborState == ZoneStates.SPAWNER_SPACE)
					{
						aliveNeighbors++;
					}
				}
				
//				if(aliveNeighbors > 0)
//				{
//					System.out.println("Position " + row + ", " + col + " has alive neighbors " + aliveNeighbors);
//				}
				
				zoneState = world[row][col].getState();
				
				nextWorldState[row][col] = new Zone2D(row,col);

				if( zoneState == ZoneStates.ALIVE_SPACE  )
				{					
					if(aliveNeighbors == 2 || aliveNeighbors == 3 )
					{

						nextWorldState[row][col].setState(ZoneStates.ALIVE_SPACE);

					}
					else
					{
						nextWorldState[row][col].setState(ZoneStates.EMPTY_SPACE);
					}

				}
				else if(zoneState == ZoneStates.EMPTY_SPACE)
				{						
					if( aliveNeighbors == 3 )//&& rand.nextDouble() <= spawnChance)
					{
						System.out.println("It's Alive!!: " + row + ", " + col);
						nextWorldState[row][col].setState(ZoneStates.ALIVE_SPACE);
					}
					else
					{
						nextWorldState[row][col].setState(ZoneStates.EMPTY_SPACE);
					}
				}
				else
				{
					nextWorldState[row][col].setState(zoneState);
				}
				
			}
		}

		//System.out.println("Transition: "+ getWorld().toString() + " to " +  nextWorldState);
		
		
		nextTransition.setWorld(nextWorldState);
		nextTransition.setTurnCount(getTurnCount() + 1);
				
		return nextTransition;
	}

	@Override
	public String[] getWorldState() 
	{
		String[] retval = new String[world.length];
		StringBuilder builder = new StringBuilder();

		for(int row = 0; row < world.length; row++)
		{
			builder.setLength(0);
			for(int col = 0; col < world[row].length; col++)
			{
				builder.append(world[row][col].getState());
			}

			retval[row] = builder.toString();
		}


		return retval;
	}

	@Override
	public TreeSet<Zone> getNeighbors(Zone target) 
	{
		//list of neighbors should be minimal and unique

		TreeSet<Zone> retval = new TreeSet<Zone>();

		int x = target.getDimension(Zone2D.X_DIM);
		int y = target.getDimension(Zone2D.Y_DIM);

//		int zoneState = world[y][x].getState();
//		if(zoneState != ZoneStates.EMPTY_SPACE)
//		{
			//check row up
			if(y - 1 >= 0 )
			{
				if(x - 1 >= 0)
				{
					retval.add(world[y-1][x-1]);
				}

				retval.add(world[y-1][x]);

				if(x + 1 < getXDim())
				{
					retval.add(world[y-1][x+1]);
				}
			}

			//check row down
			if( y + 1 < getYDim() )
			{
				if(x - 1 >= 0)
				{
					retval.add(world[y+1][x-1]);
				}

				retval.add(world[y+1][x]);

				if(x + 1 < getXDim())
				{
					retval.add(world[y+1][x+1]);
				}
			}

			//check col left
			if(x - 1 >= 0)
			{
				if(y - 1 >= 0)
				{
					retval.add(world[y-1][x-1]);
				}

				retval.add(world[y][x-1]);

				if(y + 1 < getYDim())
				{
					retval.add(world[y+1][x-1]);
				}
			}

			//check col right
			if(x + 1 < getXDim())
			{
				if(y - 1 >= 0)
				{
					retval.add(world[y-1][x+1]);
				}

				retval.add(world[y][x+1]);

				if(y + 1 < getYDim())
				{
					retval.add(world[y+1][x+1]);
				}
			}	
//		}



		return retval;
	}

	@Override
	public String render() 
	{
		StringBuilder retval=  new StringBuilder();
		
		return retval.toString();
	}
}
