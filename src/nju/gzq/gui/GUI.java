package nju.gzq.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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


    // menus
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem openMenuItem = new JMenuItem("Open");
    private JMenuItem exportMenuItem = new JMenuItem("Export");
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    private JMenu aboutMenu = new JMenu("About");
    private JMenuItem aboutMenuItem = new JMenuItem("About");
    private JMenuItem helpMenuItem = new JMenuItem("Help");

    // buttons
    private JButton openButton = new JButton("Open Data file");
    private JButton rankButton = new JButton("Ranking");
    private JButton selectButton = new JButton("Selecting");
    private JButton exportButton = new JButton("Export");

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
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //SwingUtilities.updateComponentTreeUI(frame.getContentPane());
        } catch (Exception e) {

        }

        //Menu setting
        fileMenu.setMnemonic('F');
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        fileMenu.add(openMenuItem);
        fileMenu.add(exportMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        aboutMenu.add(aboutMenuItem);
        aboutMenu.add(helpMenuItem);
        menuBar.add(aboutMenu);
        frame.setJMenuBar(menuBar);


        //Panel setting
        rootPanel.setLayout(new BorderLayout());
        rootPanel.add(northPanel, BorderLayout.NORTH);
        rootPanel.add(centralPanel, BorderLayout.CENTER);
        rootPanel.add(southPanel, BorderLayout.SOUTH);

        //Button setting
        northPanel.add(openButton);
        northPanel.add(exportButton);
        southPanel.add(rankButton);
        southPanel.add(selectButton);


        //Event listening
        openButton.addActionListener(this::actionPerformed);
        exportButton.addActionListener(this::actionPerformed);
        rankButton.addActionListener(this::actionPerformed);
        selectButton.addActionListener(this::actionPerformed);
        openMenuItem.addActionListener(this::actionPerformed);
        exportMenuItem.addActionListener(this::actionPerformed);
        exitMenuItem.addActionListener(this::actionPerformed);
    }

    /**
     * Response to event
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        // open data folder
        if (e.getSource() == openButton || e.getSource() == openMenuItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  //only show directories
            int value = fileChooser.showDialog(new JLabel(), "Select root dir");

            // approve option
            if (value == JFileChooser.APPROVE_OPTION) {
                File dir = fileChooser.getSelectedFile();
                System.out.println(dir.getPath());
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

        // export result files .dot file and graph file
        if (e.getSource() == exportButton || e.getSource() == exportMenuItem) {
            System.out.println("export");
        }

        // Exiting tool
        if (e.getSource() == exitMenuItem) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }
}
