package goldoni.calculator.bar_optimization_system.interfaces;

import goldoni.calculator.bar_optimization_system.exporters.ExcelExporter;
import goldoni.calculator.bar_optimization_system.optimizers.OptimizationResult;
import goldoni.calculator.bar_optimization_system.exporters.PdfExporter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.Map;

public class ResultScreen {
    public static void showResultsScreen(Stage primaryStage,  String result, Map<Integer, OptimizationResult> optimizationResultMap) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(20));

        Text headerText = new Text("Cálculo concluído com êxito!");
        headerText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 18));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        TextFlow textFlow = new TextFlow();
        textFlow.setTextAlignment(TextAlignment.LEFT);

        String[] sections = result.split("\n\n");

        for (String section : sections) {
            Text sectionText = new Text(section + "\n");

            if (section.startsWith("Sobras:") || section.startsWith("Aproveitamento:")) {
                sectionText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 18));
            }
            else {
                sectionText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
            }

            textFlow.getChildren().add(sectionText);
        }

        scrollPane.setContent(textFlow);

        Button btnExportPDF = createButton("Exportar PDF");
        Button btnExportExcel = createButton("Exportar Excel");
        Button btnCopyText = createButton("Copiar texto");
        Button btnHome = createButton("Home");

        btnExportPDF.setOnAction(event -> {
            System.out.println("Exporting to PDF...");
            try {
                PdfExporter.exportToPDFWithChooser(primaryStage, result);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error exporting to PDF: " + e.getMessage());
            }
        });

        btnExportExcel.setOnAction(event -> {
            ExcelExporter.exportToExcel(primaryStage, optimizationResultMap);
            System.out.println("Exporting to Excel...");
        });

        btnCopyText.setOnAction(event -> {
            //Object StringSelection with the text
            StringSelection stringSelection = new StringSelection(result);

            // get system clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            //defined the content in clipboard
            clipboard.setContents(stringSelection, null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Texto copiado!");
            alert.setHeaderText("Texto copiado!");
            alert.setContentText("Texto copiado para a área de transferência!");
            alert.showAndWait();
        });

        btnHome.setOnAction(event -> {
            HomeScreen.homeScreen(primaryStage);
        });

        HBox buttonsBox = new HBox(10, btnExportPDF, btnExportExcel, btnCopyText, btnHome);
        buttonsBox.setAlignment(Pos.CENTER);

        contentBox.getChildren().addAll(
                headerText,
                scrollPane,
                buttonsBox
        );

        root.setCenter(contentBox);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Resultados da Calculadora");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    private static Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("""
            -fx-background-color: #156EB1;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 10 20 10 20;
            -fx-border-radius: 5;
            -fx-cursor: hand;
        """);
        return button;
    }
}