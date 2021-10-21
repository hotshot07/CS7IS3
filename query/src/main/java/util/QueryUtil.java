package util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import parser.utils.QueryParserUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static util.Utils.replacePunctuation;

// This class queries the index and creates the score file in
// Results/results_{analyser}_{similarity}

public class QueryUtil {
  private final int max_results;
  private final Analyzer analyzer;
  private final Similarity similarity;
  private String filenameAddon = "";
  private HashMap<String, Float> booster;

  public QueryUtil(Analyzer analyzer, Similarity similarity, int max_results) {
    this.analyzer = analyzer;
    this.similarity = similarity;
    this.max_results = max_results;
  }

  // the constructor is overloaded, as I had to check the optimal values for boosters for the same
  // analyser and similarity, hence the filenameAddon as well to distinguish it
  public QueryUtil(
      Analyzer analyzer,
      Similarity similarity,
      int max_results,
      String filenameAddon,
      HashMap<String, Float> booster) {
    this.analyzer = analyzer;
    this.similarity = similarity;
    this.max_results = max_results;
    this.filenameAddon = filenameAddon;
  }

  public void runQuery() throws IOException, ParseException {

    // creates the result dir
    createDir();

    final String INDEX_DIRECTORY = "index";
    Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));

    DirectoryReader ireader = DirectoryReader.open(directory);
    IndexSearcher isearcher = new IndexSearcher(ireader);
    isearcher.setSimilarity(similarity);

    String filename =
        "Results/results_"
            + analyzer.getClass().getSimpleName().toLowerCase(Locale.ROOT)
            + "_"
            + similarity.getClass().getSimpleName().toLowerCase(Locale.ROOT)
            + filenameAddon;

    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

    // using boosting in MultiFieldQueryParser to boost scores for certain fields
    HashMap<String, Float> booster = new HashMap<String, Float>();
    booster.put("title", 0.50f);
    booster.put("author", 0.04f);
    booster.put("bibliography", 0.03f);
    booster.put("text", 1.1f);

    QueryParser queryParser =
        new MultiFieldQueryParser(
            new String[] {"title", "author", "bibliography", "text"}, analyzer, booster);

    // getting a LinkedHashMap<Integer, String> using QueryParserUtil in parser module
    QueryParserUtil queryParserUtil = new QueryParserUtil("data/cran/cran.qry");
    LinkedHashMap<Integer, String> queries = queryParserUtil.parseQuery();

    for (Map.Entry<Integer, String> query : queries.entrySet()) {

      ScoreDoc[] hits =
          isearcher.search(queryParser.parse(replacePunctuation(query.getValue())), max_results)
              .scoreDocs;

      // writing query-id Q0 document-id rank score STANDARD in results file
      for (ScoreDoc hit : hits) {
        Document hitDoc = isearcher.doc(hit.doc);
        writer.write(
            query.getKey() + " Q0 " + hitDoc.get("id") + " 1 " + hit.score + " STANDARD \n");
      }
    }
    System.out.format("\nCreated result file: %s\n", filename);
    writer.close();
    ireader.close();
    directory.close();
  }

  private void createDir() {
    boolean indexDirectory = new File("Results").mkdir();
  }
}
