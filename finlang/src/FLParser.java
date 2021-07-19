package com.finlang.lang;


import java.util.List;

import static com.finlang.lang.TokenType.*;

// NOTE(MIGUEL): MENTIONED IN: 6.2.1
class Parser
{
    private static class ParseError extends RuntimeException {}
    
    private final List<Token> tokens;
    private int current = 0;
    
    
    Parser(List<Token> tokens)
    {
        this.tokens = tokens;
    }
    
    //~ EVALUATING FUNCTIONS
    Expr parse()
    {
        try
        {
            return expression();
        }
        catch (ParseError error)
        {
            return null;
        }
        
        return null;
    }
    
    private Expr expression()
    {
        return equality();
    }
    
    private Expr equality()
    {
        Expr expr = comparison();
        
        // TODO(MIGUEL): rename BANG_EQUAL "!="
        while (match(BANG_EQUAL, EQUAL_EQUAL))
        {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }
        
        return expr;
    }
    
    private Expr comparison()
    {
        Expr expr = term();
        
        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL))
        {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }
        
        return expr;
    }
    
    // NOTE(MIGUEL): addition & subtraction
    private Expr term()
    {
        Expr expr = factor();
        
        while (match(MINUS, PLUS))
        {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }
        
        return expr;
    }
    
    // NOTE(MIGUEL): multiplication & division
    private Expr factor()
    {
        Expr expr = unary();
        
        while (match(SLASH, STAR))
        {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }
        
        return expr;
    }
    
    // NOTE(MIGUEL): negation
    private Expr unary()
    {
        if (match(BANG, MINUS))
        {
            Token operator = previous();
            Expr right = unary();
            // NOTE(MIGUEL): RETURNS EARLY V
            return new Expr.Unary(operator, right);
        }
        
        return primary();
    }
    
    private Expr primary()
    {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE )) return new Expr.Literal(true);
        if (match(NIL  )) return new Expr.Literal(null);
        
        if (match(NUMBER, STRING)) 
        {
            return new Expr.Literal(previous().literal);
        }
        
        if (match(LEFT_PAREN))
        {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }
        
        throw error(peek(), "Expect expression.");
    }
    
    //~ HELPER FUNCTIONS
    private boolean match(TokenType... types)
    {
        for(TokenType type : types)
        {
            if (check(type))
            {
                advance();
                return true;
            }
        }
        
        return false;
    }
    
    private Token consume(TokenType type, String message)
    {
        if (check(type)) return advance();
        
        throw error(peek(), message);
    }
    private boolean check(TokenType type)
    {
        if (isAtEnd()) return false;
        
        return peek().type == type;
    }
    
    private Token advance()
    {
        if (!isAtEnd()) current++;
        
        return previous();
    }
    
    private boolean isAtEnd()
    {
        return peek().type == EOF;
    }
    
    private Token peek()
    {
        return tokens.get(current);
    }
    
    private Token previous()
    {
        return tokens.get(current - 1);
    }
    
    //~ PANIC MODE ERROR REPORTING
    private ParseError error(Token token, String message)
    {
        Lox.error(token, message);
        
        return new ParseError();
    }
    
    static void error(Token token, String message)
    {
        if (token.type == TokenType.EOF)
        {
            report(token.line, " at end", message);
        }
        else 
        {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
        
        return;
    }
    
    private void synchronize()
    {
        advance();
        
        while (!isAtEnd())
        {
            if (previous().type == SEMICOLON) return;
            
            switch (peek().type)
            {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                return;
            }
            
            advance();
        }
        
        return;
    }
}