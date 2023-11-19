package indy.pseudokod.main;

import indy.pseudokod.lexer.Lexer;
import indy.pseudokod.lexer.Token;

public class Main {

    public static void printStructure(Token[] tokens) {
        System.out.println("[");
        for(Token token : tokens) {
            System.out.println("\t{\"value\": \"" + token.value() + "\", \"type\": " + token.type() + "},");
        }
        System.out.println("]");
    }

    public static void main(String[] args) throws Throwable {
        String code = """
            dane:
                \tliczba x ∈ (5, 10) := 6,
                \tznak y in (0, 10) <- 5,
                \ttekst z
        """;
        printStructure(Lexer.tokenize(code));
    }
}
