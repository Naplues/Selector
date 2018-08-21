package nju.gzq;

import nju.gzq.evaluation.AUC;
import nju.gzq.evaluation.Evaluation;
import nju.gzq.selector.Selector;

public class Main extends Selector {
    /**
     * Program entry
     *
     * @param args
     */
    public static void main(String[] args) {
        testSelector();
    }

    /**
     * 测试特征选择器
     *
     * @throws Exception
     */
    public static void testSelector() {
        //选择特征
        int featureNumber = 6;
        int neededFeatureNumber = featureNumber;
        double threshold = 0.0;
        String outputPath = "C:\\Users\\gzq\\Desktop\\auc";
        String fileType = "svg";
        int top = 10;
        new Main().start(featureNumber, outputPath, fileType, neededFeatureNumber, threshold, false, top, false);
    }

    public double getValue(Integer[] features) {
        int labelIndex = 33;
        int[] abandonIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 28, 29, 30, 31, 32};
        return Evaluation.getAUC(features, "data/ant", AUC.MULTIPLE, labelIndex, abandonIndex);
    }
}
