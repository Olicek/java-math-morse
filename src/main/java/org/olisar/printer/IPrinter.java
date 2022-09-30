package org.olisar.printer;

import org.olisar.mapper.IMapper;
import org.olisar.math.IOperation;

import java.util.Set;

public interface IPrinter {

    void printAlphabetToNumbers(IMapper mapper);

    void printExercises(Set<IOperation> operations);

    void printSentence(String sentence);
}
