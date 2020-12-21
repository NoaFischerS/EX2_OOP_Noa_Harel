package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class EdgeList {

    private HashMap<Integer, edge_data> NodeNi;

    /**
     * constructor
     */
    public EdgeList() {
        this.NodeNi = new HashMap<Integer, edge_data>();
    }

    /**
     * copy constructor
     * @param l
     */
    public EdgeList(EdgeList l) {
        this.NodeNi = new HashMap<Integer, edge_data>();
        for (int i : l.getKeyL()) {
            addNi(l.getE(i));
        }
    }

    /**
     * adds a new node to the neighbor list
     *
     */
    public void addNi(edge_data e) {
        if(e!=null)
        {
            NodeNi.put(e.getDest(),e);
        }
    }

    /**
     * get the EdgeData of neighbors
     * @param i
     * @return
     */
    public edge_data getE(int i) {
        if (hasE(i)) {
            return NodeNi.get(i);
        }
        return null;
    }

    /**
     * check if this node and the given node are neighbors
     * @param i
     * @return
     */
    public boolean hasE(int i) {
        if (NodeNi.containsKey(i)) {
            return true;
        }
        return false;
    }

    /**
     * return a set values of all the keys that are neighbor of this node
     * @return
     */
    public Set<Integer> getKeyL() {
        return this.NodeNi.keySet();
    }

    /**
     * removes node from the neighbor list
     * @param key
     */
    public void removeE(int key) {
        if (NodeNi.containsKey(key)) {
            NodeNi.remove(key);
        }
    }

    /**
     * return collection of all edges that are neighbors of node
     * @return
     */
    public Collection<edge_data> getV()
    {
       return NodeNi.values();
    }

}