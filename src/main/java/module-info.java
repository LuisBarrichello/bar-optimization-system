module goldoni.calculadora.calculadora_de_sobras {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.apache.pdfbox;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.datatransfer;
    requires java.desktop;

    opens goldoni.calculadora.calculadora_de_sobras to javafx.fxml;
    exports goldoni.calculadora.calculadora_de_sobras;
}