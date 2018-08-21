package nju.gzq.evaluation;

import nju.gzq.base.BaseFeature;

public class F1 {

    /**
     * F1 value
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
        System.out.println(precision + ", " + recall + ", " + F1value);
        return F1value;
    }
}
