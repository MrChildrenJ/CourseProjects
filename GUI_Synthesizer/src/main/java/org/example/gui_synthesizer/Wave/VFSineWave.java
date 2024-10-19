package org.example.gui_synthesizer.Wave;

import org.example.gui_synthesizer.AudioComponent.AudioClip;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;

public class VFSineWave implements AudioComponent {
    private AudioComponent input;

    @Override
    public AudioClip getClip() {
        AudioClip outputClip = new AudioClip();

        if (input == null)      return outputClip;

        AudioClip inputClip = input.getClip();
        double phase = 0;

        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            int inputSample = inputClip.getSample(i);
            phase += 2 * Math.PI * inputSample / AudioClip.sampleRate;
            short outputSample = (short) (Math.sin(phase) * Short.MAX_VALUE);
            outputClip.setSample(i, outputSample);
        }
        return outputClip;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        this.input = input;
    }
}
