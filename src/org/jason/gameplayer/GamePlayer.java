package org.jason.gameplayer;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import org.jason.gameplayer.game.Game;
import org.jason.gameplayer.ui.GOLUI;
import org.jason.gameplayer.util.ThreadPool;
import org.jason.gameplayer.world.world2d.Squares;

import com.google.gson.Gson;

public class GamePlayer 
{
	private ArrayList<Game> games;
	private Gson gson;
	
	public final static String NAME_DIRECTIVE = "name";
	public final static String TURN_DIRECTIVE = "turn";
	public final static String WORLD_DIRECTIVE = "world";
	
	//one game per thread
	private ThreadPool gameThreadPool;
	
	public GamePlayer()
	{
		games = new ArrayList<Game>();
		
		gson = new Gson();

		
	}
	
	public void addGame(Game game)
	{
		games.add(game);
	}
	
	public synchronized String getCurrentState()
	{
		LinkedHashMap<String, Object> states = new LinkedHashMap<String, Object>();
		
		LinkedHashMap<String, Object> thisGameState;
		for(Game thisGame : games)
		{
			thisGameState = new LinkedHashMap<String, Object>();
			
			thisGameState.put(NAME_DIRECTIVE, thisGame.getName());
			thisGameState.put(TURN_DIRECTIVE, thisGame.getCurrentWorld().getTurnCount() );
			thisGameState.put(WORLD_DIRECTIVE, thisGame.getCurrentWorld().getWorldState() );
			
			states.put(thisGame.getName(), thisGameState);
		}
		
		return gson.toJson(states);
	}
	
	public void start()
	{
		int poolSize = 6; 
		
		gameThreadPool = new ThreadPool(poolSize);
		
		Collections.sort(games);
		for(Game game : games)
		{
			System.out.println("Starting game " + game.getName());
			gameThreadPool.runTask(game);
		}
	}
	
	public void shutdown()
	{
		for(Game game : games)
		{
			game.stopGame();
		}
		
		if(gameThreadPool != null)
		{
			gameThreadPool.close();
		}
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		GamePlayer gameplayer = new GamePlayer();
		
		Squares world1 = new Squares();
		
		//need at least a 3x3 box to reliably spawn continuously
		
		//world1.addSpawner(4, 4);
		world1.addSpawner(4, 5);
		//world1.addSpawner(4, 6);
		world1.addSpawner(5, 4);
		world1.addSpawner(5, 5);
		world1.addSpawner(5, 6);
		//world1.addSpawner(6, 4);
		world1.addSpawner(6, 5);
		//world1.addSpawner(6, 6);
		
		//world1.addSpawner(4, 14);
		world1.addSpawner(4, 15);
		//world1.addSpawner(4, 16);
		world1.addSpawner(5, 14);
		world1.addSpawner(5, 15);
		world1.addSpawner(5, 16);
		//world1.addSpawner(6, 14);
		world1.addSpawner(6, 15);
		//world1.addSpawner(6, 16);
		
		//world1.addSpawner(14, 4);
		world1.addSpawner(14, 5);
		//world1.addSpawner(14, 6);
		world1.addSpawner(15, 4);
		world1.addSpawner(15, 5);
		world1.addSpawner(15, 6);
		//world1.addSpawner(16, 4);
		world1.addSpawner(16, 5);
		//world1.addSpawner(16, 6);
		
		//world1.addSpawner(14, 14);
		world1.addSpawner(14, 15);
		//world1.addSpawner(14, 16);
		world1.addSpawner(15, 14);
		world1.addSpawner(15, 15);
		world1.addSpawner(15, 16);
		//world1.addSpawner(16, 14);
		world1.addSpawner(16, 15);
		//world1.addSpawner(16, 16);
				
		world1.setSpawnChance(1.0);
		
		Squares world2 = new Squares();
		
		for(int i = 0; i < world2.getXDim(); i++)
		{
			world2.addSpawner(i, 0);
			world2.addSpawner(i, world2.getYDim() - 1);
			world2.addSpawner(0, i);
			world2.addSpawner(world2.getYDim() - 1, i);
			world2.addSpawner(i, i);
		}
		
		Game game1 = new Game("Game1", 1, world1);
		Game game2 = new Game("Game2", 10, world2);
				
		gameplayer.addGame(game1);
		//gameplayer.addGame(game2);
		
		gameplayer.start();
		

		
		//==================================
//		boolean running = true;
//		
//		while(running)
//		{
//			System.out.println(conway.getCurrentState());
//			Thread.sleep(1000);
//		}
		
		staticFileLocation("/public");
		
		get("/golstate", (req, res) -> 
		{
			res.type("application/json");
			return gameplayer.getCurrentState();	
		});
		
		get("/gol", (req, res) -> 
		{
			//lookup game by name, and invoke its render function
			
			res.type("text/html");
			
//			StringBuilder retval = new StringBuilder();
//			
//			 retval
//				.append("<html><head><title>Conway's Game Of Life</title>")
//				.append("<script type=\"text/javascript\" src=\"http://mbostock.github.com/d3/d3.v2.js\"></script>\n")
//				.append("</head><body>");
//			
//			retval.append("</body></html\n");
			 
			return GOLUI.render(gameplayer.getCurrentState());	
		});
		
		get("/shutdown", (req, res) -> 
		{
			gameplayer.shutdown();
			return "Shutdown initiated";
		});
	}
}



