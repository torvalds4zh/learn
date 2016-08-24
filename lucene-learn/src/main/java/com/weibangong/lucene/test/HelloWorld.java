package com.weibangong.lucene.test;

import com.google.common.collect.Lists;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Created by chenbo on 16/8/17.
 */
public class HelloWorld {

    @Test
    public void testCreateIndex() throws IOException {
        /**
         * 创建一个Article对象,并且把信息存放进去
         * 调用IndexWriter把Article存放在索引库中
         * 关闭IndexWriter
         */

        Article article = new Article();
        article.setId(1l);
        article.setTitle("this is my first lucene test");
        article.setContent("baidu, Google都是很好的搜索引擎");

        //创建索引库
//        Directory directory = FSDirectory.open(Paths.get("./index"));
        //在target目录下创建
        Directory directory = FSDirectory.open(Paths.get(new File(HelloWorld.class.getClassLoader().getResource("").getPath() + "/index").getPath()));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(directory, conf);

        //创建Document
        Document doc = new Document();
        //TODO 这里用StringField不行,查不出来
        Field idField = new TextField("id", article.getId().toString(), Field.Store.YES);
        Field titleField = new TextField("title", article.getTitle(), Field.Store.YES);
        Field contentField = new TextField("content", article.getContent(), Field.Store.YES);
        doc.add(idField);
        doc.add(titleField);
        doc.add(contentField);

        indexWriter.addDocument(doc);
        indexWriter.close();
        directory.close();
    }

    @Test
    public void testSearchIndex() throws IOException, ParseException {
        Directory directory = FSDirectory.open(Paths.get(new File(HelloWorld.class.getClassLoader().getResource("").getPath() + "/index").getPath()));
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建IndexSearcher对象
        IndexSearcher searcher = new IndexSearcher(indexReader);

        Analyzer analyzer = new StandardAnalyzer();
        QueryParser queryParser = new QueryParser("title", analyzer);
        Query query = queryParser.parse("lucene");    //关键词

        //调用search进行检索
        TopDocs topDocs = searcher.search(query, 2);
        int count = topDocs.totalHits;  //根据关键词查询出来的总的记录数
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;   //查询出来的记录
        List<Article> articleList = Lists.newArrayList();

        System.out.println("查询出来的总记录数: " + count);

        for (ScoreDoc scoreDoc : scoreDocs) {
            float score = scoreDoc.score; //关键词得分
            int index = scoreDoc.doc;   //索引的下标

            Document document = searcher.doc(index);
            Article article = new Article();
            article.setId(Long.parseLong(document.get("id")));
            article.setTitle(document.get("title"));
            article.setContent(document.get("content"));
            articleList.add(article);

            System.out.println("关键词得分: " + score);
            System.out.println(article.getId());
            System.out.println(article.getTitle());
            System.out.println(article.getContent());
        }
        directory.close();
    }

    @Test
    public void testDeleteIndex() throws IOException {
        Directory directory = FSDirectory.open(Paths.get(new File(HelloWorld.class.getClassLoader().getResource("").getPath() + "/index").getPath()));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(directory, iwc);

        //indexWriter.deleteAll(); //删除所有的索引值

        Term term = new Term("title", "lucene");
        indexWriter.deleteDocuments(term);
        indexWriter.close();
    }

    @Test
    public void testUpdateIndex() throws Exception {
        Directory directory = FSDirectory.open(Paths.get(new File(HelloWorld.class.getClassLoader().getResource("").getPath() + "/index").getPath()));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(directory, iwc);

        Term term = new Term("title", "lucene");
        Article article = new Article();
        article.setId(1l);
        article.setTitle("lucene可以做搜索引擎");
        article.setContent("修改后的内容");
        //创建Document
        Document doc = new Document();
        Field idField = new TextField("id", article.getId().toString(), Field.Store.YES);
        Field titleField = new TextField("title", article.getTitle(), Field.Store.YES);
        Field contentField = new TextField("content", article.getContent(), Field.Store.YES);
        doc.add(idField);
        doc.add(titleField);
        doc.add(contentField);
        List<Document> documents = Collections.singletonList(doc);
        indexWriter.updateDocuments(term, documents);
        indexWriter.close();
    }
}
