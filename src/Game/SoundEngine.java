package Game;

import java.io.File;
import javax.sound.sampled.*;

public class SoundEngine {


    public SoundEngine() {
    }

    public void continuous(String fileName) {


        File file = new File(fileName);
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.loop(javax.sound.midi.Sequencer.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void playOnce(String fileName) {


        File file = new File(fileName);
        try {
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(file));
            c.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

