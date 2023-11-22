package indy.pseudokod.main;

import indy.pseudokod.ast.Program;
import indy.pseudokod.environment.Environment;
import indy.pseudokod.lexer.Lexer;
import indy.pseudokod.parser.Parser;
import indy.pseudokod.runtime.Interpreter;
import indy.pseudokod.runtime.values.BooleanValue;
import indy.pseudokod.runtime.values.NullValue;
import indy.pseudokod.runtime.values.NumberValue;
import indy.pseudokod.runtime.values.ValueType;
import indy.pseudokod.utils.Utils;

import java.io.*;
import java.util.Scanner;


public class Main {
    static final Parser parser = new Parser();
    static Environment env;

    public static void setupEnvironment() throws Throwable {
        env = new Environment();
        env.declareVariable("true", ValueType.Boolean, true, new BooleanValue(true));
        env.declareVariable("false", ValueType.Boolean, true, new BooleanValue(false));
        env.declareVariable("null", ValueType.NULL, true, new NullValue());
        env.declareVariable("infinity", ValueType.Number, true, new NumberValue(Double.MAX_VALUE));
        env.declareVariable("nieskonczonosc", ValueType.Number, true, new NumberValue(Double.MAX_VALUE));
        env.declareVariable("∞", ValueType.Number, true, new NumberValue(Double.MAX_VALUE));
        env.declareVariable("pi", ValueType.Number, true, new NumberValue(Math.PI));
        env.declareVariable("π", ValueType.Number, true, new NumberValue(Math.PI));
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

//            System.out.println(Utils.stringifyTokens(Lexer.tokenize(input)));
//            System.out.println(Utils.stringifyProgram(program));
            System.out.println(Utils.stringifyRuntimeValue(Interpreter.evaluate(program, env)));
        }
    }

    public static void run(String path) throws Throwable {
        final String code = readFile(path);
        final Program program = parser.produceAST(code);

//        System.out.println(Utils.stringifyTokens(Lexer.tokenize(code)));
//        System.out.println(Utils.stringifyProgram(program));
        System.out.println(Utils.stringifyRuntimeValue(Interpreter.evaluate(program, env)));
    }

    public static void main(String[] args) throws Throwable {
        setupEnvironment();

        run("./test.pk");
//        repl();
    }
}
