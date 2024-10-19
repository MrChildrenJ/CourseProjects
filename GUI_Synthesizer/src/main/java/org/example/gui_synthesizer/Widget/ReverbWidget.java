package org.example.gui_synthesizer.Widget;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;
import org.example.gui_synthesizer.AudioComponent.Mixer;
import org.example.gui_synthesizer.AudioComponent.Reverb;
import org.example.gui_synthesizer.AudioComponent.VolumeAdjuster;

public class ReverbWidget extends AudioComponentWidget {
    private AudioComponentWidget connectedInput;
    static int numOfRW = 1;
    private final Reverb reverb;

    public ReverbWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
        this.reverb = (Reverb) ac;
        numOfRW++;
    }

    @Override
    protected void setLeft(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Label title = new Label(name + " #" + numOfRW);

        // Delay value control
        Label delayLabel = new Label("Delay:");
        TextField delayInput = new TextField("0");
        delayInput.setPrefWidth(60);

        // Scale value control
        Label scaleLabel = new Label("Scale:");
        TextField scaleInput = new TextField("1");
        scaleInput.setPrefWidth(60);

        HBox startBox = new HBox(5, delayLabel, delayInput);
        HBox stopBox = new HBox(5, scaleLabel, scaleInput);

        delayInput.setOnAction(e -> updateReverbDelay(delayInput.getText()));
        scaleInput.setOnAction(e -> updateReverbScale(scaleInput.getText()));

        vbox.getChildren().addAll(title, startBox, stopBox);
    }

    protected void updateReverbDelay(String dValue) {
            int newDelayValue = Integer.parseInt(dValue);

            if (newDelayValue >= 0 && newDelayValue <= audioComponent.getClip().getData().length) {
                reverb.setDelay(newDelayValue);
            } else {
                reverb.setDelay(0);
            }
    }

    protected void updateReverbScale(String sValue) {
        double newScaleValue = Double.parseDouble(sValue);

        if (newScaleValue >= 0 && newScaleValue <= 3.0) {
            reverb.setScale(newScaleValue);
        } else {
            reverb.setScale(1);
        }
    }

    public void addInput(AudioComponentWidget input) {
        if (connectedInput == null) {
            connectedInput = input;
            audioComponent.connectInput(input.getAudioComponent());

            updateConnectionLine();
        }
    }

    public void removeInput(AudioComponentWidget input) {
        if (connectedInput == input) {
            ((Reverb) audioComponent).disconnectInput();
            connectedInput = null;
            updateConnectionLine();
        }
    }
}
