package com.weibangong.weka.test;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.*;
import weka.core.converters.ArffLoader;

import java.io.*;

/**
 * Created by chenbo on 16/7/22.
 */
public class PredictTest {
    public static void main(String[] args) throws Exception{
        double[][] a = {{476005046, 306349941}, {377331965, 476005046}};

        File inputFile = new File("/Users/haizhi/chenbo/IntellijWorkspace/learn/weka-learn/src/main/resources/test.arff");
        ArffLoader atf = new ArffLoader();
        try {
            atf.setFile(inputFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Instances instancesTrain = atf.getDataSet();
        instancesTrain.setClassIndex(0);


        FastVector attrs = new FastVector();
        Attribute ratio = new Attribute("CURtrdsum", 1);
        Attribute preratio = new Attribute("PREtrdsum", 2);
        attrs.addElement(ratio);
        attrs.addElement(preratio);
        Instances instancesTest = new Instances("bp", attrs, attrs.size());
        instancesTest.setClass(ratio);
        for (int k = 0; k < 2; k++) {
            Instance ins = new DenseInstance(attrs.size());
            ins.setDataset(instancesTest);
            ins.setValue(ratio, a[k][0]);
            ins.setValue(preratio, a[k][1]);
            instancesTest.add(ins);
        }


        MultilayerPerceptron m_classifier = new MultilayerPerceptron();
        try {
            m_classifier.buildClassifier(instancesTrain);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            try {
                System.out.println(m_classifier.classifyInstance(instancesTest.instance(i)) + ",,," + instancesTest.instance(i).classValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("bp success!");
    }

}
