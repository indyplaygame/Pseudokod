package indy.pseudokod.main;

import indy.pseudokod.ast.Program;
import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.InvalidConversionDataTypeException;
import indy.pseudokod.functions.Functions;
import indy.pseudokod.lexer.Lexer;
import indy.pseudokod.parser.Parser;
import indy.pseudokod.runtime.Interpreter;
import indy.pseudokod.runtime.values.*;
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
        env.declareVariable("prawda", ValueType.Boolean, true, new BooleanValue(true));
        env.declareVariable("falsz", ValueType.Boolean, true, new BooleanValue(false));
        env.declareVariable("null", ValueType.NULL, true, new NullValue());
        env.declareVariable("infinity", ValueType.Number, true, new NumberValue(Double.MAX_VALUE));
        env.declareVariable("nieskonczonosc", ValueType.Number, true, new NumberValue(Double.MAX_VALUE));
        env.declareVariable("∞", ValueType.Number, true, new NumberValue(Double.MAX_VALUE));
        env.declareVariable("pi", ValueType.Number, true, new NumberValue(Math.PI));
        env.declareVariable("π", ValueType.Number, true, new NumberValue(Math.PI));

        env.declareVariable("date", ValueType.NativeFunction, true, new NativeFunction(Functions::date));
        env.declareVariable("time", ValueType.NativeFunction, true, new NativeFunction(Functions::time));
        env.declareVariable("czas", ValueType.NativeFunction, true, new NativeFunction(Functions::time));
    }

    public static String readFile(String path) {
        File file = new File(path);
        StringBuilder code = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            line = reader.readLine();
            do {
                code.append(line);
                if((line = reader.readLine()) != null) code.append("\n");
            } while (line != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return code.toString();
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
//        System.out.println(Utils.stringifyRuntimeValue(Interpreter.evaluate(program, env)));
        Interpreter.evaluate(program, env);
    }

    public static void main(String[] args) throws Throwable {
        setupEnvironment();

        run("./test.pk");
//        repl();
    }
}
