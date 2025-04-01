public enum Token {
    // Syntax symbols
    LPAREN,
    RPAREN,
    QUOTE,

    // Literals
    NUM_LITERAL,
    STR_LITERAL,
    BOOL_LITERAL,

    // Keywords
    LAMBDA_KW,
    DEFINE_KW,
    QUOTE_KW,
    CAR_KW,
    CDR_KW,
    CONS_KW,
    ADD1_KW,
    SUB1_KW,

    // Others
    NAME,
    EOF
}
