package org.jason.gameplayer.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jason.gameplayer.GamePlayer;
import org.jason.gameplayer.game.Game;
import org.jason.gameplayer.game.conwaygol.world.zone.ZoneStates;
import org.jason.gameplayer.world.zone.Zone;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class GOLUI {

	private final static int MAX_WIDTH = 6;
	private final static int REFRESH_INTERVAL = 5;
	private static Gson gson = new Gson();
	
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
	
	public static String render(String currentState) 
	{
		//currentState is json string of all games
		
		StringBuilder retval = new StringBuilder();
		
		 retval
			.append("<html><head><title>Game Player</title>")
			.append("<script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-1.11.3.min.js\"></script>\n")
			.append("<script type=\"text/javascript\" src=\"http://mbostock.github.com/d3/d3.v2.js\"></script>\n")
			.append("<script type=\"text/javascript\" src=\"viewUpdater.js\"></script>\n")
			.append("</head><body>");
		
		Type type = new TypeToken<Map<String, HashMap<String, Object>>>(){}.getType();
		LinkedTreeMap<String, HashMap<String, Object>> games = gson.fromJson(currentState, type);
		 
		
			
			//.append(currentState)
		retval.append("<table>");
		for(Entry<String, HashMap<String, Object>> game: games.entrySet())
		{
			retval.append("<tr>"
					+ "<td border=\"1\"><table><tr><td><div id=\"gameheader" + game.getKey() + "\"></div></td></tr>"
					+ "<td border=\"1\"><tr><td border=\"1\"><div id=\"gameworld" + game.getKey() + "\"></div></td></tr></table></td>"
					+ "</tr>");
			
//			retval.append(game.getKey())
//			.append(", Turn: ")
//			.append(game.getValue().get(GamePlayer.TURN_DIRECTIVE))
//			.append("<br>\n");
//			
//			ArrayList<String> world = (ArrayList<String>) game.getValue().get(GamePlayer.WORLD_DIRECTIVE);
			
//			for(String row : world)
//			{
//				retval.append(row)
//				.append("<br>\n");
//				
//			}
//			
//			retval.append("<hr>\n");
			
			
			
		}
		retval.append("</table>");
			
		retval.append("</body></html>\n");
		 
		return retval.toString();
	}

}
