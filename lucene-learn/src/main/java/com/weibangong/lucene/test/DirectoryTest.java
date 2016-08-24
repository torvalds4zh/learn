package com.weibangong.lucene.test;

import com.google.common.collect.Lists;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by chenbo on 16/8/19.
 */
public class DirectoryTest {
    @Test
    public void testRam() throws Exception{
        /**
         * 内存索引库
         *  速度比较快
         *  数据是暂时的
         */
        Directory directory = new RAMDirectory();
        IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory, iwc);

        Article article = new Article();
        article.setId(2l);
        article.setTitle("lucene内存索引");
        article.setContent("第二个例子啊,Lucene is a Java full-text search engine. Lucene is not a complete application, but rather a code library and API that can easily be used to add search capabilities to applications.");

        indexWriter.addDocument(DocumentUtils.bean2Document(article));
        indexWriter.close();
        this.showData(directory);
    }

    public void showData(Directory directory) throws IOException, ParseException {
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        QueryParser queryParser = new QueryParser("title", new StandardAnalyzer());
        Query query = queryParser.parse("lucene");
        TopDocs topDocs = indexSearcher.search(query, 10);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Article> articleList = Lists.newArrayList();
        for(ScoreDoc scoreDoc : scoreDocs){
            Document document = indexSearcher.doc(scoreDoc.doc);
            articleList.add((Article) DocumentUtils.document2Bean(document, Article.class));
        }

        for(Article article : articleList){
            System.out.println(article.getId());
            System.out.println(article.getTitle());
            System.out.println(article.getContent());
        }
    }

    /**
     * 文件索引库和内存索引库相结合
     */
    @Test
    public void testRamAndFile() throws Exception{
        /**
         * 当应用程序启动的时候, 把文件索引库的内容复制到内存索引库中
         * 让内存索引库和应用程序交互
         * 把内存索引库的内容同步到文件索引库
         */
        Directory fileDirectory = FSDirectory.open(getTargetPath());
        Directory ramDirectory = new RAMDirectory((FSDirectory) fileDirectory, IOContext.READONCE);
//        Directory ramDirectory = new RAMDirectory((FSDirectory) fileDirectory, IOContext.DEFAULT);

        IndexWriterConfig fileIwc = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter fileIndexWriter = new IndexWriter(fileDirectory, fileIwc);
        /**
         * 在索引库没有建立并且没有索引文件的时候首先要commit一下让他建立一个
         * 如果第一次没有commit就打开一个索引读取器的话就会报异常
         *  IndexNotFoundException: no segments* file found in RAMDirectory
         */
        fileIndexWriter.commit();

        IndexWriterConfig ramIwc = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter ramIndexWriter = new IndexWriter(ramDirectory, ramIwc);

        /**
         * 在内存索引库中根据关键词查询
         */
        this.showData(ramDirectory);
        System.out.println("上面是从内存索引库中查询出来的");
        System.out.println();

        /**
         * 把一条新信息插入到内存索引库
         */
        Article article2 = new Article();
        article2.setId(3l);
        article2.setTitle("lucene内存索引第二条");
        article2.setContent("第二个例子啊,Lucene is a Java full-text search engine. Lucene is not a complete application, but rather a code library and API that can easily be used to add search capabilities to applications.");

        ramIndexWriter.addDocument(DocumentUtils.bean2Document(article2));
        ramIndexWriter.close();

        /**
         * 同步文件索引库到内存索引
         */
        fileIndexWriter.addIndexes(ramDirectory);
        fileIndexWriter.close();
        this.showData(fileDirectory);
        System.out.println("上面的是从文件索引库中查询出来的");
    }

    public Path getTargetPath(){
        return Paths.get(new File(DirectoryTest.class.getClassLoader().getResource("").getPath() + "/index").getPath());
    }
}
