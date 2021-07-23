# Finlang Specs


## Metasyntax v.08
```c
program     -> statement* EOF ;

decleration -> varDecl | statement ;

varDecl     -> "var" IDENTIFIER ( "=" expression )? ";" ;

statement   -> exprStmt | forStmt | ifStmt | printStmt | whileStmt | block ;

forStmt     -> "for" "(" ( varDecl | exprStmt | ";" )
               expression? ";"
               expression? ")" statement ;

ifStmt      -> "if" "(" expression ")" statement ("else"                         statement) ? ;

block       -> "{" statement "}" 

exprStmt    -> expression ";" ;
printStmt   -> "print" expression ";" ;

expression  -> literal; | unary | binary | grouping

assignment  -> IDENTIFIER "=" assignment | logic_or;

logic_or    -> logic_and ( "or" logic_and )* ;

logic_and   -> equality ( "and" equality )* ;


literal     ->  NUMBER | STRING | "true" | "false" | "nullptr" ;
grouping    ->  
unary       ->  ("-" | "!") primary;                                       
binary      ->  expression operator expression                                
operator    ->  "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" 
primary     ->  NUMBER | STRING | "true" | "false" | "nullptr" "(" | expression | ")" | IDENTIFIER;
```
### Added 
## Metasyntax v.07
```c
program     -> statement* EOF ;

decleration -> varDecl | statement ;

varDecl     -> "var" IDENTIFIER ( "=" expression )? ";" ;

statement   -> exprStmt | ifStmt | printStmt | block ;

block       -> "{" statement "}" 

ifStmt      -> "if" "(" expression ")" statement ("else" statement) ? ;

exprStmt    -> expression ";" ;
printStmt   -> "print" expression ";" ;

expression  -> literal; | unary | binary | grouping

assignment  -> IDENTIFIER "=" assignment | logic_or;

logic_or    -> logic_and ( "or" logic_and )* ;

logic_and   -> equality ( "and" equality )* ;


literal     ->  NUMBER | STRING | "true" | "false" | "nullptr" ;
grouping    ->  
unary       ->  ("-" | "!") primary;                                       
binary      ->  expression operator expression                                
operator    ->  "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" 
primary     ->  NUMBER | STRING | "true" | "false" | "nullptr" "(" | expression | ")" | IDENTIFIER;
```


## Metasyntax v.06
```c
program     -> statement* EOF ;

decleration -> varDecl | statement ;

varDecl     -> "var" IDENTIFIER ( "=" expression )? ";" ;

statement   -> exprStmt | ifStmt | printStmt | block ;

block       -> "{" statement "}" 

ifStmt      -> "if" "(" expression ")" statement ("else" statement) ? ;

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


