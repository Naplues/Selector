# Selector
Feature Selector: To find excellent feature combinations according to various metrics using greedy strategy in various domain.

# 代码介绍及使用方法

## package: nju.gzq.selector
该包内包含了特征选择的算法实现和最后的结果作图 (Algorithm).

## package: nju.gzq.evaluation
该包内实现了部分度量的计算，包括Recall@k, MRR, MAP, F1, AUC 5种, 统一通过Evaluation 类来调用各个度量.
后续可以继续添加新的度量.

## package: jnu.gzq.base
该包内实现了数据集在运行时的存储结构和一些排序过程.

## demo: 计算最优F1组合
```java
/**
 * 简单使用:实现一个子类继承Selector类, 重写父类的getValue(Integer[] features)方法(即,调用预先实现的度量计算结果)
 *
 * @param args
 */
public class Main {
    /**
     * Program entry
     *
     * @param args
     */
    public static void main(String[] args) {
    	/**
	     * @param featureNumber       特征数目
	     * @param filePath            输出图片路径
	     * @param type                输出图片类型
	     * @param neededFeatureNumber 最多组合特征数
	     * @param threshold           性能阈值
	     * @param isHorizontal        节点摆放位置
	     * @param top                 输出前top个结果
    	*/
        new F1Selector().start(20, "f1", "svg", 10, .0, true, 10);
    }
}

class F1Selector extends Selector {
    public double getValue(Integer[] features) {
        int labelIndex = 20; //类别索引
        int[] abandonIndex = {}; //遗弃的索引，不使用这些索引的特征

        
        return Evaluation.getF1(features, "data/new_data", Evaluation.MULTIPLE, labelIndex, abandonIndex);
    }
}
```

## Sample Result
![Algorithm](https://github.com/Naplues/Selector/blob/master/assert/f1.svg "Algorithm")

## Selector Algorithm
![sample](https://github.com/Naplues/Selector/blob/master/assert/algorithm.bmp)


