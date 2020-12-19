package api;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGrpah_AlgoTest {
    directed_weighted_graph grpah = graphForTest();

    @Test
    void init()
    {
        dw_graph_algorithms gl=new DWGrpah_Algo();
        gl.init(grpah);
        assertTrue(grpah.equals(gl.getGraph()));

    }
    @Test
    void getGraph()
    {
        dw_graph_algorithms gl=new DWGrpah_Algo();
        gl.init(grpah);
        assertTrue(grpah.equals(gl.getGraph()));
    }

    @Test
    void copy()
    {
        dw_graph_algorithms gl=new DWGrpah_Algo();
        gl.init(grpah);
        directed_weighted_graph g1= gl.copy();
        assertTrue(grpah.equals(g1));

    }

    @Test
    void isConnected()
    {
        dw_graph_algorithms gl=new DWGrpah_Algo();
        directed_weighted_graph g=new DWGraph_DS();
        gl.init(g);
        assertTrue(gl.isConnected());
        g.addNode(new NodeData(0,0,0,0));
        assertTrue(gl.isConnected());
        for(int i=0;i<2;i++)
        {
            g.addNode(new NodeData(i,i-1,0.4*(1/(1+1)),5*Math.sqrt(i)));
        }
        assertFalse(gl.isConnected());

        g.connect(0,1,2);
        g.connect(1,0,3);
        assertTrue(gl.isConnected());
        g.addNode(new NodeData(2,4,4,-9));
        g.addNode(new NodeData(3,1,-3,2));
        for(int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                gl.getGraph().connect(i,j,(5*(i*j)/40)+12);
            }
        }
        assertTrue(gl.isConnected());
        g.addNode(new NodeData(5,0,1,2));
        assertFalse(gl.isConnected());

        g=DWGraph_DSTest.graph_creator(100,9900,1);
        gl.init(g);
        assertTrue(gl.isConnected());
        g.addNode(new NodeData(-1,-1,-1,2));
        assertFalse(gl.isConnected());
        g=graphForTest();
        gl.init(g);
        assertFalse(gl.isConnected());
    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph g = graphForTest();
        dw_graph_algorithms ga1 = new DWGrpah_Algo();
        ga1.init(g);
        assertEquals(4.7, ga1.shortestPathDist(0, 2));
        assertEquals(5.75, ga1.shortestPathDist(0, 9));
        assertEquals(1, ga1.shortestPathDist(0, 5));
        assertEquals(3.45, ga1.shortestPathDist(2, 4));
        ga1.getGraph().connect(4, 7, 70215);
        assertEquals(-1, ga1.shortestPathDist(0, 45));

        g.connect(2,0,4);
        assertEquals(5, ga1.shortestPathDist(2, 4));
        assertEquals(5.07, ga1.shortestPathDist(2, 7));

        assertEquals(5.02, ga1.shortestPathDist(0, 8));
        g.connect(0,5,4);
        assertEquals(6.78, ga1.shortestPathDist(0, 8));

        assertFalse(ga1.isConnected());

        assertEquals(-1, ga1.shortestPathDist(7, 6));
        assertEquals(0, ga1.shortestPathDist(7, 7));


    }

    @Test
    void shortestPath()
    {
        directed_weighted_graph g = graphForTest();
        dw_graph_algorithms ga = new DWGrpah_Algo();
        ga.init(g);

        List<node_data> path=ga.shortestPath(0,0);
        assertEquals(1,path.size());
        assertTrue(path.get(0).equals(ga.getGraph().getNode(0)));

        path=ga.shortestPath(0,4);
        assertEquals(2,path.size());
        assertTrue(path.get(1).equals(ga.getGraph().getNode(4)));

        g.connect(0,4,3.3);
        path=ga.shortestPath(0,4);
        assertEquals(3,path.size());
        assertTrue(path.get(1).equals(ga.getGraph().getNode(5)));
        g.connect(0,4,1.75);

        path=ga.shortestPath(5,0);
        assertEquals(3,path.size());
        assertTrue(path.get(1).equals(ga.getGraph().getNode(2)));

        path=ga.shortestPath(0,9);
        assertEquals(3,path.size());
        assertTrue(path.get(1).equals(ga.getGraph().getNode(2)));
        assertTrue(path.get(2).equals(ga.getGraph().getNode(9)));

        g.connect(0,3,0.1);
        g.connect(3,6,0.1);
        g.connect(6,8,0.1);

        path=ga.shortestPath(0,9);
        assertEquals(5,path.size());
        assertTrue(path.get(1).equals(ga.getGraph().getNode(3)));
        assertTrue(path.get(3).equals(ga.getGraph().getNode(8)));

        g.removeEdge(3,6);
        g.removeEdge(0,2);
        path=ga.shortestPath(0,9);
        assertEquals(4,path.size());
        assertTrue(path.get(1).equals(ga.getGraph().getNode(5)));
        assertTrue(path.get(2).equals(ga.getGraph().getNode(8)));

        g.removeNode(8);
        path=ga.shortestPath(7,6);
        assertEquals(0,path.size());

    }



    @org.junit.jupiter.api.Test
    void save_load() {

        directed_weighted_graph g=new DWGraph_DS();
        g.addNode(new NodeData(0,0,0,0));
        dw_graph_algorithms ga=new DWGrpah_Algo();
        ga.init(g);
        ga.save("test");
        ga.load("test");
        assertTrue(g.equals(ga.getGraph()));

        g=graphForTest();
        ga.init(g);
        ga.save("graphForTest");
        ga.load("graphForTest");
        assertTrue(g.equals(ga.getGraph()));

        g=DWGraph_DSTest.graph_creator(100,1000,1);
        ga.init(g);
        ga.save("test1");
        ga.load("test1");
        assertTrue(g.equals(ga.getGraph()));

        ga.load("no");
        assertTrue(g.equals(ga.getGraph()));

        ga.load("graphForTest");
        assertFalse(g.equals(ga.getGraph()));
    }

        directed_weighted_graph graphForTest() {
            directed_weighted_graph g = new DWGraph_DS();
            for(int i=0;i<10;i++)
            {
                g.addNode(new NodeData(i,i-1,0.4*(1/(1+1)),5*Math.sqrt(i)));
            }

            g.connect(0, 5, 1);
            g.connect(0, 2, 4.7);
            g.connect(0, 4, 1.75);
            g.connect(4, 7, 0.1);
            g.connect(5, 4, 2);
            g.connect(1, 2, 0.825);
            g.connect(1, 7, 0.5);
            g.connect(2, 1, 7.05);
            g.connect(2, 5, 3);
            g.connect(5, 2, 4.05);
            g.connect(5, 8, 4.02);
            g.connect(2, 8, 2.08);
            g.connect(8, 9, 1.08);
            g.connect(2, 9, 1.05);
            g.connect(5, 7, 2.07);
            g.connect(2, 0, 1.7);
            return g;
        }
    }