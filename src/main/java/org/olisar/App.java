package org.olisar;

import org.olisar.mapper.AlphabetMapper;
import org.olisar.mapper.IMapper;
import org.olisar.math.IOperation;
import org.olisar.math.OperationFactory;
import org.olisar.printer.PdfPrinter;
import org.olisar.settings.PropertiesSetting;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App  extends JFrame {

    private JLabel generationResult;
    private JTextField sentenceInput;
    private JTextField pdfInput;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new App().setVisible(true));
    }

    public App() {
        pack();
        // make the frame half the height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        setSize(width/2, height/2);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        sentenceInput = new JTextField();
        pdfInput = new JTextField();
        JLabel sentenceLabel = new JLabel();
        JButton generateButton = new JButton();
        generationResult = new JLabel();

        JLabel pdfLabel = new JLabel();
        JButton pdfButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Generátor příkladů");

        sentenceLabel.setText("Zadej text, ze kterého se má sestavit příklad");
        sentenceLabel.setHorizontalAlignment(0);

        generateButton.setText("Vygeneruj příklad");
        generateButton.addActionListener(this::convertButtonActionPerformed);

        pdfLabel.setText("Adresář, kde bude PDF vytvořeno");
        pdfLabel.setHorizontalAlignment(0);

        pdfButton.setText("Vyber adresář");
        pdfButton.addActionListener(this::pickDirectory);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(pdfInput, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pdfLabel))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(pdfButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pdfLabel))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(sentenceInput, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sentenceLabel))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(generateButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(generationResult)))
                                .addContainerGap(100, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, sentenceInput, generateButton);

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(pdfInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pdfLabel))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(pdfButton)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pdfLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(sentenceInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sentenceLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(generateButton)
                                        .addComponent(generationResult))
                                .addContainerGap(100, Short.MAX_VALUE))
        );
        pack();
    }

    private void pickDirectory(ActionEvent actionEvent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Vyber složku nebo sobour");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".pdf");
                }
            }

            @Override
            public String getDescription() {
                return "PDF dokumenty (*.pdf)";
            }
        });
        chooser.showSaveDialog(null);

        try {
            this.pdfInput.setText(chooser.getSelectedFile().getAbsolutePath());
        } catch (NullPointerException e) {
            System.out.println("Nebyl vybran zadny adresar");
        }
    }

    private void convertButtonActionPerformed(ActionEvent evt)
    {
        String[] sentence = sentenceInput.getText().toUpperCase().split("");

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

        new PdfPrinter.PdfPrinterBuilder()
                .setAlphabetToNumbers(mapper)
                .setExercises(operations)
                .setPdfPath(pdfInput.getText())
                .build();

        generationResult.setText("PDF bylo úspěšně vygenerováno do souboru: " + pdfInput.getText());
    }

}
