import org.apache.commons.io.IOUtils;
import org.immutables.value.Value;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// should only contain the parsing logic
//
public class Parser{
    private String file;

    public Parser(String fileToRead){
        file = fileToRead;
    }

    public void NewThing() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(file);
        String delimiterRegex = ".[A-Z]";

        Scanner scanner = new Scanner(inputStream).useDelimiter(delimiterRegex);

        while (scanner.hasNext()){
            FileFormat fileFormat = ImmutableFileFormat.builder().Index(scanner.nextInt())
        }


    }

    public void PrintingOutput() throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        String delimiterRegex = ".[A-Z]";

        Scanner scanner = new Scanner(inputStream).useDelimiter(delimiterRegex);
        int i = 0;
        StringBuilder outputString = new StringBuilder();
        while(scanner.hasNext()){
            outputString.append(scanner.next());
            i++;
            if (i%5==0){
                WriteToCorpus(outputString);
                outputString = new StringBuilder("");
            }
        }
    }

    private void WriteToCorpus(StringBuilder outputString) throws IOException {
        String outputS = outputString.toString().trim();
        String filename  = outputS.substring(0,outputS.indexOf("\n"));
        if(filename.matches("[0-9]")){
            String file = "Corpus/" + outputS.substring(0,outputS.indexOf("\n"))+ ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write(outputString.toString().trim());
            writer.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Parser testParser = new Parser("cran/cran.all.1400");
        testParser.PrintingOutput();
    }
}





//final Pattern pattern = Pattern.compile(delimiterRegex, Pattern.MULTILINE);


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