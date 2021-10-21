package parser;

import parser.utils.DocumentParser;

import java.io.IOException;

public class Parser {

  public static void main(String[] args) throws IOException {
    // Parsing the "cran.all.1400" using DocumentParser in parser.utils
    DocumentParser documentParser = new DocumentParser("data/cran/cran.all.1400");
    documentParser.parseDocument();
  }
}
