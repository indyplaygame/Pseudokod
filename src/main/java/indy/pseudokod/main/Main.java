package indy.pseudokod.main;

import indy.pseudokod.ast.Program;
import indy.pseudokod.environment.Environment;
import indy.pseudokod.exceptions.DataTypeMismatchException;
import indy.pseudokod.exceptions.VariableDeclaredException;
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
    private static Environment env;

    /**
     * This function sets up the environment for the interpreter,
     * declares and initializes various built-in variables and functions,
     * such as boolean values, numbers, strings, ranges, and native functions.
     *
     * @throws VariableDeclaredException If a variable with the same name is already declared.
     * @throws DataTypeMismatchException If there is a data type mismatch.
     */
    public static void setupEnvironment() throws VariableDeclaredException, DataTypeMismatchException {
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

    /**
     * Reads the content of a file located at the specified path.
     *
     * @param path The path to the file to be read.
     * @return A {@link String} containing the content of the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static String readFile(String path) throws IOException {
        Path filePath = Paths.get(path);
        return Files.readString(filePath);
    }

    /**
     * This function starts a Read-Eval-Print Loop (REPL) for the Pseudokod interpreter.
     * The REPL allows user to interactively enter and execute Pseudokod code.
     *
     * @throws Throwable If an error occurs during the execution of the REPL.
     */
    public static void repl() throws Throwable {
        final Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Pseudokod v1.0-Pre.");

        while(true) {
            System.out.print("> ");
            input = scanner.nextLine();

            final Parser parser = new Parser();
            final Program program = parser.produceAST(input);

            if(input.equals("exit")) return;

            Interpreter.evaluate(program, env);
            System.out.print("\n");
        }
    }

    /**
     * This function runs the Pseudokod program located at the specified file path.
     * If the file does not exist in the given path, the function attempts to locate it in the directory of the interpreter.
     *
     * @param path The path to the Pseudokod file to be interpreted.
     * @throws Throwable If an error occurs during the execution of the Pseudokod program.
     */
    public static void run(String path) throws Throwable {
        if(!(new File(path)).exists()) {
            CodeSource source = Main.class.getProtectionDomain().getCodeSource();
            File file = new File(source.getLocation().toURI().getPath());
            String dir = file.getParentFile().getPath();
            path = dir + "\\" + path;
        }

        final Parser parser = new Parser();
        final String code = readFile(path);
        final Program program = parser.produceAST(code);

        Interpreter.evaluate(program, env);
    }

    /**
     * The main entry point of the Pseudokod interpreter.
     * This function initializes the environment, runs the Pseudokod program, and provides a Read-Eval-Print Loop (REPL) for interactive execution.
     *
     * @param args Command-line arguments. The first argument can be the path to a Pseudokod file to be executed.
     *             If no argument is provided, the interpreter starts a REPL.
     *             The second argument, if present, should be "-debug" to display the execution time.
     * @throws Throwable If an error occurs during the execution of the Pseudokod program or the REPL.
     */
    public static void main(String[] args) throws Throwable {
        double start = System.currentTimeMillis();
        setupEnvironment();

        if(args.length > 0) run(args[0]);
        else repl();

        double end = System.currentTimeMillis();
        if(args.length > 1 && args[1].equalsIgnoreCase("-debug")) System.out.println("\nCode execution completed with no errors in " + (end - start) / 1000 + " seconds.");
    }
}
