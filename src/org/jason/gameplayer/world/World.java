package org.jason.gameplayer.world;

import java.util.TreeSet;

import org.jason.gameplayer.world.zone.Zone;

public abstract class World
{

	
	private long turnCount;
	protected double spawnChance = .08;

	public World()
	{
		turnCount = 0;
	}
	
	public abstract Zone[][] getWorld();
	
	public abstract void setWorld(Zone[][] world);

	public long getTurnCount()
	{
		return turnCount;
	}
	
	public void setTurnCount(long turnCount)
	{
		this.turnCount = turnCount;
	}
	
	public void addTurn()
	{
		setTurnCount(getTurnCount() + 1L);
	}
	
	public double getSpawnChance() {
		return spawnChance;
	}

	public void setSpawnChance(double spawnChance) {
		this.spawnChance = spawnChance;
	}
	
	public abstract void resetWorld();
	
	public abstract Object getWorldState();
	
	public abstract World computeNextTransition();
	
	public abstract TreeSet<Zone> getNeighbors(Zone target);
	
	public abstract String render();
}
