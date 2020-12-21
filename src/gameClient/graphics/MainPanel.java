package gameClient.graphics;

import api.*;
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

import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MainPanel extends JPanel {
    //LinkedList<Point3D> points = new LinkedList<Point3D>();

    private Arena arena;
    private Range2Range _w2f;
    private game_service game;

    private BufferedImage imageTitle;
    private BufferedImage imagePokemon;
    private BufferedImage imageTrainer;

    public void initImages() {
        try {
            imageTitle = ImageIO.read(new File("res/title.png"));
            imagePokemon = ImageIO.read(new File("res/pokemon.png"));
            imageTrainer = ImageIO.read(new File("res/trainer.png"));
        } catch (IOException ex) {
            System.exit(1);
        }
    }

    public MainPanel() {
        this.setBackground(Color.gray);
        initImages();
    }
/**
 * paint panel paint
 */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTitle(g);
        drawScore(g);

        int w = this.getWidth();
        int h = this.getHeight();
        updateW2f(w,h);
        drawPokemons(g);
        drawGraph(g);
        drawAgents(g);
        drawInfo(g);

    }


    public void initArena(Arena ar, int frameWidth, int frameHeight) {
        this.arena = ar;
        updateW2f(frameWidth, frameHeight);

    }

    private void updateW2f(int frameWidth, int frameHeight) {
        Range width = new Range(20, getWidth() - 20);
        Range height = new Range(getHeight() - 10, 150);

        Range2D frame = new Range2D(width, height);
        directed_weighted_graph graph = arena.getGraph();
        _w2f = Arena.w2f(graph, frame);
    }


    private void drawTitle(Graphics g) {
        Image scaledImage = imageTitle.getScaledInstance(300, 100, Image.SCALE_SMOOTH);
        g.drawImage(scaledImage, 0, 0, this);
    }

    private void drawInfo(Graphics g) {

        java.util.List<String> str = arena.get_info();
        String dt = "none";
        for (int i = 0; i < str.size(); i++) {
            g.drawString(str.get(i) + " dt: " + dt, 100, 60 + i * 20);
        }


        int time = (int) arena.getTime_left() / 1000;
        g.setColor(Color.black);
        g.drawString("Time left : " + time + "s", this.getWidth() - 150, 50);
    }


    private void drawGraph(Graphics g) {
        directed_weighted_graph graph = arena.getGraph();
        for (node_data n : graph.getV()) {
            g.setColor(Color.black);
            drawNode(n, 5, g);

            for (edge_data e : graph.getE(n.getKey())) {
                g.setColor(Color.black);
                drawEdge(e, g);
            }
        }
    }

    private void drawPokemons(Graphics g) {
        List<CL_Pokemon> pokemons = arena.getPokemons();
        if (pokemons != null) {
            for (CL_Pokemon p : pokemons) {
                Point3D c = p.getLocation();
                int r = 40;
                g.setColor(Color.green);
                if (p.getType() < 0) {
                    g.setColor(Color.orange);
                }
                if (c != null) {

                    geo_location fp = this._w2f.world2frame(c);

                    int x = (int) fp.x() - r;
                    int y = (int) fp.y() - r;
                    int w = 2 * r;
                    int h = 2 * r;
                    Image scaledImage = imagePokemon.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    g.drawImage(scaledImage, x, y, this);

                }
            }
        }
    }

    private void drawAgents(Graphics g) {
        java.util.List<CL_Agent> agents = (ArrayList<CL_Agent>) arena.getAgents();
        g.setColor(Color.red);
        int i = 0;
        while (agents != null && i < agents.size()) {
            geo_location c = agents.get(i).getLocation();
            int r = 20;

            i++;
            if (c != null) {

                geo_location fp = this._w2f.world2frame(c);
                int x = (int) fp.x() - r;
                int y = (int) fp.y() - r;
                int w = 2 * r;
                int h = 2 * r;

                Image scaledImage = imageTrainer.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, x, y, this);



            }
        }
    }

    private void drawScore(Graphics g) {
        var agents = arena.getAgents();
        var score = 0;
        int count = 0;
        for (CL_Agent agent : agents) {
            g.setColor(Color.black);
            g.setFont(new Font(null, Font.BOLD, 20));


            g.drawString("Score agent " + agent.getID() + ": " + agent.getValue(), this.getWidth()  -400, 50 + count);
            count += 20;


            score += agent.getValue();
        }

        g.setColor(Color.black);
        g.setFont(new Font(null, Font.BOLD, 20));

        g.drawString("Total Score: " + score, this.getWidth()  -400,50 + count );

    }

    private void drawNode(node_data n, int r, Graphics g) {
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        g.setColor(Color.black);
        g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
        g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);

    }

    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = arena.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
    }


}

