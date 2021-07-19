package com.finlang.lang;

// NOTE(MIGUEL): MENTIONED IN: 7.2
class Interpreter implements Expr.Visitor<Object>
{
    @Override
        public Object visitLiteralExpr(Expr.Literal expr)
    {
        return expr.value;
    }
    
    @Override
        public Object visitUnaryExpr(Expr.Unary expr)
    {
        Object right = evaluate(expr.right);
        
        switch (expr.operator.type)
        {
            case BANG : return !isTruthy(right);
            case MINUS: checkNumberOperand(expr.operator, right); return -(double)right;
        }
        
        // Unreachable.
        return null;
    }
    
    private void checkNumberOperand(Token operator, Object operand)
    {
        if (operand instanceof Double) return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }
    
    private void checkNumberOperands(Token operator, Object left, Object right)
    {
        if (left instanceof Double && right instanceof Double) return;
        
        throw new RuntimeError(operator, "Operands must be numbers.");
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
    
    @Override
        public Object visitGroupingExpr(Expr.Grouping expr)
    {
        return evaluate(expr.expression);
    }
    
    //~ HELPER FUNCTIONS
    private Object evaluate(Expr expr) 
    {
        return expr.accept(this);
    }
    
    @Override
        public Object visitBinaryExpr(Expr.Binary expr)
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
            throw new RuntimeError(expr.operator,
                                   "Operands must be two numbers or two strings.");
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
            case BANG_EQUAL   :
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
    
    private boolean isTruthy(Object object)
    {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        
        return true;
    }
}