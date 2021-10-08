package parser;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
  private final String file;

  public Parser(String fileToRead) {
    file = fileToRead;
  }

  public static void main(String[] args) throws IOException {
    Parser testParser = new Parser("cran/cran.all.1400");
    testParser.PrintingOutput();
  }

  public void PrintingOutput() throws IOException {
    FileInputStream inputStream = new FileInputStream(file);
    String delimiterRegex = ".[A-Z]";

    Scanner scanner = new Scanner(inputStream).useDelimiter(delimiterRegex);
    List<String> outputList = new ArrayList<>();
    while (scanner.hasNext()) {
      String addToList = scanner.next();
      addToList = addToList.replaceAll("\n+", "").trim();

      outputList.add(addToList);

      if (outputList.size() == 5) {
        if (outputList.get(0).matches("[0-9]+")) {
          // System.out.format("Creating text for %s\n", outputList.get(0));
          WriteToCorpus(outputList);
          outputList.clear();
        } else {
          // check if there is index somewhere
          int index = getIndex(outputList);
          if (index != -1) {
            outputList = outputList.subList(index, outputList.size());
          } else {
            outputList.clear();
          }
        }
      }
    }
  }

  private int getIndex(List<String> outputList) {
    for (int i = 0; i < outputList.size(); i++) {
      if (outputList.get(i).matches("[0-9]+")) {
        return i;
      }
    }
    return -1;
  }

  private void WriteToCorpus(List<String> outputList) throws IOException {

    String filename = outputList.get(0).trim();

    if (filename.matches("[0-9]+")) {
      String file = "corpus/" + filename + ".txt";

      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      String outputString = String.join("\n\n", outputList);

      writer.write(outputString.toString().trim());
      writer.close();

      System.out.format("Writing to corpus %s\n", filename);
    }
  }
}