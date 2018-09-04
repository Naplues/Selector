package nju.gzq.gui;

import nju.gzq.MySelector;
import nju.gzq.base.BaseProject;
import nju.gzq.selector.FileHandle;
import nju.gzq.selector.Setting;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Window implements ActionListener {
    // JFrame
    private JFrame frame = new JFrame("Feature Selector");
    // 面板实例
    private JPanel panel = new JPanel(); //主面板
    private JPanel operatePanel = new JPanel(); // 1.操作面板
    private JPanel contentPanel = new JPanel(); // 2.设置面板
    private JPanel displayPanel = new JPanel(); // 3.显示面板

    //1.操作面板 组件
    private JLabel info = new JLabel("数据文件存放: 数据集文件夹>项目文件夹>项目文件(.csv/.arff)");
    private JButton openButton = new JButton("打开数据集文件夹");
    private JButton allButton = new JButton("全选");
    private JButton reverseButton = new JButton("反选");
    private JButton startButton = new JButton("开始选择");

    //2.设置面板 组件
    private JPanel argsPanel = new JPanel(); //参数面板
    private JPanel argsLabelPanel = new JPanel(); // 参数标签面板
    private JPanel argsValuePanel = new JPanel(); // 参数值面板
    private JPanel featurePanel = new JPanel(); // 特征面板
    private JScrollPane jspFeaturePanel = new JScrollPane(featurePanel);

    // 标签
    private JLabel featureNumber = new JLabel("使用特征数", JLabel.CENTER);
    private JLabel maxSelectFeatureNumber = new JLabel("最大选择特征数", JLabel.CENTER);
    private JLabel threshold = new JLabel("最小性能阈值", JLabel.CENTER);
    private JLabel metric = new JLabel("度量选择", JLabel.CENTER);
    private JLabel combination = new JLabel("组合方式", JLabel.CENTER);
    private JLabel top = new JLabel("Top(K)组合结果", JLabel.CENTER);
    private JLabel filePath = new JLabel("输出文件路径", JLabel.CENTER);
    private JLabel fileType = new JLabel("输出文件类型", JLabel.CENTER);
    private JLabel position = new JLabel("树形方向", JLabel.CENTER);
    private JLabel label = new JLabel("标记特征索引及取值(e.g. 10;true;false)", JLabel.CENTER);

    // 输入框
    private JTextField featureNumberText = new JTextField("0", 15);
    private JTextField maxSelectFeatureNumberText = new JTextField("0", 15);
    private JTextField thresholdText = new JTextField("0.0", 15);
    private JComboBox<String> metricComboBox = new JComboBox<>();
    private JComboBox<String> combinationComboBox = new JComboBox<>();
    private JTextField topText = new JTextField("10", 15);
    private JTextField filePathText = new JTextField(15);
    private JComboBox<String> fileTypeComboBox = new JComboBox<>();
    private JComboBox<String> positionComboBox = new JComboBox<>();
    private JTextField labelText = new JTextField(15);

    //特征复选
    private JCheckBox[] checkBoxes;

    // 3.显示面板 组件
    private JPanel progressPanel = new JPanel(); //进度面板

    private JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100); //进度条
    private JLabel proInfo = new JLabel("Path(s): 0/0");

    public static JTextArea logArea = new JTextArea(20, 42);
    private JScrollPane logPanel = new JScrollPane(logArea); //日志面板
    public static JTextArea resultArea = new JTextArea(20, 42);
    private JScrollPane resultPanel = new JScrollPane(resultArea); //结果面板

    // 菜单
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("文件(F)");
    private JMenu helpMenu = new JMenu("帮助(H)");
    private JMenuItem openMenuItem = new JMenuItem("打开数据集");
    private JMenuItem helpMenuItem = new JMenuItem("帮助");
    //计时器
    private Timer timer = new Timer(50, this);

    //数据变量
    public static String[] attributeNames;
    public static int[] selectedIndices;
    public static String dataPath;
    public static StringBuffer log = new StringBuffer();
    public static int currentProgress = 0;

    // 耗时任务线程
    private SwingWorker<Void, Integer> swingWorker;

    public Window() {
        placeComponents(); // 摆放组件
        setComponentStyle(); // 设置组件样式
        addEventListener(); // 添加事件监听器
        frame.setVisible(true);// 设置界面可见
    }

    /**
     * 放置组件
     */
    public void placeComponents() {
        // 各面板布局
        panel.setLayout(new BorderLayout()); // 设置默认BorderLayout布局
        contentPanel.setLayout(new GridLayout(1, 2)); // 设置布局
        argsPanel.setLayout(new GridLayout(1, 2));
        argsLabelPanel.setLayout(new GridLayout(10, 1)); // 参数标签布局
        argsValuePanel.setLayout(new GridLayout(10, 1)); // 参数值布局
        featurePanel.setLayout(new GridLayout(10, 2)); // 特征布局
        displayPanel.setLayout(new BorderLayout());

        //面板位置
        frame.add(panel); // 主面板
        panel.add(operatePanel, BorderLayout.NORTH); // 操作面板
        panel.add(contentPanel, BorderLayout.CENTER); // 设置面板
        panel.add(displayPanel, BorderLayout.SOUTH); // 显示面板

        // 1.操作面板组件
        operatePanel.add(info);
        operatePanel.add(openButton);
        operatePanel.add(allButton);
        operatePanel.add(reverseButton);
        operatePanel.add(startButton);

        // 2.设置面板组件
        contentPanel.add(argsPanel); //参数面板
        contentPanel.add(jspFeaturePanel); //特征面板

        argsPanel.add(argsLabelPanel);
        argsPanel.add(argsValuePanel);

        argsLabelPanel.add(featureNumber);
        argsLabelPanel.add(maxSelectFeatureNumber);
        argsLabelPanel.add(threshold);
        argsLabelPanel.add(metric);
        argsLabelPanel.add(combination);
        argsLabelPanel.add(top);
        argsLabelPanel.add(filePath);
        argsLabelPanel.add(fileType);
        argsLabelPanel.add(position);
        argsLabelPanel.add(label);

        argsValuePanel.add(featureNumberText);
        argsValuePanel.add(maxSelectFeatureNumberText);
        argsValuePanel.add(thresholdText);
        argsValuePanel.add(metricComboBox);
        argsValuePanel.add(combinationComboBox);
        argsValuePanel.add(topText);
        argsValuePanel.add(filePathText);
        argsValuePanel.add(fileTypeComboBox);
        argsValuePanel.add(positionComboBox);
        argsValuePanel.add(labelText);

        // 3.显示面板组件
        progressPanel.add(new JLabel("探索路径百分比: "));
        progressPanel.add(progressBar);
        progressPanel.add(proInfo);

        displayPanel.add(progressPanel, BorderLayout.NORTH);
        displayPanel.add(logPanel, BorderLayout.WEST);
        displayPanel.add(resultPanel, BorderLayout.EAST);

        // 菜单
        frame.setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        fileMenu.add(openMenuItem);
        helpMenu.add(helpMenuItem);
    }

    /**
     * 设置组件样式
     */
    public void setComponentStyle() {
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // 设置窗体居中显示

        operatePanel.setBorder(BorderFactory.createLineBorder(new Color(12, 128, 32)));
        argsPanel.setBorder(new TitledBorder(null, "参数设置", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.RED));
        featurePanel.setBorder(new TitledBorder(null, "备选属性(包括n个特征+1个类别)", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.RED));
        displayPanel.setBorder(new TitledBorder(null, "Top (K) 输出结果(导出图片请先安装Graphviz)", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
        logPanel.setBorder(new TitledBorder(null, "特征组合日志", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.BLACK));
        resultPanel.setBorder(new TitledBorder(null, "组合结果", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.BLACK));

        progressBar.setStringPainted(true);
        allButton.setEnabled(false);
        reverseButton.setEnabled(false);
        startButton.setEnabled(false);
        featureNumberText.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setEditable(false);

        metricComboBox.addItem("F1");
        metricComboBox.addItem("AUC");
        metricComboBox.addItem("Recall@1");
        metricComboBox.addItem("Recall@5");
        metricComboBox.addItem("Recall@10");
        metricComboBox.addItem("MRR");
        metricComboBox.addItem("MAP");

        combinationComboBox.addItem("连乘");
        combinationComboBox.addItem("累加");

        fileTypeComboBox.addItem("svg");
        fileTypeComboBox.addItem("jpg");
        fileTypeComboBox.addItem("png");

        positionComboBox.addItem("垂直");
        positionComboBox.addItem("水平");

        // 菜单快捷键设置
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
    }

    /**
     * 添加事件监听器
     */
    public void addEventListener() {
        fileTypeComboBox.addActionListener(this::actionPerformed);
        positionComboBox.addActionListener(this::actionPerformed);
        startButton.addActionListener(this::actionPerformed);
        allButton.addActionListener(this::actionPerformed);
        reverseButton.addActionListener(this::actionPerformed);
        openButton.addActionListener(this::actionPerformed);

        openMenuItem.addActionListener(this::actionPerformed);
        helpMenuItem.addActionListener(this::actionPerformed);
    }

    /**
     * 事件动作实现
     *
     * @param e 事件源
     */
    public void actionPerformed(ActionEvent e) {

        //////////////////////////////////////////////////////////打开数据集文件夹事件///////////////////////////////////////
        if (e.getSource() == openButton || e.getSource() == openMenuItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  //only show directories
            int value = fileChooser.showDialog(new JLabel(), "选择数据集文件夹");
            // approve option
            if (value == JFileChooser.APPROVE_OPTION) {
                File dir = fileChooser.getSelectedFile();
                attributeNames = FileHandle.getAttributeNames(dir);
                selectedIndices = new int[attributeNames.length];
                for (int i = 0; i < selectedIndices.length; i++) selectedIndices[i] = 1;
                dataPath = dir.getPath();
                filePathText.setText(System.getProperty("user.dir") + "\\result");
                checkBoxes = new JCheckBox[attributeNames.length];

                //remove 之前复选框
                featurePanel.removeAll();
                for (int i = 0; i < checkBoxes.length; i++) {
                    checkBoxes[i] = new JCheckBox(attributeNames[i] + ": " + i);
                    featurePanel.add(checkBoxes[i]);
                }

                for (int i = 0; i < checkBoxes.length; i++) {
                    checkBoxes[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int count = 0;
                            for (int i = 0; i < checkBoxes.length; i++) {
                                if (checkBoxes[i].isSelected()) {
                                    count++;
                                }
                            }
                            if (count == 0) count += 1;
                            featureNumberText.setText((count - 1) + "");
                            maxSelectFeatureNumberText.setText(featureNumberText.getText());
                        }
                    });
                }
                allButton.setEnabled(true);
                reverseButton.setEnabled(true);
                startButton.setEnabled(true);
                featurePanel.revalidate();
            }
        }

        ////////////////////////////////////////////////////////////////选择测试特征/////////////////////////////////////
        if (e.getSource() == allButton) {
            for (int i = 0; i < checkBoxes.length; i++) checkBoxes[i].setSelected(true);
            featureNumberText.setText((checkBoxes.length - 1) + "");
            maxSelectFeatureNumberText.setText(featureNumberText.getText());
        }

        if (e.getSource() == reverseButton) {
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) checkBoxes[i].setSelected(false);
                else checkBoxes[i].setSelected(true);
            }
            int count = 0;
            for (int i = 0; i < checkBoxes.length; i++) if (checkBoxes[i].isSelected()) count++;

            featureNumberText.setText((count - 1) + "");
            maxSelectFeatureNumberText.setText(featureNumberText.getText());
        }

        /////////////////////////////////////////////////////////开始选择事件///////////////////////////////////////////
        if (e.getSource() == startButton) {
            timer.restart();
            //设置运行配置
            Setting.dataPath = dataPath;
            try {
                String[] temps = labelText.getText().split(";");
                Setting.labelIndex = Integer.parseInt(temps[0]);
                Setting.positiveName = temps[1];
                Setting.negativeName = temps[2];
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "请输入正确的标记特征索引值格式:index;true;false!", "标记特征出错!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (!checkBoxes[Setting.labelIndex].isSelected()) {
                JOptionPane.showMessageDialog(null, "请勾选标记属性: " + checkBoxes[Setting.labelIndex].getText() + "!", "出错!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int abandonCount = 0;
            for (int i = 0; i < checkBoxes.length; i++) if (!checkBoxes[i].isSelected()) abandonCount++;

            int[] abandonIndex = new int[abandonCount];
            for (int i = 0, j = 0; i < checkBoxes.length; i++) if (!checkBoxes[i].isSelected()) abandonIndex[j++] = i;

            Setting.abandonIndex = abandonIndex;
            Setting.metric = metricComboBox.getSelectedItem().toString();
            Setting.combination = combinationComboBox.getSelectedIndex();

            Setting.featureNumber = Integer.parseInt(featureNumberText.getText());
            if (Setting.featureNumber == 0) {
                JOptionPane.showMessageDialog(null, "请选择标记属性和至少一个特征!", "出错!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                Setting.maxSelectFeatureNumber = Integer.parseInt(maxSelectFeatureNumberText.getText());
                Setting.threshold = Double.parseDouble(thresholdText.getText());
                Setting.top = Integer.parseInt(topText.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "请在[最大特征数, 阈值栏, Top(k)] 中输入数字!", "出错!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Setting.filePath = filePathText.getText();
            Setting.fileType = fileTypeComboBox.getSelectedItem().toString();
            if (positionComboBox.getSelectedIndex() == 0) Setting.isHorizontal = false;
            else Setting.isHorizontal = true;

            try {
                Setting.setProjects();  //读取数据集
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "请不要选择非数值型属性!", "出错!", JOptionPane.INFORMATION_MESSAGE);
                return;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "请指定正确的标记索引或检查索引值!", "出错!", JOptionPane.INFORMATION_MESSAGE);
                ex.printStackTrace();
                return;
            }
            //开始选择
            progressBar.setMinimum(0);
            progressBar.setMaximum((int) Math.pow(2, Setting.featureNumber) - 1);

            Window.log = new StringBuffer();
            logArea.setText(log.toString());
            resultArea.setText("");
            progressBar.setValue(0);
            proInfo.setText("Path(s): 0");
            currentProgress = 0;
            try {
                swingWorker = new SwingWorker<Void, Integer>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        new MySelector().start(Setting.featureNumber, Setting.filePath, Setting.fileType, Setting.maxSelectFeatureNumber, Setting.threshold, Setting.isHorizontal, Setting.top);
                        BaseProject.finish();
                        timer.stop();
                        return null;
                    }
                };
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "请检查数据集格式是否正确!", "出错!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            swingWorker.execute();
        }

        //////////////////////////////////////////////// 计时器刷新界面界面
        if (e.getSource() == timer) {
            proInfo.setText("Path(s): " + currentProgress + "/" + progressBar.getMaximum());
            progressBar.setValue(currentProgress);
            resultArea.setText(Setting.resultString);
        }

        //////////////////////////////////////////////// 显示帮助信息
        if (e.getSource() == helpMenuItem) {
            JFrame helpFrame = new JFrame("帮助信息");
            helpFrame.setSize(500, 400);
            helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            helpFrame.setLocationRelativeTo(null); // 设置窗体居中显示
            JPanel panel = new JPanel();

            String textString = "数据文件说明\n";
            textString += "1. 数据集文件类型: .csv格式/.arff格式\n";
            textString += "2. 数据集文件夹格式: 一个数据集文件夹包含若干个子文件夹, 每个子文件夹代表一个项目, 每个项目文件夹包含若干个csv数据文件\n\n\n";
            textString += "   数据集样例\n";
            textString += "   data\n";
            textString += "       -> project_1\n";
            textString += "                   -> version_1.csv\n";
            textString += "                   -> version_2.csv\n";
            textString += "       -> project_2\n";
            textString += "                   -> version_1.csv\n";
            textString += "   \n";

            JTextArea textArea = new JTextArea(textString, 23, 44);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            JScrollPane jsp = new JScrollPane(textArea);
            panel.add(jsp);
            helpFrame.setContentPane(panel);
            helpFrame.setResizable(false);
            helpFrame.setVisible(true);
        }
    }
}


