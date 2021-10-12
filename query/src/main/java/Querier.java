import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.similarities.*;
import util.Indexer;
import util.QueryUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Querier {
  static List<Analyzer> analysers = new ArrayList<>();
  static List<Similarity> similarities = new ArrayList<>();

  public static void main(String[] args) throws IOException, InterruptedException, ParseException {
    analysers.add(new SimpleAnalyzer());
    analysers.add(new StandardAnalyzer());
    analysers.add(new ClassicAnalyzer());
    analysers.add(new EnglishAnalyzer(EnglishAnalyzer.getDefaultStopSet()));

    similarities.add(new BM25Similarity());
    similarities.add(new BooleanSimilarity());
    similarities.add(new ClassicSimilarity());
    similarities.add(
        new MultiSimilarity(new Similarity[] {new BM25Similarity(), new ClassicSimilarity()}));

    for (Analyzer analyser : analysers) {
      for (Similarity similarity : similarities) {
        Indexer indexer = new Indexer(analyser, similarity);
        indexer.createIndex();
        System.out.println(
            "Created index for "
                + analyser.getClass().getSimpleName().toLowerCase(Locale.ROOT)
                + "with"
                + similarity.getClass().getSimpleName().toLowerCase(Locale.ROOT)
                + "\n");

        TimeUnit.SECONDS.sleep(3);

        QueryUtil queryUtil = new QueryUtil(analyser, similarity, 20);
        queryUtil.runQuery();
      }
    }
  }
}
