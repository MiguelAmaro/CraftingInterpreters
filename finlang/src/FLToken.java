package com.finlang.lang;

class FLToken
{
    final FLTokenType type;
    final String    lexeme; //null
    final Object    literal;
    final int       line; 
    
    FLToken(FLTokenType type, String lexeme, Object literal, int line)
    {
        this.type    = type;
        this.lexeme  = lexeme;
        this.literal = literal;
        this.line    = line;
    }
    
    public String toString()
    {
        return type + " " + lexeme + " " + literal;
    }
    
}