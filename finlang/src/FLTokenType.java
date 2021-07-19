package com.finlang.lang;

enum FLTokenType
{
    // Single-character tokens.
    PAREN_LEFT, PAREN_RIGHT, BRACE_LEFT, BRACE_RIGHT,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,
    
    // One or two character tokens.
    NOT, NOT_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,
    
    // Literals.
    IDENTIFIER, STRING, NUMBER,
    
    // TODO(MIGUEL): NO VAR. NO PRINT. NO SUPER
    // Keywords.
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NULL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,
    
    EOF
}