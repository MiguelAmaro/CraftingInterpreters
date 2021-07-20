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
    AND, CLASS, STRUCT, ELSE, FALSE, FUN, FOR, IF, NULL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE,
    // NOTE(MIGUEL): PRIMITIVE TYPES
    // REF: https://www.baeldung.com/jni
    SIGNED_INT_8BIT,
    SIGNED_INT_16BIT,
    SIGNED_INT_32BIT,
    SIGNED_INT_64BIT,
    
    UNSIGNED_INT_8BIT,
    UNSIGNED_INT_16BIT,
    UNSIGNED_INT_32BIT,
    UNSIGNED_INT_64BIT,
    
    FLOATING_POINT_32BIT,
    FLOATING_POINT_64BIT,
    VAR,
    WHILE,
    
    EOF
}