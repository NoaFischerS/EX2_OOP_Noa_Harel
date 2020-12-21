package gameClient.graphics;

import api.game_service;
import gameClient.Arena;

import java.awt.Color;

import javax.swing.JFrame;

/**GUI
 * main frame class
 *
 */
public class MainFrame extends JFrame
{

    private static int FRAME_HEIGHT = 800;
    private static int FRAME_WIDTH = 800;


    MainPanel mainPanel;
    game_service game;
    /**
     * constructor
     */


    public MainFrame(String title, Arena arena)
    {

        super(title);
        initFrame();
        initPanel(arena);
    }

    /**
     * updates the painting
     */

    public void updateGraphic() {
        mainPanel.repaint();
    }

    /**
     * init the frame and closes it when user exists the game
     */

    private void initFrame()
    {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.blue);

    }
    /**
     * init the panel
     */

    private void initPanel(Arena arena)
    {
        mainPanel = new MainPanel();
        this.add(mainPanel);
        mainPanel.initArena(arena, FRAME_WIDTH, FRAME_HEIGHT);
    }
}
