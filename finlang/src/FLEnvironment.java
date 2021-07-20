package com.finlang.lang;

import java.util.HashMap;
import java.util.Map;

class FLEnvironment
{
    final FLEnvironment enclosing;
    private final Map<String, Object> values = new HashMap<>();
    
    FLEnvironment()
    {
        enclosing = null;
        
        return;
    }
    
    FLEnvironment(FLEnvironment enclosing)
    {
        this.enclosing = enclosing;
        
        return;
    }
    
    void define(String name, Object value)
    {
        values.put(name, value);
        
        return;
    }
    
    Object get(FLToken name)
    {
        if (values.containsKey(name.lexeme))
        {
            return values.get(name.lexeme);
        }
        
        if (enclosing != null) return enclosing.get(name);
        
        throw new FLRuntimeError(name,
                                 "Undefined variable '" +
                                 name.lexeme + "'.");
    }
    
    void assign(FLToken name, Object value)
    {
        if (values.containsKey(name.lexeme))
        {
            values.put(name.lexeme, value);
            return;
        }
        
        if (enclosing != null)
        {
            enclosing.assign(name, value);
            return;
        }
        
        throw new FLRuntimeError(name,
                                 "Undefined variable '" +
                                 name.lexeme + "'.");
    }
}