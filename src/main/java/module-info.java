module goldoni.calculadora.calculadora_de_sobras {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.apache.pdfbox;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.datatransfer;
    requires java.desktop;

    opens goldoni.calculator.bar_optimization_system to javafx.fxml;
    exports goldoni.calculator.bar_optimization_system;
    exports goldoni.calculator.bar_optimization_system.optimizers;
    opens goldoni.calculator.bar_optimization_system.optimizers to javafx.fxml;
    exports goldoni.calculator.bar_optimization_system.exporters;
    opens goldoni.calculator.bar_optimization_system.exporters to javafx.fxml;
    exports goldoni.calculator.bar_optimization_system.model;
    opens goldoni.calculator.bar_optimization_system.model to javafx.fxml;
    exports goldoni.calculator.bar_optimization_system.inputFile;
    opens goldoni.calculator.bar_optimization_system.inputFile to javafx.fxml;
}