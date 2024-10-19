package org.example.gui_synthesizer.Widget;

import javafx.scene.layout.AnchorPane;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;

import java.util.*;

import static java.lang.System.*;

public class MFWidget extends AudioComponentWidget{
    static int numOfMFW = 1;
    public MFWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
        numOfMFW++;
    }

}
