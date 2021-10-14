import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
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

import static util.StopWords.getStopWords;

public class Querier {

  public static void main(String[] args) throws IOException, InterruptedException, ParseException {
    List<Analyzer> analysers = new ArrayList<>();
    analysers.add(new SimpleAnalyzer());
    analysers.add(new StandardAnalyzer(getStopWords()));
    analysers.add(new ClassicAnalyzer(getStopWords()));
    analysers.add(new EnglishAnalyzer(getStopWords()));
    analysers.add(new StopAnalyzer(getStopWords()));

    List<Similarity> similarities = new ArrayList<>();

    //    similarities.add(new ClassicSimilarity());
    similarities.add(new BM25Similarity());
    similarities.add(
        new MultiSimilarity(new Similarity[] {new BM25Similarity(), new AxiomaticF1LOG()}));
    similarities.add(new AxiomaticF1EXP());
    similarities.add(new AxiomaticF1LOG());
    similarities.add(new AxiomaticF2EXP());

    for (Analyzer analyser : analysers) {
      for (Similarity similarity : similarities) {
        Indexer indexer = new Indexer(analyser, similarity);
        indexer.createIndex();
        System.out.println(
            "Created index with "
                + analyser.getClass().getSimpleName().toLowerCase(Locale.ROOT)
                + " and "
                + similarity.getClass().getSimpleName().toLowerCase(Locale.ROOT));

        TimeUnit.SECONDS.sleep(2);

        QueryUtil queryUtil = new QueryUtil(analyser, similarity, 1400);
        queryUtil.runQuery();
      }
    }
  }

  // CODE TO CHECK THE MOST EFFICIENT PARAMETERS FOR BM25 SIMILARITY K,B

  //    float k;
  //    float b;
  //    for (k = 0.2f; k <= 5f; k += 0.2f) {
  //      for (b = 0.1f; b <= 1.0f; b += 0.1f) {
  //        Analyzer analyzer = new EnglishAnalyzer(getStopWords());
  //        Similarity similarity = new BM25Similarity(k, b);
  //        Indexer indexer = new Indexer(analyzer, similarity);
  //        indexer.createIndex();
  //        System.out.println(
  //            "Created index with "
  //                + analyzer.getClass().getSimpleName().toLowerCase(Locale.ROOT)
  //                + " and "
  //                + similarity.getClass().getSimpleName().toLowerCase(Locale.ROOT));
  //
  //        TimeUnit.SECONDS.sleep(3);
  //
  //        QueryUtil queryUtil = new QueryUtil(analyzer, similarity, 100, "k_" + k + "b_" + b);
  //        queryUtil.runQuery();
  //      }
  //    }
}
