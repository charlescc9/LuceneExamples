// Exploration of Apache Lucene using tutorialspoint: tutorialspoint.com/lucene/index.htm

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import java.io.IOException;

class Searcher {

    private QueryParser queryParser;
    private IndexReader indexReader;
    private IndexSearcher indexSearcher;

    Searcher(Directory directory) throws IOException {
        // Initialize variables
        queryParser = new QueryParser(LuceneConstants.CONTENTS, new StandardAnalyzer());
        indexReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);
    }

    TopDocs search() throws IOException, ParseException {
        // Search query
        Query query = queryParser.parse("Reena");
        return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
    }

    Document getDocument(ScoreDoc scoreDoc) throws IOException {
        // Get document
        return indexSearcher.doc(scoreDoc.doc);
    }

    void close() throws IOException {
        // Close index reader
        indexReader.close();
    }
}