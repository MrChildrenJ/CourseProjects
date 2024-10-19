package org.example.gui_synthesizer.Test;

import org.example.gui_synthesizer.AudioComponent.LinearRamp;
import org.example.gui_synthesizer.Wave.VFSineWave;
import org.example.gui_synthesizer.Main;

import javax.sound.sampled.AudioFormat;

import static java.lang.System.*;

public class SynthesizerTest {
    public static void main(String[] args) {
        // Set up audio format
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

        // Test rising tone
        out.println("Test 1: Basic rising tone");
        testSynthesizer(50, 2000, format16);

        // Test falling tone
        out.println("Test 2: Falling tone");
        testSynthesizer(2000, 50, format16);

        // Test high frequency range
        out.println("Test 3: High frequency range");
        testSynthesizer(5000, 10000, format16);

        // Test low frequency range
        out.println("Test 4: Low frequency range");
        testSynthesizer(20, 200, format16);
    }

    private static void testSynthesizer(double start, double stop, AudioFormat format) {
        LinearRamp frequencyRamp = new LinearRamp(start, stop);
        VFSineWave vfSineWave = new VFSineWave();
        vfSineWave.connectInput(frequencyRamp);

        out.println("Playing tone from " + start + " Hz to " + stop + " Hz");
        Main.playAudio(vfSineWave, format);
        out.println();
    }
}
