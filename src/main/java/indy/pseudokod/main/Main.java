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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        env.declareVariable("random", ValueType.NativeFunction, true, new NativeFunction(Functions::random));
        env.declareVariable("losuj", ValueType.NativeFunction, true, new NativeFunction(Functions::random));
        env.declareVariable("wait", ValueType.NativeFunction, true, new NativeFunction(Functions::wait));
        env.declareVariable("delay", ValueType.NativeFunction, true, new NativeFunction(Functions::wait));
        env.declareVariable("czekaj", ValueType.NativeFunction, true, new NativeFunction(Functions::wait));
    }

    public static String readFile(String path) throws IOException {
        Path filePath = Paths.get(path);
        return Files.readString(filePath);
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

        Interpreter.evaluate(program, env);
    }

    public static void main(String[] args) throws Throwable {
        double start = System.currentTimeMillis();
        setupEnvironment();

        if(args.length > 0) run(args[0]);
        else repl();

        double end = System.currentTimeMillis();
        System.out.println("Code execution completed with no errors in " + (end - start) / 1000 + " seconds.");
    }
}
