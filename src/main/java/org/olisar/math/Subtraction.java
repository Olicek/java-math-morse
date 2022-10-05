package org.olisar.math;

import java.util.concurrent.ThreadLocalRandom;

public class Subtraction implements IOperation {

    private final int result;

    private final int firstNumber;

    private final int secondNumber;

    public Subtraction(int maxLimit, int result) {
        this.result = result;

        // TODO: Nastaveni toho, jestli je nastaveni pro pocet cislic v mensiteli
        int maxSecondNumberDigits = 99;

        int secondNumberCandidate = maxLimit - result;

        if (maxSecondNumberDigits < secondNumberCandidate)
        {
            secondNumberCandidate = maxSecondNumberDigits;
        }

        if (secondNumberCandidate > result)
        {
            secondNumberCandidate = result;
        }

        secondNumber = ThreadLocalRandom.current().nextInt(0, secondNumberCandidate + 1);
        firstNumber = secondNumber + result;
    }

    @Override
    public String sign() {
        return "-";
    }

    @Override
    public int getFirstNumber() {
        return firstNumber;
    }

    @Override
    public int getSecondNumber() {
        return secondNumber;
    }

    @Override
    public int getResult() {
        return result;
    }

    public String toString() {
        return getFirstNumber() + " " + sign() + " " + getSecondNumber() + " = " + getResult();
    }

}
