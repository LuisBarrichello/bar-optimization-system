package calculator.bar_optimization_system;

import calculator.bar_optimization_system.controller.MainController;
import calculator.bar_optimization_system.interfaces.HomeScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class InterfaceCalculatorApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainController controller = new MainController(stage);

        HomeScreen.homeScreen(stage, controller);
    }
}
