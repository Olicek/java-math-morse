package org.olisar.printer;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.apache.commons.collections4.ListUtils;
import org.olisar.mapper.IMapper;
import org.olisar.math.IOperation;

import java.io.FileOutputStream;
import java.util.List;

public class PdfPrinter implements IPrinter {

    public static final String BODONIBLACK = "src/main/resources/fonts/times.ttf";

//    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

    private static Font titleFont;

    private static Font regularFont;

//    private static final Font REGULAR_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);

    private Document document;

    private PdfPrinter(PdfPrinterBuilder builder) {


        try {
            BaseFont baseFont = BaseFont.createFont(BODONIBLACK, BaseFont.WINANSI, BaseFont.EMBEDDED);
            titleFont = new Font(baseFont, 18, Font.BOLD);
            regularFont = new Font(baseFont, 16, Font.BOLD);

            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(builder.pdfPath));
            document.open();
            addMetaData(document);
            printAlphabetToNumbers(builder.mapper);
            printExercises(builder.operations);
            printResultLine();

            document.close();
        } catch (Exception e) {
            System.out.println("e = " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void printAlphabetToNumbers(IMapper mapper) throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);

        Anchor anchor = new Anchor("Hon za pokladem", titleFont);
        anchor.setName("Hon za pokladem");

        // Second parameter is the number of the chapter
        Paragraph catPart = new Paragraph(anchor);

        Paragraph mapperParagramp = new Paragraph("Mapování čísel", regularFont);
        catPart.add(mapperParagramp);

        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 1);
        catPart.add(paragraph);

        // add a table
        createMapperTable(catPart, mapper);
        // now add all this to the document
        document.add(catPart);
    }

    @Override
    public void printExercises(List<IOperation> operations) throws DocumentException {
        // Second parameter is the number of the chapter
        Chapter exercisesChapter = new Chapter(new Paragraph("Příklady"), 1);

        Paragraph exercises = new Paragraph("Příklady", regularFont);

        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 1);
        exercises.add(paragraph);

        // add a table
        PdfPTable table = new PdfPTable(3);
        int columnSize = operations.size()/3;
        if (columnSize % 3 != 0)
        {
            columnSize = columnSize + 1;
        }

        List<List<IOperation>> partitionOperations = ListUtils.partition(operations, columnSize);

        for (List<IOperation> columns:partitionOperations)
        {
            PdfPTable column = new PdfPTable(1);
            column.getDefaultCell().setBorder(0);
            columns.forEach(
                    (v) -> column.addCell(v.getFirstNumber() + " " + v.sign() + " " + v.getSecondNumber() + " = ")
            );
            table.addCell(column);
        }

        table.completeRow();
        exercises.add(table);
        exercisesChapter.add(exercises);

        document.add(exercises);
    }

    public void printResultLine() throws DocumentException {
        Paragraph space = new Paragraph();
        addEmptyLine(space, 3);
        document.add(space);

        Paragraph paragraph = new Paragraph("Výsledek");
        addEmptyLine(paragraph, 1);
        document.add(paragraph);

        LineSeparator dashedLine = new LineSeparator(titleFont);
        document.add(dashedLine);
    }

    @Override
    public void printSentence(String sentence) {

    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Matematické příklady s tajenkou");
        document.addSubject("Matematické příklady s tajenkou");
        document.addKeywords("matematika, příklady, počítání, tajenka, šifrování");
        document.addAuthor("Petr Olišar");
        document.addCreator("Petr Olišar");
    }

    private static void createMapperTable(Paragraph subCatPart, IMapper mapper) {
        PdfPTable table = new PdfPTable(3);
        mapper.getMap()
                .forEach((k, v) -> table.addCell(k + ":" + v));
        table.completeRow();
        subCatPart.add(table);
    }

    private static void addEmptyLine(Paragraph paragraph, int lines) {
        for (int i = 0; i < lines; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static class PdfPrinterBuilder {

        private IMapper mapper;

        private List<IOperation> operations;

        private String pdfPath;

        public PdfPrinterBuilder setAlphabetToNumbers(IMapper mapper) {
            this.mapper = mapper;
            return this;
        }

        public PdfPrinterBuilder setExercises(List<IOperation> operations) {
            this.operations = operations;
            return this;
        }

        public PdfPrinterBuilder setPdfPath(String pdfPath) {
            this.pdfPath = pdfPath;
            return this;
        }

        public void build() {
            new PdfPrinter(this);
        }
    }


}
