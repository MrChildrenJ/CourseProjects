package org.example.gui_synthesizer.Test;

import org.example.gui_synthesizer.AudioComponent.*;
import org.example.gui_synthesizer.Main;
import org.example.gui_synthesizer.Wave.SineWave;

import javax.sound.sampled.AudioFormat;

public class VATest {
    public static void main(String[] args) {
        SineWave sineWave = new SineWave(440);
        VolumeAdjuster va = new VolumeAdjuster(2.f);

        va.connectInput(sineWave);
        AudioClip originalClip = sineWave.getClip();
        AudioClip adjustedClip = va.getClip();

        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

//        Main.playAudio(sineWave, format16);
//        Main.playAudio(va, format16);

        double vol = 0.3;
        AudioComponent A4 = new VolumeAdjuster(vol, new SineWave(440));
        AudioComponent CS4 = new VolumeAdjuster(vol, new SineWave(277.2));
        AudioComponent C4 = new VolumeAdjuster(vol, new SineWave(261.6));
        AudioComponent D4 = new VolumeAdjuster(vol, new SineWave(293.7));
        AudioComponent E4 = new VolumeAdjuster(vol, new SineWave(329.6));

        // Test playing original clip
        Main.playAudio(A4, format16);
        Main.playAudio(C4, format16);
        Main.playAudio(E4, format16);

        Mixer aMinor = new Mixer();
        aMinor.connectInput(A4);
        aMinor.connectInput(C4);
        aMinor.connectInput(E4);
        Main.playAudio(aMinor, format16);

    }

}
