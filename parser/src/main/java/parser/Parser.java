package parser;

import parser.utils.DocumentParser;

import java.io.IOException;

public class Parser {

  public static void main(String[] args) throws IOException {
    DocumentParser documentParser = new DocumentParser("cran/cran.all.1400");
    documentParser.ParseDocument();
  }
}
