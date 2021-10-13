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

public class QueryUtil {
  private static String INDEX_DIRECTORY = "index";
  private final int max_results;
  private final Analyzer analyzer;
  private final Similarity similarity;

  public QueryUtil(Analyzer analyzer, Similarity similarity, int max_results) {
    this.analyzer = analyzer;
    this.similarity = similarity;
    this.max_results = max_results;
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
            + similarity.getClass().getSimpleName().toLowerCase(Locale.ROOT);

    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

    HashMap<String, Float> score_booster = new HashMap<String, Float>();
    score_booster.put("title", 0.65f);
    score_booster.put("author", 0.06f);
    score_booster.put("bibliography", 0.03f);
    score_booster.put("text", 0.95f);

    QueryParser queryParser =
        new MultiFieldQueryParser(
            new String[] {"title", "author", "bibliography", "text"}, analyzer, score_booster);

    QueryParserUtil queryParserUtil = new QueryParserUtil("data/cran/cran.qry");
    LinkedHashMap<Integer, String> queries = queryParserUtil.ParseQuery();

    for (Map.Entry<Integer, String> query : queries.entrySet()) {

      ScoreDoc[] hits =
          isearcher.search(queryParser.parse(query.getValue()), max_results).scoreDocs;

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
