package nju.gzq;

import nju.gzq.evaluation.Evaluation;
import nju.gzq.selector.Selector;
import nju.gzq.selector.Setting;

public class MySelector extends Selector {
    public double getValue(Integer[] features) {

        if (Setting.metric.equals("Recall"))
            return Evaluation.getRecall(features, Setting.dataPath, Setting.k, Evaluation.MULTIPLE, Setting.labelIndex, Setting.abandonIndex);
        else if (Setting.metric.equals("MRR"))
            return Evaluation.getMRR(features, Setting.dataPath, Evaluation.MULTIPLE, Setting.labelIndex, Setting.abandonIndex);
        else if (Setting.metric.equals("MAP"))
            return Evaluation.getMAP(features, Setting.dataPath, Evaluation.MULTIPLE, Setting.labelIndex, Setting.abandonIndex);
        else if (Setting.metric.equals("F1"))
            return Evaluation.getF1(features, Setting.dataPath, Evaluation.MULTIPLE, Setting.labelIndex, Setting.abandonIndex);
        else if (Setting.metric.equals("AUC"))
            return Evaluation.getAUC(features, Setting.dataPath, Evaluation.MULTIPLE, Setting.labelIndex, Setting.abandonIndex);
        else return .0;
    }
}


class RecallSelector extends Selector {
    public double getValue(Integer[] features) {
        int labelIndex = 12;
        int[] abandonIndex = {0, 2};
        return Evaluation.getRecall(features, "data/buckets_data/form3", 1, Evaluation.MULTIPLE, labelIndex, abandonIndex);
    }
}

class MRRSelector extends Selector {
    public double getValue(Integer[] features) {
        int labelIndex = 12;
        int[] abandonIndex = {0, 2};

        return Evaluation.getMRR(features, "data/buckets_data/form3", Evaluation.MULTIPLE, labelIndex, abandonIndex);
    }
}

class MAPSelector extends Selector {
    public double getValue(Integer[] features) {
        int labelIndex = 12;
        int[] abandonIndex = {0, 2};
        return Evaluation.getMAP(features, "data/buckets_data/form3", Evaluation.MULTIPLE, labelIndex, abandonIndex);
    }
}

