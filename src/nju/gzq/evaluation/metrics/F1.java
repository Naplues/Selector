package nju.gzq.evaluation.metrics;

import nju.gzq.base.BaseFeature;

public class F1 {

    /**
     * F1 value 阈值0.5
     * F1 = 2 * P * R / (P + R)
     *
     * @param features
     * @return
     */
    public static Double getValue(BaseFeature[][] features, double threshold) {
        Double F1value = .0;
        Double precision = .0, recall = .0;

        for (BaseFeature[] feature : features) {
            Double positive = .0;
            int position = 0;         //计数位置
            int total = 0;            //所有正例数目
            for (int i = 0; i < feature.length; i++) {
                if (feature[i].isLabel()) total++;
            }
            if (threshold > 0 && threshold <= 1) position = new Double(feature.length * threshold).intValue();
            else for (int i = 0; i < feature.length; i++) if (feature[i].getTemp() != 0) position++;

            for (int i = 0; i < position; i++) if (feature[i].isLabel()) positive++;

            Double P = positive / position; // 32/47
            Double R = positive / total;    // 32/195
            F1value += 2 * P * R / (P + R);
            precision += P;
            recall += R;
        }
        F1value /= features.length;
        //System.out.println(precision/features.length + ", " + recall/features.length + ", " + F1value);
        return F1value;
    }

    /**
     * F1 value 1为正例,0为负类
     * F1 = 2 * P * R / (P + R)
     *
     * @param features
     * @return
     */
    public static Double getValue(BaseFeature[][] features) {
        Double F1value = .0;
        Double precision = .0, recall = .0;

        for (BaseFeature[] feature : features) {
            Double positive = .0;
            int position = 0;         //计数位置
            int total = 0;            //所有正例数目
            for (int i = 0; i < feature.length; i++) {
                if (feature[i].getTemp() != 0) position++;
                if (feature[i].isLabel()) total++;
            }

            for (int i = 0; i < position; i++) if (feature[i].isLabel()) positive++;

            Double P = positive / position; // 32/47
            Double R = positive / total;    // 32/195
            F1value += 2 * P * R / (P + R);
            precision += P;
            recall += R;
        }
        precision /= features.length;
        recall /= features.length;
        F1value /= features.length;
        // System.out.println(precision + ", " + recall + ", " + F1value);
        return F1value;
    }
}
