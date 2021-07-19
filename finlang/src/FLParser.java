package com.finlang.lang;


import java.util.List;

import static com.finlang.lang.FLTokenType.*;

// NOTE(MIGUEL): MENTIONED IN: 6.2.1
class Parser
{
    private static class ParseError extends RuntimeException {}
    
    private final List<FLToken> tokens;
    private int current = 0;
    
    
    Parser(List<FLToken> tokens)
    {
        this.tokens = tokens;
    }
    
    //~ EVALUATING FUNCTIONS
    FLExpr parse()
    {
        try
        {
            return expression();
        }
        catch (ParseError error)
        {
            return null;
        }
    }
    
    private FLExpr expression()
    {
        return equality();
    }
    
    private FLExpr equality()
    {
        FLExpr expr = comparison();
        
        // TODO(MIGUEL): rename NOT_EQUAL "!="
        while (match(NOT_EQUAL, EQUAL_EQUAL))
        {
            FLToken operator = previous();
            FLExpr right = comparison();
            expr = new FLExpr.Binary(expr, operator, right);
        }
        
        return expr;
    }
    
    private FLExpr comparison()
    {
        FLExpr expr = term();
        
        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL))
        {
            FLToken operator = previous();
            FLExpr right = term();
            expr = new FLExpr.Binary(expr, operator, right);
        }
        
        return expr;
    }
    
    // NOTE(MIGUEL): addition & subtraction
    private FLExpr term()
    {
        FLExpr expr = factor();
        
        while (match(MINUS, PLUS))
        {
            FLToken operator = previous();
            FLExpr right = factor();
            expr = new FLExpr.Binary(expr, operator, right);
        }
        
        return expr;
    }
    
    // NOTE(MIGUEL): multiplication & division
    private FLExpr factor()
    {
        FLExpr expr = unary();
        
        while (match(SLASH, STAR))
        {
            FLToken operator = previous();
            FLExpr right = unary();
            expr = new FLExpr.Binary(expr, operator, right);
        }
        
        return expr;
    }
    
    // NOTE(MIGUEL): negation
    private FLExpr unary()
    {
        if (match(NOT, MINUS))
        {
            FLToken operator = previous();
            FLExpr right = unary();
            // NOTE(MIGUEL): RETURNS EARLY V
            return new FLExpr.Unary(operator, right);
        }
        
        return primary();
    }
    
    private FLExpr primary()
    {
        if (match(FALSE)) return new FLExpr.Literal(false);
        if (match(TRUE )) return new FLExpr.Literal(true);
        if (match(NULL  )) return new FLExpr.Literal(null);
        
        if (match(NUMBER, STRING)) 
        {
            return new FLExpr.Literal(previous().literal);
        }
        
        if (match(PAREN_LEFT))
        {
            FLExpr expr = expression();
            consume(PAREN_RIGHT, "Expect ')' after expression.");
            return new FLExpr.Grouping(expr);
        }
        
        throw error(peek(), "Expect expression.");
    }
    
    //~ HELPER FUNCTIONS
    private boolean match(FLTokenType... types)
    {
        for(FLTokenType type : types)
        {
            if (check(type))
            {
                advance();
                return true;
            }
        }
        
        return false;
    }
    
    private FLToken consume(FLTokenType type, String message)
    {
        if (check(type)) return advance();
        
        throw error(peek(), message);
    }
    private boolean check(FLTokenType type)
    {
        if (isAtEnd()) return false;
        
        return peek().type == type;
    }
    
    private FLToken advance()
    {
        if (!isAtEnd()) current++;
        
        return previous();
    }
    
    private boolean isAtEnd()
    {
        return peek().type == EOF;
    }
    
    private FLToken peek()
    {
        return tokens.get(current);
    }
    
    private FLToken previous()
    {
        return tokens.get(current - 1);
    }
    
    private ParseError error(FLToken token, String message)
    {
        Finlang.error(token, message);
        
        return new ParseError();
    }
    
    
    //~ PANIC MODE ERROR REPORTING
    
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
