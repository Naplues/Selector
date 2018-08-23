package nju.gzq.gui;

import nju.gzq.MySelector;
import nju.gzq.selector.FileHandle;
import nju.gzq.selector.Setting;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Window implements ActionListener {
    // 创建 JFrame 实例
    public JFrame frame = new JFrame("Feature Selector");

    public static JPanel panel = new JPanel();
    public static JPanel leftPanel = new JPanel();
    public static JPanel rightPanel = new JPanel();
    public static JPanel bottomPanel = new JPanel();
    public static JPanel operatePanel = new JPanel(); // 操作面板
    public static JPanel contentPanel = new JPanel(); // 内容面板
    public static JPanel argsPanel = new JPanel(); //参数面板
    public static JPanel panel1 = new JPanel(); // 1
    public static JPanel panel2 = new JPanel(); // 2
    public static JPanel featurePanel = new JPanel(); // 特征面板

    // 操作按钮
    public static JButton openButton = new JButton("打开数据集");

    public static JButton allButton = new JButton("全选");
    public static JButton reverseButton = new JButton("反选");

    public static JButton startButton = new JButton("开始选择");


    // 标签
    public static JLabel featureNumber = new JLabel("使用特征数", JLabel.CENTER);
    public static JLabel maxSelectFeatureNumber = new JLabel("最大选择特征数", JLabel.CENTER);
    public static JLabel threshold = new JLabel("最小性能阈值", JLabel.CENTER);
    public static JLabel metric = new JLabel("度量选择", JLabel.CENTER);
    public static JLabel top = new JLabel("Top(K)组合结果", JLabel.CENTER);
    public static JLabel filePath = new JLabel("输出文件路径", JLabel.CENTER);
    public static JLabel fileType = new JLabel("输出文件类型", JLabel.CENTER);
    public static JLabel position = new JLabel("树形方向", JLabel.CENTER);
    public static JLabel label = new JLabel("类别特征名(从备选特征中选择)", JLabel.CENTER);


    // 输入框
    public static JTextField featureNumberText = new JTextField(15);
    public static JTextField maxSelectFeatureNumberText = new JTextField(15);
    public static JTextField thresholdText = new JTextField(15);
    public static JComboBox<String> metricComboBox = new JComboBox<>();
    public static JTextField topText = new JTextField(15);
    public static JTextField filePathText = new JTextField(15);
    public static JComboBox<String> fileTypeComboBox = new JComboBox<>();
    public static JComboBox<String> positionComboBox = new JComboBox<>();
    public static JTextField labelText = new JTextField(15);


    //特征复选
    public static JCheckBox[] checkBoxes;

    //结果区域
    public static JTextArea resultArea = new JTextArea(20, 88);
    public static JScrollPane jsp = new JScrollPane(resultArea);
    public static JScrollPane jspFeaturePanel = new JScrollPane(featurePanel);

    public static String[] attributeNames;
    public static int[] selectedIndices;
    public static String dataPath;

    public Window() {
        placeComponents(); // 摆放组件
    }

    public void placeComponents() {
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // 设置窗体居中显示
        frame.add(Window.panel); // 添加面板
        panel.setLayout(new BorderLayout()); // 设置默认BorderLayout布局
        panel.add(operatePanel, BorderLayout.NORTH); // 操作面板
        panel.add(contentPanel, BorderLayout.CENTER); // 内容面板
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        // 操作面板
        operatePanel.setBorder(BorderFactory.createLineBorder(new Color(12, 128, 32)));
        operatePanel.add(openButton);
        operatePanel.add(allButton);
        operatePanel.add(reverseButton);
        operatePanel.add(startButton);
        allButton.setEnabled(false);
        reverseButton.setEnabled(false);
        startButton.setEnabled(false);


        // 内容面板
        contentPanel.setLayout(new GridLayout(1, 2)); // 设置内容面板
        contentPanel.add(argsPanel);
        contentPanel.add(jspFeaturePanel);
        argsPanel.setLayout(new GridLayout(1, 2));
        argsPanel.setBorder(new TitledBorder(null, "参数设置", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.RED));

        panel1.setLayout(new GridLayout(10, 1)); // 设置布局
        panel2.setLayout(new GridLayout(10, 1)); // 设置布局
        argsPanel.add(panel1);
        argsPanel.add(panel2);

        featurePanel.setLayout(new GridLayout(10, 2));
        featurePanel.setBorder(new TitledBorder(null, "备选属性(包括n个特征+1个类别)", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.RED));

        panel1.add(featureNumber);
        panel1.add(maxSelectFeatureNumber);
        panel1.add(threshold);
        panel1.add(metric);
        panel1.add(top);
        panel1.add(filePath);
        panel1.add(fileType);
        panel1.add(position);
        panel1.add(label);


        // 创建文本域用于用户输入

        featureNumberText.setEditable(false);
        featureNumberText.setText("0");
        maxSelectFeatureNumberText.setText("0");
        thresholdText.setText("0.0");
        topText.setText("10");

        metricComboBox.addItem("F1");
        metricComboBox.addItem("AUC");
        metricComboBox.addItem("Recall");
        metricComboBox.addItem("MRR");
        metricComboBox.addItem("MAP");

        fileTypeComboBox.addItem("svg");
        fileTypeComboBox.addItem("jpg");
        fileTypeComboBox.addItem("png");

        positionComboBox.addItem("垂直");
        positionComboBox.addItem("水平");
        panel2.add(featureNumberText);
        panel2.add(maxSelectFeatureNumberText);
        panel2.add(thresholdText);
        panel2.add(metricComboBox);
        panel2.add(topText);
        panel2.add(filePathText);
        panel2.add(fileTypeComboBox);
        panel2.add(positionComboBox);
        panel2.add(labelText);


        // 结果输出框
        bottomPanel.add(jsp);
        bottomPanel.setBorder(new TitledBorder(null, "Top (K) 输出结果", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.BLACK));

        // 添加事件监听
        fileTypeComboBox.addActionListener(this::actionPerformed);
        positionComboBox.addActionListener(this::actionPerformed);
        startButton.addActionListener(this::actionPerformed);
        allButton.addActionListener(this::actionPerformed);
        reverseButton.addActionListener(this::actionPerformed);
        openButton.addActionListener(this::actionPerformed);

        frame.setVisible(true);// 设置界面可见
    }

    /**
     * 动作实现
     */
    public void actionPerformed(ActionEvent e) {

        //选择数据集
        if (e.getSource() == openButton) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  //only show directories
            int value = fileChooser.showDialog(new JLabel(), "选择数据集文件夹");

            // approve option
            if (value == JFileChooser.APPROVE_OPTION) {
                File dir = fileChooser.getSelectedFile();

                attributeNames = FileHandle.getAttributeNames(dir);
                selectedIndices = new int[attributeNames.length];
                for (int i = 0; i < selectedIndices.length; i++) selectedIndices[i] = 1;
                filePathText.setText(dir.getPath() + "\\result");
                dataPath = dir.getPath();
                checkBoxes = new JCheckBox[attributeNames.length];
                for (int i = 0; i < checkBoxes.length; i++) {
                    checkBoxes[i] = new JCheckBox(attributeNames[i]);
                    featurePanel.add(checkBoxes[i]);
                }

                for (int i = 0; i < checkBoxes.length; i++) {
                    checkBoxes[i].addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
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

        //选择测试特征
        if (e.getSource() == allButton) {
            for (int i = 0; i < checkBoxes.length; i++)
                checkBoxes[i].setSelected(true);
            featureNumberText.setText((checkBoxes.length - 1) + "");
            maxSelectFeatureNumberText.setText(featureNumberText.getText());
        }

        if (e.getSource() == reverseButton) {
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected())
                    checkBoxes[i].setSelected(false);
                else
                    checkBoxes[i].setSelected(true);
            }
            int count = 0;
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    count++;
                }
            }
            featureNumberText.setText((count - 1) + "");
            maxSelectFeatureNumberText.setText(featureNumberText.getText());
        }

        //开始选择事件
        if (e.getSource() == startButton) {

            //设置运行配置

            Setting.dataPath = dataPath;
            String labelString = labelText.getText();
            for (int i = 0; i < attributeNames.length; i++) {
                if (labelString.equals(attributeNames[i])) {
                    Setting.labelIndex = i;
                    break;
                }
            }

            int abandonCount = 0;
            for (int i = 0; i < checkBoxes.length; i++) {
                if (!checkBoxes[i].isSelected()) abandonCount++;
            }
            int[] abandonIndex = new int[abandonCount];
            for (int i = 0, j = 0; i < checkBoxes.length; i++)
                if (!checkBoxes[i].isSelected()) {
                    abandonIndex[j++] = i;
                }


            Setting.abandonIndex = abandonIndex;
            Setting.metric = metricComboBox.getSelectedItem().toString();

            Setting.featureNumber = Integer.parseInt(featureNumberText.getText());
            Setting.maxSelectFeatureNumber = Integer.parseInt(maxSelectFeatureNumberText.getText());
            Setting.threshold = Double.parseDouble(thresholdText.getText());
            Setting.top = Integer.parseInt(topText.getText());
            Setting.filePath = filePathText.getText();
            Setting.fileType = fileTypeComboBox.getSelectedItem().toString();
            if (positionComboBox.getSelectedIndex() == 0)
                Setting.isHorizontal = false;
            else
                Setting.isHorizontal = true;

            System.out.println("开始选择");
            //开始选择
            new MySelector().start(Setting.featureNumber, Setting.filePath, Setting.fileType, Setting.maxSelectFeatureNumber, Setting.threshold, Setting.isHorizontal, Setting.top);
            System.out.println("选择结束");
            resultArea.setText(Setting.resultString);
        }
    }
}