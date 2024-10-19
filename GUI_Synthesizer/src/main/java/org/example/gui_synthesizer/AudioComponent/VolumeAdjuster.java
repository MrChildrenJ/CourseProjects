package org.example.gui_synthesizer.AudioComponent;

public class VolumeAdjuster implements AudioComponent {
    private AudioComponent input;
    private double scale;

    public VolumeAdjuster(double scale)                {  this.scale = scale;  }
    public VolumeAdjuster(double scale, AudioComponent input) {
        this.scale = scale;
        this.input = input;
    }
    public double getScale()                    {  return this.scale;  }
    public void setScale(double s)               {  this.scale = s;  }

    @Override
    public AudioClip getClip() {
        AudioClip originalClip = input.getClip();
        AudioClip result = new AudioClip();

        for (int i = 0; i < originalClip.getData().length / 2; i++) {
            short originalSample = originalClip.getSample(i);
            int resultSample = (int) (scale * originalSample);

            resultSample = Math.max(Short.MIN_VALUE ,Math.min(resultSample, Short.MAX_VALUE));

            result.setSample(i, resultSample);
        }
        return result;
    }

    @Override
    public boolean hasInput()                           {  return true;  }

    @Override
    public void connectInput(AudioComponent input)      {  this.input = input;  }

    public void disconnectInput()                       {  this.input = null;  }
}
