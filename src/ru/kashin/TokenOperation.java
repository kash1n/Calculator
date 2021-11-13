package ru.kashin;

public class TokenOperation extends Token {
    public enum OperationType { ADD, SUBTRACT, MULTIPLY, DIVIDE };
    private OperationType opType;

    public TokenOperation(String expr) {
        super(expr);
        switch (expr) {
            case "+" -> opType = OperationType.ADD;
            case "-" -> opType = OperationType.SUBTRACT;
            case "*" -> opType = OperationType.MULTIPLY;
            case "/" -> opType = OperationType.DIVIDE;
        }
    }

    public TokenNumber apply(TokenNumber lhs, TokenNumber rhs) {
        double lValue = lhs.get_value();
        double rValue = rhs.get_value();
        return switch (opType) {
            case ADD -> new TokenNumber(lValue + rValue);
            case SUBTRACT -> new TokenNumber(lValue - rValue);
            case MULTIPLY -> new TokenNumber(lValue * rValue);
            case DIVIDE -> new TokenNumber(lValue / rValue);
        };
    }

    public int priority () {
        return opType.ordinal();
    }
}
