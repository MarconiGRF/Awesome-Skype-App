package hel;

import javax.sound.sampled.*;

class Recorder {
    // Record duration, in milliseconds
    //private static long RECORD_TIME = 5000;

    // Audio source from computer.
    AudioFormat format;
    DataLine.Info info;
    SourceDataLine audio_out;
    TargetDataLine audio_in;
    DataLine.Info info_in;
    DataLine.Info info_out;

    Recorder() throws LineUnavailableException {
        this.format = new AudioFormat(44100, 8, 2, true, true);
        this.info = new DataLine.Info(TargetDataLine.class, format);
        this.info_in = new DataLine.Info(TargetDataLine.class, format);
        this.info_out = new DataLine.Info(SourceDataLine.class, format);
        audio_out = (SourceDataLine) AudioSystem.getLine(info_out);
        audio_in = (TargetDataLine) AudioSystem.getLine(info_in);
        try {
            audio_out.open(format);
            audio_in.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        audio_out.start();
        audio_in.start();
    }
}