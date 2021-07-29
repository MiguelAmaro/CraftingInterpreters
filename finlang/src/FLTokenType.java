package com.finlang.lang;

enum FLTokenType
{
    // Single-character tokens.
    PAREN_LEFT, PAREN_RIGHT, BRACE_LEFT, BRACE_RIGHT,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR, COLON,
    
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
    
    
    // NOTE(MIGUEL): preprocessor directives
    PP_DEF,
    
    PP_IF,
    PP_ELIF,
    PP_ELSE,
    PP_ENDIF,
    
    PP_IFDEF,
    PP_IFNDEF,
    PP_UNDEF,
    
    // NOTE(MIGUEL): NATIVE TYPES
    // REF: https://www.baeldung.com/jni
    
    NATIVE_U8,
    NATIVE_U16,
    NATIVE_U32,
    NATIVE_U64,
    
    NATIVE_S8,
    NATIVE_S16,
    NATIVE_S32,
    NATIVE_S64,
    
    NATIVE_F32,
    NATIVE_F64,
    
    VAR, //Dynamic typeing
    WHILE,
    
    EOF
}