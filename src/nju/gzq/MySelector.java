package nju.gzq;

import nju.gzq.evaluation.Evaluation;
import nju.gzq.selector.Selector;
import nju.gzq.selector.Setting;

public class MySelector extends Selector {
    public double getValue(Integer[] features) {

        if (Setting.metric.equals("Recall@1"))
            return Evaluation.getRecall(features, Setting.dataPath, 1, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
        else if (Setting.metric.equals("MRR"))
            return Evaluation.getMRR(features, Setting.dataPath, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
        else if (Setting.metric.equals("MAP"))
            return Evaluation.getMAP(features, Setting.dataPath, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
        else if (Setting.metric.equals("F1"))
            return Evaluation.getF1(features, Setting.dataPath, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
        else if (Setting.metric.equals("AUC"))
            return Evaluation.getAUC(features, Setting.dataPath, Setting.combination, Setting.labelIndex, Setting.abandonIndex);
        else return .0;
    }
}

