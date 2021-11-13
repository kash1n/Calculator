package ru.kashin;

public class TokenNumber extends Token {
    private final double value;

    public TokenNumber(double _value) {
        super(String.valueOf(_value));
        this.value = _value;
    }

    public double get_value() {
        return value;
    }
}
