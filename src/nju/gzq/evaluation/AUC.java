package nju.gzq.evaluation;

import nju.gzq.base.BaseFeature;

import java.util.ArrayList;
import java.util.List;

public class AUC {

    // combination approach (MULTIPLE x /SUMMATION +)
    public static final int MULTIPLE = 0;
    public static final int SUMMATION = 1;

    /**
     * 一个项目的平均AUC值
     *
     * @param features
     * @param baseFeatures
     * @return
     */
    public static double getAUC(Integer[] features, BaseFeature[][] baseFeatures, int combination) {
        double auc = .0;
        for (int i = 0; i < baseFeatures.length; i++) {
            // 计算组合分数
            for (int j = 0; j < baseFeatures[i].length; j++) {
                double probability = 1.0;
                for (int k = 0; k < features.length; k++) {
                    if (combination == MULTIPLE)
                        probability *= baseFeatures[i][j].getValueFromIndex(features[k]);
                    else
                        probability += baseFeatures[i][j].getValueFromIndex(features[k]);
                }

                baseFeatures[i][j].setTemp(probability);
            }
            auc += singleAUC(baseFeatures[i]);
        }

        auc /= baseFeatures.length;
        return auc;
    }

    /**
     * 单个文件中数据的AUC
     *
     * @param baseFeatures
     * @return
     */
    public static double singleAUC(BaseFeature[] baseFeatures) {
        List<Integer> mIndex = new ArrayList<>();
        List<Integer> nIndex = new ArrayList<>();
        for (int i = 0; i < baseFeatures.length; i++) {
            if (baseFeatures[i].isLabel()) mIndex.add(i);
            else nIndex.add(i);
        }

        double result = .0;
        for (int i = 0; i < mIndex.size(); i++) {
            double mP = baseFeatures[mIndex.get(i)].getTemp();
            for (int j = 0; j < nIndex.size(); j++) {
                double nP = baseFeatures[nIndex.get(j)].getTemp();
                if (mP > nP) result += 1;
                else if (mP == nP) result += 0.5;
            }
        }
        result /= (mIndex.size() * nIndex.size());
        return result;
    }
}
