package goldoni.calculadora.calculadora_de_sobras;

import goldoni.calculadora.calculadora_de_sobras.interfaces.HomeScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class InterfaceCalculatorApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HomeScreen.homeScreen(stage);
    }
}
