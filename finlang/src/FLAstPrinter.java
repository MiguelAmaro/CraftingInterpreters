package com.finlang.lang;

class FLAstPrinter implements FLExpr.Visitor<String>
{
    String print(FLExpr expr)
    {
        return expr.accept(this);
    }
    
    @Override
        public String visitVariableFLExpr(FLExpr.Variable expr)
    {
        return parenthesize(expr.name.lexeme);
    }
    
    @Override
        public String visitLogicalFLExpr(FLExpr.Logical expr)
    {
        return parenthesize(expr.operator.lexeme,
                            expr.left, expr.right);
    }
    
    @Override
        public String visitAssignFLExpr(FLExpr.Assign expr)
    {
        return parenthesize(expr.name.lexeme);
    }
    
    @Override
        public String visitBinaryFLExpr(FLExpr.Binary expr)
    {
        return parenthesize(expr.operator.lexeme,
                            expr.left, expr.right);
    }
    
    @Override
        public String visitGroupingFLExpr(FLExpr.Grouping expr)
    {
        return parenthesize("group", expr.expression);
    }
    
    @Override
        public String visitLiteralFLExpr(FLExpr.Literal expr)
    {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }
    
    @Override
        public String visitUnaryFLExpr(FLExpr.Unary expr)
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
}