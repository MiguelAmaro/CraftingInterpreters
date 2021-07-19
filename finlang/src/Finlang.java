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
            System.exit(64); // [64]]
        }
        else if (args.length == 1 && !args[0].equals(""))
        {
            String option = args[0];
            
            if(option.equals("--help"))
            {
                System.out.println("Usage: finlang [script]");
            }
            else
            {
                runFile(option);
            }
        }
        else
        {
            runPrompt();
        }
        
        return;
    }
    
    private static void runFile(String path) throws IOException
    {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        
        return;
    }
    
    private static void runPrompt() throws IOException
    {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader   reader = new BufferedReader(input);
        
        for (;;) 
        { 
            System.out.print("> ");
            String line = reader.readLine();
            
            if (line == null) System.out.print("should break");
            
            run(line);
        }
    }
    
    private static void run(String source)
    {
        FLScanner     scanner = new FLScanner(source);
        List<FLToken>  tokens = scanner.scanTokens();
        
        // NOTE(MIGUEL): Parser not hooked up yet?????
        // For now, just print the tokens.
        for (FLToken token : tokens)
        {
            System.out.println(token);
        }
        
        return;
    }
    
    static void error(int line, String message)
    {
        report(line, "", message);
        
        return;
    }
    
    private static void report(int line, String where, String message)
    {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
        
        return;
    }
    
    
    static void error(FLToken token, String message)
    {
        if (token.type == FLTokenType.EOF)
        {
            report(token.line, " at end", message);
        }
        else 
        {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
        
        return;
    }
    
    static void runtimeError(FLRuntimeError error)
    {
        System.err.println(error.getMessage() +
                           "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
        
        return;
    }
}