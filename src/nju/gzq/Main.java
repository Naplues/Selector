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
        //new MRRSelector().start(10, "mrr", "svg", 10,.0, false, 10);
        //new MAPSelector().start(10, "map", "svg", 3, .0, false, 10);
        new AUCSelector().start(6, "auc", "svg",6, .0, false, 10);
        //new MySelector().start(20, "f1", "svg", 3, .0, true, 10);
        //new Window();

    }
}

class AUCSelector extends Selector {
    public double getValue(Integer[] features) {
        int labelIndex = 33;
        int[] abandonIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 28, 29, 30, 31, 32};
        return Evaluation.getAUC(features, "data/ant", Evaluation.SUMMATION, labelIndex, abandonIndex);
    }
}