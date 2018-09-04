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
     * @param features     选中的特征索引
     * @param dataPath     数据路径
     * @param k            top k取值
     * @param combination  组合方式
     * @param labelIndex   类别索引
     * @param abandonIndex 遗弃索引
     * @return Recall value
     */
    public static double getRecall(Integer[] features, String dataPath, int k, int combination, int labelIndex, int... abandonIndex) throws Exception {
        double recall = .0;

        File[] projects = new File(dataPath).listFiles();
        if (projects == null) return recall;
        for (File p : projects) {
            Project project = new Project(p.getPath(), labelIndex, abandonIndex);
            project.setFeatures(Ranking.rankByFeature(project, combination, Ranking.RANK_DESC, features));
            recall += Recall.getValue(project.getFeatures(), k, project.getRevisionNumber());
        }
        recall /= projects.length;
        StringBuilder tempLog = new StringBuilder().append("Path ").append(Window.currentProgress).append(":[ ");
        for (Integer feature : features) tempLog.append(feature).append(" ");
        tempLog.append("], Recall@").append(k).append("= ").append(recall).append("\n");
        Window.log.append(tempLog.toString());
        Window.logArea.setText(Window.log.toString());
        return recall;
    }

    /**
     * 获取数据集上所有项目的平均MRR
     *
     * @param features     选中的特征索引
     * @param dataPath     数据路径
     * @param combination  组合方式
     * @param labelIndex   类别索引
     * @param abandonIndex 遗弃索引
     * @return MRR value
     */
    public static double getMRR(Integer[] features, String dataPath, int combination, int labelIndex, int... abandonIndex) throws Exception {
        double mrr = .0;

        File[] projects = new File(dataPath).listFiles();
        if (projects == null) return mrr;
        for (File p : projects) {
            Project project = new Project(p.getPath(), labelIndex, abandonIndex);
            project.setFeatures(Ranking.rankByFeature(project, combination, Ranking.RANK_DESC, features));
            mrr += MRR.getValue(project.getFeatures(), project.getRevisionNumber());
        }
        mrr /= projects.length;
        StringBuilder tempLog = new StringBuilder().append("Path ").append(Window.currentProgress).append(":[ ");
        for (Integer feature : features) tempLog.append(feature).append(" ");
        tempLog.append("], MRR= ").append(mrr).append("\n");
        Window.log.append(tempLog.toString());
        Window.logArea.setText(Window.log.toString());
        return mrr;
    }

    /**
     * 获取数据集上所有项目的MAP
     *
     * @param features     选中的特征索引
     * @param dataPath     数据路径
     * @param combination  组合方式
     * @param labelIndex   类别索引
     * @param abandonIndex 遗弃索引
     * @return MAP value
     */
    public static double getMAP(Integer[] features, String dataPath, int combination, int labelIndex, int... abandonIndex) throws Exception {
        double map = .0;

        File[] projects = new File(dataPath).listFiles();
        if (projects == null) return map;
        for (File p : projects) {
            Project project = new Project(p.getPath(), labelIndex, abandonIndex);
            project.setFeatures(Ranking.rankByFeature(project, combination, Ranking.RANK_DESC, features));
            map += MAP.getValue(project.getFeatures(), project.getRevisionNumber());
        }
        map /= projects.length;
        StringBuilder tempLog = new StringBuilder().append("Path ").append(Window.currentProgress).append(":[ ");
        for (Integer feature : features) tempLog.append(feature).append(" ");
        tempLog.append("], MAP= ").append(map).append("\n");
        Window.log.append(tempLog.toString());
        Window.logArea.setText(Window.log.toString());
        return map;
    }

    /**
     * 获取数据集上所有项目的平均F1值
     *
     * @param features     选中的特征索引
     * @param baseProjects 项目数据
     * @param combination  组合方式
     * @return F1 value
     */
    public static double getF1_50(Integer[] features, BaseProject[] baseProjects, int combination) {
        Double value = .0;

        for (BaseProject project : baseProjects) {
            project.setFeatures(BaseRanking.rankByFeature(project, combination, BaseRanking.RANK_DESC, features));  //Ranking
            value += F1.getValue(project.getFeatures(), 0.5);
        }
        value /= baseProjects.length;
        StringBuilder tempLog = new StringBuilder().append("Path ").append(Window.currentProgress).append(":[ ");
        for (Integer feature : features) tempLog.append(feature).append(" ");
        tempLog.append("], F1_50%= ").append(value).append("\n");
        Window.log.append(tempLog.toString());
        Window.logArea.setText(Window.log.toString());
        return value;
    }

    /**
     * 获取数据集上所有项目的平均F1值
     *
     * @param features     选中的特征索引
     * @param baseProjects 项目数据
     * @param combination  组合方式
     * @return F1 value
     */
    public static double getF1_HTW(Integer[] features, BaseProject[] baseProjects, int combination) {
        Double value = .0;

        for (BaseProject project : baseProjects) {
            project.setFeatures(BaseRanking.rankByFeature(project, combination, BaseRanking.RANK_DESC, features));  //Ranking
            value += F1.getValue(project.getFeatures());
        }
        value /= baseProjects.length;
        StringBuilder tempLog = new StringBuilder().append("Path ").append(Window.currentProgress).append(":[ ");
        for (Integer feature : features) tempLog.append(feature).append(" ");
        tempLog.append("], F1_HTW= ").append(value).append("\n");
        Window.log.append(tempLog.toString());
        Window.logArea.setText(Window.log.toString());
        return value;
    }

    /**
     * 获取数据集上所有项目的平均AUC值
     *
     * @param features     选中的特征索引
     * @param baseProjects 项目数据
     * @param combination  组合方式
     * @return AUC value
     */
    public static double getAUC(Integer[] features, BaseProject[] baseProjects, int combination) {
        double auc = .0;

        for (BaseProject project : baseProjects) auc += AUC.getValue(features, project.getFeatures(), combination);
        auc /= baseProjects.length;
        StringBuilder tempLog = new StringBuilder().append("Path ").append(Window.currentProgress).append(":[ ");
        for (Integer feature : features) tempLog.append(feature).append(" ");
        tempLog.append("], AUC= ").append(auc).append("\n");
        Window.log.append(tempLog.toString());
        Window.logArea.setText(Window.log.toString());
        return auc;
    }
}
