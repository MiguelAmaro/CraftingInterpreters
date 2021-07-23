package com.finlang.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst
{
    public static void main(String[] args) throws IOException
    {
        // NOTE(MIGUEL): output directory is fixed
        /*if (args.length != 1)
        {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        */
        String outputDir = ".\\src\\";
        
        try
        {
            
            defineAst(outputDir, "FLExpr", Arrays.asList("Assign   : FLToken name, FLExpr value",
                                                         "Binary   : FLExpr left, FLToken operator, FLExpr right",
                                                         "Grouping : FLExpr expression",
                                                         "Literal  : Object value",
                                                         "Logical  : FLExpr left, FLToken operator, FLExpr right",
                                                         "Unary    : FLToken operator, FLExpr right",
                                                         "Variable : FLToken name" ));
            
            defineAst(outputDir, "FLStmt", Arrays.asList("Block      : List<FLStmt> statements",
                                                         "Expression : FLExpr expression",
                                                         "If         : FLExpr condition, FLStmt thenBranch," + 
                                                         " FLStmt elseBranch",
                                                         "Print      : FLExpr expression",
                                                         "Var        : FLToken name, FLExpr initializer",
                                                         "While      : FLExpr condition, FLStmt body"));
        }
        catch(IOException e)
        {
            System.out.println(e);
            System.out.println("Working Directory: " +
                               System.getProperty("user.dir"));
        }
    }
    
    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException 
    {
        String      path   = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        
        writer.println("package com.finlang.lang;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + "\n{");
        
        defineVisitor(writer, baseName, types);
        
        for (String type : types)
        {
            String className = type.split(":")[0].trim();
            String fields    = type.split(":")[1].trim(); 
            defineType(writer, baseName, className, fields);
        }
        
        writer.println();
        writer.println("  abstract <R> R accept(Visitor<R> visitor);");
        
        writer.println("}\n");
        writer.close();
    }
    
    private static void defineType(PrintWriter writer, String baseName,
                                   String className, String fieldList)
    {
        writer.println("  static class " + className + " extends " +
                       baseName + "\n{");
        
        // Constructor.
        writer.println("    " + className + "(" + fieldList + ")\n{");
        
        // Store parameters in fields.
        String[] fields = fieldList.split(", ");
        
        for (String field : fields)
        {
            String name = field.split(" ")[1];
            writer.println("      this." + name + " = " + name + ";");
        }
        
        writer.println("    }\n");
        
        // Visitor pattern.
        writer.println();
        writer.println("    @Override");
        writer.println("    <R> R accept(Visitor<R> visitor)\n{");
        writer.println("      return visitor.visit" +
                       className + baseName + "(this);");
        writer.println("    }");
        
        // Fields.
        writer.println();
        for (String field : fields)
        {
            writer.println("    final " + field + ";");
        }
        
        writer.println("  }\n");
    }
    
    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types)
    {
        writer.println("  interface Visitor<R>\n{");
        
        for (String type : types)
        {
            String typeName = type.split(":")[0].trim();
            writer.println("    R visit" +
                           typeName + baseName + "(" +
                           typeName + " " + baseName.toLowerCase() + ");");
        }
        
        writer.println("  }\n");
    }
}