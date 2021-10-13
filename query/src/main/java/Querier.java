import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Querier {

  public static void main(String[] args) throws IOException, InterruptedException, ParseException {
    List<Analyzer> analysers = new ArrayList<>();
    analysers.add(new SimpleAnalyzer());
    analysers.add(new StandardAnalyzer());
    analysers.add(new ClassicAnalyzer());
    analysers.add(new EnglishAnalyzer(getStopWords()));

    List<Similarity> similarities = new ArrayList<>();
    similarities.add(new BM25Similarity());
    similarities.add(new BooleanSimilarity());
    similarities.add(new ClassicSimilarity());
    similarities.add();
    similarities.add(
        new MultiSimilarity(new Similarity[] {new BM25Similarity(), new ClassicSimilarity()}));

    for (Analyzer analyser : analysers) {
      for (Similarity similarity : similarities) {
        Indexer indexer = new Indexer(analyser, similarity);
        indexer.createIndex();
        System.out.println(
            "Created index with "
                + analyser.getClass().getSimpleName().toLowerCase(Locale.ROOT)
                + " and "
                + similarity.getClass().getSimpleName().toLowerCase(Locale.ROOT));

        TimeUnit.SECONDS.sleep(3);

        QueryUtil queryUtil = new QueryUtil(analyser, similarity, 20);
        queryUtil.runQuery();
      }
    }
  }

  private static CharArraySet getStopWords() {
    List<String> stopWords =
        Arrays.asList(
            "i",
            "me",
            "my",
            "myself",
            "we",
            "our",
            "ours",
            "ourselves",
            "you",
            "your",
            "yours",
            "yourself",
            "yourselves",
            "he",
            "him",
            "his",
            "himself",
            "she",
            "her",
            "hers",
            "herself",
            "it",
            "its",
            "itself",
            "they",
            "them",
            "their",
            "theirs",
            "themselves",
            "what",
            "which",
            "who",
            "whom",
            "this",
            "that",
            "these",
            "those",
            "am",
            "is",
            "are",
            "was",
            "were",
            "be",
            "been",
            "being",
            "have",
            "has",
            "had",
            "having",
            "do",
            "does",
            "did",
            "doing",
            "a",
            "an",
            "the",
            "and",
            "but",
            "if",
            "or",
            "because",
            "as",
            "until",
            "while",
            "of",
            "at",
            "by",
            "for",
            "with",
            "about",
            "against",
            "between",
            "into",
            "through",
            "during",
            "before",
            "after",
            "above",
            "below",
            "to",
            "from",
            "up",
            "down",
            "in",
            "out",
            "on",
            "off",
            "over",
            "under",
            "again",
            "further",
            "then",
            "once",
            "here",
            "there",
            "when",
            "where",
            "why",
            "how",
            "all",
            "any",
            "both",
            "each",
            "few",
            "more",
            "most",
            "other",
            "some",
            "such",
            "no",
            "nor",
            "not",
            "only",
            "own",
            "same",
            "so",
            "than",
            "too",
            "very",
            "s",
            "t",
            "can",
            "will",
            "just",
            "don",
            "should",
            "now");
    return new CharArraySet(stopWords, true);
  }
}
