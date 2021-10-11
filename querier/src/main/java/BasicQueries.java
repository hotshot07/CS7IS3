import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class BasicQueries {
  private static String INDEX_DIRECTORY = "index";

  // Limit the number of search results we get
  private static int MAX_RESULTS = 20;

  public static void main(String[] args) throws IOException, ParseException {

    Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));

    DirectoryReader ireader = DirectoryReader.open(directory);
    IndexSearcher isearcher = new IndexSearcher(ireader);

    Analyzer analyzer = new StandardAnalyzer();

    QueryParser queryParser = new QueryParser("text", analyzer);
    Query q =
        queryParser.parse(
            "what similarity laws must be obeyed when constructing aeroelastic models of heated high speed aircraft .");

    ScoreDoc[] hits = isearcher.search(q, MAX_RESULTS).scoreDocs;

    // Print the results
    System.out.println("Documents: " + hits.length);
    for (int i = 0; i < hits.length; i++) {
      Document hitDoc = isearcher.doc(hits[i].doc);
      System.out.println(i + ") " + hitDoc.get("id") + hitDoc.get("author") + " " + hits[i].score);
    }

    // close everything we used
    ireader.close();
    directory.close();
  }
}
