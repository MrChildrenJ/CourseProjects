package org.example.gui_synthesizer.AudioComponent;

// Input  : multiple
// Output : 1

import java.util.ArrayList;
import java.util.List;

public class Mixer implements AudioComponent {
    private ArrayList<AudioComponent> inputAudios;
    private Runnable onConnectListener;

    public Mixer()                                      {  this.inputAudios = new ArrayList<>();  }
    public void connectInputListener(Runnable listener) {  this.onConnectListener = listener;  }
    @Override
    public boolean hasInput()                           {  return true;  }
    @Override
    public void connectInput(AudioComponent input) {
        if (input != null)              inputAudios.add(input);
        if (onConnectListener != null)  onConnectListener.run();
    }
    @Override
    public AudioClip getClip() {
        if (inputAudios.isEmpty())                      return new AudioClip();

        AudioClip result = new AudioClip();
        List<AudioClip> inputClips = inputAudios.stream().map(AudioComponent::getClip).toList();

        for (int i = 0; i < result.getData().length / 2; i++) {
            int mixedSample = 0;
            for (AudioClip c : inputClips)
                mixedSample += c.getSample(i);

            mixedSample = Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, mixedSample));

            result.setSample(i, (short) mixedSample);
        }
        return result;
    }

    public void disconnectInput(AudioComponent input) {
        inputAudios.remove(input);
        if (onConnectListener != null) onConnectListener.run();
    }
}
