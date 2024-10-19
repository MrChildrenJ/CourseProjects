package org.example.gui_synthesizer.Widget;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.gui_synthesizer.AudioComponent.*;
import javafx.scene.layout.*;
import org.example.gui_synthesizer.Speaker;

public class SpeakerWidget extends AudioComponentWidget {
    private AudioComponentWidget connectedInput;

    public SpeakerWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
    }

    protected void setLeft(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Label title = new Label(name);
        vbox.getChildren().addAll(title);
    }

    protected void setRight(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Button close = new Button("x");
        close.setOnAction(e -> removeInput(connectedInput));

        connectionCircle = new Circle(15);
        connectionCircle.setFill(Color.DARKORANGE);
        enableConnection(connectionCircle);

        vbox.getChildren().addAll(close, connectionCircle);
    }

    public void addInput(AudioComponentWidget input) {
        if (connectedInput == null)     connectedInput = input;
        ((Speaker) audioComponent).connectInput(input.getAudioComponent());
        connectionCircle.setFill(Color.DARKRED);
    }

    public void removeInput(AudioComponentWidget input) {
        if (connectedInput != null) {
            ((Speaker) audioComponent).disconnectInput(input.getAudioComponent());
            connectionCircle.setFill(Color.DARKORANGE);
            parent.getChildren().remove(input.connectionLine);
            input.connectionLine = null;
        }
        updateConnectionLine();
    }


}
