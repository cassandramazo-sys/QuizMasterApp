package ph.edu.dlsu.lbycpob.quizmaster.utils;

import javafx.scene.media.AudioClip;

/**
 * This class is playing game sound clips
 */
public class AudioClipManager {
    private static AudioClipManager instance;

    // Sound volume control
    private double volume = 0.5; // Default volume 50%
    private boolean muted = false;

    private AudioClipManager() {
        // Private constructor for singleton
    }

    public static AudioClipManager getInstance() {
        if (instance == null) {
            instance = new AudioClipManager();
        }
        return instance;
    }

    public void playSound(AudioClip sound) {
        if (sound != null && !muted) {
            sound.setVolume(volume);
            sound.play();
        }
    }

    public void setVolume(double volume) {
        if (volume >= 0.0 && volume <= 1.0) {
            this.volume = volume;
        }
    }

    public double getVolume() {
        return volume;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isMuted() {
        return muted;
    }

    public void toggleMute() {
        muted = !muted;
    }
}