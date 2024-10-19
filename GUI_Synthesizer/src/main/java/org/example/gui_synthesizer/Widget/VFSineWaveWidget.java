package org.example.gui_synthesizer.Widget;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;
import org.example.gui_synthesizer.Wave.VFSineWave;

import javax.sound.midi.Synthesizer;
import java.util.*;

import static java.lang.System.*;

public class VFSineWaveWidget extends AudioComponentWidget {
    protected static int numOfVFSWW = 1;
    private Label infoLabel;

    public VFSineWaveWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
        ++numOfVFSWW;
    }
    @Override
    protected void setLeft(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Label title = new Label(name + " #" + numOfVFSWW);
        infoLabel = new Label("Connect input to modify frequency");

        vbox.getChildren().addAll(title, infoLabel);
    }
    @Override
    public void addInput(AudioComponentWidget input) {
        if (input instanceof LinearRampWidget) {
            super.addInput(input);
            ((VFSineWave) audioComponent).connectInput(input.getAudioComponent());
            connectionCircle.setFill(Color.GREEN);
            infoLabel.setText("Connected to LinearRamp");
            infoLabel.setTextFill(Color.GREEN);
        } else {
            // Provide feedback that only LinearRampWidget is accepted
            infoLabel.setText("Only accepts LinearRamp");
            infoLabel.setTextFill(Color.RED);
        }
    }
    @Override
    public void removeInput(AudioComponentWidget input) {
        super.removeInput(input);
        ((VFSineWave) audioComponent).connectInput(null);
        connectionCircle.setFill(Color.BLUE);
        infoLabel.setText("Connect LinearRamp input");
        infoLabel.setTextFill(Color.GRAY);
    }
}
