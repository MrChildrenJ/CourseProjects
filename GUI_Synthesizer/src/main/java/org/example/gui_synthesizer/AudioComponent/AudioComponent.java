package org.example.gui_synthesizer.AudioComponent;

public interface AudioComponent {
    AudioClip getClip();                        // return the current sound produced by this component
    boolean hasInput();                         // can you connect something to this as an input
    void connectInput(AudioComponent input);    // connect another device to this input
}

