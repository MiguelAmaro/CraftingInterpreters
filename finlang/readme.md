![Image of Yaktocat](https://octodex.github.com/images/yaktocat.png)
                     
# Finlang Specs
                     
                     
                     
                     ## Metasyntax v.05
                     ```c
                     program     -> statement* EOF ;
                     
                     decleration -> varDecl | statement ;
                     
                     varDecl     -> "var" IDENTIFIER ( "=" expression )? ";" ;
                     
                     statement   -> exprStmt | printStmt ;
                     
                     exprStmt    -> expression ";" ;
                     printStmt   -> "print" expression ";" ;
                     
                     expression  -> literal; | unary | binary | grouping
                     
                     assignment  -> IDENTIFIER "=" assignment | equality ;
                     
                     
                     literal     ->  NUMBER | STRING | "true" | "false" | "nullptr" ;
                     grouping    ->  
                     unary       ->  ("-" | "!") primary;                                       
                     binary      ->  expression operator expression                                
                     operator    ->  "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" 
                     primary     ->  NUMBER | STRING | "true" | "false" | "nullptr" "(" | expression | ")" | IDENTIFIER;
                     ```
                     
                     
                     ## Metasyntax v.04
                     ```c
                     program     -> statement* EOF ;
                     
                     decleration -> varDecl | statement;
                     
                     varDecl     -> "var" IDENTIFIER ( "=" expression )? ";" ;
                     
                     statement   -> exprStmt | printStmt ;
                     
                     exprStmt    -> expression ";" ;
                     printStmt   -> "print" expression ";" ;
                     
                     expression  -> literal; | unary | binary | grouping
                     
                     literal     ->  NUMBER | STRING | "true" | "false" | "nullptr" ;
                     grouping    ->  
                     unary       ->  ("-" | "!") primary;                                       
                     binary      ->  expression operator expression                                
                     operator    ->  "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" 
                     primary     ->  NUMBER | STRING | "true" | "false" | "nullptr" "(" | expression | ")" | IDENTIFIER;
                     ```
                     
                     
                     ## Metasyntax v.03
                     ```c
                     program    -> statement* EOF ;
                     
                     statement  -> exprStmt | printStmt ;
                     exprStmt   -> expression ";" ;
                     printStmt  -> "print" expression ";" ;
                     
                     expression -> literal; | unary | binary | grouping
                     
                     literal    ->  NUMBER | STRING | "true" | "false" | "nullptr" ;
                     grouping   ->  
                     unary      ->  ("-" | "!") primary;                                       
                     binary     ->  expression operator expression                                
                     operator   ->  "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" 
                     primary    ->  NUMBER | STRING | "true" | "false" | "nullptr" "(" | expression | ")";
                     ```
                     
                     
                     ## Metasyntax v.02
                     ```c
                     expression -> literal; | unary | binary | grouping
                     
                     literal    ->  NUMBER | STRING | "true" | "false" | "nullptr" ;
                     grouping   ->  
                     unary      ->  ("-" | "!") primary;                                       
                     binary     ->  expression operator expression                                
                     operator   ->  "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" 
                     primary    ->  NUMBER | STRING | "true" | "false" | "nullptr" "(" | expression | ")";
                     ```
                     
                     ## Metasyntax v.01
                     ```c
                     // TODO(MIGUEL): expand on this to this need to deviate from jlox
                     
                     expression -> literal; | unary | binary | grouping
                     
                     literal    ->  NUMBER | STRING | "true" | "false" | "nullptr" ;                                                 
                     grouping   ->  "(" | expression | ")";                                       
                     unary      ->  ("-" | "!") expression;                                       
                     binary     ->  expression operator expression                                
                     operator   ->  "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" 
                     ```
                     
                     ## Associativity Table
                     | Op classes  | Symbol Set         | Associativity |
                     | :---------- | :----------------- | :------------ |
                     |Equality     | "==" "!="          |  left         |
                     |Comparison   | ">"  ">=" "<" "<=" |  left         |
                     |Term         | "-"  "+"           |  left         |
                     |Factor       | "/"  "*"           |  left         |
                     |Unary        | "!"  "-"           |  left         |
                     
                     
                     