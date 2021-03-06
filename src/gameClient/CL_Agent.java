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
    private node_data nextNode;
    private directed_weighted_graph graph;
    private CL_Pokemon pokemonDest;
//    private long _sg_dt;
    private double score;

    /**
     * agent constructor
     * @param g
     * @param start_node
     */
    public CL_Agent(directed_weighted_graph g, int start_node) {
        graph = g;
        setScore(0);
        this.currentNode = graph.getNode(start_node);
        agentPosition = currentNode.getLocation();
        agentId = -1;
        setSpeed(0);
    }

    /**
     * update agent information from json
     * @param json
     */
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

    /**
     * get src node of the agent
     * @return
     */
    public int getSrcNode() {
        return this.currentNode.getKey();
    }

    /**
     * agent object to json
     * @return
     */
    public String toJSON() {
        int d = this.getNextNode().getKey();
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

    /**
     * allows setting the achived score of the agent
     * @param v
     */
    private void setScore(double v) {
        this.score = v;
    }

    /**
     * allows setting the next  node of the agent
     * @param dest
     * @return
     */
    public boolean setNextNode(int dest) {
        boolean ans = false;
        int src = this.currentNode.getKey();
        this.currentEdge = graph.getEdge(src, dest);
        if (currentEdge != null)
        {
            ans = true;
        }
        return ans;
    }

    /**
     * allows setting the current node of the agent
     * @param src
     */
    public void setCurrNode(int src) {
        this.currentNode = graph.getNode(src);
    }

    /**
     * return true iff the agent is moving
     * @return
     */
    public boolean isMoving() {
        return this.currentEdge != null;
    }

    /**
     * return string that represent agent
     * @return
     */
    public String toString() {
        return toJSON();
    }

    /**
     * return the agent's id
     * @return
     */
    public int getID()
    {
        return this.agentId;
    }

    /**
     * return agent location
     * @return
     */
    public geo_location getLocation()
    {
        return agentPosition;
    }

    /**
     * return the achived score of the agent
     */
    public double getValue()
    {
        return this.score;
    }

    /**
     * return the next node of agent
     * @return
     */
    public node_data getNextNode()
    {
        return this.nextNode;
    }

    /**
     * allows setting the next step node of the agent
     * @return
     */
    public void setNextNode(node_data n)
    {
        this.nextNode=n;
    }

    /**
     * return agent speed
     * @return
     */
    public double getSpeed() {
        return this.agentSpeed;
    }

    /**
     * allows setting agent speed
     * @param v
     */
    public void setSpeed(double v) {
        this.agentSpeed = v;
    }

    /**
     * return the next Pokemon dest
     * @return
     */
    public CL_Pokemon getPokemonDest() {
        return pokemonDest;
    }

    /**
     * set the next pokemon dest
     * @return
     */
    public void setPokemonDest(CL_Pokemon curr_fruit) {
        this.pokemonDest = curr_fruit;
    }

    /**
     * return the current edge  the agent walks along
     * @return
     */
    public edge_data get_curr_edge() {
        return this.currentEdge;
    }

    /**
     * checks the time to sleep to reduce moves
     * by checking distance and weight
     * @return
     */
        public long timeSleep()
    {
        double weight=get_curr_edge().getWeight();
        double dis=getDisOnEdge();
        double s= weight * dis / this.getSpeed();
        long d=(long)(1000.0 * s);
        return d;
    }

    /**
     * check the time best to sleep to reduce moves by distance and weight
     * after the pokemon is eaten
     * @return
     */
    public long getTimeAfterEat()
    {
        double weight=get_curr_edge().getWeight();
        double dis= 1 - getDisOnEdge();
        double s=weight*dis /this.getSpeed();
        long d=(long)(1000.0 * s);
        return d;
    }

    /**
     * used in sleeping time
     * @return
     */
    private double getDisOnEdge()
    {

        geo_location src=graph.getNode(get_curr_edge().getSrc()).getLocation();
        geo_location dest=graph.getNode(get_curr_edge().getDest()).getLocation();
        double edge=src.distance(dest);
        double dist=agentPosition.distance(dest);
        if(getPokemonDest().get_edge().equals(this.get_curr_edge()))
        {
            dist=getPokemonDest().getLocation().distance(src);
        }
        return dist / edge;

    }

    /**
     * Compers two agents
     * @param a
     * @return
     */
    public boolean equals(CL_Agent a)
    {
        if(this.getID()==a.getID())
            return true;
        return false;

    }

}
