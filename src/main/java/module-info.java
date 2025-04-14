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
}