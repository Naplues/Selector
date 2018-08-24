package nju.gzq.evaluation;

import nju.gzq.advance.plc.Project;
import nju.gzq.advance.plc.Ranking;
import nju.gzq.base.BaseProject;
import nju.gzq.base.BaseRanking;
import nju.gzq.evaluation.metrics.*;
import nju.gzq.gui.Window;

import java.io.File;


public class Evaluation {
    // combination approach (MULTIPLE x /SUMMATION +)
    public static final int MULTIPLE = 0;
    public static final int SUMMATION = 1;

    /**
     * 获取数据集上所有项目的平均Recall值
     *
     * @param features
     * @param dataPath
     * @param k
     * @param combination
     * @param labelIndex
     * @param abandonIndex
     * @return
     */
    public static double getRecall(Integer[] features, String dataPath, int k, int combination, int labelIndex, int... abandonIndex) {
        double recall = .0;

        File[] projects = new File(dataPath).listFiles();
        for (File p : projects) {
            Project project = new Project(p.getPath(), labelIndex, abandonIndex);
            project.setFeatures(Ranking.rankByFeature(project, combination, Ranking.RANK_DESC, features));
            recall += Recall.getValue(project.getFeatures(), k, project.getRevisionNumber());
        }
        recall /= projects.length;
        System.out.println("Recall@" + k + ": " + recall);
        return recall;
    }

    /**
     * 获取数据集上所有项目的平均MRR
     *
     * @param features
     * @param dataPath
     * @param combination
     * @param labelIndex
     * @param abandonIndex
     * @return
     */
    public static double getMRR(Integer[] features, String dataPath, int combination, int labelIndex, int... abandonIndex) {
        double mrr = .0;

        File[] projects = new File(dataPath).listFiles();
        for (File p : projects) {
            Project project = new Project(p.getPath(), labelIndex, abandonIndex);
            project.setFeatures(Ranking.rankByFeature(project, combination, Ranking.RANK_DESC, features));
            mrr += MRR.getValue(project.getFeatures(), project.getRevisionNumber());
        }
        mrr /= projects.length;
        System.out.println("MRR: " + mrr);
        return mrr;
    }

    /**
     * 获取数据集上所有项目的MAP
     *
     * @param features
     * @param dataPath
     * @param combination
     * @param labelIndex
     * @param abandonIndex
     * @return
     */
    public static double getMAP(Integer[] features, String dataPath, int combination, int labelIndex, int... abandonIndex) {
        double map = .0;

        File[] projects = new File(dataPath).listFiles();
        for (File p : projects) {
            Project project = new Project(p.getPath(), labelIndex, abandonIndex);
            project.setFeatures(Ranking.rankByFeature(project, combination, Ranking.RANK_DESC, features));
            map += MAP.getValue(project.getFeatures(), project.getRevisionNumber());
        }
        map /= projects.length;
        System.out.println("MAP: " + map);
        return map;
    }

    /**
     * 获取数据集上所有项目的平均F1值
     *
     * @param features
     * @param labelIndex   类别索引
     * @param abandonIndex 遗弃索引
     * @return
     */
    public static double getF1(Integer[] features, BaseProject[] baseProjects, int combination, int labelIndex, int... abandonIndex) {
        Double value = .0;

        for (BaseProject project : baseProjects) {
            project.setFeatures(BaseRanking.rankByFeature(project, combination, BaseRanking.RANK_DESC, features));  //Ranking
            value += F1.getValue(project.getFeatures());
        }
        value /= baseProjects.length;
        System.out.println("F1: " + value);
        return value;
    }

    /**
     * 获取数据集上所有项目的平均AUC值
     *
     * @param features
     * @param baseProjects
     * @param labelIndex   类别索引
     * @param abandonIndex 遗弃索引
     * @return
     */
    public static double getAUC(Integer[] features, BaseProject[] baseProjects, int combination, int labelIndex, int... abandonIndex) {
        double auc = .0;

        for (BaseProject project : baseProjects) auc += AUC.getValue(features, project.getFeatures(), combination);
        auc /= baseProjects.length;
        System.out.println("AUC: " + auc);
        Window.resultArea.append("AUC: " + auc);
        return auc;
    }
}
