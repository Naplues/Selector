package nju.gzq.gui;

import javax.swing.*;

/**
 * Program panel
 */
public class Panel extends JFrame {
    private JPanel mainPanel = new JPanel();

    public Panel(String name) {
        super(name);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);

        //main panel
        this.setContentPane(mainPanel);
        mainPanel.setSize(600,400);

        JLabel label = new JLabel("Hello world");

        //show window
        this.pack();
        this.setVisible(true);
    }
}
