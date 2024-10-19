package org.example.gui_synthesizer;

import org.example.gui_synthesizer.AudioComponent.AudioClip;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;

public class Speaker implements AudioComponent {
    private AudioComponent input;

    @Override
    public AudioClip getClip() {
        if (input == null)  return null;
        else                return input.getClip();
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        this.input = input;
    }

    public void disconnectInput(AudioComponent input) {
        if (this.input == input)    this.input = null;
    }
}
