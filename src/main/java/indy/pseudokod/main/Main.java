package indy.pseudokod.main;

import indy.pseudokod.ast.DataDeclaration;
import indy.pseudokod.ast.Program;
import indy.pseudokod.ast.Statement;
import indy.pseudokod.ast.VariableDeclaration;
import indy.pseudokod.environment.Environment;
import indy.pseudokod.lexer.Lexer;
import indy.pseudokod.parser.Parser;
import indy.pseudokod.runtime.Interpreter;
import indy.pseudokod.runtime.values.BooleanValue;
import indy.pseudokod.runtime.values.NullValue;
import indy.pseudokod.runtime.values.NumberValue;
import indy.pseudokod.utils.Utils;

import java.io.*;
import java.util.Scanner;


public class Main {
    static final Parser parser = new Parser();
    static Environment env;

    public static void setupEnvironment() throws Throwable {
        env = new Environment();
        env.declareVariable("true", new BooleanValue(true));
        env.declareVariable("false", new BooleanValue(false));
        env.declareVariable("null", new NullValue());
        env.declareVariable("infinity", new NumberValue(Double.MAX_VALUE));
        env.declareVariable("pi", new NumberValue(Math.PI));
        env.declareVariable("π", new NumberValue(Math.PI));
    }

    public static String readFile(String path) {
        File file = new File(path);
        String code = "";
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            line = reader.readLine();
            do {
                code += line;
                if((line = reader.readLine()) != null) code += "\n";
            } while (line != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;
    }

    public static void repl() throws Throwable {
        final Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Pseudokod v1.0-Pre.");

        while(true) {
            System.out.print("> ");
            input = scanner.nextLine();
            final Program program = parser.produceAST(input);

            if(input.equals("exit")) return;

            System.out.println(Utils.stringifyRuntimeValue(Interpreter.evaluate(program, env)));
        }
    }

    public static void run(String path) throws Throwable {
        final String code = readFile(path);
        final Program program = parser.produceAST(code);

//        System.out.println(Utils.stringifyProgram(program));
        System.out.println(Utils.stringifyRuntimeValue(Interpreter.evaluate(program, env)));
    }

    public static void main(String[] args) throws Throwable {
        setupEnvironment();
        run("./test.pk");
//        repl();
    }
}
