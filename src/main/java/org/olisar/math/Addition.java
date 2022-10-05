package org.olisar.math;

import java.util.concurrent.ThreadLocalRandom;

public class Addition implements IOperation {

    private final int result;

    private final int firstNumber;

    private final int secondNumber;

    public Addition(int result) {
        this.result = result;
        this.firstNumber = ThreadLocalRandom.current().nextInt(0, result + 1);
        this.secondNumber = this.result - firstNumber;
    }

    @Override
    public String sign() {
        return "+";
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
