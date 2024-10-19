package org.example.gui_synthesizer.AudioComponent;

import java.util.Arrays;

public class AudioClip {
    public static double duration = 1.0;
    public static int sampleRate = 44100;
    public static int TOTAL_SAMPLES = (int) (duration * sampleRate);
    private final byte[] data;

    public AudioClip() {
        data = new byte[(int) TOTAL_SAMPLES * 2];
    }

    public short getSample(int index) {
        int firstHalf = data[2 * index + 1] & 0b11111111;
        int secondHalf = data[2 * index] & 0b11111111;
        return (short) ((firstHalf << 8) | secondHalf);
    }

    public void setSample(int index, int value) {
        data[2 * index + 1] = (byte) (value >> 8 & 0b11111111);
        data[2 * index] = (byte) (value & 0b11111111);
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }
}
