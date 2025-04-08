package goldoni.calculadora.calculadora_de_sobras.interfaces;

import goldoni.calculadora.calculadora_de_sobras.ExcelExporter;
import goldoni.calculadora.calculadora_de_sobras.OptimizationResult;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import java.util.Map;

public class ResultScreen {
    public static void showResultsScreen(Stage primaryStage,  String result, Map<Integer, OptimizationResult> optimizationResultMap) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Container principal para os resultados
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(20));

        Text headerText = new Text("Calculation completed successfully!");
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
            System.out.println("Texto copiado para a área de transferência!");
        });

        btnHome.setOnAction(event -> {
            HomeScreen.homeScreen(primaryStage);
        });

        HBox buttonsBox = new HBox(10, btnExportPDF, btnExportExcel, btnCopyText, btnHome);
        buttonsBox.setAlignment(Pos.CENTER);

        // Organizing elements in the VBox
        contentBox.getChildren().addAll(
                headerText,
                scrollPane,
                buttonsBox
        );

        root.setCenter(contentBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Resultados da Calculadora");
        primaryStage.setScene(scene);
        primaryStage.show();
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