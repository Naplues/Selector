package nju.gzq.evaluation.metrics;

import nju.gzq.base.BaseFeature;

public class Recall {
    /**
     * 召回率
     * @param features
     * @param k
     * @param revisionNumber
     * @return
     */
    public static double getValue(BaseFeature[][] features, int k, int revisionNumber) {
        double rc = 0;

        for (int i = 0; i < features.length; i++) {
            for (int j = 0; j < k && j < features[i].length; j++) {
                if (features[i][j].isLabel()) {
                    rc++;
                    break;
                }
            }
        }
        rc /= revisionNumber;
        return rc;
    }
}
