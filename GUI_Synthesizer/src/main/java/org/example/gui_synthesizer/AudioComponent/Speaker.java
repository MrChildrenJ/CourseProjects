package org.example.gui_synthesizer.AudioComponent;

public class Speaker implements AudioComponent {
    private AudioComponent input;

    public void connectInput(AudioComponent input) {
        this.input = input;
        System.out.println("Speaker connected to: " + input);
    }
    public void disconnectInput()                   {  this.input = null;  }
    @Override
    public boolean hasInput()                       {  return true;  }
    @Override
    public AudioClip getClip() {
        return (input != null) ? input.getClip() : new AudioClip(); // Return silent clip if no input
    }
}