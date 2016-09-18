package com.weibangong.weka.test;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;

/**
 * Created by chenbo on 16/7/22.
 */
public class LinearRegressionTest {
    public static void main(String[] args) throws IOException {
        File inputFile = new File(""); //训练文件
        ArffLoader loader = new ArffLoader();
        loader.setFile(inputFile);
        Instances instancesTrain =loader.getDataSet();
        instancesTrain.setClassIndex(0);

    }
}
