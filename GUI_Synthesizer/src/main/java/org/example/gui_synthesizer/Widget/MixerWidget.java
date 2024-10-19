package org.example.gui_synthesizer.Widget;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;
import org.example.gui_synthesizer.AudioComponent.Mixer;

import java.util.ArrayList;
import java.util.List;

public class MixerWidget extends AudioComponentWidget {
    private List<AudioComponentWidget> connectedInputs = new ArrayList<>();
    protected static int numOfMW = 1;
    private Label connectionLabel;

    public MixerWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
        ++numOfMW;
    }

    protected void setLeft(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Label title = new Label(name + " " + numOfMW);
        connectionLabel = new Label("Connections: 0");

        vbox.getChildren().addAll(title, connectionLabel);

        ((Mixer) audioComponent).connectInputListener(this::updateConnectionLabel);
    }

    @Override
    public void destroyWidget() {
        // Disconnect all inputs
        for (AudioComponentWidget input : new ArrayList<>(connectedInputs)) {
            input.disconnectFrom(this);
        }
        connectedInputs.clear();
        super.destroyWidget();
        updateConnectionLine();
    }

    public void addInput(AudioComponentWidget input) {
        if (!connectedInputs.contains(input)) {
            connectedInputs.add(input);
            audioComponent.connectInput(input.getAudioComponent());
            updateConnectionLabel();
        }
    }

    public void removeInput(AudioComponentWidget input) {
        if (connectedInputs.remove(input)) {
            ((Mixer) audioComponent).disconnectInput(input.getAudioComponent());
            updateConnectionLabel();
        }
    }

    private void updateConnectionLabel() {
        connectionLabel.setText("Connections: " + connectedInputs.size());
    }
}