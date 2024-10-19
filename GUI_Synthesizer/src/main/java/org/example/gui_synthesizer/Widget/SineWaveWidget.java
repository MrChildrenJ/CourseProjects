package org.example.gui_synthesizer.Widget;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.gui_synthesizer.AudioComponent.*;
import org.example.gui_synthesizer.Wave.SineWave;

public class SineWaveWidget extends AudioComponentWidget {
    protected static int numOfSWW = 1;

    public SineWaveWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
        ++numOfSWW;
    }

    protected void setLeft(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Label title = new Label(name + " #" + numOfSWW);
        Label frequencyLabel = new Label("Freq: 440 Hz");

        TextField frequencyInput = new TextField("440");
        frequencyInput.setPrefWidth(60);

        Slider slider = new Slider(20, 2000, 440);
        slider.setBlockIncrement(1);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double frequency = newVal.doubleValue();
            ((SineWave) audioComponent).setFrequency(frequency);
            frequencyLabel.setText(String.format("Freq: %.2f Hz", frequency));
            frequencyInput.setText(String.format("%.2f", frequency));
        });

        frequencyInput.setOnAction(event -> {
            try {
                double frequency = Double.parseDouble(frequencyInput.getText());
                if (frequency >= 20 && frequency <= 2000) {
                    slider.setValue(frequency);
                } else {
                    frequencyInput.setText(String.format("%.2f", slider.getValue()));
                }
            } catch (NumberFormatException e) {
                frequencyInput.setText(String.format("%.2f", slider.getValue()));
            }
        });

        HBox frequencyBox = new HBox(5, frequencyLabel, frequencyInput);
        vbox.getChildren().addAll(title, slider, frequencyBox);
    }
}
