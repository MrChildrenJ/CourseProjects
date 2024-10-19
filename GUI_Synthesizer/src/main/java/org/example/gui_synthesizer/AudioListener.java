package org.example.gui_synthesizer;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.util.*;

import static java.lang.System.*;

public class AudioListener implements LineListener {
    public AudioListener(Clip c)    {  clip = c;  }

    @Override
    public void update(LineEvent e) {  if(e.getType() == LineEvent.Type.STOP)   clip.close();  }

    private Clip clip;
}
