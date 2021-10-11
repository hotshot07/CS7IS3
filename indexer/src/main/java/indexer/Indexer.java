package indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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
  public static String INDEX_DIR = "index";

  public static void main(String[] args) throws IOException {
    boolean indexDirectory = new File(INDEX_DIR).mkdir();
    writeToIndex();
  }

  private static void writeToIndex() throws IOException {
    Analyzer analyzer = new StandardAnalyzer();

    ArrayList<Document> documents = new ArrayList<Document>();

    Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));

    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
    IndexWriter iwriter = new IndexWriter(directory, config);

    List<Path> pathToFilesInFolder =
        Files.walk(Paths.get("corpus")).filter(Files::isRegularFile).collect(Collectors.toList());

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

    iwriter.close();
    directory.close();
  }

  private static List<String> modifyList(List<String> listToModify) {
    int i;
    for (i = 0; i < listToModify.size(); i++) {
      listToModify.set(i, listToModify.get(i).replaceAll("\\w+:", ""));
    }
    return listToModify;
  }
}
