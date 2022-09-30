package org.olisar.printer;

import org.olisar.mapper.IMapper;
import org.olisar.math.IOperation;

import java.util.Set;

public class ConsolePrinter implements IPrinter {

    @Override
    public void printAlphabetToNumbers(IMapper mapper)
    {
        mapper.getMap()
            .forEach((k, v) -> System.out.println((k + ":" + v)));
    }

    @Override
    public void printExercises(Set<IOperation> operations)
    {
        operations.forEach(
                (v) -> System.out.println(v.getFirstNumber() + " " + v.sign() + " " + v.getSecondNumber() + " = " + v.getResult())
        );
    }
    @Override
    public void printSentence(String sentence)
    {
        System.out.println(sentence);
    }

}
