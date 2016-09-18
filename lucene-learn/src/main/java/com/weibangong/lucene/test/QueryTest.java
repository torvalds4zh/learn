package com.weibangong.lucene.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by chenbo on 16/8/22.
 */
public class QueryTest {
    /**
     * 关键词查询
     * 因为没有分词器,所以区分大小写
     */
    @Test
    public void testTermQuery() throws Exception {
        Term term = new Term("title", "lucene");
        Query query = new TermQuery(term);
        showData(query);
    }

    /**
     * 查询所有文档
     *
     * @throws IOException
     */
    @Test
    public void testAllDocQuery() throws IOException {
        Query query = new MatchAllDocsQuery();
        showData(query);
    }

    /**
     * 通配符查询
     *
     * @throws IOException
     */
    @Test
    public void testWildCartQuery() throws IOException {
        Term term = new Term("title", "*.java");
        Query query = new WildcardQuery(term);
        showData(query);
    }

    /**
     * 短语查询, 所有的关键词对象必须针对同一个属性
     *
     * @throws IOException
     */
    @Test
    public void testPhraseQuery() throws IOException {
        PhraseQuery query = new PhraseQuery("title", "lucene", "query");
        showData(query);
    }

    /**
     * boolean 查询
     * 各种关键词的组合
     *
     * @throws IOException
     */
    @Test
    public void testBooleanQuery() throws IOException {
        Term term = new Term("title", "query");
        Term term2 = new Term("title", "lucene");
        TermQuery termQuery = new TermQuery(term);
        TermQuery termQuery2 = new TermQuery(term2);
        BooleanQuery query = new BooleanQuery();
        query.add(termQuery, BooleanClause.Occur.SHOULD);
        query.add(termQuery2, BooleanClause.Occur.SHOULD);
        showData(query);
    }


    /**
     * 范围查询
     * @throws IOException
     */
    @Test
    public void testRangeQuery() throws IOException {
        Query query = NumericRangeQuery.newLongRange("id", 1L, 5L, true, true);
        showData(query);
    }

    @Test
    public void testCreateIndex() throws IOException {
        /**
         * 创建一个Article对象,并且把信息存放进去
         * 调用IndexWriter把Article存放在索引库中
         * 关闭IndexWriter
         */

        Article article = new Article();
        article.setId(4l);
        article.setTitle("lucene can query by long");
        article.setContent("搜索搜索");

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
        Field idField = new LongField("id", article.getId(), Field.Store.YES);
        Field titleField = new TextField("title", article.getTitle(), Field.Store.YES);
        Field contentField = new TextField("content", article.getContent(), Field.Store.YES);
        doc.add(idField);
        doc.add(titleField);
        doc.add(contentField);

        indexWriter.addDocument(doc);
        indexWriter.close();
        directory.close();
    }

    private void showData(Query query) throws IOException {
        Directory directory = FSDirectory.open(getTargetPath());
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        TopDocs topDocs = indexSearcher.search(query, 25);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc doc : scoreDocs) {
            Article article = (Article) DocumentUtils.document2Bean(indexSearcher.doc(doc.doc), Article.class);
            System.out.println(article.getId());
            System.out.println(article.getTitle());
            System.out.println(article.getContent());
        }
    }

    public Path getTargetPath() {
        return Paths.get(new File(DirectoryTest.class.getClassLoader().getResource("").getPath() + "/index").getPath());
    }
}
