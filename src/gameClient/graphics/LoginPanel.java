package gameClient.graphics;

import gameClient.Ex2_Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class LoginPanel extends JFrame implements ActionListener {
    JPanel panel;
    JLabel idLabel, LevelLabel, message;
    JTextField idInput;
    JTextField levelInput;
    JButton submit, cancel;
    LoginPanel() {
        // Username Label
        idLabel = new JLabel();
        idLabel.setText("ID:");
        idInput = new JTextField();
        // Password Label
        LevelLabel = new JLabel();
        LevelLabel.setText("Level :");
        levelInput = new JTextField();
        // Submit
        submit = new JButton("SUBMIT");
        panel = new JPanel(new GridLayout(3, 1));
        panel.add(idLabel);
        panel.add(idInput);
        panel.add(LevelLabel);
        panel.add(levelInput);
        message = new JLabel();
        panel.add(message);
        panel.add(submit);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Adding the listeners to components..
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Harel & Noa");
        setSize(450,100);
        setVisible(true);
    }

    /**
     * run login panel
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            int id = Integer.parseInt(args[0]);
            int level = Integer.parseInt(args[1]);
            Ex2_Client.startGame(level, id);
        }
        else
            new LoginPanel();
    }

    /**
     * get login panel input and start the game
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        int id = Integer.parseInt(idInput.getText());
        int level = Integer.parseInt(levelInput.getText());
        setVisible(false); //you can't see me!
        dispose(); //Destroy the JFrame object
        if(level>23||level<0)
            new LoginPanel();
        else
            Ex2_Client.startGame(level, id);

    }
}