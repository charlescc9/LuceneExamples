// Exploration of Apache Lucene using tutorialspoint: tutorialspoint.com/lucene/index.htm

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

class LuceneConstants {
    static final String CONTENTS = "contents";
    static final String FILE_NAME = "filename";
    static final String FILE_PATH = "filepath";
    static final int MAX_SEARCH = 10;
}

class TextFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(".txt");
    }
}

public class LuceneTester {

    private Directory directory = new RAMDirectory();

    public static void main(String[] args) {
        try {
            LuceneTester tester = new LuceneTester();
            tester.createIndex();
            tester.search();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void createIndex() throws IOException {
        // Initialize indexer
        Indexer indexer = new Indexer(directory);
        long startTime = System.currentTimeMillis();

        // Index all documents
        int numIndexed = indexer.createIndex(new TextFileFilter());
        indexer.close();

        System.out.println(numIndexed + " File indexed in " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private void search() throws IOException, ParseException {
        // Initialize searcher
        Searcher searcher = new Searcher(directory);
        long startTime = System.currentTimeMillis();

        // Perform search
        TopDocs hits = searcher.search();

        System.out.println("\n" + hits.totalHits + " documents found in " + (System.currentTimeMillis() - startTime) + "ms");

        // Print out documents with hits
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: " + doc.get(LuceneConstants.FILE_NAME));
        }

        // Close searcher
        searcher.close();
    }
}