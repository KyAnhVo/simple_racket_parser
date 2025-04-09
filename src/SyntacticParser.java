import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SyntacticParser {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java SyntacticParser <file>");
            System.exit(1);
        }

        try {
            parseSyntax(args[0]);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static void parseSyntax(String fileName) throws Exception {
        try {
            LexicalParser.fileReader = new FileReader(fileName);
            LexicalParser.lexemeBuilder = new StringBuilder();

            LexicalParser.getChar();  // Initialize first character
            LexicalParser.lex();

            list(0);
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("Error while reading file");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (LexicalParser.fileReader != null) {
                LexicalParser.fileReader.close();
            }
        }
    }

    public static void list(int tabs) throws Exception {
        enterStatement(tabs, "list");

        // If first char is quote, then read second char.
        switch (LexicalParser.nextToken) {

            // '...
            case QUOTE:
                LexicalParser.lex();

                // ...(...
                if (LexicalParser.nextToken != Token.LPAREN)
                    throw new RuntimeException("<list> expect '(' after quote in list");
                LexicalParser.lex();

                // ...<items>...
                if (LexicalParser.nextToken != Token.RPAREN)
                    items(tabs + 1);

                // ...)
                if (LexicalParser.nextToken != Token.RPAREN)
                    throw new RuntimeException("<list> missing closing paren ')'");
                LexicalParser.lex();

                break;

            // (...
            case LPAREN:
                LexicalParser.lex();

                // ...<items>... or ...<nothing>...
                if (LexicalParser.nextToken != Token.RPAREN)
                    items(tabs + 1);

                // ...)
                if (LexicalParser.nextToken != Token.RPAREN)
                    throw new RuntimeException("<list> not terminated");
                LexicalParser.lex();
        }

        exitStatement(tabs, "list");
    }

    public static void items(int tabs) throws Exception {
        enterStatement(tabs, "items");

        // <item> {<item>}

        do {
            item(tabs + 1);
        }
        while (LexicalParser.nextToken != Token.RPAREN);

        exitStatement(tabs, "items");
    }

    public static void item(int tabs) throws Exception {
        enterStatement(tabs, "item");

        if (LexicalParser.nextToken == Token.LPAREN || LexicalParser.nextToken == Token.QUOTE) {
            list(tabs + 1);
        }
        else {
            atom(tabs + 1);
        }

        exitStatement(tabs, "item");
    }

    public static void atom(int tabs) throws Exception {
        enterStatement(tabs, "atom");

        switch (LexicalParser.nextToken) {
            case NUM_LITERAL:
            case STR_LITERAL:
            case BOOL_LITERAL:
                literal(tabs + 1);
                break;
            case NAME:
            case KEYWORD:
                LexicalParser.lex();
                break;
            default:
                throw new RuntimeException("<atom> unknown token");
        }


        exitStatement(tabs, "atom");
    }

    public static void literal(int tabs) throws Exception {
        enterStatement(tabs, "literal");
        LexicalParser.lex();
        exitStatement(tabs, "literal");
    }

    private static void exitStatement(int tabs, String nonLeaf) {
        for (int i = 0; i < tabs; i++)
            System.out.print("|\t");
        System.out.println("Exiting <" + nonLeaf + ">, current lexeme: " + LexicalParser.lexeme);
    }

    private static void enterStatement(int tabs, String nonLeaf) {
        for (int i = 0; i < tabs; i++)
            System.out.print("|\t");
        System.out.println("Entering <" + nonLeaf + ">, current lexeme: " + LexicalParser.lexeme);
    }
}
