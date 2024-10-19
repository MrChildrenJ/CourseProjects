package org.example.gui_synthesizer.AudioComponent;

// Input  : 0
// Output : 1

public class LinearRamp implements AudioComponent {
    private double start;
    private double stop;

    public LinearRamp(double start, double stop) {
        this.start = start;
        this.stop = stop;
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();

        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            double sample = (start * (AudioClip.TOTAL_SAMPLES - i) + stop * i) / AudioClip.TOTAL_SAMPLES;
            clip.setSample(i, (int) sample);
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input)  {  assert false : "LinearRamp does not accept input";  }
    public void setStart(double newValue)           {  this.start = newValue;  }
    public void setStop(double newValue)            {  this.stop = newValue;  }
}
