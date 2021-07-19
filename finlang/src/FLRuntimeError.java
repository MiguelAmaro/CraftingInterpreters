package com.finlang.lang;

class FLRuntimeError extends RuntimeException
{
    final FLToken token;
    
    FLRuntimeError(FLToken token, String message)
    {
        super(message);
        this.token = token;
    }
}