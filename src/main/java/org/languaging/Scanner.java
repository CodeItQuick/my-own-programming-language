package org.languaging;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.languaging.TokenType.*; // [static-import]

class Scanner {
    //> keyword-map
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    AND);
        keywords.put("class",  CLASS);
        keywords.put("else",   ELSE);
        keywords.put("false",  FALSE);
        keywords.put("for",    FOR);
        keywords.put("fun",    FUN);
        keywords.put("if",     IF);
        keywords.put("nil",    NIL);
        keywords.put("or",     OR);
        keywords.put("print",  PRINT);
        keywords.put("return", RETURN);
        keywords.put("super",  SUPER);
        keywords.put("this",   THIS);
        keywords.put("true",   TRUE);
        keywords.put("var",    VAR);
        keywords.put("while",  WHILE);
    }
    //< keyword-map
    private final List<Token> tokens = new ArrayList<>();
    final ScanState scanState;
    //> scan-state
//< scan-state

    Scanner(String source) {
        scanState = new ScanState(source);
    }
    //> scan-tokens
    List<Token> scanTokens() {
        while (!scanState.isAtEnd()) {
            // We are at the beginning of the next lexeme.
            scanState.start = scanState.current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, scanState.line));
        return tokens;
    }
    //< scan-tokens
//> scan-token
    void scanToken() {
        char c = scanState.advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break; // [slash]
//> two-char-tokens
            case '!':
                addToken(scanState.match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(scanState.match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(scanState.match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(scanState.match('=') ? GREATER_EQUAL : GREATER);
                break;
//< two-char-tokens
//> slash
            case '/':
                if (scanState.match('/')) {
                    // A comment goes until the end of the scanState.line.
                    while (scanState.peek() != '\n' && !scanState.isAtEnd()) scanState.advance();
                } else {
                    addToken(SLASH);
                }
                break;
//< slash
//> whitespace

            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;

            case '\n':
                scanState.line++;
                break;
//< whitespace
//> string-scanState.start

            case '"': string(); break;
//< string-scanState.start
//> char-error

            default:
/* Scanning char-error < Scanning digit-scanState.start
        Lox.error(scanState.line, "Unexpected character.");
*/
//> digit-scanState.start
                if (isDigit(c)) {
                    number();
//> identifier-scanState.start
                } else if (isAlpha(c)) {
                    identifier();
//< identifier-scanState.start
                } else {
                    Lox.error(scanState.retrieveLineNumber(), "Unexpected character.");
                }
//< digit-scanState.start
                break;
//< char-error
        }
    }

    //< scan-token
//> identifier
    private void identifier() {
        String text = scanState.extractIdentifier();
        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);
//< keyword-type
    }

    //< identifier
//> number
    private void number() {
        scanState.extractDigit();

        addToken(NUMBER,
                Double.parseDouble(scanState.source.substring(scanState.start, scanState.current)));
    }

    //< number
//> string
    private void string() {
        while (scanState.peek() != '"' && !scanState.isAtEnd()) {
            if (scanState.peek() == '\n') scanState.line++;
            scanState.advance();
        }

        if (scanState.isAtEnd()) {
            Lox.error(scanState.line, "Unterminated string.");
            return;
        }

        // The closing ".
        scanState.advance();

        // Trim the surrounding quotes.
        String value = scanState.source.substring(scanState.start + 1, scanState.current - 1);
        addToken(STRING, value);
    }

    //< peek-next
//> is-alpha
    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    public static boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
    //< is-alpha
//> is-digit
    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    } // [is-digit]

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = scanState.source.substring(scanState.start, scanState.current);
        tokens.add(new Token(type, text, literal, scanState.line));
    }

    public List<Token> retrieveTokens() {
        return tokens;
    }
//< advance-and-add-token
}