public enum CharacterClass {
    LETTER,
    DIGIT,
    PERIOD,     // For decimal
    QUOTE,      // For quoted list
    STR_QUOTE,  // For str
    HASH,       // For #t and #f in bool_lit
    LPAREN,
    RPAREN,
    WHITESPACE, // For getNonBlank()
    EOF,
    OTHER_ASCII
}
