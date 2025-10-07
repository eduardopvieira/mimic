package br.edu.ufersa.mimic.temporary;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;

public class PdfFieldFinder {

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/pdfs/dnd_2024_character_sheet.pdf");

        try (PDDocument pdfDocument = Loader.loadPDF(file)) {
            PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

            if (acroForm != null) {
                System.out.println("--- Nomes dos Campos no PDF ---");
                for (PDField field : acroForm.getFields()) {
                    System.out.println(field.getFullyQualifiedName());
                }
                System.out.println("-----------------------------");
            } else {
                System.out.println("Este PDF não contém um formulário AcroForm.");
            }
        }
    }
}
