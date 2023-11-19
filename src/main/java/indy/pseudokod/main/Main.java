package indy.pseudokod.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indy.pseudokod.ast.Program;
import indy.pseudokod.ast.Statement;
import indy.pseudokod.lexer.Lexer;
import indy.pseudokod.lexer.Token;
import indy.pseudokod.parser.Parser;
import indy.pseudokod.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static String readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String code = "";

        while(scanner.hasNextLine()) {
            code += scanner.nextLine();
            if (scanner.hasNextLine()) code += "\n";
        }

        return code;
    }

    public static void repl() throws Throwable {
        final Parser parser = new Parser();
        final Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Pseudokod v1.0-Pre.");

        while(true) {
            System.out.print("> ");
            input = scanner.nextLine();
            final Program program = parser.produceAST(input);

            if(input.equals("exit")) return;

            System.out.println(Utils.stringifyProgram(program));
        }
    }

    public static void main(String[] args) throws Throwable {
        repl();
//        String code = readFile("./test.pk");
//        Parser parser = new Parser();
//
////        printStructure(Lexer.tokenize(code));
//        System.out.println(parser.produceAST(code));
    }
}
