package indy.pseudokod.main;

import indy.pseudokod.ast.Program;
import indy.pseudokod.environment.Environment;
import indy.pseudokod.functions.Functions;
import indy.pseudokod.parser.Parser;
import indy.pseudokod.runtime.Interpreter;
import indy.pseudokod.runtime.values.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
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
        env.declareVariable("endl", ValueType.String, true, new StringValue("\n"));
        env.declareVariable("Q", ValueType.Range, true, new RangeValue(-Double.MAX_VALUE, Double.MAX_VALUE, false, false));
        env.declareVariable("Z", ValueType.Range, true, new RangeValue(-Double.MAX_VALUE, Double.MAX_VALUE, false, false, (n) -> n == n.intValue()));
        env.declareVariable("Z+", ValueType.Range, true, new RangeValue(0, Double.MAX_VALUE, false, false, (n) -> n == n.intValue()));
        env.declareVariable("Z-", ValueType.Range, true, new RangeValue(-Double.MAX_VALUE, 0, false, false, (n) -> n == n.intValue()));
        env.declareVariable("N", ValueType.Range, true, new RangeValue(0, Double.MAX_VALUE, true, false, (n) -> n == n.intValue()));
        env.declareVariable("N+", ValueType.Range, true, new RangeValue(0, Double.MAX_VALUE, true, false, (n) -> n == n.intValue()));

        env.declareVariable("date", ValueType.NativeFunction, true, new NativeFunction(Functions::date));
        env.declareVariable("time", ValueType.NativeFunction, true, new NativeFunction(Functions::time));
        env.declareVariable("czas", ValueType.NativeFunction, true, new NativeFunction(Functions::time));
        env.declareVariable("random", ValueType.NativeFunction, true, new NativeFunction(Functions::random));
        env.declareVariable("losuj", ValueType.NativeFunction, true, new NativeFunction(Functions::random));
        env.declareVariable("wait", ValueType.NativeFunction, true, new NativeFunction(Functions::wait));
        env.declareVariable("delay", ValueType.NativeFunction, true, new NativeFunction(Functions::wait));
        env.declareVariable("czekaj", ValueType.NativeFunction, true, new NativeFunction(Functions::wait));
        env.declareVariable("push", ValueType.NativeFunction, true, new NativeFunction(Functions::push));
        env.declareVariable("pchnij", ValueType.NativeFunction, true, new NativeFunction(Functions::push));
        env.declareVariable("pop", ValueType.NativeFunction, true, new NativeFunction(Functions::pop));
        env.declareVariable("puknij", ValueType.NativeFunction, true, new NativeFunction(Functions::pop));
        env.declareVariable("size", ValueType.NativeFunction, true, new NativeFunction(Functions::size));
        env.declareVariable("rozmiar", ValueType.NativeFunction, true, new NativeFunction(Functions::size));
        env.declareVariable("empty", ValueType.NativeFunction, true, new NativeFunction(Functions::empty));
        env.declareVariable("pusty", ValueType.NativeFunction, true, new NativeFunction(Functions::empty));
        env.declareVariable("top", ValueType.NativeFunction, true, new NativeFunction(StackValue::top));
        env.declareVariable("szczyt", ValueType.NativeFunction, true, new NativeFunction(StackValue::top));
        env.declareVariable("front", ValueType.NativeFunction, true, new NativeFunction(QueueValue::front));
        env.declareVariable("poczatek", ValueType.NativeFunction, true, new NativeFunction(QueueValue::front));
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

            Interpreter.evaluate(program, env);
            System.out.print("\n");
        }
    }

    public static void run(String path) throws Throwable {
        if(!(new File(path)).exists()) {
            CodeSource source = Main.class.getProtectionDomain().getCodeSource();
            File file = new File(source.getLocation().toURI().getPath());
            String dir = file.getParentFile().getPath();
            path = dir + "\\" + path;
        }

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
        if(args.length > 1 && args[1].equalsIgnoreCase("-debug")) System.out.println("\nCode execution completed with no errors in " + (end - start) / 1000 + " seconds.");
    }
}
