package nju.gzq.evaluation.metrics;

import nju.gzq.base.BaseFeature;

public class MRR {

    /**
     * MRR
     *
     * @param features
     * @param revisionNumber
     * @return
     */
    public static double getValue(BaseFeature[][] features, int revisionNumber) {
        double mrr = 0.0;
        for (int i = 0; i < features.length; i++) {
            for (int j = 0; j < features[i].length; j++) {
                if (features[i][j].isLabel()) {
                    mrr += 1.0 / (j + 1);
                    break;
                }
            }
        }
        mrr /= revisionNumber;
        return mrr;
    }
}
