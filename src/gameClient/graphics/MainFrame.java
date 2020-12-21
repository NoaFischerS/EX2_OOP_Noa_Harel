package gameClient.graphics;

import api.game_service;
import gameClient.Arena;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * main frame class, we paint on panel for better Performance
 */
public class MainFrame extends JFrame
{

    private static int FRAME_HEIGHT = 800;
    private static int FRAME_WIDTH = 800;


    MainPanel mainPanel;
    game_service game;

    public MainFrame(String title, Arena arena)
    {

        super(title);
        initFrame();
        initPanel(arena);
    }



    public void updateGraphic() {
        mainPanel.repaint();
    }

    private void initFrame()
    {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.blue);

    }

    private void initPanel(Arena arena)
    {
        mainPanel = new MainPanel();
        this.add(mainPanel);
        mainPanel.initArena(arena, FRAME_WIDTH, FRAME_HEIGHT);
    }
}
