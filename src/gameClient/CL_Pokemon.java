package gameClient;
import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class CL_Pokemon {

	private edge_data pokmenEdge;
	private double pokmenScore;
	private int pokmenType; // up or down
	private Point3D pokmenPosition;
	private CL_Agent eatAgent=null;

	/**
	 * constructor
	 * @param p
	 * @param t
	 * @param v
	 * @param s
	 * @param e
	 */
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		pokmenType = t;
		pokmenScore = v;
		set_edge(e);
		pokmenPosition = p;

	}

	/**
	 * create and return a pokemon from a string/ json
	 * @param json
	 * @return
	 */
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			JSONObject p = new JSONObject(json);
			int id = p.getInt("id");

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}

	/**
	 * pokemon to string for printing
	 * @return
	 */
	public String toString()
	{
		return "F:{v="+ pokmenScore +", t="+ pokmenType +"}";
	}

	/**
	 * return the pokemon edge is
	 * @return
	 */
	public edge_data get_edge() {
		return pokmenEdge;
	}
	/**
	 * set the pokemon edge is
	 * @return
	 */
	public void set_edge(edge_data e) {
		this.pokmenEdge = e;
	}

	/**
	 * return the pokemon location is
	 * @return
	 */
	public Point3D getLocation() {
		return pokmenPosition;
	}

	/**
	 * get the pokemon type
	 * @return
	 */
	public int getType()
	{
		return pokmenType;
	}

	/**
	 * return the pokemon score
	 * @return
	 */
	public double getScore() {
		return pokmenScore;
	}

	/**
	 * get pokemon eating agent
	 * @return
	 */
	public CL_Agent getEatAgent()
	{
		return this.eatAgent;
	}

	/**
	 * set pokemon eating agent
	 * @param agent
	 */
	public void setEatAgent(CL_Agent agent)
	{
		this.eatAgent=agent;
	}

	/**
	 * equals between tow pokemon
	 * @param p
	 * @return
	 */
	public boolean equals(CL_Pokemon p)
	{
		if(this.getLocation().equals(p.getLocation())&&
				this.getScore()==p.getScore())
			return true;
		return false;
	}
}
