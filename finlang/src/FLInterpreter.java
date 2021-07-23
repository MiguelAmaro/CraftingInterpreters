package com.finlang.lang;

import java.util.List;

// NOTE(MIGUEL): MENTIONED IN: 7.2
class FLInterpreter implements
FLExpr.Visitor<Object>,
FLStmt.Visitor<Void>
{
    private FLEnvironment environment = new FLEnvironment();
    
    void interpret(List<FLStmt> statements)
    {
        try
        {
            for (FLStmt statement : statements)
            {
                execute(statement);
            }
        }
        catch (FLRuntimeError error) 
        {
            Finlang.runtimeError(error);
        }
    }
    
    @Override
        public Object visitLiteralFLExpr(FLExpr.Literal expr)
    {
        return expr.value;
    }
    
    @Override
        public Object visitLogicalFLExpr(FLExpr.Logical expr)
    {
        Object left = evaluate(expr.left);
        
        if (expr.operator.type == FLTokenType.OR)
        {
            if (isTruthy(left)) return left;
        }
        else
        {
            if (!isTruthy(left)) return left;
        }
        
        return evaluate(expr.right);
    }
    
    @Override
        public Object visitUnaryFLExpr(FLExpr.Unary expr)
    {
        Object right = evaluate(expr.right);
        
        switch (expr.operator.type)
        {
            case NOT : return !isTruthy(right);
            case MINUS: checkNumberOperand(expr.operator, right); return -(double)right;
        }
        
        // Unreachable.
        return null;
    }
    
    @Override
        public Object visitVariableFLExpr(FLExpr.Variable expr)
    {
        
        return environment.get(expr.name);
    }
    
    @Override
        public Void visitWhileFLStmt(FLStmt.While stmt)
    {
        
        while (isTruthy(evaluate(stmt.condition)))
        {
            execute(stmt.body);
        }
        
        return null;
    }
    
    @Override
        public Object visitGroupingFLExpr(FLExpr.Grouping expr)
    {
        return evaluate(expr.expression);
    }
    
    private void checkNumberOperand(FLToken operator, Object operand)
    {
        if (operand instanceof Double) return;
        
        throw new FLRuntimeError(operator, "Operand must be a number.");
    }
    
    
    private void checkNumberOperands(FLToken operator, Object left, Object right)
    {
        if (left instanceof Double && right instanceof Double) return;
        
        throw new FLRuntimeError(operator, "Operands must be numbers.");
    }
    
    private boolean isTruthy(Object object)
    {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        
        return true;
    }
    
    private boolean isEqual(Object a, Object b)
    {
        if (a == null && b == null) return true;
        if (a == null)              return false;
        
        return a.equals(b);
    }
    
    private String stringify(Object object)
    {
        if (object == null) return "nil";
        
        if (object instanceof Double)
        {
            String text = object.toString();
            if (text.endsWith(".0"))
            {
                text = text.substring(0, text.length() - 2);
            }
            
            return text;
        }
        
        return object.toString();
    }
    
    //~ HELPER FUNCTIONS
    private Object evaluate(FLExpr expr) 
    {
        return expr.accept(this);
    }
    
    private void execute(FLStmt stmt)
    {
        stmt.accept(this);
        
        return;
    }
    
    void executeBlock(List<FLStmt> statements, FLEnvironment environment)
    {
        FLEnvironment previous = this.environment;
        
        try
        {
            this.environment = environment;
            
            for (FLStmt statement : statements)
            {
                execute(statement);
            }
        }
        finally
        {
            this.environment = previous;
        }
        
        return;
    }
    
    @Override
        public Void visitBlockFLStmt(FLStmt.Block stmt)
    {
        executeBlock(stmt.statements, new FLEnvironment(environment));
        
        return null;
    }
    
    @Override
        public Void visitExpressionFLStmt(FLStmt.Expression stmt)
    {
        evaluate(stmt.expression);
        
        return null;
    }
    
    @Override
        public Void visitIfFLStmt(FLStmt.If stmt)
    {
        if (isTruthy(evaluate(stmt.condition)))
        {
            execute(stmt.thenBranch);
        }
        else if (stmt.elseBranch != null)
        {
            execute(stmt.elseBranch);
        }
        
        return null;
    }
    
    @Override
        public Void visitPrintFLStmt(FLStmt.Print stmt)
    {
        Object value = evaluate(stmt.expression);
        System.out.println(stringify(value));
        
        return null;
    }
    
    @Override
        public Void visitVarFLStmt(FLStmt.Var stmt)
    {
        Object value = null;
        
        if (stmt.initializer != null)
        {
            value = evaluate(stmt.initializer);
        }
        
        environment.define(stmt.name.lexeme, value);
        
        return null;
    }
    
    @Override
        public Object visitAssignFLExpr(FLExpr.Assign expr)
    {
        Object value = evaluate(expr.value);
        environment.assign(expr.name, value);
        
        return value;
    }
    
    @Override
        public Object visitBinaryFLExpr(FLExpr.Binary expr)
    {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right); 
        
        switch (expr.operator.type)
        {
            case GREATER      :
            {
                checkNumberOperands(expr.operator, left, right);
                return (double)left >  (double)right;
            }
            case GREATER_EQUAL:
            { 
                checkNumberOperands(expr.operator, left, right);
                return (double)left >= (double)right;
            }
            case LESS         :
            { 
                checkNumberOperands(expr.operator, left, right);
                return (double)left <  (double)right;
            }
            case LESS_EQUAL   :
            { 
                checkNumberOperands(expr.operator, left, right);
                return (double)left <= (double)right;
            }
            case MINUS        :
            { 
                checkNumberOperands(expr.operator, left, right);
                return (double)left -  (double)right;
            }
            case PLUS:
            {
                if (left instanceof Double && right instanceof Double)
                {
                    return (double)left + (double)right;
                }
                if (left instanceof String && right instanceof String)
                {
                    return (String)left + (String)right;
                }
                
                throw new FLRuntimeError(expr.operator,
                                         "Operands must be two numbers or two strings.");
            }
            case SLASH        :
            { 
                checkNumberOperands(expr.operator, left, right);
                return (double)left /  (double)right;
            }
            case STAR         :
            { 
                checkNumberOperands(expr.operator, left, right);
                return (double)left *  (double)right;
            }
            case NOT_EQUAL   :
            { 
                return !isEqual(left, right);
            }
            case EQUAL_EQUAL  :
            { 
                return  isEqual(left, right);
            }
        }
        
        // Unreachable.
        return null;
    }
}