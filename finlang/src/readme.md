# Finlang Specs

// TODO(MIGUEL): add multiline comment /* */

## Metasyntax v.02
```c
// TODO(MIGUEL): expand on this to this need to deviate from jlox

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


