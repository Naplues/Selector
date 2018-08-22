package nju.gzq;

import nju.gzq.evaluation.Evaluation;
import nju.gzq.selector.Selector;

public class Main {
    /**
     * Program entry
     *
     * @param args
     */
    public static void main(String[] args) {
        //new RecallSelector().start(10, "rc1", "svg", .0, false, 10);
        //new MRRSelector().start(10, "mrr", "svg", .0, false, 10);
        //new MAPSelector().start(10, "map", "svg", .0, false, 10);
        //new AUCSelector().start(6, "auc", "svg", .0, false, 10);
        new F1Selector().start(5, "f1", "svg", .0, true, 10);

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

class F1Selector extends Selector {
    public double getValue(Integer[] features) {
        int labelIndex = 0;
        int[] abandonIndex = {};
        return Evaluation.getF1(features, "data/debt_data", Evaluation.SUMMATION, labelIndex, abandonIndex);
    }
}

class AUCSelector extends Selector {
    public double getValue(Integer[] features) {
        int labelIndex = 33;
        int[] abandonIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 28, 29, 30, 31, 32};
        return Evaluation.getAUC(features, "data/ant", Evaluation.SUMMATION, labelIndex, abandonIndex);
    }
}