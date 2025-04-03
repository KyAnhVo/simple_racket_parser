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

            list();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error while reading file");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void list() throws Exception {
        System.out.println("Entering <list>");

        // If first char is quote, then read second char.
        switch (Lex.nextToken) {

            // If first char is quote, then read second char. If second char is LPAREN, send to items.
            // If second char is not LPAREN, throw error.
            case QUOTE:
                Lex.lex();
                if (Lex.nextToken != Token.LPAREN)
                    throw new RuntimeException("<list> -- expect '(' after quote in list");

                Lex.lex();
                if (Lex.nextToken != Token.RPAREN)
                    items();

                if (Lex.nextToken != Token.RPAREN)
                    throw new RuntimeException("<list> -- missing closing paren ')'");

            case LPAREN:
                items();
        }

        System.out.println("Exiting <list>");
    }

    public static void items() {
        System.out.println("Entering <items>");
        System.out.println("Exiting <items>");
    }

    public static void item() {
        System.out.println("Entering <item>");
        System.out.println("Exiting <item>");
    }

    public static void atom() {
        System.out.println("Entering <atom>");
        System.out.println("Exiting <atom>");
    }

    public static void literal() {
        System.out.println("Entering <literal>");
        System.out.println("Exiting <literal>");
    }
}
