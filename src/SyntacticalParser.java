public class SyntacticalParser {

    public static void list() throws Exception {
        System.out.println("Entering <list>");

        // If first char is quote, then read second char.
        switch (LexicalParser.nextToken) {

            // If first char is quote, then read second char. If second char is LPAREN, send to items.
            // If second char is not LPAREN, throw error.
            case QUOTE:
                LexicalParser.lex();
                if (LexicalParser.nextToken != Token.LPAREN)
                    throw new RuntimeException("<list> -- expect '(' after quote in list");

                LexicalParser.lex();
                if (LexicalParser.nextToken != Token.RPAREN)
                    items();

                if (LexicalParser.nextToken != Token.RPAREN)
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
