package org.example.gui_synthesizer.AudioComponent;

// Input  : 1
// Output : 1

public class Reverb implements AudioComponent{
    private AudioComponent input;
    private int delay;
    private double scale;

    public Reverb(int delay, double scale) {
        this.delay = Math.max(0, Math.min(delay, AudioClip.TOTAL_SAMPLES - 1));
        this.scale = Math.max(0, Math.min(scale, 3.0));
    }
    @Override
    public AudioClip getClip() {
        if (input == null)  return new AudioClip();

        AudioClip inputClip = input.getClip();
        AudioClip result = new AudioClip();

        int maxEchoes = 5;
        double decayFactor = 0.6;

        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++)
            result.setSample(i, inputClip.getSample(i));

        for (int echo = 1; echo <= maxEchoes; echo++) {
            int currDelay = delay * echo;
            double currScale = Math.pow(decayFactor, echo) * scale;

            for (int i = 0; i < AudioClip.TOTAL_SAMPLES - currDelay; i++) {
                int delaySample = result.getSample(i + currDelay) + (int) (currScale * inputClip.getSample(i));
                result.setSample(i + currDelay, (short) Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, delaySample)));
            }
        }
        return result;
    }
    @Override
    public boolean hasInput()                       {  return true;  }
    @Override
    public void connectInput(AudioComponent input)  {  this.input = input;  }
    public void disconnectInput()                   {  this.input = null;  }
    public void setDelay(int value)                 {  this.delay = value;  }
    public void setScale(double value)              {  this.scale = value;  }
    public int getDelay()                           {  return delay;  }
    public double getScale()                        {  return scale;  }
}
