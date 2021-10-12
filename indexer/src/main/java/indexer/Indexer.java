package indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Indexer {
  public static final String INDEX_DIR = "index";
  private final Analyzer analyzer;
  private final Similarity similarity;

  public Indexer(Analyzer analyzer, Similarity similarity) {
    this.analyzer = analyzer;
    this.similarity = similarity;
  }

  private List<String> modifyList(List<String> listToModify) {
    int i;
    for (i = 0; i < listToModify.size(); i++) {
      listToModify.set(i, listToModify.get(i).replaceAll("\\w+:", ""));
    }
    return listToModify;
  }

  private void createDir(){
    boolean indexDirectory = new File(INDEX_DIR).mkdir();
  }

  public void createIndex() throws IOException {
    createDir();
    // Analyzer analyzer = new EnglishAnalyzer(EnglishAnalyzer.getDefaultStopSet());

    // Analyzer analyzer = new SimpleAnalyzer();

    ArrayList<Document> documents = new ArrayList<Document>();

    Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));

    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
    config.setSimilarity(similarity);

    IndexWriter iwriter = new IndexWriter(directory, config);

    List<Path> pathToFilesInFolder =
        Files.walk(Paths.get("data/corpus"))
            .filter(Files::isRegularFile)
            .collect(Collectors.toList());

    for (Path path : pathToFilesInFolder) {
      Scanner scanner = new Scanner(path).useDelimiter("\n+");
      List<String> originalList = new ArrayList<>();
      while (scanner.hasNext()) {
        originalList.add(scanner.next());
      }

      List<String> modifiedList = modifyList(originalList);

      Document doc = new Document();
      doc.add(new StringField("id", modifiedList.get(0), Field.Store.YES));
      doc.add(new TextField("title", modifiedList.get(1), Field.Store.YES));
      doc.add(new TextField("author", modifiedList.get(2), Field.Store.YES));
      doc.add(new TextField("bibliography", modifiedList.get(3), Field.Store.YES));
      doc.add(new TextField("text", modifiedList.get(4), Field.Store.YES));

      documents.add(doc);
    }

    iwriter.addDocuments(documents);
    System.out.format("Indexed %s documents", documents.size());

    iwriter.close();
    directory.close();
  }
}
