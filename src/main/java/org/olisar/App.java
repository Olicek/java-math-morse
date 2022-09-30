package org.olisar;

import org.olisar.mapper.AlphabetMapper;
import org.olisar.mapper.IMapper;
import org.olisar.math.IOperation;
import org.olisar.math.OperationFactory;
import org.olisar.printer.ConsolePrinter;
import org.olisar.printer.IPrinter;
import org.olisar.settings.PropertiesSetting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            String[] sentence = introduction();

            String[] allowedSigns = PropertiesSetting.getAllowedSigns();

            IMapper mapper = new AlphabetMapper(allowedSigns);

            Set<IOperation> operations = new HashSet<>();

            for (String letter : sentence) {
                int number = mapper.assignNumber(letter);
                operations.add(OperationFactory.create(number));
            }

//            Arrays.stream(test)
//                    .map(mapper::assignNumber)
//                    .map(OperationFactory::create)
//                    .forEach(System.out::println);


            IPrinter printer = new ConsolePrinter();
            printer.printAlphabetToNumbers(mapper);
            printer.printExercises(operations);
            printer.printSentence(Arrays.toString(sentence));

        } catch (IOException e) {
            System.out.println("Došlo k pěkně blbý chybě");
        }
    }

    private static String[] introduction() throws IOException {
        System.out.println("Zadej větu, ze které se má sestavit příklad:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        return reader.readLine().toUpperCase().split("");
    }
}
