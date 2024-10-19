package org.example.gui_synthesizer.AudioComponent;

// Input  : 1
// Output : 1

import java.util.ArrayList;

public class MultiplicativeFilter implements AudioComponent{
    private ArrayList<AudioComponent> inputAudios = new ArrayList<>();
    @Override
    public AudioClip getClip() {
        if (inputAudios.isEmpty())  return new AudioClip();

        AudioClip result = inputAudios.getFirst().getClip();
        for (int i = 1; i < inputAudios.size(); i++) {
            AudioClip nextClip = inputAudios.get(i).getClip();
            for (int j = 0; j < AudioClip.TOTAL_SAMPLES; j++) {
                long product = ((long) result.getSample(j) * nextClip.getSample(j)) / Short.MAX_VALUE;
                result.setSample(j, (short)Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, product)));
            }
        }
        return result;
    }

    @Override
    public boolean hasInput()                       {  return true;  }

    @Override
    public void connectInput(AudioComponent input)  {  inputAudios.add(input);  }
}
