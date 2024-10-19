package org.example.gui_synthesizer.Wave;

import org.example.gui_synthesizer.AudioComponent.AudioClip;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;

import java.util.Random;

public class WhiteNoise implements AudioComponent {
    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        Random random = new Random();
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            short sample = (short) (random.nextInt(Short.MAX_VALUE * 2 + 1) - Short.MAX_VALUE);
            // sample = [0, 65534] - 32767 = [-32767, 32767];
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
        assert false : "White noise does not accept input";
    }
}
