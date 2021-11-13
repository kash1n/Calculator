package ru.kashin;

public class TokenBracket extends Token {
    boolean leftBracket;

    public TokenBracket(String expr) {
        super(expr);
        if (expr.equals("("))
            this.leftBracket = true;
        else if (expr.equals(")"))
            this.leftBracket = false;
    }

    public boolean isLeftBracket() {
        return leftBracket;
    }
    public boolean isRightBracket() {
        return !leftBracket;
    }
}
