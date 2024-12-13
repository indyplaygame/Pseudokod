/**
 * The {@link indy.pseudokod.lexer} package provides the classes and utilities necessary
 * for lexical analysis of Pseudokod programs.
 *
 * <p>The lexer, also known as a lexical analyzer or tokenizer, is responsible for breaking
 * down the source code of Pseudokod programs into meaningful lexical units called tokens.
 * These tokens serve as the basic building blocks for syntactic analysis (parsing) and
 * program execution in the interpreter.</p>
 *
 * <p>Main Responsibilities:</p>
 * <ul>
 *   <li>Reading raw source code and converting it into a sequence of tokens.</li>
 *   <li>Identifying different types of tokens, such as keywords, identifiers, literals,
 *       operators, and symbols.</li>
 *   <li>Handling whitespace, comments, and other non-executable elements in the source code.</li>
 *   <li>Providing error reporting for invalid or unrecognizable sequences.</li>
 * </ul>
 *
 * <p>Key Components:</p>
 * <ul>
 *   <li>Lexer Engine: Handles the core logic of breaking source code into tokens.</li>
 *   <li>Token Definitions: Contains representations for various token types,
 *       such as {@link indy.pseudokod.lexer.TokenType#Identifier}, {@link indy.pseudokod.lexer.TokenType#Number},
 *       or {@link indy.pseudokod.lexer.TokenType#BinaryOperator}.</li>
 *   <li>Error Handling: Provides mechanisms for reporting lexical errors,
 *       such as unclosed strings or unknown characters.</li>
 * </ul>
 *
 * <p>By converting source code into structured tokens, this package enables the smooth
 * operation of subsequent phases in the Pseudokod interpreter pipeline, such as parsing
 * and code execution.</p>
 *
 * @see indy.pseudokod.lexer.Lexer
 * @see indy.pseudokod.lexer.Token
 * @see indy.pseudokod.lexer.TokenType
 */
package indy.pseudokod.lexer;