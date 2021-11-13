package ru.kashin;

public class Token {
    String _expression;

    public Token(String expr) {
        this._expression = expr;
    }

    public String expr() {
        return _expression;
    }
}
