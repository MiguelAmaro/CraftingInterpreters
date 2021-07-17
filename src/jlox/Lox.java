package com.craftinginterpreters.lox;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import java.util.List;

public class Lox
{
    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError        = false;
    static boolean hadRuntimeError = false;
    
    ///!!!! MAIN !!!!
    public static void main(String[] args) throws IOException
    {
        if (args.length > 1)
        {
            System.out.println("Usage: jlox [script]");
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
    
    // NOTE(MIGUEL): THIS BASED ON COMMAND LINE ARGS
    private static void runFile(String path) throws IOException
    {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        
        // Indicate an error in the exit code.
        if (hadError) 
        {
            System.exit(65);
        }
        
        if (hadRuntimeError)
        {
            System.exit(70);
        }
        
    }
    
    private static void runPrompt() throws IOException
    {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader   reader = new BufferedReader(input);
        
        for (;;)
        { 
            // [repl]
            // NOTE(MIGUEL):
            // [R]ead a line of input,
            // [E]valuate it,
            // [P]rint the result,
            // [L]oop
            
            System.out.print("-test-> ");
            
            String line = reader.readLine();
            if (line == null) break;
            run(line); // NOTE(MIGUEL): PARSING AND AST BUILD BEGINS HERE!!!
            
            hadError = false;
        }
    }
    
    private static void run(String source)
    {
        // TODO(MIGUEL): check out the constructor
        Scanner scanner = new Scanner(source);
        // TODO(MIGUEL): check out the constructor
        List<Token> tokens = scanner.scanTokens();
        
        // TODO(MIGUEL): check out the constructor
        Parser parser = new Parser(tokens);
        
        List<Stmt> statements = parser.parse();
        
        if (hadError) return;
        
        //< Parsing Expressions print-ast
        //> Resolving and Binding create-resolver
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);
        //> resolution-error
        
        // Stop if there was a resolution error.
        if (hadError) return;
        //< resolution-error
        
        //< Resolving and Binding create-resolver
        /* Parsing Expressions print-ast < Evaluating Expressions interpreter-interpret
            System.out.println(new AstPrinter().print(expression));
        */
        /* Evaluating Expressions interpreter-interpret < Statements and State interpret-statements
            interpreter.interpret(expression);
        */
        //> Statements and State interpret-statements
        interpreter.interpret(statements);
        //< Statements and State interpret-statements
    }
    //< run
    //> lox-error
    static void error(int line, String message)
    {
        report(line, "", message);
    }
    
    private static void report(int line, String where,
                               String message) 
    {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
    //< lox-error
    //> Parsing Expressions token-error
    static void error(Token token, String message)
    {
        if (token.type == TokenType.EOF)
        {
            report(token.line, " at end", message);
        } else
        {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }
    //< Parsing Expressions token-error
    //> Evaluating Expressions runtime-error-method
    static void runtimeError(RuntimeError error)
    {
        System.err.println(error.getMessage() +
                           "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }
    //< Evaluating Expressions runtime-error-method
}