package ru.kashin;

import exception.InconsistentNumberOfBracketsException;
import exception.InvalidOperatorPositionException;
import exception.InvalidSymbolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTests {

    @Test
    @DisplayName("Calculator Tests")
    public void testCalculator() {
        assertEquals(3., Calculator.calculate("1 + 2"));
        assertEquals(15., Calculator.calculate("(1 + 2)*4 + 3"));
        assertEquals(10., Calculator.calculate("10/2 + (6*2 - 2)/2"));
        assertEquals(0., Calculator.calculate("10-1-2-3-4"));
        assertEquals(12., Calculator.calculate("10-(1-(2-(3-4)))"));
        assertEquals(9., Calculator.calculate("10 - (1 + 2*3) / 7"));
        try {
            Calculator.calculate("6/2(1+2)");
        } catch (RuntimeException e) {
            // Missing operator before left bracket
            assertTrue(e instanceof InvalidOperatorPositionException);
        }
        try {
            Calculator.calculate("*(1+2)");
        } catch (RuntimeException e) {
            // Operator at start of expression
            assertTrue(e instanceof InvalidOperatorPositionException);
        }
        try {
            Calculator.calculate("2++");
        } catch (RuntimeException e) {
            // Two operators in a row
            assertTrue(e instanceof InvalidOperatorPositionException);
        }
        try {
            Calculator.calculate("2 * (+3*2)");
        } catch (RuntimeException e) {
            // Operator after left bracket
            assertTrue(e instanceof InvalidOperatorPositionException);
        }
        try {
            Calculator.calculate("2 * ((2+3)*2))");
        } catch (RuntimeException e) {
            // Inconsistent number of brackets
            assertTrue(e instanceof InconsistentNumberOfBracketsException);
        }
        try {
            Calculator.calculate("hello");
        } catch (RuntimeException e) {
            // Unknown symbols
            assertTrue(e instanceof InvalidSymbolException);
        }

    }
}
