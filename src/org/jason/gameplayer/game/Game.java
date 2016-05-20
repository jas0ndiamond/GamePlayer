package org.jason.gameplayer.game;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jason.gameplayer.world.World;
import org.jason.gameplayer.world.world2d.Squares;

public class Game implements Runnable, Comparable<Game>
{
	
	public final static int DEFAULT_HISTORY_SIZE = 10;
	private ArrayList<World> history;
	
	private String name;
	private AtomicBoolean isRunning;
	private int historySize;
	private long transitionDelay;
	
	private final static long DEFAULT_TRANSITION_DELAY = 1000;
	
	public Game(String name)
	{
		this(name, DEFAULT_HISTORY_SIZE);
	}
	public Game(String name, int historySize)
	{
		this(name, historySize, new Squares());
	}
	
	public Game(String name, int historySize, World startingWorld)
	{
		this.name = name;
		this.historySize = historySize;
		
		history = new ArrayList<World>(this.historySize + 1);
		
		history.add(startingWorld);
		
		isRunning = new AtomicBoolean(true);
		
		transitionDelay= DEFAULT_TRANSITION_DELAY;
	}
	
	public long getTransitionDelay() 
	{
		return transitionDelay;
	}

	public void setTransitionDelay(long transitionDelay) 
	{
		this.transitionDelay = transitionDelay;
	}

	public World getCurrentWorld()
	{
		return history.get(history.size()-1);
	}
		
	public String getName()
	{
		return name;
	}
	
	public void stopGame()
	{
		isRunning.set(false);
	}
	
	public synchronized void transition()
	{
		//compute the next iteration
		//history.add(Transitioner.conwayGOLTransitioner(currentWorld));
		history.add(getCurrentWorld().computeNextTransition());
		
		//add the current world to history, pop old one if necessary
		if(history.size() >= historySize)
		{
			history.remove(0);
		}
	}
	
	@Override
	public void run() 
	{
		isRunning.set(true);
		while(isRunning.get())
		{
			transition();
			try 
			{
				Thread.sleep(transitionDelay);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public int compareTo(Game otherGame) 
	{
		if(otherGame != null)
		{
			return getName().compareTo(otherGame.getName());
		}
		
		return 0; 
	}
}
