package com.weibangong.lucene.test;

import com.google.common.collect.Sets;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

/**
 * Created by chenbo on 16/8/19.
 */
public class AnalyzerTest {

    @Test
    public void testEn() {
        String text = "Creates a searcher searching the index in the named directory";
        Analyzer analyzer = new StandardAnalyzer();
        this.testAnalyzer(analyzer, text);
    }

    @Test
    public void testZH() {
        /**
         * 中文的标准分词是单字分词
         */
        Analyzer analyzer = new StandardAnalyzer();
        String text = "百度的CEO是李彦宏";
        testAnalyzer(analyzer, text);
    }

    @Test
    public void testZH2() {
        /**
         * 双字分词
         */
        Analyzer analyzer = new CJKAnalyzer();
        String text = "百度的CEO是李彦宏";
        testAnalyzer(analyzer, text);
    }

    @Test
    public void testZH3(){
        String[] str = {"的", "在","了", "呢", "，", "0", "：", ",", "是", "流"};
        Set<String> words = Sets.newHashSet();
        for(String s : str){
            words.add(s);
        }
        CharArraySet stopWords = new CharArraySet(words, true);
        Analyzer analyzer = new SmartChineseAnalyzer(stopWords);
        String text = "百度的CEO是李彦宏,lucene分析器使用分词器和过滤器构成一个“管道”，" +
                "文本在流经这个管道后成为可以进入索引的最小单位，因此，一个标准的分析器有两个部分组成，" +
                "一个是分词器tokenizer,它用于将文本按照规则切分为一个个可以进入索引的最小单位。" +
                "另外一个是TokenFilter，它主要作用是对切出来的词进行进一步的处理（如去掉敏感词、英文大小写转换、单复数处理）等。" +
                "lucene中的Tokenstram方法首先创建一个tokenizer对象处理Reader对象中的流式文本，然后利用TokenFilter对输出流进行过滤处理";
        testAnalyzer(analyzer, text);
    }

    /**
     * 获取分词结果
     *
     * @param analyzer
     * @param text
     * @throws Exception
     */
    private void testAnalyzer(Analyzer analyzer, String text) {
        TokenStream tokenStream = null;

        try {
            tokenStream = analyzer.tokenStream("content", new StringReader(text));
            tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
                System.out.println(attribute.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                tokenStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
