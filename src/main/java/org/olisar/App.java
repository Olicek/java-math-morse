package org.olisar;

import org.olisar.mapper.AlphabetMapper;
import org.olisar.mapper.IMapper;
import org.olisar.math.IOperation;
import org.olisar.math.OperationFactory;
import org.olisar.printer.PdfPrinter;
import org.olisar.settings.PropertiesSetting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class App  extends javax.swing.JFrame {

    private javax.swing.JLabel generationResult;
    private javax.swing.JTextField sentenceInput;

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
        sentenceInput = new javax.swing.JTextField();
        javax.swing.JLabel sentenceLabel = new javax.swing.JLabel();
        javax.swing.JButton generateButton = new javax.swing.JButton();
        generationResult = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Generátor příkladů");

        sentenceLabel.setText("Zadej text, ze kterého se má sestavit příklad");
        sentenceLabel.setHorizontalAlignment(0);

        generateButton.setText("Vygeneruj příklad");
        generateButton.addActionListener(this::convertButtonActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(sentenceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sentenceLabel))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(generateButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(generationResult)))
                                .addContainerGap(100, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, sentenceInput, generateButton);

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(sentenceInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sentenceLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(generateButton)
                                        .addComponent(generationResult))
                                .addContainerGap(100, Short.MAX_VALUE))
        );
        pack();
    }

    private void convertButtonActionPerformed(java.awt.event.ActionEvent evt)
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
                .setPdfPath("f://priklady.pdf")
                .build();

        generationResult.setText("PDF bylo úspěšně vygenerováno do souboru: f://priklady.pdf");
    }

}
