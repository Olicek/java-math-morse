package org.olisar.printer;

import com.itextpdf.text.DocumentException;
import org.olisar.mapper.IMapper;
import org.olisar.math.IOperation;

import java.util.List;

public interface IPrinter {

    void printAlphabetToNumbers(IMapper mapper) throws DocumentException;

    void printExercises(List<IOperation> operations) throws DocumentException;

    void printSentence(String sentence);
}
