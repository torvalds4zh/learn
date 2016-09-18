package com.weibangong.weka.test;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;

/**
 * Created by chenbo on 16/7/22.
 */
public class HelloWeka {
    public static void main(String[] args) throws Exception {
        Classifier classifier = new J48();

        File inputFile = new File("/Users/haizhi/chenbo/weka/data/cpu.with.vendor.arff");//训练文件
        ArffLoader arff = new ArffLoader();
        arff.setFile(inputFile);
        Instances instanceTrain = arff.getDataSet();

        inputFile = new File("/Users/haizhi/chenbo/weka/data/cpu.with.vendor.test.arff");//测试文件
        arff.setFile(inputFile);
        Instances instanceTest = arff.getDataSet();
        instanceTest.setClassIndex(0);//设置分类属性所在行号
        double sum = instanceTest.numInstances();//获取实例数
        double right = 0.0f;

        instanceTrain.setClassIndex(0);
        classifier.buildClassifier(instanceTrain);
        System.out.println(classifier);

        for (int i = 0; i < sum; i++) {
            double predicted = classifier.classifyInstance(instanceTest.instance(i));
            System.out.println("预测某条记录的分类id: " + predicted + ", 分类值: " + instanceTest.classAttribute().value(((int) predicted)));
            System.out.println("测试文件的分类值： " + instanceTest.instance(i).classValue() + ", 记录：" + instanceTest.instance(i));
            System.out.println("-------------------------------------------------------------");
            if (classifier.classifyInstance(instanceTest.instance(i)) == instanceTest.instance(i).classValue()) {
                right++;
            }
        }
        System.out.println("J48 classification precision:" + (right / sum));
    }
}
