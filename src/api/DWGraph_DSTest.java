package api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {
    private static Random random = null;
    directed_weighted_graph g = graph_creator(20, 50, 1);

    /**
     * test for getNode method, create a graph and ask for some nodes, some exist and some not.
     */
    @Test
    void getNode() {
        int getNodeCounter = 0;
        int getNodeCounterNull = 0;

        for (int i = -5; i < 33; i += 5) {
            node_data n = g.getNode(i);
            if (n != null)
                getNodeCounter++;
            else
                getNodeCounterNull++;

        }
        assertEquals(4, getNodeCounter);
        assertEquals(4,getNodeCounterNull);
    }


    /**
     * test for getEdge method for checking if the distance that connected its really is
     */
    @Test
    void getEdge() {
        directed_weighted_graph g1 = graph_creator(20, 0, 1);
        g1.connect(0, 1, 15);
        g1.connect(0, -1, 2.5);
        g1.connect(3, 5, -3);
        g1.connect(3, 5, 0.23);
        g1.connect(0, 1, 4);
        g1.connect(0, 9, 4);
        g1.connect(5, 8, 4);
        assertEquals(4,g1.getEdge(0,1).getWeight());
        assertEquals(null,g1.getEdge(3,6));
        assertEquals(4,g1.getEdge(5,8).getWeight());
        int getEdgeCounter = 0;

        for (int i = -1; i < 25; i++)
        {
            if (g1.getEdge(0, i) != null)
                getEdgeCounter++;
            if (g1.getEdge(i, 5) != null)
                getEdgeCounter++;
        }
        assertEquals(3, getEdgeCounter);
    }

    /**
     * test for addNode, creat graph, add some node and check if its success
     */
    @Test
    void addNode() {
        assertNull(g.getNode(-13));
        for (int i = -13; i < 50; i += 7)
            g.addNode(new NodeData(i, i, i, i));
        assertEquals(-13,g.getNode(-13).getKey());
        assertEquals(26,g.nodeSize());
        assertNull(g.getNode(-12));
        assertEquals(36,g.getNode(36).getLocation().x());
    }

    /**
     * test for connect node at the graph
     */
    @Test
    void connect() {
        directed_weighted_graph g1 = graph_creator(20, 0, 1);
        g1.connect(0, 1, 15);
        g1.connect(0, -1, 2.5);
        g1.connect(3, 5, 0.23);
        g1.connect(0, 1, 4);
        g1.connect(0, 9, 4);
        g1.connect(5, 8, 4);
        assertEquals( 4,g1.getEdge(0, 1).getWeight());
        assertEquals( 0.23,g1.getEdge(3, 5).getWeight());
        g1.connect(3, 5, -3);
        assertNull(g1.getEdge(5, 3));
        assertTrue(g1.getEdge(0, 1) != null);
        assertNull(g1.getEdge(0, -1));

    }

    @Test
    void getV() {
        Collection<node_data> col = g.getV();
        assertEquals( 20,col.size());
        for (int i = 0; i < 20; i++) {
            assertTrue(col.contains(g.getNode(i)));
        }
        assertFalse(col.contains(-5));
        assertFalse(col.contains(22));
    }

    @Test
    void getE() {
        directed_weighted_graph g = new DWGraph_DS();
        for (int i = 0; i < 15; i++) {
            g.addNode(new NodeData(i, i, i, i));
        }
        for (int i = 0; i < 15; i += 3) {
            g.connect(1, i, i + 0.33);
        }
        Collection<edge_data> col = g.getE(1);
        assertEquals(5,col.size());
        assertTrue(col.contains(g.getEdge(1,6)));
        assertFalse(col.contains(g.getEdge(1,1)));
        assertFalse(col.contains(g.getEdge(1,2)));
    }

    /**
     * test for remove node.
     * remove some node and check if its done
     */
    @Test
    void removeNode() {
        node_data n = g.removeNode(-1);
        assertEquals(n, null);
        g.removeNode(3);
        assertEquals(g.nodeSize(), 19);
        for (int i = 22; i > -4; i -= 4) {
            g.removeNode(i);
        }
        assertEquals(g.nodeSize(), 14);
        assertNull(g.getEdge(18, 15));
        g.removeNode(0);
        g.removeNode(0);
        assertNull(g.getEdge(0, 15));
        assertNull(g.getEdge(0, 0));

        g=graph_creator(10,0,1);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(1,3,0.5);
        g.connect(3,1,4);
        g.connect(1,4,4);
        g.connect(5,8,3);
        g.connect(4,9,0.2);
        g.connect(0,4,9);
        g.connect(9,0,9);
        g.connect(2,3,5);
        assertEquals(10,g.edgeSize());
        g.removeNode(5);
        assertEquals(9,g.edgeSize());
        g.removeNode(3);
        assertEquals(6,g.edgeSize());
        g.removeNode(0);
        assertEquals(2,g.edgeSize());
        assertNull(g.getEdge(0,1));
        assertNull(g.getEdge(1,3));
        assertNull(g.getEdge(5,8));

        g=graph_creator(1000,10000,1);
        g.removeNode(0);
        assertEquals(999,g.nodeSize());
    }

    /**
     * test for remove edge.
     * remove some edge and check if its done
     */
    @Test
    void removeEdge() {
        directed_weighted_graph g = graph_creator(20, 0, 1);
        g.connect(0, 1, 5);
        g.removeEdge(0, 1);
        assertEquals( 0,g.edgeSize());
        for (int i = 0; i < 20; i++) {
            g.connect(i, i + 1, i + 2.222222);
        }
        for (int i = 24; i > -5; i -= 5) {
            g.removeEdge(i, i + 1);
        }
        assertEquals(g.edgeSize(), 16);
        assertNull(g.getEdge(14, 15));
        g.connect(14, 15, 0.1);
        assertNull(g.getEdge(15, 14));
    }

    /**
     * test for node size value return
     */
    @Test
    void nodeSize() {
        assertEquals( 20,g.nodeSize());
        g.addNode(new NodeData(22, 0, 0, 0));
        g.removeNode(19);
        assertEquals( 20,g.nodeSize());
        g.removeNode(19);
        g.removeNode(18);
        assertEquals( 19,g.nodeSize());
    }

    /**
     * test for edge size value return
     */
    @Test
    void edgeSize() {
        directed_weighted_graph g = graph_creator(20, 5, 1);
        assertEquals(g.edgeSize(), 5);
        g.addNode(new NodeData(22, 0, 1, 2));
        g.connect(19, 22, 24.4);
        assertEquals(6, g.edgeSize());
        g.removeEdge(19, 22);
        g.removeEdge(19, 22);
        assertNull(g.getEdge(19, 22));
        assertEquals(5, g.edgeSize());
        g.connect(18,17,3);
        assertEquals(6, g.edgeSize());
        g.connect(18,17,7);
        assertEquals(6, g.edgeSize());


    }

    /**
     * test for counter changes value return
     */
    @Test
    void getMC() {
        directed_weighted_graph g = new DWGraph_DS();
        for (int i = 0; i < 5; i++) {
            g.addNode(new NodeData(i, 1, i, 5));
        }
        assertEquals(5, g.getMC());
        g.removeEdge(0, 1);
        g.removeNode(0);
        assertEquals(6, g.getMC());
        g.connect(0, 1, 2);
        g.addNode(new NodeData(0, 1, 1, 1));
        g.connect(0, 1, 2);
        assertEquals(8, g.getMC());

    }

    static class DWGraph_DSJsonTest
    {
        directed_weighted_graph g=graph_creator(10,40,1);
        DWGraph_DS.DWGraph_DSJson gj=new DWGraph_DS.DWGraph_DSJson();
        Type type;
        JsonSerializationContext jsonSerializationContext;
        JsonDeserializationContext jsonDeserializationContext;

        @Test
        void serialize_deserialize()
        {
            directed_weighted_graph g1=new DWGraph_DS();
            g1.addNode(new NodeData(0,0,0,0));
            g1.addNode(new NodeData(1,1,2,3));
            g1.addNode(new NodeData(2,3,-2,0));
            g1.connect(0,1,3);
            g1.connect(0,2,2.5);
            g1.connect(2,0,15);
            JsonElement je=gj.serialize(g1,type,jsonSerializationContext);
            directed_weighted_graph graph=gj.deserialize(je,type,jsonDeserializationContext);

            assertTrue(g1.equals(graph));
            assertEquals(3,graph.nodeSize());
            assertEquals(3,graph.edgeSize());
            assertEquals(3,graph.getEdge(0,1).getWeight());
            assertNull(graph.getEdge(2,1));


            je=gj.serialize(g,type,jsonSerializationContext);
            graph=gj.deserialize(je,type,jsonDeserializationContext);
            assertTrue(g.equals(graph));

            g=graph_creator(100,500,1);
            graph=gj.deserialize(gj.serialize(g,type,jsonSerializationContext),type,jsonDeserializationContext);
            assertTrue(g.equals(graph));
        }
    }

    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
        directed_weighted_graph g = new DWGraph_DS();
        random = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(new NodeData(i,i-1,2*(-1/(i+1)),5*Math.sqrt(i)));
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = random.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = random.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes( directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();
        node_data[] nodes = new node_data[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }

}