# Metasyntax

```c
// TODO(MIGUEL): expand on this to this need to deviate from jlox

expression -> literal; | unary | binary | grouping

literal    ->                                                                
grouping   ->  "(" | expression | ")";                                       
unary      ->  ("-" | "!") expression;                                       
binary     ->  expression operator expression                                
operator   ->  "==" | "!=" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "/" 
```

# Associativity Table
| name        | operators          | associates |
| :---------- | :----------------- | :--------- |
|Equality     | "==" "!="          |  left      |
|Comparison   | ">"  ">=" "<" "<=" |  left      |
|term         | "-"  "+"           |  left      |
|factor       | "/"  "*"           |  left      |
|unary        | "!"  "-"           |  left      |


