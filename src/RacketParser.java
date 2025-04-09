import java.io.File;

public class RacketParser {
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.err.println("Usage: java RacketParser [-lex] <fileName>");
            System.exit(1);
        }

        // java RacketParser -lex <filename>
        if (args[0].equals("-lex")) {
            if (args.length != 2) {
                System.err.println("Input: -lex, missing file name as argument");
                System.exit(1);
            }
            try {
                LexicalParser.parseLexical(args[1]);
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        // java RacketParser <filename>
        else {
            if (args.length != 1) {
                System.err.println("Input: no -lex, expected 1 argument");
                System.exit(1);
            }
            try {
                SyntacticParser.parseSyntax(args[0]);
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}
