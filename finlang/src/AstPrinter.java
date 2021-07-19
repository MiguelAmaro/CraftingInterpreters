package com.finlang.lang;

class AstPrinter implements FLExpr.Visitor<String>
{
    String print(FLExpr expr)
    {
        return expr.accept(this);
    }
    
    @Override
        public String visitBinaryExpr(FLExpr.Binary expr)
    {
        return parenthesize(expr.operator.lexeme,
                            expr.left, expr.right);
    }
    
    @Override
        public String visitGroupingExpr(FLExpr.Grouping expr)
    {
        return parenthesize("group", expr.expression);
    }
    
    @Override
        public String visitLiteralExpr(FLExpr.Literal expr)
    {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }
    
    @Override
        public String visitUnaryExpr(FLExpr.Unary expr)
    {
        return parenthesize(expr.operator.lexeme, expr.right);
    }
    
    private String parenthesize(String name, FLExpr... exprs)
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append("(").append(name);
        for (FLExpr expr : exprs)
        {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        
        builder.append(")");
        
        return builder.toString();
    }
    
    public static void main(String[] args)
    {
        FLExpr expression = new FLExpr.Binary(new FLExpr.Unary(new Token(TokenType.MINUS, "-", null, 1),
                                                               new FLExpr.Literal(123)),
                                              new Token(TokenType.STAR, "*", null, 1),
                                              new FLExpr.Grouping(
                                                                  new FLExpr.Literal(45.67)));
        
        System.out.println(new AstPrinter().print(expression));
    }
}