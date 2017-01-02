// Exploration of Apache Lucene using tutorialspoint: tutorialspoint.com/lucene/index.htm

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Scanner;

class Indexer {

    private IndexWriter indexWriter;

    Indexer(Directory directory) throws IOException {
        // Initialize index indexWriter
        indexWriter = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
    }

    private void indexFile(File file) throws IOException {
        System.out.println("Indexing " + file.getName());

        // Initialize document
        Document document = new Document();

        // Index file
        Field contentField = new TextField(LuceneConstants.CONTENTS, new Scanner(file).next(), Field.Store.YES);
        Field fileNameField = new TextField(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES);
        Field filePathField = new TextField(LuceneConstants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES);

        // Add fields to document
        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);

        indexWriter.addDocument(document);
    }

    int createIndex(String dataDirPath, FileFilter filter) throws IOException {
        // Get all files in the data directory
        File[] files = new File(dataDirPath).listFiles();

        for (File file : files != null ? files : new File[0])
            if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file))
                indexFile(file);

        return indexWriter.numDocs();
    }

    void close() throws IOException {
        // Close indexWriter
        indexWriter.close();
    }
}