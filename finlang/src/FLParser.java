package com.finlang.lang;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.finlang.lang.FLTokenType.*;

// NOTE(MIGUEL): MENTIONED IN: 6.2.1
class FLParser
{
    private static class ParseError extends RuntimeException {}
    
    private final List<FLToken> tokens;
    private int current = 0;
    
    
    FLParser(List<FLToken> tokens)
    {
        this.tokens = tokens;
    }
    
    //~ EVALUATING FUNCTIONS
    List<FLStmt> parse()
    {
        List<FLStmt> statements = new ArrayList<>();
        
        while (!isAtEnd())
        {
            statements.add(declaration());
        }
        
        return statements; 
    }
    
    private FLStmt statement()
    {
        if (match(PRINT     )) return  printStatement();
        if (match(IF        )) return     ifStatement();
        if (match(WHILE     )) return  whileStatement();
        if (match(FOR       )) return    forStatement();
        if (match(BRACE_LEFT)) return new FLStmt.Block(block());
        
        return expressionStatement();
    }
    
    private FLStmt forStatement()
    {
        consume(PAREN_LEFT, "Expect '(' after 'for'.");
        
        FLStmt initializer;
        
        if (match(SEMICOLON))
        {
            initializer = null;
        }
        else if (match(VAR))
        {
            initializer = varDeclaration();
        }
        else
        {
            initializer = expressionStatement();
        }        
        
        FLExpr condition = null;
        if (!check(SEMICOLON))
        {
            condition = expression();
        }
        
        consume(SEMICOLON, "Expect ';' after loop condition.");
        
        FLExpr increment = null;
        if (!check(PAREN_RIGHT))
        {
            increment = expression();
        }
        
        consume(PAREN_RIGHT, "Expect ')' after for clauses.");
        
        FLStmt body = statement();
        
        if (increment != null)
        {
            body = new FLStmt.Block(Arrays.asList(body, new FLStmt.Expression(increment)));
        }
        
        if (condition == null) condition = new FLExpr.Literal(true);
        
        body = new FLStmt.While(condition, body);
        
        if (initializer != null)
        {
            body = new FLStmt.Block(Arrays.asList(initializer, body));
        }
        
        return body;
    }
    
    private FLStmt whileStatement()
    {
        consume(PAREN_LEFT, "Expect '(' after 'while'.");
        FLExpr condition = expression();
        
        consume(PAREN_RIGHT, "Expect ')' after condition.");
        FLStmt body = statement();
        
        return new FLStmt.While(condition, body);
    }
    
    private FLStmt ifStatement()
    {
        consume(PAREN_LEFT, "Expect '(' after 'if'.");
        FLExpr condition = expression();
        consume(PAREN_RIGHT, "Expect ')' after if condition."); 
        
        FLStmt thenBranch = statement();
        FLStmt elseBranch = null;
        
        if (match(ELSE))
        {
            elseBranch = statement();
        }
        
        return new FLStmt.If(condition, thenBranch, elseBranch);
    }
    
    private FLStmt printStatement()
    {
        FLExpr value = expression();
        
        consume(SEMICOLON, "Expect ';' after value.");
        
        return new FLStmt.Print(value);
    }
    
    private FLStmt varDeclaration()
    {
        FLToken name = consume(IDENTIFIER, "Expect variable name.");
        
        FLExpr initializer = null;
        
        if (match(EQUAL))
        {
            initializer = expression();
        }
        
        consume(SEMICOLON, "Expect ';' after variable declaration.");
        
        return new FLStmt.Var(name, initializer);
    }
    
    private FLStmt expressionStatement()
    {
        FLExpr expr = expression();
        
        consume(SEMICOLON, "Expect ';' after expression.");
        
        return new FLStmt.Expression(expr);
    }
    
    private List<FLStmt> block()
    {
        List<FLStmt> statements = new ArrayList<>();
        
        while (!check(BRACE_RIGHT) && !isAtEnd())
        {
            statements.add(declaration());
        }
        
        consume(BRACE_RIGHT, "Expect '}' after block.");
        
        return statements;
    }
    
    private FLExpr assignment()
    {
        FLExpr expr = or();
        
        if (match(EQUAL))
        {
            FLToken equals = previous();
            FLExpr  value  = assignment();
            
            if (expr instanceof FLExpr.Variable)
            {
                FLToken name = ((FLExpr.Variable)expr).name;
                return new FLExpr.Assign(name, value);
            }
            
            error(equals, "Invalid assignment target."); 
        }
        
        return expr;
    }
    
    private FLExpr or()
    {
        FLExpr expr = and();
        
        while (match(OR))
        {
            FLToken operator = previous();
            FLExpr     right = and();
            expr             = new FLExpr.Logical(expr, operator, right);
        }
        
        return expr;
    }
    
    private FLExpr and()
    {
        FLExpr expr = equality();
        
        while (match(AND))
        {
            FLToken operator = previous();
            FLExpr right = equality();
            expr       = new FLExpr.Logical(expr, operator, right);
        }
        
        return expr;
    }
    
    private FLExpr expression()
    {
        return assignment();
    }
    
    private FLStmt declaration()
    {
        try
        {
            if (match(VAR)) 
            {
                return varDeclaration();
            }
            else if(match(  SIGNED_INT_8BIT ) ||
                    match(  SIGNED_INT_16BIT) ||
                    match(  SIGNED_INT_32BIT) ||
                    match(  SIGNED_INT_64BIT) ||
                    match(UNSIGNED_INT_8BIT ) ||
                    match(UNSIGNED_INT_16BIT) ||
                    match(UNSIGNED_INT_32BIT) ||
                    match(UNSIGNED_INT_64BIT))
            {
                // TODO(MIGUEL): implement native types
                //return native call;
                return varDeclaration(); //temp
            }
            
            
            return statement();
        }
        catch (ParseError error)
        {
            synchronize();
            return null;
        }
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
        
        throw error(peek(), "Expect expression. Im here");
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
