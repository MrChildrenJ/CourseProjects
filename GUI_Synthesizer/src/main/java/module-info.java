module org.example.gui_synthesizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.testng;


    opens org.example.gui_synthesizer to javafx.fxml;
    exports org.example.gui_synthesizer;
    exports org.example.gui_synthesizer.Widget;
    opens org.example.gui_synthesizer.Widget to javafx.fxml;
    exports org.example.gui_synthesizer.AudioComponent;
    opens org.example.gui_synthesizer.AudioComponent to javafx.fxml;
    exports org.example.gui_synthesizer.Test;
    opens org.example.gui_synthesizer.Test to javafx.fxml;
    exports org.example.gui_synthesizer.Wave;
    opens org.example.gui_synthesizer.Wave to javafx.fxml;
}