package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class CL_Agent {
    public static final double EPS = 0.0001;
    private static int _count = 0;
    private static int _seed = 3331;
    private int agentId;
    //	private long _key;
    private geo_location agentPosition;
    private double agentSpeed;
    private edge_data currentEdge;
    private node_data currentNode;
    private directed_weighted_graph graph;
    private CL_Pokemon pokemonDest;
    private long _sg_dt;

    private double score;


    public CL_Agent(directed_weighted_graph g, int start_node) {
        graph = g;
        setScore(0);
        this.currentNode = graph.getNode(start_node);
        agentPosition = currentNode.getLocation();
        agentId = -1;
        setSpeed(0);
    }

    public void update(String json) {
        JSONObject line;
        try {
            // "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
            line = new JSONObject(json);
            JSONObject jsonObjectAgent = line.getJSONObject("Agent");
            int id = jsonObjectAgent.getInt("id");
            if (id == this.getID() || this.getID() == -1) {
                // if (this.getID() == -1)
                {
                    this.agentId = id;
                }
                double speed = jsonObjectAgent.getDouble("speed");
                String p = jsonObjectAgent.getString("pos");
                Point3D location = new Point3D(p);
                int src = jsonObjectAgent.getInt("src");
                int dest = jsonObjectAgent.getInt("dest");
                double value = jsonObjectAgent.getDouble("value");
                this.agentPosition = location;
                this.setCurrNode(src);
                this.setSpeed(speed);
                this.setNextNode(dest);
                this.setScore(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Override
    public int getSrcNode() {
        return this.currentNode.getKey();
    }

    public String toJSON() {
        int d = this.getNextNode();
        String ans = "{\"Agent\":{"
                + "\"id\":" + this.agentId + ","
                + "\"value\":" + this.score + ","
                + "\"src\":" + this.currentNode.getKey() + ","
                + "\"dest\":" + d + ","
                + "\"speed\":" + this.getSpeed() + ","
                + "\"pos\":\"" + agentPosition.toString() + "\""
                + "}"
                + "}";
        return ans;
    }

    private void setScore(double v) {
        this.score = v;
    }

    public boolean setNextNode(int dest) {
        boolean ans = false;
        int src = this.currentNode.getKey();
        this.currentEdge = graph.getEdge(src, dest);
        if (currentEdge != null)
        {
            ans = true;
        }
//        else {
//            currentEdge = null;
//        }
        return ans;
    }

    public void setCurrNode(int src) {
        this.currentNode = graph.getNode(src);
    }

    public boolean isMoving() {
        return this.currentEdge != null;
    }

    public String toString() {
        return toJSON();
    }

    public String toString1() {
        String ans = "" + this.getID() + "," + agentPosition + ", " + isMoving() + "," + this.getValue();
        return ans;
    }

    public int getID()
    {
        return this.agentId;
    }

    public geo_location getLocation()
    {
        return agentPosition;
    }


    public double getValue()
    {
        return this.score;
    }


    public int getNextNode()
    {
        if (this.currentEdge == null)
        {
            return -1;
        }
        return this.currentEdge.getDest();
    }

    public double getSpeed() {
        return this.agentSpeed;
    }

    public void setSpeed(double v) {
        this.agentSpeed = v;
    }

    public CL_Pokemon getPokemonDest() {
        return pokemonDest;
    }

    public void setPokemonDest(CL_Pokemon curr_fruit) {
        this.pokemonDest = curr_fruit;
    }

    public void set_SDT(long ddtt) {
        long ddt = ddtt;
        if (this.currentEdge != null) {
            double w = get_curr_edge().getWeight();
            geo_location dest = graph.getNode(get_curr_edge().getDest()).getLocation();
            geo_location src = graph.getNode(get_curr_edge().getSrc()).getLocation();
            double de = src.distance(dest);
            double dist = agentPosition.distance(dest);
            if (this.getPokemonDest().get_edge() == this.get_curr_edge()) {
                dist = pokemonDest.getLocation().distance(this.agentPosition);
            }
            double norm = dist / de;
            double dt = w * norm / this.getSpeed();
            ddt = (long) (1000.0 * dt);
        }
        this._sg_dt=ddt;
    }

    public edge_data get_curr_edge() {
        return this.currentEdge;
    }

    public long get_sg_dt() {
        return _sg_dt;
    }

//    public void set_sg_dt(long _sg_dt) {
//        this._sg_dt = _sg_dt;
//    }
}
