package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.*;

public class DWGrpah_Algo implements dw_graph_algorithms{
    private directed_weighted_graph graph;

    /**
     * constructor
     */
    public DWGrpah_Algo()
    {
        graph=new DWGraph_DS();
    }

    /**
     * init graph algo to a specific given graph
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        if(g!=null)
        {
            this.graph=g;
        }
    }

    /**
     * return this graph
     * @return
     */
    @Override
    public directed_weighted_graph getGraph() {
        return graph;
    }

    /**
     * return copy of this graph
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        return new DWGraph_DS(graph);
    }

    /**
     * checks if the graph is a Strong connected graph
     * meaning that from every node there is a path to all the other nodes.
     * @return
     */
    @Override
    public boolean isConnected() {
        if (graph.nodeSize() <= 1) {
            return true;
        }
        node_data n = graph.getV().iterator().next();
        HashMap<Integer, Weight> temp = Dijkstra(n.getKey());
        clear();
        if (temp.size() != graph.nodeSize())
        {
            return false;
        }
        directed_weighted_graph gRevers=reverseGraph(graph);
        n = gRevers.getV().iterator().next();
        temp = Dijkstra(n.getKey());
        clear();
        if (temp.size() == gRevers.nodeSize())
        {
            return true;
        }
        return false;
    }

    /**
     * return the distance of the shortest path between two given nodes
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) != null && graph.getNode(dest) != null) {
            if(src==dest)
                return 0;
            HashMap<Integer, Weight> hash = Dijkstra(src);
            if(hash.get(dest)!=null)
            {
                double path = hash.get(dest).getW();
                clear();
                return path;
            }
        }
        return -1;
    }

    /**
     * return the shortest path between two given nodes
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> path = new LinkedList<node_data>();
        if (graph.getNode(src) != null && graph.getNode(dest) != null)
        {
            HashMap<Integer, Weight> tempH = Dijkstra(src);
            if((tempH.get(dest)!=null)|| src==dest)
            {
                int i = dest;
                path.add(0, graph.getNode(dest));
                while (i != src) {
                    Weight k = tempH.get(i); // get the father of the currently node
                    path.add(0, graph.getNode(k.faterKey));
                    i = k.getFaterKey();
                }
            }
            clear();
        }
        return path;
    }

    /**
     * saves the graph to a file
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {

        try {
            GsonBuilder gson =new GsonBuilder();
            gson.registerTypeAdapter(DWGraph_DS.class, new DWGraph_DS.DWGraph_DSJson());
            Gson g= gson.create();
            PrintWriter gFile=new PrintWriter(new File(file));
            gFile.write(g.toJson(graph));
            gFile.close();
            return true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("can't write the graph to a file ");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * reads the graph from a file
     * @param file - file name of JSON file
     * @return
     */
    @Override
    public boolean load(String file) {

        try {
            GsonBuilder builder=new GsonBuilder();
            builder.registerTypeAdapter(DWGraph_DS.class, new DWGraph_DS.DWGraph_DSJson());
            Gson gson=builder.create();
            BufferedReader gFile=new BufferedReader(new FileReader(file));
            directed_weighted_graph g=gson.fromJson(gFile,DWGraph_DS.class);
            init(g);
            return true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("can't read the graph from the file");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Dijkstra algorithm to check if a graph is connected and to find the shortest path between two nodes.
     * @param src
     * @return
     */
    private HashMap<Integer, Weight> Dijkstra(int src) {
        Queue<Weight> queue = new PriorityQueue<Weight>();
        HashMap<Integer, Weight> hash = new HashMap<Integer, Weight>(); // hash for father key.
        hash.put(src, null);
        Weight w=new Weight(src,0,-1);
        queue.add(w);

        while (!queue.isEmpty()) {
            Weight n = queue.remove();
            if(graph.getNode(n.getKey()).getInfo().equals("x"))
            {
                for (edge_data i : graph.getE(n.getKey()))
                {
                    if (graph.getNode(i.getDest()).getInfo().equals("x"))
                    {
                        double weight = n.getW() + i.getWeight();
                        if((hash.get(i.getDest())==null)||(hash.get(i.getDest()).getW()>weight))
                        {
                            Weight e=new Weight(i.getDest(),weight, n.key);
                            queue.add(e);
                            hash.put(i.getDest(), e);
                        }
                    }
                }
                graph.getNode(n.getKey()).setInfo("v");
            }
        }
        return hash;
    }

    /**
     * clear node details
     */
    private void clear()
    {
        for (node_data n:graph.getV())
        {
            n.setInfo("x");
            n.setTag(-1);
        }
    }

    /**
     * return a reverse graph, by reversing the edges
     * @param g
     * @return
     */
    private directed_weighted_graph reverseGraph(directed_weighted_graph g)
    {
        directed_weighted_graph reverseG=new DWGraph_DS();
        for(node_data n:g.getV())
        {
            reverseG.addNode(new NodeData(n));
        }
        for (node_data n : g.getV()) {
            for (edge_data k : g.getE(n.getKey())) {
                reverseG.connect(k.getDest(),n.getKey(),k.getWeight());
            }
        }
        return reverseG;
    }

    /**
     * private class that represents the distance/ weight between nodes, used in Dijkstra algorithm
     */
    private class Weight implements Comparable<Weight>
    {
        private int key;
        private double w;
        private int faterKey;

        public Weight(int key1, double w1, int key2)
        {
            this.key=key1;
            this.w=w1;
            this.faterKey=key2;
        }

        public int getKey()
        {
            return this.key;
        }

        public void setW(double weight ){
            if (weight > 0) {
                this.w = weight;
            }
        }
        public double getW()
        {
            return this.w;
        }
        public void setFaterKey(int key)
        {
            this.faterKey=key;
        }
        public int getFaterKey()
        {
            return this.faterKey;
        }

        /**
         * Compares between the w-distance of nodes, used in Dijkstra algorithm on a Priority Queue
         */
        @Override
        public int compareTo(Weight n) {
            if (this.w > n.getW()) {
                return 1;
            }
            else if (this.w == n.getW()) {
                return 0;
            }
            return -1;
        }
    }



}
