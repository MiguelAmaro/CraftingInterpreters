package com.finlang.lang;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import java.util.List;

class Finlang
{
    private static final FLInterpreter interpreter     = new FLInterpreter();
    static boolean                     hadError        = false;
    static boolean                     hadRuntimeError = false;
    
    
    public static void main(String[] args) throws IOException
    {
        if (args.length > 1)
        {
            System.out.println("Usage: finlang [script]");
            System.exit(64); // [64]
        }
        else if (args.length == 1)
        {
            runFile(args[0]);
        }
        else
        {
            runPrompt();
        }
    }
}