package nju.gzq.evaluation;

import nju.gzq.base.BaseFeature;
import nju.gzq.base.BaseProject;

import java.io.File;


public class Evaluation {


    public static double getRecall() {
        double recall = .0;
        return recall;
    }

    public static double getMRR() {
        double mrr = .0;
        return mrr;
    }

    public static double getMAP() {
        double map = .0;
        return map;
    }

    public static double getF1() {
        double f1 = .0;
        return f1;
    }

    /**
     * 获取数据集上所有项目的平均AUC值
     *
     * @param features
     * @param dataPath
     * @param labelIndex   类别索引
     * @param abandonIndex 遗弃索引
     * @return
     */
    public static double getAUC(Integer[] features, String dataPath, int combination, int labelIndex, int... abandonIndex) {
        double auc = .0;
        File[] files = new File(dataPath).listFiles();
        for (File file : files) {
            BaseProject project = new BaseProject(file.getPath(), labelIndex, abandonIndex);
            BaseFeature[][] baseFeatures = project.getFeatures();
            auc += AUC.getAUC(features, baseFeatures, combination);
        }
        auc /= files.length;
        System.out.println(auc);
        return auc;
    }
}
