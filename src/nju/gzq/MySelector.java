package nju.gzq;

import nju.gzq.evaluation.Evaluation;
import nju.gzq.selector.Selector;
import nju.gzq.selector.Setting;

public class MySelector extends Selector {
    public double getValue(Integer[] features) {
        double value = .0;
        try {
            if (Setting.metric.equals("Recall@1"))
                value = Evaluation.getRecall(features, Setting.dataPath, 1, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
            else if (Setting.metric.equals("Recall@5"))
                value = Evaluation.getRecall(features, Setting.dataPath, 5, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
            else if (Setting.metric.equals("Recall@10"))
                value = Evaluation.getRecall(features, Setting.dataPath, 10, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
            else if (Setting.metric.equals("MRR"))
                value = Evaluation.getMRR(features, Setting.dataPath, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
            else if (Setting.metric.equals("MAP"))
                value = Evaluation.getMAP(features, Setting.dataPath, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
            else if (Setting.metric.equals("F1_50%"))
                value = Evaluation.getF1_50(features, Setting.getProjects(), Setting.combination, Setting.labelIndex, Setting.abandonIndex);
            else if(Setting.metric.equals("F1_HTW"))
                value = Evaluation.getF1_HTW(features, Setting.getProjects(), Setting.combination, Setting.labelIndex, Setting.abandonIndex);
            else if (Setting.metric.equals("AUC"))
                value = Evaluation.getAUC(features, Setting.getProjects(), Setting.combination, Setting.labelIndex, Setting.abandonIndex);
            else value = .0;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return value;
        }
    }
}

