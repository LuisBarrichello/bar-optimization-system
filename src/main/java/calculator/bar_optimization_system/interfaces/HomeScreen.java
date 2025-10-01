package calculator.bar_optimization_system.interfaces;

import calculator.bar_optimization_system.controller.MainController;
import calculator.bar_optimization_system.inputFile.FilePDF;
import calculator.bar_optimization_system.optimizers.BarOptimizer;
import calculator.bar_optimization_system.optimizers.OptimizationResult;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

public class HomeScreen {
    public static void homeScreen(Stage primaryStage, MainController mainController) {
        Image image = new Image(HomeScreen.class.getResource("/images/icon.png").toExternalForm());
        primaryStage.getIcons().add(image);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(40));

        Text title = new Text("Calculadora de Aproveitamento de Barras");
        title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 28));

        Button buttonSendRomaneio = new Button("Enviar Romaneio");
        buttonSendRomaneio.setStyle("""
                    -fx-background-color: #156EB1;
                                -fx-text-fill: white;
                                -fx-font-size: 16px;
                                -fx-padding: 15 30 15 30;
                                -fx-border-radius: 8;
                                -fx-cursor: hand;
                                -fx-background-radius: 8;
                """);

        buttonSendRomaneio.setOnAction (e -> {
            mainController.processFile();
        });

        HBox topBox = new HBox(title);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(0, 0, 30, 0));

        HBox bottomBox = new HBox(buttonSendRomaneio);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));

        root.setTop(topBox);
        root.setCenter(bottomBox);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Calculadora de Aproveitamento");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }
}
