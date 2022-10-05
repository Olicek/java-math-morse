package org.olisar;

import com.itextpdf.text.DocumentException;
import org.olisar.mapper.AlphabetMapper;
import org.olisar.mapper.IMapper;
import org.olisar.math.IOperation;
import org.olisar.math.OperationFactory;
import org.olisar.printer.ConsolePrinter;
import org.olisar.printer.PdfPrinter;
import org.olisar.printer.IPrinter;
import org.olisar.settings.PropertiesSetting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Hello world!
 *
 */
public class Console
{
    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String ANSI_YELLOW = "\u001B[31m";

    /**
     * Pouziti java -jar c:/cesta/k/souboru/priklady.pdf
     * @param args parametr cesta k souboru
     */
    public static void main( String[] args ) {
        try {
            if (args.length == 0)
            {
                System.out.println(ANSI_YELLOW
                        + "Nebyla zadana cesta k PDF souboru."
                        + ANSI_RESET);
                System.exit(1);
            }

            String pdfPath = args[0];

            String[] sentence = introduction();

            String[] allowedSigns = PropertiesSetting.getAllowedSigns();

            IMapper mapper = new AlphabetMapper(allowedSigns);

            List<IOperation> operations = new ArrayList<>();

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

            new PdfPrinter.PdfPrinterBuilder()
                    .setAlphabetToNumbers(mapper)
                    .setExercises(operations)
                    .setPdfPath(pdfPath)
                    .build();

        } catch (IOException e) {
            System.out.println("Došlo k pěkně blbý chybě");
        } catch (DocumentException e)
        {
            System.out.println("Došlo k chybě při sestavení PDF");
        }
    }

    private static String[] introduction() throws IOException {
        System.out.println("Zadej větu, ze které se má sestavit příklad:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        return reader.readLine().toUpperCase().split("");
    }
}
