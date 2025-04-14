package goldoni.calculator.bar_optimization_system.inputFile;

import goldoni.calculator.bar_optimization_system.model.Element;
import goldoni.calculator.bar_optimization_system.optimizers.BarOptimizer;
import goldoni.calculator.bar_optimization_system.optimizers.OptimizationResult;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FilePDF {
    public static Map<Integer, OptimizationResult> openPdfFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file == null) {
            return Collections.emptyMap();
        }

        String text = readFile(file);
        if (text == null) {
            return Collections.emptyMap();
        }

        ExtractData extractor = new ExtractData();
        List<Element> elementList = extractor.extractData(text);

        if (elementList == null || elementList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Documento");
            alert.setHeaderText("Documento Inválido");
            alert.setContentText("O arquivo PDF não contém o formato de romaneio válido.\n" +
                    "Por favor, verifique se o arquivo contém dados no formato:\n" +
                    "número#númeroØnúmero - número");
            alert.showAndWait();
            return Collections.emptyMap();
        }

        Map<Integer, OptimizationResult> optimizationResultMap = BarOptimizer.optimizeUsageByDiameter(elementList);
        return optimizationResultMap;
    }

    public static String readFile(File file) {
        try (PDDocument document = Loader.loadPDF(file);) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
