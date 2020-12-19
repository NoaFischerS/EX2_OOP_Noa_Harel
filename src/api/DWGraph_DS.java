package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph
{
    private HashMap<Integer,node_data> graph;
    private HashMap<Integer,EdgeList > ni;
    private HashMap<Integer, Collection<Integer>> destSet;
    private int edgeSize=0;
    private int MC=0;

    public DWGraph_DS()
    {
        graph=new HashMap<Integer,node_data>();
        ni=new HashMap<Integer,EdgeList>();
        destSet=new HashMap<Integer,Collection<Integer>>();
    }

    public DWGraph_DS(directed_weighted_graph g)
    {
        graph = new HashMap<Integer, node_data>();
        ni = new HashMap<Integer, EdgeList>();
        destSet=new HashMap<Integer,Collection<Integer>>();
        for (node_data n : g.getV()) {

            addNode(new NodeData(n));
        }
        for (node_data n : g.getV()) {
            for (edge_data k : g.getE(n.getKey())) {
                connect(n.getKey(),k.getDest(),k.getWeight());
            }
        }
    }

    @Override
    public node_data getNode(int key) {
        if(graph.containsKey(key))
        {
            return graph.get(key);
        }
        return null;
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(graph.containsKey(src) && graph.containsKey(dest) && src!=dest)
        {
            return ni.get(src).getE(dest);
        }
        return null;
    }

    @Override
    public void addNode(node_data n) {
        if(n!=null)
        {
            graph.put(n.getKey(),n);
            ni.put(n.getKey(),new EdgeList());
            destSet.put(n.getKey(),new ArrayList<Integer>());
            MC++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(graph.containsKey(src) && graph.containsKey(dest))
        {
            if ((w>0) && (src!=dest))
            {
                if(!ni.get(src).hasE(dest))
                {
                    edgeSize++;
                }
                edge_data e = new EdgeData(src, dest, w);
                ni.get(src).addNi(e);
                destSet.get(dest).add(src);
                MC++;
            }
        }
    }

    @Override
    public Collection<node_data> getV() {
        return graph.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if(graph.containsKey(node_id))
        {
            return ni.get(node_id).getV();
        }
        Collection <edge_data> c=new ArrayList<>();
        return c;
    }

    @Override
    public node_data removeNode(int key) {
        if(graph.containsKey(key))
        {
            //delete all edge that key is  src
            for(edge_data e : getE(key))
            {
                    destSet.get(e.getDest()).remove(key);
                    edgeSize--;
                    MC++;
            }
            //delete all edge that key is dest
            for(Integer e : destSet.get(key))
            {
                ni.get(e).removeE(key);
                edgeSize--;
                MC++;
            }
            node_data n=graph.remove(key);
            MC++;
            return n;
        }
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(graph.containsKey(src) && graph.containsKey(dest))
        {
            if(ni.get(src).hasE(dest))
            {
                ni.get(src).removeE(dest);
                destSet.get(dest).remove(src);
                edgeSize--;
                MC++;
            }
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return graph.size();
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public int getMC() {
        return MC;
    }

    /**
     * methode for equals between 2 graph
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != getClass())
            return false;
        if (this == null && o == null) {
            return true;
        }
        directed_weighted_graph g = (directed_weighted_graph) o;
        if (this != null && g != null) {
            Collection<node_data> col1 = this.getV();
            Collection<node_data> col2 = g.getV();
            if (col1.size() == col2.size()) {
                //first compare between the node at the graph
                for (node_data n1 : col1) {
                    if (!col2.contains(n1))
                        return false;
                }
                // second compare between the neighbor for any node at the graph and their distance
                for (node_data n1 : col1) {
                    Collection<edge_data> col1Ni = this.getE(n1.getKey());
                    Collection<edge_data> col2Ni = g.getE(n1.getKey());
                    if (col1Ni.size() != col2Ni.size())
                        return false;
                    for (edge_data n1Ni : col1Ni) {
                        if (!col2Ni.contains(n1Ni)) {
                            return false;
                        }
                        if (this.getEdge(n1.getKey(), n1Ni.getSrc()) != g.getEdge(n1.getKey(), n1Ni.getSrc())) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static class DWGraph_DSJson implements JsonSerializer<directed_weighted_graph>, JsonDeserializer<directed_weighted_graph> {

        NodeData.NodeDataJson nodeJson = new NodeData.NodeDataJson();
        EdgeData.EdgeDataJson edgeJson = new EdgeData.EdgeDataJson();

        @Override
        public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            directed_weighted_graph graph=new DWGraph_DS();
            JsonArray nodes=jsonElement.getAsJsonObject().get("Nodes").getAsJsonArray();
            JsonArray edges=jsonElement.getAsJsonObject().get("Edges").getAsJsonArray();
            for(JsonElement je: nodes)
            {
                graph.addNode(nodeJson.deserialize(je,type,jsonDeserializationContext));
            }

            for(JsonElement je: edges)
            {
                int s=je.getAsJsonObject().get("src").getAsInt();
                int d=je.getAsJsonObject().get("dest").getAsInt();
                double w=je.getAsJsonObject().get("w").getAsDouble();
                graph.connect(s,d,w);
            }
            return graph;
        }

        @Override
        public JsonElement serialize(directed_weighted_graph graph, Type type, JsonSerializationContext jsonSerializationContext)
        {
            JsonObject graphJson=new JsonObject();
            JsonArray nodes=new JsonArray();
            JsonArray edges=new JsonArray();
            for (node_data n: graph.getV())
            {
                nodes.add(nodeJson.serialize(n, type, jsonSerializationContext));
                for (edge_data e : graph.getE(n.getKey()))
                {
                    edges.add(edgeJson.serialize(e,type,jsonSerializationContext));                }
            }
            graphJson.add("Edges",edges);
            graphJson.add("Nodes",nodes);
            return graphJson;
        }
    }

}
