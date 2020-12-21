package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons and avoid the Zombies.
 * @author boaz.benmoshe
 *
 */
public class Arena {
	public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
	private directed_weighted_graph graph;
	private List<CL_Agent> agentsList;
	private List<CL_Pokemon> pokemonList;
	private List<String> info;
	private static Point3D MIN = new Point3D(0, 100,0);
	private static Point3D MAX = new Point3D(0, 100,0);
	private game_service game;

	/**
	 * constructor
	 */
	public Arena()
	{
		info = new ArrayList<String>();
	}

	/**
	 * copy constructor
	 * @param g
	 * @param agents
	 * @param pokemon
	 */
	private Arena(directed_weighted_graph g, List<CL_Agent> agents, List<CL_Pokemon> pokemon)
	{
		this.graph = g;
		this.setAgents(agents);
		this.setPokemons(pokemon);
	}

	/**
	 * set pokemon for arena
	 * @param p
	 */
	public void setPokemons(List<CL_Pokemon> p)
	{
		this.pokemonList = p;
	}
	/**
	 * set agents for arena
	 * @param a
	 */
	public void setAgents(List<CL_Agent> a)
	{
		this.agentsList = a;
	}

	/**
	 * return the left time until ending of the game
	 * @return
	 */
	public long getTime_left() {
		return game.timeToEnd();
	}

	/**
	 * return game service
	 * @return
	 */
	public game_service getGame() {
		return game;
	}
/**
 * set the game service
 */
	public void setGame(game_service game) {
		this.game = game;
	}

	/**
	 * set the graph
	 */
	public void setGraph(directed_weighted_graph g)
	{
		this.graph =g;
	}

	/**
	 * return agents list
	 * @return
	 */
	public List<CL_Agent> getAgents()
	{
		return agentsList;
	}
	/**
	 * return pokemon list
	 * @return
	 */
	public List<CL_Pokemon> getPokemons()
	{
		return pokemonList;
	}

	/**
	 * return graph
	 * @return
	 */
	public directed_weighted_graph getGraph()
	{
		return graph;
	}

	public List<String> get_info()
	{
		return info;
	}
	/**
	 * create and return agent list from json/string
	 * @param s
	 * @param g
	 * @return
	 */
	public static List<CL_Agent> json2Agent(String s, directed_weighted_graph g) {
		ArrayList<CL_Agent> agentsListAns = new ArrayList<CL_Agent>();
		try {
			JSONObject jsonObject = new JSONObject(s);
			JSONArray jsonArray = jsonObject.getJSONArray("Agents");
			for(int i=0;i<jsonArray.length();i++) {
				CL_Agent c = new CL_Agent(g,0);
				c.update(jsonArray.get(i).toString());
				agentsListAns.add(c);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return agentsListAns;
	}

	/**
	 * create and return pokemon list from json/string
	 * @param fs
	 * @return
	 */
	public static ArrayList<CL_Pokemon> json2Pokemons(String fs) {
		ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>();
		try {
			JSONObject jsonObject = new JSONObject(fs);
			JSONArray jsonArray = jsonObject.getJSONArray("Pokemons");
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject pp = jsonArray.getJSONObject(i);
				JSONObject pk = pp.getJSONObject("Pokemon");
				int t = pk.getInt("type");
				double v = pk.getDouble("value");
				//double s = 0;//pk.getDouble("speed");
				String p = pk.getString("pos");
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);
				ans.add(f);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		return ans;
	}

	/**
	 * update the edge for a pokemon
	 * @param p
	 * @param g
	 */
	public static void updateEdge(CL_Pokemon p, directed_weighted_graph g) {
		for(node_data n:g.getV())
		{
			for(edge_data e: g.getE(n.getKey()))
			{
				boolean f = isOnEdge(p.getLocation(), e,p.getType(), g);
				if(f)
				{
					p.set_edge(e);
					return;
				}
			}
		}
	}

	/**
	 * all this next methode check if the pokemon is on edge and update it.
	 * @param p
	 * @param src
	 * @param dest
	 * @return
	 */
	private static boolean isOnEdge(geo_location p, geo_location src, geo_location dest )
	{
		double dist = src.distance(dest);
		double d1 = src.distance(p) + p.distance(dest);
		if(dist>(d1-EPS2))
		{
			return true;
		}
		return false;
	}

	private static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g) {
		geo_location src = g.getNode(s).getLocation();
		geo_location dest = g.getNode(d).getLocation();
		return isOnEdge(p,src,dest);
	}
	private static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g) {
		int src = g.getNode(e.getSrc()).getKey();
		int dest = g.getNode(e.getDest()).getKey();
		if(type<0 && dest>src)
		{
			return false;
		}
		if(type>0 && src>dest)
		{
			return false;
		}
		return isOnEdge(p,src, dest, g);
	}

	private static Range2D GraphRange(directed_weighted_graph g) {
		Iterator<node_data> itr = g.getV().iterator();
		double x0=0,x1=0,y0=0,y1=0;
		boolean first = true;
		while(itr.hasNext()) {
			geo_location p = itr.next().getLocation();
			if(first) {
				x0=p.x(); x1=x0;
				y0=p.y(); y1=y0;
				first = false;
			}
			else {
				if(p.x()<x0) {x0=p.x();}
				if(p.x()>x1) {x1=p.x();}
				if(p.y()<y0) {y0=p.y();}
				if(p.y()>y1) {y1=p.y();}
			}
		}
		Range xr = new Range(x0,x1);
		Range yr = new Range(y0,y1);
		return new Range2D(xr,yr);
	}
	public static Range2Range w2f(directed_weighted_graph g, Range2D frame) {
		Range2D world = GraphRange(g);
		Range2Range ans = new Range2Range(world, frame);
		return ans;
	}

}
