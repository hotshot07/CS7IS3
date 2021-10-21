package parser.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class to parse the cran.all.1400 file
public class DocumentParser {
  private final String file;

  public DocumentParser(String fileToRead) {
    this.file = fileToRead;
  }

  public void parseDocument() throws IOException {
    FileInputStream inputStream = new FileInputStream(file);
    String delimiterRegex = ".[A-Z]";

    Scanner scanner = new Scanner(inputStream).useDelimiter(delimiterRegex);
    List<String> outputList = new ArrayList<>();

    // while there is something in scanner
    while (scanner.hasNext()) {
      String addToList = scanner.next();
      addToList = addToList.replaceAll("\n+", "").trim();

      outputList.add(addToList);

      // if the output list is equal to 5 AND if the first thing is ID of form [0-9]+
      // then we can write to corpus in data/corpus
      // else, we get the index of the id, and remove everything else present before the index
      // as thats useless info
      if (outputList.size() == 5) {
        if (outputList.get(0).matches("[0-9]+")) {
          writeToCorpusDir(outputList);
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

  private void writeToCorpusDir(List<String> outputList) throws IOException {

    boolean directory = new File("data/corpus").mkdir();

    //filename is the id
    String filename = outputList.get(0).trim();

    if (filename.matches("[0-9]+")) {
      String file = "data/corpus/" + filename + ".txt";

      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      // modifying the list, so it's easier to read while indexing
      List<String> modifiedOutputlist = modifyOutputlist(outputList);
      String modifiedOutputString = String.join("\n\n", modifiedOutputlist);

      writer.write(modifiedOutputString.trim());
      writer.close();

      System.out.format("Writing to corpus %s.txt\n", filename);
    }
  }

  private List<String> modifyOutputlist(List<String> outputList) {
    outputList.set(0, "Id: " + outputList.get(0));
    outputList.set(1, "Title: " + outputList.get(1));
    outputList.set(2, "Author: " + outputList.get(2));
    outputList.set(3, "Bibliography: " + outputList.get(3));
    outputList.set(4, "Text: " + outputList.get(4));

    return outputList;
  }
}
