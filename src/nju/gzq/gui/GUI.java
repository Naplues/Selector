package nju.gzq.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Program window
 */
public class GUI implements ActionListener {
    private JFrame frame = new JFrame("Selector");
    private JPanel rootPanel = new JPanel();
    private JPanel northPanel = new JPanel();
    private JPanel southPanel = new JPanel();
    private JPanel centralPanel = new JPanel();

    private JButton openButton = new JButton("Open Data file");
    private JButton rankButton = new JButton("Ranking");
    private JButton selectButton = new JButton("Selecting");

    public GUI() {
        //Frame setting
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        //frame.set
        frame.setContentPane(rootPanel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame.getContentPane());
        } catch (Exception e) {

        }

        //Panel setting
        rootPanel.setLayout(new BorderLayout());
        rootPanel.add(northPanel, BorderLayout.NORTH);
        rootPanel.add(centralPanel, BorderLayout.CENTER);
        rootPanel.add(southPanel, BorderLayout.SOUTH);

        //Button setting
        northPanel.add(openButton);
        southPanel.add(rankButton);
        southPanel.add(selectButton);


        //Event listening
        openButton.addActionListener(this);
        rankButton.addActionListener(this);
        selectButton.addActionListener(this);
    }

    /**
     * Response to event
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        // open data folder
        if (e.getSource() == openButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(new JLabel(), "选择");
            File dir = fileChooser.getCurrentDirectory();
            if (dir.isDirectory()) {
                System.out.println(dir.getPath());
            } else {
                System.out.println("Error");
            }
        }

        // ranking data
        if (e.getSource() == rankButton) {
            System.out.println("rank");
        }

        //selecting combination features whose performance is best
        if (e.getSource() == selectButton) {
            System.out.println("select");
        }
    }
}
