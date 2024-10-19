package org.example.gui_synthesizer.Wave;

import org.example.gui_synthesizer.AudioComponent.AudioClip;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;

public class SineWave implements AudioComponent {
    private double frequency;
    private final int sampleRate = AudioClip.sampleRate;
    private final double maxValue = Short.MAX_VALUE;

    public SineWave(double freq)                {  this.frequency = freq;  }
    public double getFrequency()                {  return this.frequency;  }
    public void   setFrequency(double freq)     {  this.frequency = freq;  }
    @Override
    public boolean hasInput()                   {  return false;  }
    @Override
    public void connectInput(AudioComponent input) {
        assert false : "SineWave does not accept input";
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();

        double x = 0.0;
        double dx = 2 * Math.PI * frequency / sampleRate;
        for (int i = 0; i < clip.getData().length / 2; i++) {
            double y = maxValue * Math.sin(x);
            clip.setSample(i, (short) y);
            x += dx;
        }
        return clip;
    }






}
