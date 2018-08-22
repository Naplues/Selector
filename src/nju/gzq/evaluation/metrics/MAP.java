package nju.gzq.evaluation.metrics;

import nju.gzq.base.BaseFeature;

public class MAP {

    /**
     * MAP
     *
     * @param features
     * @param revisionNumber
     * @return
     */
    public static double getValue(BaseFeature[][] features, int revisionNumber) {
        double map = 0.0;
        for (int i = 0; i < features.length; i++) {
            double ap = 0.0;
            int k = 0;
            for (int j = 0; j < features[i].length; j++) {
                if (features[i][j].isLabel()) {
                    ap += (double) (k + 1) / (j + 1);
                    k++;
                }
            }
            ap /= k;
            map += ap;
        }
        map /= revisionNumber;
        return map;
    }
}
