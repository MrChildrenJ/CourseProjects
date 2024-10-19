package org.example.gui_synthesizer.Wave;

import org.example.gui_synthesizer.AudioComponent.AudioClip;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;

public class SquareWave implements AudioComponent {
    private double frequency;

    public SquareWave(double frequency)         {  this.frequency = frequency;  }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {         // TOTAL_SAMPLES = 0.5 * 44100 = 22050
            double t = (double) i / AudioClip.sampleRate;           // sampleRate = 44100
            short sample = (short) (((frequency * t) % 1 > 0.5 ? 1 : -1) * Short.MAX_VALUE);
            clip.setSample(i, sample);
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        assert false : "Square Wave does not accept input";
    }

    public double getFrequency()                {  return this.frequency;  }
    public void   setFrequency(double freq)     {  this.frequency = freq;  }
}
