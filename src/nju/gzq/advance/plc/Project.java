package nju.gzq.advance.plc;

import nju.gzq.base.BaseFeature;
import nju.gzq.base.BaseProject;

/**
 * feature[][]: 每一行代表一个bucket, 每一个元素代表一个bucket中的一个revision的特征记录
 */
public class Project extends BaseProject {

    private int revisionNumber; //原来版本数目
    private int filterNumber;   //过滤后的版本数目

    /**
     * Pid: 保存一个项目的所有bucket信息
     *
     * @param path         项目版本号
     * @param labelIndex
     * @param abandonIndex
     */
    public Project(String path, int labelIndex, int... abandonIndex) throws Exception {
        super(path, labelIndex, abandonIndex);
        revisionNumber = features.length;
        selectOracleFeature();  //选择有oracle的bucket
    }

    /**
     * 选择有oracle的bucket
     */
    public void selectOracleFeature() {
        int[] pass = new int[features.length];

        for (int i = 0, j; i < features.length; i++) {
            if (features[i].length == 1) continue;
            for (j = 0; j < features[i].length; j++) if (features[i][j].isLabel()) break;
            if (j == features[i].length) pass[i] = 1;
        }
        // 统计保留多少bucket
        int count = 0;
        int remain = 0;
        for (int i = 0; i < pass.length; i++) if (pass[i] == remain) count++;

        BaseFeature[][] filterFeature = new BaseFeature[count][];
        String[] filterBucketNames = new String[count];
        for (int i = 0, j = 0; i < features.length; i++) {
            if (pass[i] == remain) {
                filterFeature[j] = features[i];
                filterBucketNames[j] = dataFileNames[i];
                j++;
            }
        }
        features = filterFeature;
        dataFileNames = filterBucketNames;
        filterNumber = count;
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public int getFilterNumber() {
        return filterNumber;
    }

}
