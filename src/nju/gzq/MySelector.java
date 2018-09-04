package nju.gzq;

import nju.gzq.evaluation.Evaluation;
import nju.gzq.selector.Selector;
import nju.gzq.selector.Setting;

public class MySelector extends Selector {
    public double getValue(Integer[] features) {
        double value = .0;
        try {
            switch (Setting.metric) {
                case "Recall@1":
                    value = Evaluation.getRecall(features, Setting.dataPath, 1, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
                    break;
                case "Recall@5":
                    value = Evaluation.getRecall(features, Setting.dataPath, 5, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
                    break;
                case "Recall@10":
                    value = Evaluation.getRecall(features, Setting.dataPath, 10, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
                    break;
                case "MRR":
                    value = Evaluation.getMRR(features, Setting.dataPath, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
                    break;
                case "MAP":
                    value = Evaluation.getMAP(features, Setting.dataPath, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
                    break;
                case "F1_50%":
                    value = Evaluation.getF1_50(features, Setting.getProjects(), Setting.combination);
                    break;
                case "F1_HTW":
                    value = Evaluation.getF1_HTW(features, Setting.getProjects(), Setting.combination);
                    break;
                case "AUC":
                    value = Evaluation.getAUC(features, Setting.getProjects(), Setting.combination);
                    break;
                default:
                    value = .0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }
}

