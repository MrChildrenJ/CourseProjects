package org.example.gui_synthesizer.Widget;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;

import java.util.*;

import static java.lang.System.*;

public class WhiteNoiseWidget extends AudioComponentWidget {
    static int numOfWN = 1;
    public WhiteNoiseWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
        numOfWN++;
    }

    @Override
    protected void setLeft(VBox vbox) {
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(2));
        vbox.setSpacing(3);

        Label title = new Label(name + "#" + numOfWN);
        //Slider slider = new Slider();
        vbox.getChildren().addAll(title);
    }
}
