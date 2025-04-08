import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Syn {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Syn <file>");
            System.exit(1);
        }

        try {
            Lex.fileReader = new FileReader(args[0]);
            Lex.lexemeBuilder = new StringBuilder();

            Lex.getChar();  // Initialize first character
            Lex.lex();

            list(0);
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("Error while reading file");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void list(int tabs) throws Exception {
        enterStatement(tabs, "list");

        // If first char is quote, then read second char.
        switch (Lex.nextToken) {

            // '...

            case QUOTE:
                Lex.lex();

                // ...(...

                if (Lex.nextToken != Token.LPAREN)
                    throw new RuntimeException("<list> expect '(' after quote in list");
                Lex.lex();

                // ...<items>...

                if (Lex.nextToken != Token.RPAREN)
                    items(tabs + 1);

                // ...)

                // or '()
                if (Lex.nextToken != Token.RPAREN)
                    throw new RuntimeException("<list> missing closing paren ')'");
                Lex.lex();

                break;

            // (...

            case LPAREN:
                Lex.lex();

                // ...<items>... or ...<nothing>...

                if (Lex.nextToken != Token.RPAREN)
                    items(tabs + 1);

                // ...)

                if (Lex.nextToken != Token.RPAREN)
                    throw new RuntimeException("<list> not terminated");
                Lex.lex();
        }

        exitStatement(tabs, "list");
    }

    public static void items(int tabs) throws Exception {
        enterStatement(tabs, "items");

        // <item> {<item>}

        do {
            item(tabs + 1);
        }
        while (Lex.nextToken != Token.RPAREN);

        exitStatement(tabs, "items");
    }

    public static void item(int tabs) throws Exception {
        enterStatement(tabs, "item");

        if (Lex.nextToken == Token.LPAREN || Lex.nextToken == Token.QUOTE) {
            list(tabs + 1);
        }
        else {
            atom(tabs + 1);
        }

        exitStatement(tabs, "item");
    }

    public static void atom(int tabs) throws Exception {
        enterStatement(tabs, "atom");

        switch (Lex.nextToken) {
            case NUM_LITERAL:
            case STR_LITERAL:
            case BOOL_LITERAL:
                literal(tabs + 1);
                break;
            case NAME:
            case KEYWORD:
                Lex.lex();
                break;
            default:
                throw new RuntimeException("<atom> unknown token");
        }


        exitStatement(tabs, "atom");
    }

    public static void literal(int tabs) throws Exception {
        enterStatement(tabs, "literal");
        Lex.lex();
        exitStatement(tabs, "literal");
    }

    private static void exitStatement(int tabs, String nonLeaf) {
        for (int i = 0; i < tabs; i++)
            System.out.print("|\t");
        System.out.println("Exiting <" + nonLeaf + ">, current lexeme: " + Lex.lexeme);
    }

    private static void enterStatement(int tabs, String nonLeaf) {
        for (int i = 0; i < tabs; i++)
            System.out.print("|\t");
        System.out.println("Entering <" + nonLeaf + ">, current lexeme: " + Lex.lexeme);
    }
}
