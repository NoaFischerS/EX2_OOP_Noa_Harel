package gameClient.graphics;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.Arena;
import gameClient.CL_Agent;
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class MainPanel extends JPanel
{
    LinkedList<Point3D> points = new LinkedList<Point3D>();

    private Arena arena;
    private Range2Range _w2f;

    private BufferedImage imageTitle;
    private BufferedImage imagePokemon;
    private BufferedImage imageTrainer;

    private int panelWidth;
    private int panelHeight;

    public void initImages() {
        try {
            imageTitle = ImageIO.read(new File("res/title.png"));
            imagePokemon = ImageIO.read(new File("res/pokemon.png"));
            imageTrainer = ImageIO.read(new File("res/trainer.png"));
        } catch (IOException ex) {
            System.exit(1);
        }
    }


    public MainPanel()
    {
        this.setBackground(Color.green);
        initImages();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //mainFrame.updateGraphics applies here:

        drawTitle(g);
        drawScore(g);

        int w = this.getWidth();
        int h = this.getHeight();
        //g.clearRect(0, 0, w, h);
        //	updateFrame();
        drawPokemons(g);
        drawGraph(g);
        drawAgents(g);
        drawInfo(g);
        //this.setBackground(Color.pink);
//
//        Point3D prev = null;
//
//        for (Point3D p : points)
//        {
//            g.setColor(Color.GREEN);
//            g.fillOval((int) p.x() - 10, (int) p.y() - 10, 20, 20);
//
//            if (prev != null) {
//                g.setColor(Color.RED);
//                g.drawLine((int) p.x(), (int) p.y(), (int) prev.x(), (int) prev.y());
//
//                g.drawString("some string", (int) ((p.x() + prev.x()) / 2), (int) ((p.y() + prev.y()) / 2)); //draw in the middle
//            }
//
//            prev = p;
//        }

    }



    public void initArena(Arena ar, int frameWidth, int frameHeight) {
        this.arena = ar;
        updateW2f(frameWidth, frameHeight);
    }

    private void updateW2f(int frameWidth, int frameHeight) {
        panelWidth = frameWidth-50;
        panelHeight = frameWidth-100;
        Range width = new Range(20,panelWidth);
        Range height = new Range(20,panelHeight);
        Range2D frame = new Range2D(width,height);
        directed_weighted_graph graph = arena.getGraph();
        _w2f = Arena.w2f(graph,frame);
    }

    private void drawTitle(Graphics g) {
        Image scaledImage = imageTitle.getScaledInstance(300,100,Image.SCALE_SMOOTH);
        g.drawImage(scaledImage, 0, 0, this);
    }

    private void drawInfo(Graphics g) {
        java.util.List<String> str = arena.get_info();
        String dt = "none";
        for(int i=0;i<str.size();i++) {
            g.drawString(str.get(i)+" dt: "+dt,100,60+i*20);
        }

    }
    private void drawGraph(Graphics g) {
        directed_weighted_graph graph = arena.getGraph();
//		Iterator<node_data> iter = graph.getV().iterator();
//		while(iter.hasNext()) {
//			node_data n = iter.next();
        for (node_data n: graph.getV()){
            g.setColor(Color.blue);
            drawNode(n,5,g);
//			Iterator<edge_data> itr = graph.getE(n.getKey()).iterator();
//			while(itr.hasNext()) {
            for (edge_data e: graph.getE(n.getKey())){
                //edge_data e = itr.next();
                g.setColor(Color.black);
                drawEdge(e, g);
            }
        }
    }
    private void drawPokemons(Graphics g) {
        List<CL_Pokemon> pokemons = arena.getPokemons();
        if(pokemons!=null) {
//		Iterator<CL_Pokemon> itr = pokemons.iterator();
//
//		while(itr.hasNext()) {
//
//			CL_Pokemon p = itr.next();
            for(CL_Pokemon p: pokemons)
            {
                Point3D c = p.getLocation();
                int r=40;
                g.setColor(Color.green);
                if(p.getType()<0) {g.setColor(Color.orange);}
                if(c!=null) {

                    geo_location fp = this._w2f.world2frame(c);
//                    int x = (int)fp.x()-r;
//                    int y = (int)fp.y()-r;
//                    int w = r;
//                    int h = r;
//                    g.fillOval(x, y, 2*r, 2*r);
//                    g.drawImage(imagePokemon, x, y, this);
//
////                g.fillOval(x, y, w, h);
//                    Image scaledImage = imagePokemon.getScaledInstance(w,h,Image.SCALE_SMOOTH);
//                    g.drawImage(scaledImage, x, y, this);
                    //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
                    int x = (int)fp.x()-r;
                    int y = (int)fp.y()-r;
                    int w = 2*r;
                    int h = 2*r;
//                g.fillOval(x, y, w, h);
                    Image scaledImage = imagePokemon.getScaledInstance(w,h,Image.SCALE_SMOOTH);
                    g.drawImage(scaledImage, x, y, this);

                }
            }
        }
    }
    private void drawAgents(Graphics g) {
        java.util.List<CL_Agent> agents = (ArrayList<CL_Agent>) arena.getAgents();
        //	Iterator<OOP_Point3D> itr = agents.iterator();
        g.setColor(Color.red);
        int i=0;
        while(agents!=null && i<agents.size()) {
            geo_location c = agents.get(i).getLocation();
            int r=20;
            i++;
            if(c!=null) {

                geo_location fp = this._w2f.world2frame(c);
                int x = (int)fp.x()-r;
                int y = (int)fp.y()-r;
                int w = 2*r;
                int h = 2*r;
//                g.fillOval(x, y, w, h);
                Image scaledImage = imageTrainer.getScaledInstance(w,h,Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, x, y, this);
            }
        }
    }

    private void drawScore(Graphics g) {
        var agents = arena.getAgents();
        var score = 0;
        for (CL_Agent agent : agents) {
            score += agent.getValue();
        }

        g.drawString("Score: " + score, 10,panelHeight );

    }

    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
        g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
        g.setColor(Color.black);
    }
    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = arena.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
        //	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    }

//    @Override
//    public void mouseClicked(MouseEvent e)
//    {
//        Point3D p = new Point3D(e.getX(), e.getY());
//        points.add(p);
//        repaint();
//        System.out.println("mouseClicked");
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e)
//    {
//
//        System.out.println("mousePressed");
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e)
//    {
//        System.out.println("mouseReleased");
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e)
//    {
//        System.out.println("mouseEntered");
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e)
//    {
//        System.out.println("mouseExited");
//    }
//
//
//






}





//super.paintComponent(g);
//
//Point3D prev = null;
//
//for (Point3D p : points)
//{
//	g.setColor(Color.BLUE);
//	g.fillOval((int)p.x(), (int)p.y(), 10, 10);
//
//	if(prev != null)
//	{
//		g.setColor(Color.RED);
//		g.drawLine((int)p.x(), (int)p.y(),
//				(int)prev.x(), (int)prev.y());
//
//		g.drawString("5", (int)((p.x()+prev.x())/2),(int)((p.y()+prev.y())/2));
//	}
//
//	prev = p;
//}
