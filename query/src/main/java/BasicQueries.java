import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import parser.utils.QueryParserUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class BasicQueries {
  private static String INDEX_DIRECTORY = "index";

  private static int MAX_RESULTS = 5;

  public static void main(String[] args) throws IOException, ParseException {

    Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));

    DirectoryReader ireader = DirectoryReader.open(directory);
    IndexSearcher isearcher = new IndexSearcher(ireader);
    isearcher.setSimilarity(new BM25Similarity());

    Analyzer analyzer = new StandardAnalyzer();

    QueryParser queryParser =
        new MultiFieldQueryParser(
            new String[] {"title", "author", "bibliography", "text"}, analyzer);

    QueryParserUtil queryParserUtil = new QueryParserUtil("data/cran/cran.qry");
    LinkedHashMap<Integer, String> queries = queryParserUtil.ParseQuery();

    for (Map.Entry<Integer, String> query : queries.entrySet()) {

      ScoreDoc[] hits =
          isearcher.search(queryParser.parse(query.getValue()), MAX_RESULTS).scoreDocs;

      System.out.println("Documents: " + hits.length);
      for (int i = 0; i < hits.length; i++) {
        Document hitDoc = isearcher.doc(hits[i].doc);
        System.out.println(i + ") " + query.getKey() + hitDoc.get("id") + " " + hits[i].score);
      }
    }

    ireader.close();
    directory.close();
  }
}
