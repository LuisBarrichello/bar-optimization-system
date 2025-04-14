package goldoni.calculator.bar_optimization_system.exporters;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class PdfExporter {
    public static void exportToPDFWithChooser(Stage stage, String data) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar PDF");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivo PDF (*.pdf)", "*.pdf")
        );

        fileChooser.setInitialFileName("sobras_e_aproveitamento.pdf");

        File selectFile = fileChooser.showSaveDialog(stage);
        if (selectFile != null) {
            generatePDF(data, selectFile);
        }
    }

    public static void generatePDF(String data, File outputFile) throws IOException {
        try (PDDocument document = new PDDocument()) {
            try (
                InputStream fontStream = PdfExporter.class.getResourceAsStream("/fonts/arial/arial.ttf")) {

                PDType0Font font = PDType0Font.load(document, fontStream);

                float margin = 50;
                float yStart = PDRectangle.A4.getHeight() - margin - 20;
                float yPosition = yStart;
                float leading = 16f;
                int pageNumber = 1;

                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                drawHeader(contentStream, font, page, "Relatório de Aproveitamento de Barras");
                contentStream.setFont(font, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yStart);

                List<String> lines = Arrays.asList(data.split("\n"));

                for (String line : lines) {
                    if (yPosition <= margin + 40) {
                        contentStream.endText();
                        drawFooter(contentStream, font, pageNumber++);
                        contentStream.close();

                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(font,12);
                        contentStream.beginText();
                        yPosition = yStart;
                        contentStream.newLineAtOffset(margin, yPosition);
                    }

                    if (line.trim().isEmpty()) {
                        yPosition -= leading;
                        contentStream.newLineAtOffset(0, -leading);
                        continue;
                    }

                    if (line.startsWith("Sobras:") || line.startsWith("Aproveitamento:")) {
                        contentStream.setFont(font, 12);
                        contentStream.showText(" ");
                        yPosition -= leading;
                        contentStream.newLineAtOffset(0, -leading);
                        contentStream.setFont(font, 12);
                        contentStream.showText(line);
                    } else {
                        contentStream.showText(line);
                    }

                    yPosition -= leading;
                    contentStream.newLineAtOffset(0, -leading);
                }
                contentStream.endText();
                drawFooter(contentStream, font, pageNumber);
                contentStream.close();
            }
            document.save(outputFile);
        }
    }

    private static void drawHeader(PDPageContentStream contentStream, PDType0Font font, PDPage page, String title) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, 14);
        contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 40);
        contentStream.showText(title);
        contentStream.endText();
    }

    private static void drawFooter(PDPageContentStream contentStream, PDType0Font font, int pageNumber) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, 10);
        contentStream.newLineAtOffset(50, 20);
        contentStream.showText("Página " + pageNumber);
        contentStream.endText();
    }

}
