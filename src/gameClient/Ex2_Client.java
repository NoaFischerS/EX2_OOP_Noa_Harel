package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gameClient.graphics.MainFrame;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2_Client implements Runnable {
	private static MainFrame windowGame;
	private static Arena arenaGame;
	private static int counterMove=0;

	/**
	 * constructor
	 * @param level
	 * @param id
	 */
	public Ex2_Client(int level ,int id) {
		this.level = level;
		this.id = id;
	}

	/**
	 * start the game
	 * @param level
	 * @param id
	 */
	public static void startGame(int level, int id)
	{
		Thread player = new Thread(new Ex2_Client(level, id));
		player.start(); //start the game
	}

	int level;
	int id;

	/**
	 * main to start the game
	 * @param a
	 */
	public static void main(String[] a) {
		startGame(1, 318926854);

	}

	/**
	 * run Thread
	 */
	@Override
	public void run() {
		//get the level from the server and login
		game_service game = Game_Server_Ex2.getServer(this.level);
		game.login(this.id);

		// creat the graph for the game
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(DWGraph_DS.class, new DWGraph_DS.DWGraph_DSJson());
		Gson gson = builder.create();
		directed_weighted_graph graph = gson.fromJson(game.getGraph(), DWGraph_DS.class);
		init(game); //graphics starts here

		game.startGame();
		double gameTime = game.timeToEnd();
		windowGame.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
		int ind = 0;
		while (game.isRunning()) {
			long d = moveAgants(game, graph);
			try {
				if (ind % 2 == 0) {
					windowGame.repaint();
					windowGame.updateGraphic();
				}
				Thread.sleep(99);
				ind++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String res = game.toString();

		System.out.println(res);
		System.exit(0);
	}

	/**
	 * Moves each of the agents along the edge,
	 * in case the agent is on a node the next destination (next edge) chosen is the closet Pokemon
	 * that have no agent that is on his way to there
	 * @param game
	 * @param graph
	 * @param
	 */
	private static long moveAgants(game_service game, directed_weighted_graph graph) {
		game.move();

		List<CL_Agent> agentsList = Arena.json2Agent(game.getAgents(), graph);
		List<CL_Pokemon> pokemonList = Arena.json2Pokemons(game.getPokemons());

		if(counterMove>0) {
			updatePokToAgent(agentsList);
			updateAgentToPok(pokemonList);
		}

		arenaGame.setAgents(agentsList);
		arenaGame.setPokemons(pokemonList);
//update pokemon
		for (CL_Pokemon p : arenaGame.getPokemons()) {
			Arena.updateEdge(p, graph);
		}

		long d=delayTime(game, graph); //this used for reduce the moves, don't success to used.

		// choose the next step for every node.
		for (CL_Agent agentPlayer : agentsList) {
			if (!agentPlayer.isMoving()){
				int id = agentPlayer.getID();
				double v = agentPlayer.getValue();
				int dest = nextNode(game, graph, agentPlayer);
				agentPlayer.setNextNode(graph.getNode(dest));
				game.chooseNextEdge(agentPlayer.getID(), dest);
				System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
			}
		}
		counterMove++;
		return d;

	}

	/**
	 * trying to make algorithm better didn't succeed. Pokemon of the agent
	 * @param agentsList
	 */
	private static void updatePokToAgent(List<CL_Agent> agentsList)
	{
		for(CL_Agent i:arenaGame.getAgents())
		{
			for(CL_Agent j:agentsList)
			{
				if(i.equals(j))
				{
					j.setPokemonDest(i.getPokemonDest());
				}
			}
		}

	}

	/**
	 * rying to make algorithm better didn't succeed. Agent eating the Pokemon
	 * @param pokemonList
	 */
	private static void updateAgentToPok(List<CL_Pokemon> pokemonList)
	{
		for(CL_Pokemon i :arenaGame.getPokemons())
		{
			for(CL_Pokemon j:pokemonList )
			{
				if(i.equals(j))
				{
					i.setEatAgent(j.getEatAgent());
				}
			}
		}

	}

	/**
	 * didn't succeed
	 * try to calculate the delay time, to reduce the moves and make algorithm better
	 * @param game
	 * @param graph
	 * @return
	 */
	private static long delayTime(game_service game, directed_weighted_graph graph) {
		long d = -1;
		long t=100;
		int movmentAgnet=0;
		for (CL_Agent agent : arenaGame.getAgents()) {
			if(agent.getPokemonDest()!=null)
			{
				if (agent.isMoving()) {
					movmentAgnet++;
					Arena.updateEdge(agent.getPokemonDest(), graph);
					if (eatPok(agent)) {
						t = agent.getTimeAfterEat();
					}
					else {
						t = agent.timeSleep();
					}
					if(t<d ||d==-1)
						d=Math.max(90,t);
				}
			}
		}
		if (movmentAgnet==0)
		return 100;
		return d;
	}

	/**
	 * checks if the dest Pokemon of the agent is eaten by a closer agent that will be there faster
	 * @param a
	 * @return
	 */
	private static boolean eatPok(CL_Agent a)
	{
		for(CL_Pokemon p: arenaGame.getPokemons())
		{
			if(p.getEatAgent()!=null)
				if(p.equals(a.getPokemonDest()))
					return false;
		}
		return true;
	}

	/**
	 * return the next node for the agent's next step,
	 * by find the closet Pokemon
	 * @param game
	 * @param g
	 * @param agentPlayer
	 * @return
	 */
	private static int nextNode(game_service game,directed_weighted_graph g,CL_Agent agentPlayer)
	{
		int src = agentPlayer.getSrcNode();
		dw_graph_algorithms ga = new DWGrpah_Algo();
		ga.init(g);
		CL_Pokemon closetPokemon=closetPokemon(g,agentPlayer);
		if(closetPokemon==null)
			return agentPlayer.getSrcNode();
		closetPokemon.setEatAgent(agentPlayer);
		agentPlayer.setPokemonDest(closetPokemon);
		if(closetPokemon.get_edge().getSrc()==agentPlayer.getSrcNode())
			return closetPokemon.get_edge().getDest();
		return ga.shortestPath(src, closetPokemon.get_edge().getSrc()).get(1).getKey();

	}

	/**
	 * return the closet pokemon from src node,
	 * if not null, meaning the Pokemon is eaten by another agent
	 * continue to the next closest Pokemon
	 * @param g
	 * @param agent
	 * @return
	 */
	public static CL_Pokemon closetPokemon(directed_weighted_graph g, CL_Agent agent)
	{
		int src=agent.getSrcNode();
		dw_graph_algorithms ga = new DWGrpah_Algo();
		ga.init(g);

		CL_Pokemon closetPokemon=null;
		for(CL_Pokemon pokemon: arenaGame.getPokemons())
		{
			if(pokemon.getEatAgent()==null)
			{	closetPokemon=pokemon;
				break;
			}
		}
		if(closetPokemon==null)
			return null;

		for(CL_Pokemon pokemon: arenaGame.getPokemons()) {

			if(pokemon.getEatAgent()!=null)
			{
				continue;
			}
			if (ga.shortestPathDist(src, pokemon.get_edge().getSrc())/pokemon.getScore() <
					ga.shortestPathDist(src, closetPokemon.get_edge().getSrc())/closetPokemon.getScore())
			{
				closetPokemon = pokemon;
				if (src == closetPokemon.get_edge().getSrc()) {
					return pokemon;
				}
			}
		}
		return closetPokemon;
	}

	/**
	 * PriorityQueue to have the highest valuable Pokemon to start
	 * @return
	 */
	private static Queue<CL_Pokemon> pokemonScore()
	{
		Comparator<CL_Pokemon> copm=new Comparator<CL_Pokemon>() {
			@Override
			public int compare(CL_Pokemon p1, CL_Pokemon p2) {
				if(p1.getScore()<p2.getScore())
					return 1;
				else
				if(p1.getScore()==p2.getScore())
					return 0;
				return -1;
			}
		};
		Queue<CL_Pokemon> queue = new PriorityQueue<CL_Pokemon>(copm);
		for(CL_Pokemon p:arenaGame.getPokemons())
			queue.add(p);
		return queue;
	}

	/**
	 * start the game and locate all agent.
	 * creates the GUI.
	 * @param game
	 */
	private void init(game_service game) {
		String pokemon = game.getPokemons();
		GsonBuilder builder=new GsonBuilder();
		builder.registerTypeAdapter(DWGraph_DS.class, new DWGraph_DS.DWGraph_DSJson());
		Gson gson=builder.create();
		directed_weighted_graph graph=gson.fromJson(game.getGraph(),DWGraph_DS.class);
		arenaGame = new Arena();
		arenaGame.setGraph(graph);
		arenaGame.setGame(game);
		arenaGame.setPokemons(Arena.json2Pokemons(pokemon));

		windowGame = new MainFrame("test Ex2", arenaGame);
		windowGame.show();



		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject jsonObject = line.getJSONObject("GameServer");
			int agentsNum = jsonObject.getInt("agents");
			System.out.println(info);
			System.out.println(game.getPokemons());

			for(CL_Pokemon pok: arenaGame.getPokemons())
			{
				Arena.updateEdge(pok,graph);
			}

			Queue<CL_Pokemon> p=pokemonScore();
			for(int i = 0;i<agentsNum;i++) {
				game.addAgent(p.remove().get_edge().getSrc());
			}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
}
