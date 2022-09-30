package org.olisar.printer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.olisar.mapper.IMapper;
import org.olisar.math.IOperation;

import java.io.IOException;
import java.util.Set;

public class PdfPrinter implements IPrinter {

    private final PDDocument document;
    private final PDPageContentStream contentStream;

    public PdfPrinter() throws IOException {
        document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();
        contentStream.showText("Priklady pro Anicku");
        contentStream.endText();

    }

    @Override
    public void printAlphabetToNumbers(IMapper mapper)
    {
        mapper.getMap()
                .forEach((k, v) -> print((k + ":" + v)));
    }

    @Override
    public void printExercises(Set<IOperation> operations)
    {
        operations.forEach(
                (v) -> print(v.getFirstNumber() + " " + v.sign() + " " + v.getSecondNumber() + " = " + v.getResult())
        );

    }
    @Override
    public void printSentence(String sentence)
    {
        try {
            contentStream.beginText();
            contentStream.showText(sentence);
            contentStream.endText();
            contentStream.close();
            document.save("test.pdf");
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void print(String text)
    {
        try {
            contentStream.beginText();
            contentStream.showText(text);
            contentStream.endText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
