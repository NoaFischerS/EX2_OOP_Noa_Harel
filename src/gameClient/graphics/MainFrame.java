package gameClient.graphics;

import gameClient.Arena;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;


public class MainFrame extends JFrame
{
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem menuItem3;

    private static int FRAME_HEIGHT = 1000;
    private static int FRAME_WIDTH = 1000;


    MainPanel mainPanel;

    public MainFrame(String title, Arena arena)
    {
        super(title);
        initFrame();
//        addMenu();
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


//    private void addMenu()
//    {
//        MenuBar menuBar = new MenuBar();
//        Menu menu = new Menu("File");
//        menuBar.add(menu);
//        this.setMenuBar(menuBar);
//
//        menuItem1 = new MenuItem("Item 1");
//        menuItem1.addActionListener(this);
//
//        menuItem2 = new MenuItem("Item 2");
//        menuItem2.addActionListener(this);
//
//        menuItem3 = new MenuItem("Item 3");
//        menuItem3.addActionListener(this);
//
//        menu.add(menuItem1);
//        menu.add(menuItem2);
//        menu.add(menuItem3);
//
//    }

//    @Override
//    public void actionPerformed(ActionEvent e)
//    {
//        if(e.getSource()== menuItem1)
//        {
//            System.out.println("menuItem1 clicked");
//
//        }
//        else if(e.getSource()==menuItem2)
//        {
//            System.out.println("menuItem2 clicked");
//        }
//    }

    private void initPanel(Arena arena)
    {
        mainPanel = new MainPanel();
        this.add(mainPanel);
        mainPanel.initArena(arena, FRAME_WIDTH, FRAME_HEIGHT);
    }
}
