package com.weibangong.spark.test;

import com.clearspring.analytics.util.Lists;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.junit.Before;
import org.junit.Test;
import scala.Tuple2;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenbo on 16/7/25.
 */
public class SimpleApp implements Serializable {

//    @Before
//    public void startUp() {
//        SparkConf conf = new SparkConf().setMaster("local").setAppName("Simple Application");
//        sc = new JavaSparkContext(conf);
//    }

    @Test
    public void aaa() {
        String textFile = "README.md"; // Should be some file on your system
        textFile = "/Users/haizhi/chenbo/spark" + File.separator + textFile;
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Simple Application");
//        SparkConf conf = new SparkConf().setMaster("spark://master:7077").setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines = sc.textFile(textFile);

//        long numAs = rdd.filter(new Function<String, Boolean>() {
//            public Boolean call(String s) {
//                return s.contains("a");
//            }
//        }).count();
//
//        long numBs = rdd.filter(new Function<String, Boolean>() {
//            public Boolean call(String s) {
//                return s.contains("b");
//            }
//        }).count();
//
//        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        /**
         * count total words
         */
//        JavaRDD<Integer> mapResults = lines.map(new Function<String, Integer>() {
//            @Override
//            public Integer call(String line) throws Exception {
//                return line.length();
//            }
//        });
//
//        Integer reduceResult = mapResults.reduce(new Function2<Integer, Integer, Integer>() {
//            @Override
//            public Integer call(Integer a, Integer b) throws Exception {
//                return a + b;
//            }
//        });
//
//        System.out.println(reduceResult);

        /**
         * count word by key
         */
        JavaPairRDD<String, Integer> mapResults = lines.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2(s, 1);
            }
        });

        JavaPairRDD<String, Integer> reduceResults = mapResults.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        System.out.println(reduceResults.take(100));
    }

    @Test
    public void testCountWord() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("hdfs://localhost:9000/user/haizhi/README.md");

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" "));
            }
        });

        JavaPairRDD<String, Integer> maResults = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2(s, 1);
            }
        });

        JavaPairRDD<String, Integer> reduceResults = maResults.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        reduceResults.saveAsTextFile("hdfs://localhost:9000/user/haizhi/count.txt");
        System.out.println(reduceResults.collect());
    }

    @Test
    public void piEstimation() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);

        Integer NUM_SAMPLES = 100;
        List<Integer> l = new ArrayList(NUM_SAMPLES);
        for (int i = 0; i < NUM_SAMPLES; i++) {
            l.add(i);
        }

        JavaRDD<Integer> match = sc.parallelize(l).filter(new Function<Integer, Boolean>() {
            @Override
            public Boolean call(Integer v1) throws Exception {
                double x = Math.random();
                double y = Math.random();
                return x * x + y * y < 1;
            }
        });

        System.out.println("Pi is roughly " + 4.0 * match.count() / NUM_SAMPLES);
    }

    @Test
    public void countTableInfo() {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Simple Application");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        String url =
                "jdbc:mysql://localhost:3306/office?user=root&useUnicode=true&amp;characterEncoding=utf8";
        DataFrame df = sqlContext
                .read()
                .format("jdbc")
                .option("url", url)
                .option("dbtable", "system_user")
                .load();

        // Looks the schema of this DataFrame.
        df.printSchema();

        // Counts people by age
        DataFrame countsByAge = df.groupBy("age").count();
        countsByAge.show();
    }
}
