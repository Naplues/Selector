package nju.gzq.selector;

import nju.gzq.base.BaseProject;

import java.io.File;

public class Setting {
    //度量计算参数
    public static String dataPath = "";
    public static int labelIndex = 0;
    public static int[] abandonIndex = {};
    public static String metric = "";
    public static int combination = 0;
    public static String positiveName = "yes";
    public static String negativeName = "no";

    //选择器参数
    public static int featureNumber = 0;
    public static int maxSelectFeatureNumber = 0;
    public static double threshold = .0;
    public static int top = 0;
    public static String filePath = "";
    public static String fileType = "";
    public static boolean isHorizontal = true;

    public static String resultString = "";

    //项目对象
    public static BaseProject[] baseProjects;

    public static void setProjects() throws Exception {
        File[] projects = new File(dataPath).listFiles();
        baseProjects = new BaseProject[projects.length];
        for (int i = 0; i < baseProjects.length; i++)
            baseProjects[i] = new BaseProject(projects[i].getPath(), labelIndex, abandonIndex);
    }

    public static BaseProject[] getProjects() {
        return baseProjects;
    }

    public static void print() {
        System.out.println("dataPath: " + dataPath);
        System.out.println("labelIndex: " + labelIndex);
        System.out.print("abandonIndex: ");
        for (int i = 0; i < abandonIndex.length; i++) System.out.print(abandonIndex[i] + " ");
        System.out.println();
        System.out.println("metric: " + metric);
        System.out.println("combination: " + combination);
        System.out.println("positiveName: " + positiveName);
        System.out.println("featureNumber: " + featureNumber);
        System.out.println("maxSelectFeatureNumber: " + maxSelectFeatureNumber);
        System.out.println("threshold: " + threshold);
        System.out.println("top: " + top);
        System.out.println("filePath: " + filePath);
        System.out.println("fileType: " + fileType);
        System.out.println();
    }
}
