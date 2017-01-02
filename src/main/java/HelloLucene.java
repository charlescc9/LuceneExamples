// Exploration of Apache Lucene using LuceneTutorial.com: lucenetutorial.com/basic-concepts.html

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
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
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;

public class HelloLucene {
    public static void main(String[] args) throws IOException, ParseException {
        // Initialize analyzer
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // Initialize index
        Directory index = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        // Add documents
        IndexWriter writer = new IndexWriter(index, config);
        addDoc(writer, "Lucene in Action", "193398817");
        addDoc(writer, "Lucene for Dummies", "55320055Z");
        addDoc(writer, "Managing Gigabytes", "55063554A");
        addDoc(writer, "The Art of Computer Science", "9900333X");
        writer.close();

        // Initialize query
        Query q = new QueryParser("isbn", analyzer).parse("9900333X");

        // Perform search
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        // Print results
        System.out.println("Found " + hits.length + " hits.");
        for(int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }

        // Close reader
        reader.close();
    }

    private static void addDoc(IndexWriter writer, String title, String isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new TextField("isbn", isbn, Field.Store.YES));
        writer.addDocument(doc);
    }
}