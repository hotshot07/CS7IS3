import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
  private String file;

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
    List<String> cars = new ArrayList<>();
    while (scanner.hasNext()) {
      String addToList = scanner.next();
      addToList = addToList.replaceAll("\n+", "").trim();

      cars.add(addToList);

      if (cars.size() == 5) {
        if (cars.get(0).matches("[0-9]+")) {
          System.out.println(cars.get(0));
          cars.clear();
        } else {
          // check if there is index somewhere
          int index = getIndex(cars);
          if (index != -1) {
            cars = cars.subList(index, cars.size());
          } else {
            cars.clear();
          }
        }
      }
    }
  }

  private int getIndex(List<String> cars) {
    for (int i = 0; i < cars.size(); i++) {
      if (cars.get(i).matches("[0-9]+")) {
        return i;
      }
    }
    return -1;
  }
}

//  public void NewThing() throws FileNotFoundException {
//    FileInputStream inputStream = new FileInputStream(file);
//    String delimiterRegex = ".[A-Z]";
//
//    Scanner scanner = new Scanner(inputStream).useDelimiter(delimiterRegex);
//
//    while (scanner.hasNext()) {
//      String maybeIndex = scanner.next();
//      if (maybeIndex.matches("[0-9]+")) {
//        maybeIndex.replaceAll("\n", "");
//
//        int Index = Integer.parseInt(maybeIndex);
//        String Title = scanner.next();
//        String Author = scanner.next();
//        String Bibliography = scanner.next();
//        String Words = scanner.next();
//
//        FileFormat fileFormat = new FileFormat(Index, Title, Author, Bibliography, Words);
//
//        System.out.println(fileFormat.getIndex());
//      }
//    }
//  }

// final Pattern pattern = Pattern.compile(delimiterRegex, Pattern.MULTILINE);

//        try {
//            String everything = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
//            final Matcher matcher = pattern.matcher(everything);
//            while (matcher.find()) {
//                System.out.println("Full match: " + matcher.group(0));
//                for (int i = 1; i <= matcher.groupCount(); i++) {
//                    System.out.println("Group " + i + ": " + matcher.group(i));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        scanner.findInLine(delimiterRegex);
//        MatchResult matchResult = scanner.match();
//
//        for (int i = 0; i < matchResult.groupCount(); i++) {
//            System.out.println(matchResult.group(i));
//        }
//        scanner.close();

//  private boolean WriteToCorpus(StringBuilder outputString) throws IOException {
//    String outputS = outputString.toString().trim();
//    String filename = outputS.substring(0, outputS.indexOf("\n"));
//
//    if (filename.matches("[0-9]")) {
//      String file = "Corpus/" + filename + ".txt";
//      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//
//      writer.write(outputString.toString().trim());
//      writer.close();
//      return false;
//    } else {
//      return true;
//    }
