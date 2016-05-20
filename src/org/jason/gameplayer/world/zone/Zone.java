package org.jason.gameplayer.world.zone;

import java.util.HashMap;

import org.jason.gameplayer.game.conwaygol.world.zone.ZoneStates;

public abstract class Zone implements Comparable<Zone>
{
	protected HashMap<String, Integer> dimensions;
	protected int state;
	
	public Zone()
	{
		dimensions = new HashMap<String, Integer>();
		state = ZoneStates.EMPTY_SPACE;
	}
	
	public int getDimension(String name)
	{
		return dimensions.get(name);
	}
	
	public void setDimension(String name, int value)
	{
		dimensions.put(name, value);
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setState(int state)
	{
		this.state = state;
	}
	
	public abstract boolean equals(Zone o); 
}
