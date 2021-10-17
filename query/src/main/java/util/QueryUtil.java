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

public class QueryUtil {
  private static String INDEX_DIRECTORY = "index";
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

    createDir();

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

    // title
    // 0.50 -> 0.4157
    // 0.55 -> 0.4156
    // 0.6 -> 0.4150
    // 0.65 -> 4131

    // text
    // 0.95 -> 0.4158
    // 0.9 ->  0.4162
    // 0.85 -> 0.4154

    HashMap<String, Float> booster = new HashMap<String, Float>();
    booster.put("title", 0.50f);
    booster.put("author", 0.04f);
    booster.put("bibliography", 0.03f);
    booster.put("text", 0.95f);

    QueryParser queryParser =
        new MultiFieldQueryParser(
            new String[] {"title", "author", "bibliography", "text"}, analyzer, booster);

    QueryParserUtil queryParserUtil = new QueryParserUtil("data/cran/cran.qry");
    LinkedHashMap<Integer, String> queries = queryParserUtil.ParseQuery();

    for (Map.Entry<Integer, String> query : queries.entrySet()) {

      ScoreDoc[] hits =
          isearcher.search(queryParser.parse(replacePunctuation(query.getValue())), max_results)
              .scoreDocs;

      // query-id Q0 document-id rank score STANDARD
      for (ScoreDoc hit : hits) {
        Document hitDoc = isearcher.doc(hit.doc);
        writer.write(
            query.getKey() + " Q0" + hitDoc.get("id") + " 1 " + hit.score + " STANDARD \n");
      }
    }
    System.out.format("Created result file in %s \n", filename);
    writer.close();
    ireader.close();
    directory.close();
  }

  private void createDir() {
    boolean indexDirectory = new File("Results").mkdir();
  }
}
