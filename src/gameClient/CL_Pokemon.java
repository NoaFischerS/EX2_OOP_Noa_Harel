package gameClient;
import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class CL_Pokemon implements Comparable<CL_Pokemon>{
	private edge_data pokmenEdge;
	private double pokmenScore;
	private int pokmenType; // up or down
	private Point3D pokmenPosition;
	private double min_dist;  //????
	private int min_ro;//????
	private CL_Agent eatAgent=null;

	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		pokmenType = t;
		pokmenScore = v;
		set_edge(e);
		pokmenPosition = p;
		min_dist = -1;
		min_ro = -1;
	}
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
	public String toString()
	{
		return "F:{v="+ pokmenScore +", t="+ pokmenType +"}";
	}
	public edge_data get_edge() {
		return pokmenEdge;
	}

	public void set_edge(edge_data e) {
		this.pokmenEdge = e;
	}

	public Point3D getLocation() {
		return pokmenPosition;
	}
	public int getType()
	{
		return pokmenType;
	}
//	public double getSpeed() {return _speed;}

	public double getScore() {
		return pokmenScore;
	}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double mid_dist) {

		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}

	public CL_Agent getEatAgent()
	{
		return this.eatAgent;
	}

	public void setEatAgent(CL_Agent agent)
	{
		this.eatAgent=agent;
	}

	@Override
	public int compareTo( CL_Pokemon p) {
		if(this.getScore()<p.getScore())
			return-1;
		else
		if(this.getScore()==p.getScore())
			return 0;
		return 1;
	}
}
