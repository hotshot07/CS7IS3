package parser.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class QueryParser {
  private final String file;

  public QueryParser(String fileToRead) {
    file = fileToRead;
  }

  public LinkedHashMap<Integer, String> ParseQuery() throws IOException {
    LinkedHashMap<Integer, String> queries = new LinkedHashMap<>();
    FileInputStream inputStream = new FileInputStream(file);
    String delimiterRegex = ".[A-Z]";

    Scanner scanner = new Scanner(inputStream).useDelimiter(delimiterRegex);
    List<String> outputList = new ArrayList<>();

    int queryFilename = 0;

    while (scanner.hasNext()) {
      String addToList = scanner.next();
      addToList = addToList.replaceAll("\n+", "").trim();

      outputList.add(addToList);

      if (outputList.size() == 2 && outputList.get(0).matches("[0-9]+")) {
        queryFilename += 1;
        queries.put(queryFilename, outputList.get(1));
        outputList.clear();
      }
    }

    System.out.println(queries);
    return queries;
  }

  //  private void WriteToQueryDir(List<String> outputList) throws IOException {
  //
  //    boolean directory = new File("queries").mkdir();
  //
  //    String filename = outputList.get(0).trim();
  //
  //    if (filename.matches("[0-9]+")) {
  //      String file = "queries/" + filename + ".txt";
  //
  //      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
  //      List<String> modifiedOutputlist = modifyOutputlist(outputList);
  //      String modifiedOutputString = String.join("\n\n", modifiedOutputlist);
  //
  //      writer.write(modifiedOutputString.trim());
  //      writer.close();
  //
  //      System.out.format("Writing to queries %s.txt\n", filename);
  //    }
  //  }

  //  private List<String> modifyOutputlist(List<String> outputList) {
  //    outputList.set(0, "Id: " + outputList.get(0));
  //    outputList.set(1, "Query: " + outputList.get(1));
  //
  //    return outputList;
  //  }
  //
  //  public LinkedHashMap<Integer, String> getQueries() throws FileNotFoundException {
  //    LinkedHashMap<Integer, String> queries = new LinkedHashMap<>();
  //    queries.put(1, "ji");
  //
  //    File f = new File("queries");
  //    String[] pathnames = f.list();
  //    Long numberOfFiles = Arrays.stream(pathnames).count();
  //
  //    for (int i = 1; i <= numberOfFiles; i++) {
  //      String filename = "queries/" + i + ".txt";
  //      FileInputStream inputStream = new FileInputStream(filename);
  //
  //      final String regex = "\\w+:";
  //      final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
  //      Scanner scanner = new Scanner(inputStream).useDelimiter(pattern);
  //      while (scanner.hasNext()) {
  //        System.out.println(scanner.next());
  //      }
  //    }
  //
  //    return queries;
  //  }
}
