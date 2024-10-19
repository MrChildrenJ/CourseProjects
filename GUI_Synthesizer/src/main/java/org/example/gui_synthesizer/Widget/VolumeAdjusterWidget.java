package org.example.gui_synthesizer.Widget;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;
import org.example.gui_synthesizer.AudioComponent.Mixer;
import org.example.gui_synthesizer.AudioComponent.VolumeAdjuster;
import org.example.gui_synthesizer.Wave.SineWave;

import java.util.*;

import static java.lang.System.*;

public class VolumeAdjusterWidget extends AudioComponentWidget {
    private AudioComponentWidget connectedInput;
    private static int numOfVAW = 1;

    public VolumeAdjusterWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
        ++numOfVAW;
    }

    protected void setLeft(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Label title = new Label(name + " #" + numOfVAW);
        Label volLabel = new Label("Vol: 1.0");

        TextField volInput = new TextField("1.0");
        volInput.setPrefWidth(60);

        Slider slider = new Slider(0, 2, 1);
        slider.setBlockIncrement(0.01);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double vol = newVal.doubleValue();
            ((VolumeAdjuster) audioComponent).setScale(vol);
            volLabel.setText(String.format("Vol: %.2f", vol));
            volInput.setText(String.format("%.2f", vol));
        });

        volInput.setOnAction(event -> {
            try {
                double vol = Double.parseDouble(volInput.getText());
                if (vol >= 0.0 && vol <= 2.0) {
                    slider.setValue(vol);
                } else {
                    volInput.setText(String.format("%.2f", slider.getValue()));
                }
            } catch (NumberFormatException e) {
                volInput.setText(String.format("%.2f", slider.getValue()));
            }
        });
        HBox volBox = new HBox(5, volLabel, volInput);
        vbox.getChildren().addAll(title, slider, volBox);
    }

    @Override
    public void destroyWidget() {
        if (connectedInput != null)     connectedInput.disconnectFrom(this);
        super.destroyWidget();
    }

    public void addInput(AudioComponentWidget input) {
        if (connectedInput == null) {
            connectedInput = input;
            ((VolumeAdjuster) audioComponent).connectInput(input.getAudioComponent());

            updateConnectionLine();
        }
    }

    public void removeInput(AudioComponentWidget input) {
        if (connectedInput == input) {
            ((VolumeAdjuster) audioComponent).disconnectInput();
            connectedInput = null;
            updateConnectionLine();
        }
    }

    @Override
    public void disconnectFrom(AudioComponentWidget target) {
        super.disconnectFrom(target);
        if (target == connectedInput) {
            connectedInput = null;
            ((VolumeAdjuster) audioComponent).disconnectInput();
        }
    }
}

