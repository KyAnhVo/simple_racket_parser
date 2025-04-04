import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Syn {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Lex <file>");
            System.exit(1);
        }

        try {
            Lex.fileReader = new FileReader(args[0]);
            Lex.lexemeBuilder = new StringBuilder();

            Lex.getChar();  // Initialize first character
            Lex.lex();

            list();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("Error while reading file");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void list() throws Exception {
        System.out.println("Entering <list>");

        // If first char is quote, then read second char.
        switch (Lex.nextToken) {

            // '...
            case QUOTE:
                Lex.lex();
                if (Lex.nextToken != Token.LPAREN)
                    throw new RuntimeException("<list> expect '(' after quote in list");

                // '(<items>...
                Lex.lex();
                if (Lex.nextToken != Token.RPAREN)
                    items();

                // ...)
                if (Lex.nextToken != Token.RPAREN)
                    throw new RuntimeException("<list> missing closing paren ')'");

                break;

            // (<items>)
            case LPAREN:
                items();
                if (Lex.nextToken != Token.RPAREN)
                    throw new RuntimeException("<list> not terminated");
        }

        System.out.println("Exiting <list>");
    }

    public static void items() throws Exception {
        System.out.println("Entering <items>");

        // <item> {<item>}
        do {
            item();
            Lex.lex();
        }
        while (Lex.nextToken != Token.RPAREN);

        System.out.println("Exiting <items>");
    }

    public static void item() throws Exception {
        System.out.println("Entering <item>");

        Lex.lex();
        if (Lex.nextToken == Token.LPAREN || Lex.nextToken == Token.QUOTE) {
            list();
        }
        else {
            atom();
        }

        System.out.println("Exiting <item>");
    }

    public static void atom() {
        System.out.println("Entering <atom>");

        switch (Lex.nextToken) {
            case NUM_LITERAL:
            case STR_LITERAL:
            case BOOL_LITERAL:
                literal();
                break;
            case NAME:
            case KEYWORD:
                break;
            default:
                throw new RuntimeException("<atom> unknown token");
        }

        System.out.println("Exiting <atom>");
    }

    public static void literal() {
        System.out.println("Entering <literal>");
        System.out.println("Exiting <literal>");
    }
}
