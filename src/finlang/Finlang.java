// TODO(MIGUEL): create language meta syntax
// TODO(MIGUEL): write scanner
// TODO(MIGUEL): write parser
// TODO(MIGUEL): find out what to do next

///!!!! MAIN !!!!
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
