import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.similarities.AxiomaticF1LOG;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.MultiSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import util.Indexer;
import util.QueryUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static util.Utils.getStopWords;

public class Querier {

  public static void main(String[] args)
      throws IOException, InterruptedException, ParseException,
          org.json.simple.parser.ParseException {
    List<Analyzer> analysers = new ArrayList<>();
    //    analysers.add(new SimpleAnalyzer());
    //    analysers.add(new StandardAnalyzer(getStopWords()));
    //    analysers.add(new ClassicAnalyzer(getStopWords()));
    analysers.add(new EnglishAnalyzer(getStopWords()));
    //    analysers.add(new StopAnalyzer(getStopWords()));

    List<Similarity> similarities = new ArrayList<>();

    //    similarities.add(new ClassicSimilarity());
    similarities.add(new BM25Similarity(1.2f, 0.8f));
    similarities.add(
        new MultiSimilarity(new Similarity[] {new BM25Similarity(), new AxiomaticF1LOG()}));
    //    similarities.add(new AxiomaticF1EXP());
    //    similarities.add(new AxiomaticF1LOG());
    //    similarities.add(new AxiomaticF2EXP());

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

  //  public static void main(String[] args) throws IOException, InterruptedException,
  // ParseException {
  //    for (float title = 0.4f; title <= 0.8f; title += 0.05f) {
  //      for (float author = 0.02f; author <= 0.08f; author += 0.01f) {
  //        for (float bibliography = 0.02f; bibliography <= 0.08f; bibliography += 0.01f) {
  //          for (float text = 0.8f; text <= 1f; text += 0.05f) {
  //
  //            Analyzer analyzer = new EnglishAnalyzer(getStopWords());
  //            Similarity similarity = new BM25Similarity();
  //            Indexer indexer = new Indexer(analyzer, similarity);
  //            indexer.createIndex();
  //            System.out.println(
  //                "Created index with "
  //                    + analyzer.getClass().getSimpleName().toLowerCase(Locale.ROOT)
  //                    + " and "
  //                    + similarity.getClass().getSimpleName().toLowerCase(Locale.ROOT));
  //
  //            TimeUnit.SECONDS.sleep(3);
  //
  //            HashMap<String, Float> booster = new HashMap<String, Float>();
  //            booster.put("title", title);
  //            booster.put("author", author);
  //            booster.put("bibliography", bibliography);
  //            booster.put("text", text);
  //
  //            String filenameAddon =
  //                String.valueOf(title)
  //                    + String.valueOf(author)
  //                    + String.valueOf(bibliography)
  //                    + String.valueOf(text);
  //            QueryUtil queryUtil = new QueryUtil(analyzer, similarity, 100, filenameAddon,
  // booster);
  //            queryUtil.runQuery();
  //          }
  //        }
  //      }
  //    }
}

  // CODE TO CHECK THE MOST EFFICIENT PARAMETERS FOR BMK,B

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
