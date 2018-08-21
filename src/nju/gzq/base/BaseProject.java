package nju.gzq.base;

import nju.gzq.selector.FileHandle;

import java.io.File;
import java.util.List;

/**
 * BaseProject: Built-in Project class which defines the data structure of a target project
 * projectName: 项目文件夹的名称
 * dataFileNames: 项目下每个文件的名称
 * BaseFeature[][]: 每一行代表一个文件里的所有记录数目, 所有行组成一个项目,若项目只有一个数据文件,则大小为BaseFeature[1][]
 */
public class BaseProject {
    private String projectName;
    private String[] dataFileNames;
    private BaseFeature[][] features;
    public static String separator = ",";


    /**
     * 创建项目数据
     *
     * @param path         项目文件夹路径
     * @param labelIndex   标记索引值
     * @param abandonIndex 遗弃索引值
     */
    public BaseProject(String path, int labelIndex, int... abandonIndex) {
        projectName = new File(path).getName();
        File[] dataFiles = new File(path).listFiles(); // all data files in a project
        dataFileNames = new String[dataFiles.length];
        features = new BaseFeature[dataFiles.length][];

        for (int i = 0; i < features.length; i++) {
            dataFileNames[i] = dataFiles[i].getPath(); // 数据文件名称
            //数据行,每行代表一条特征实例,第一行为名称
            List<String> lines = FileHandle.readFileToLines(dataFiles[i].getPath());
            //有feature[i].length 个特征实例,大小为文件行数-1
            features[i] = new BaseFeature[lines.size() - 1];

            for (int j = 0; j < features[i].length; j++) {
                features[i][j] = new BaseFeature(lines.get(j + 1).split(separator), labelIndex, abandonIndex);
            }
        }
    }

    public void setFeatures(BaseFeature[][] features) {
        this.features = features;
    }

    public BaseFeature[][] getFeatures() {
        return features;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String[] getDataFileNames() {
        return dataFileNames;
    }

    public void setDataFileNames(String[] dataFileNames) {
        this.dataFileNames = dataFileNames;
    }
}
