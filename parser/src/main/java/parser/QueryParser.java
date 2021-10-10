package parser;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QueryParser {
  private final String file;

  public QueryParser(String fileToRead) {
    file = fileToRead;
  }

  void ParseQuery() throws IOException {
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
        outputList.set(0, String.valueOf(queryFilename));
        WriteToQueryDir(outputList);
        outputList.clear();
      }
    }
  }

  private void WriteToQueryDir(List<String> outputList) throws IOException {

    String filename = outputList.get(0).trim();

    if (filename.matches("[0-9]+")) {
      String file = "query/" + filename + ".txt";

      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      String outputString = String.join("\n\n", outputList);

      writer.write(outputString.trim());
      writer.close();

      System.out.format("Writing to query %s\n", filename);
    }
  }
}
