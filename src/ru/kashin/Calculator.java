package ru.kashin;

import exception.InconsistentNumberOfBracketsException;
import exception.InvalidOperatorPositionException;
import exception.InvalidSymbolException;

import java.util.ArrayDeque;
import java.util.Stack;

public class Calculator {
    public static double calculate (String str) {

        ArrayDeque<Token> tokens = parse(str);
        ArrayDeque<Token> tokensSorted = shuntingYard(tokens);
//        System.out.println("RPN:");
//        for (Token t : tokensSorted) {
//            System.out.print(t.expr() + " ");
//        }
//        System.out.println();

        Stack<TokenNumber> stack = new Stack<>();
        for (Token t : tokensSorted) {
            if (t instanceof TokenNumber number) {
                stack.push(number);
            } else if (t instanceof TokenOperation op) {
                TokenNumber tn1 = stack.pop();
                TokenNumber tn2 = stack.pop();
                stack.push (op.apply(tn2, tn1));
            }
        }
        return stack.pop().get_value();
    }

    private static ArrayDeque<Token> parse (String str) {
        String strCleared = str.replaceAll("\\s+","");

        ArrayDeque<Token> tokens = new ArrayDeque<>();

        int leftBracketsCount = 0;
        int rightBracketsCount = 0;

        String currNumber = "";
        for (int i = 0; i < strCleared.length(); i++) {
            char c = strCleared.charAt(i);
            if (c == '(') {
                if (i != 0 && !isOperator(strCleared.charAt(i - 1)) && strCleared.charAt(i - 1) != '(') {
                    throw new InvalidOperatorPositionException("Missing operator before left bracket");
                }

                leftBracketsCount++;
                tokens.add(new TokenBracket (String.valueOf(c)));
                continue;
            }
            if (c == ')') {
                if (currNumber.length() != 0) {
                    tokens.add(new TokenNumber(Double.parseDouble(currNumber)));
                    currNumber = "";
                }

                rightBracketsCount++;
                tokens.add(new TokenBracket (String.valueOf(c)));
                continue;
            }

            if (isOperator(c)) {
                if (i == 0)
                    throw new InvalidOperatorPositionException("Operator at start of expression");
                if (isOperator(strCleared.charAt(i - 1)))
                    throw new InvalidOperatorPositionException("Two operators in a row");
                if (strCleared.charAt(i - 1) == '(')
                    throw new InvalidOperatorPositionException("Operator after left bracket");

                if (currNumber.length() != 0) {
                    tokens.add(new TokenNumber(Double.parseDouble(currNumber)));
                    currNumber = "";
                }

                tokens.add(new TokenOperation (String.valueOf(c)));
                continue;
            }

            if ((c >= 48 && c <= 57) || c == '.') {
                currNumber = currNumber + c;
            } else {
                throw new InvalidSymbolException("Invalid symbol: " + c);
            }
        }
        if (currNumber.length() != 0) {
            tokens.add(new TokenNumber(Double.parseDouble(currNumber)));
        }
        if (leftBracketsCount != rightBracketsCount)
            throw new InconsistentNumberOfBracketsException();

        return tokens;
    }

    private static boolean isOperator (char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    private static ArrayDeque<Token> shuntingYard (ArrayDeque<Token> tokens) {
        ArrayDeque<Token> output = new ArrayDeque<>();
        Stack<Token> stack = new Stack<>();

        for (Token t : tokens) {
            if (t instanceof TokenNumber) {
                output.add(t);
            } else if (t instanceof TokenOperation op1) {
                while (!stack.empty()
                        && stack.peek() instanceof TokenOperation op2
                        && op2.priority() >= op1.priority()) {
                    output.add(stack.pop());
                }
                stack.push(op1);
            } else if (t instanceof TokenBracket tb && tb.isLeftBracket()) {
                stack.push(tb);
            } else if (t instanceof TokenBracket tb && tb.isRightBracket()) {
                while (!(stack.peek() instanceof TokenBracket bracket && bracket.isLeftBracket())) {
                    if (stack.isEmpty())
                        throw new InconsistentNumberOfBracketsException();
                    output.add(stack.pop());
                }
                stack.pop();
            }
        }

        while (!stack.empty()) {
            if (stack.peek() instanceof TokenBracket)
                throw new InconsistentNumberOfBracketsException();
            output.add(stack.pop());
        }

        return output;
    }
}
