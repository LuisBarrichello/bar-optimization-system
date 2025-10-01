package calculator.bar_optimization_system.controller;

import calculator.bar_optimization_system.inputFile.FilePDF;
import calculator.bar_optimization_system.interfaces.ResultScreen;
import calculator.bar_optimization_system.optimizers.BarOptimizer;
import calculator.bar_optimization_system.optimizers.OptimizationResult;
import javafx.stage.Stage;

import java.util.Map;

public class MainController {

    private final Stage primaryStage;

    public MainController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void processFile() {
        Map<Integer, OptimizationResult> optimizationResultMap = FilePDF.openPdfFile(primaryStage);

        if (optimizationResultMap == null || optimizationResultMap.isEmpty()) {
            System.out.println("No file was selected or the file is empty.");
            return;
        }

        String resultText = BarOptimizer.formatOptimizationResults(optimizationResultMap);

        if (resultText != null) {
            ResultScreen.showResultsScreen(primaryStage, resultText, optimizationResultMap);
        } else {
            System.out.println("Error processing the PDF file and generating results.");
        }
    }
}
