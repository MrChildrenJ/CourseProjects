package org.example.gui_synthesizer;

import org.example.gui_synthesizer.AudioComponent.AudioClip;
import org.example.gui_synthesizer.AudioComponent.AudioComponent;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
    }

    public static void playAudio(AudioComponent component, AudioFormat format) {
        try {
            Clip c = AudioSystem.getClip();
            AudioClip clip = component.getClip();
            c.open(format, clip.getData(), 0, clip.getData().length);
            out.print("playing...");
            c.start();
            c.loop(0);
            while( c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning() ){
                // Do nothing while we wait for the note to play.
            }
            System.out.println( "Done." );
            c.close();
        } catch (LineUnavailableException e) {
            out.println("A clip can not be opened.");
        }
    }
}

