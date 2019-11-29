package hel;

import javax.xml.crypto.Data;
import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.BreakIterator;
import java.util.BitSet;
import java.util.Date;
import java.io.*;

public class RTPier {
    public static void main(String[] args) throws IOException{
        RTPController test = new RTPController();
    }
}

class RTPController {
    //Useful ports for RTP Controlling
    int rtpDataPort = 4002;
    int rtpControlPort = 8922;

    //Useful index for the BitSets, once they are flexible.
    int usefulLoadindex = 0;
    int sequenceNumberindex = 0;

    //The header fields (In correct order, divided by octets).
    BitSet RTPVersion = new BitSet(2);
    BitSet padding = new BitSet(1);
    BitSet extensionHeader = new BitSet(1);
    BitSet CSRCCount = new BitSet(4);

    BitSet sequenceNumber = new BitSet(16);

    BitSet timestamp = new BitSet(32);

    BitSet SSRCIdentifier = new BitSet(32);

    RTPController() throws IOException {
        //Initially every BitSet is filled with zeros.

        //First Octet - Section 1
            //1.1 - Sets RTPVersion to 2.
            this.RTPVersion.set(1, true);

            //1.2 - The Padding field is already set to false, since we don't use padding octets.
            //1.3 - The Extension Header is an optional field to IP Layer, so we don't use it (is already set to 0).

            //1.4 - We need to set CSRC Counter, which is a number between 0 to 15
            this.CSRCCount.set(2, true); //We're going to use CSRC as 4.

        //Second Octet - Section 2
            //2.1 - M Marker is to indicate significant events, so we don't need to touch it now.
            //2.2 - The Useful Load field =, we're going to use the default zero (PCM µ-law 8 kHz 64 kbits/s).

            // <!-- Important! -->
            //And so, our second octet is filled with zeros and will not change during the transmission, which means that
            //we can build a dumb byte array filled with zeros to represent these two fields.

        //Third and Fourth Octet - Section 3 and 4
            //3.1 - The Sequence number is used to provide a mechanism that ensures sequencing between the RTP packages.
            //4.1 - Since in the constructor we didn't sent no packages, this number is 0 and will only grow as packages are transmitted.

        //5th to 8th octet - Section 5
            //5.1 - The SSRC Identifier is a random number of 32bits, used to identify the source of the RTP Stream.
            this.SSRCIdentifier.set(6, true); //We're going to use 64 (?).

        //9th to 12st octet - Section 6
            //6.1 Here we constantly need to set the timestamp.
        int RTPTimeStamp = (int)new Date().getTime();

        DatagramSocket ds = new DatagramSocket();
        BitSet firstOctect = new BitSet(8);

        /* Fills the first octet, passing bit to bit synchronously writing the octet into the temporary {firstOctet},
         * this preserves the header bit order.
         */
        int i = 0;
        for(int j = 0; j < 2; j++){
            firstOctect.set(i, this.RTPVersion.get(j));
            i++;
        }
        for(int j = 0; j < 1; j++){
            firstOctect.set(i, this.padding.get(j));
            i++;
        }
        for(int j = 0; j < 1; j++){
            firstOctect.set(i, this.extensionHeader.get(j));
            i++;
        }
        for(int j = 0; j < 4; j++){
            firstOctect.set(i, this.CSRCCount.get(j));
            i++;
        }

        //Since the second octet is only composed by zero, its result is zero, so we can use a empty byte array.
        byte[] defaultZeroByteArray = new byte[1];

        //Useful static byte array for SSRCIdentifier.
        byte[] default64Array =new byte[1];
        default64Array[0] = 64;

        //Builds Complete Header
        byte[] finalHeader = new byte[12];
        finalHeader[0] = firstOctect.toByteArray()[0];
        finalHeader[1] = defaultZeroByteArray[0];
        finalHeader[2] = defaultZeroByteArray[0];
        finalHeader[3] = defaultZeroByteArray[0];

        finalHeader[4] = default64Array[0];
        finalHeader[5] = defaultZeroByteArray[0];
        finalHeader[6] = defaultZeroByteArray[0];
        finalHeader[7] = defaultZeroByteArray[0];

        //Here we need to take care of the timestamp.



        //This is a little piece of test code that you won't touch.
        //It's so edgy that you know you won't get your eyes on it, BECAUSE IT DOESN'T EVEN EXIST.
        //Just leave it there.
        //¯\_(O  ͜ʖ O)_/¯
        Double count = 0.0;
        for(i = 0; i < firstOctect.size(); i++){
            if(firstOctect.get(i)) {
                count = count + (Math.pow(2, i));
            }
        }

        DatagramPacket RTPackage = new DatagramPacket(bytes, size);
        Recorder recorder = new Recorder();
        try {
            recorder.line = (TargetDataLine)AudioSystem.getLine(recorder.info);
            recorder.line.open(recorder.format);
            AudioInputStream audioStream = new AudioInputStream(recorder.line);

            //We need to implement X bytes, those who represent 20ms of audio, so it can fit the RTP specification.
            //audioStream.readNBytes()
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}


class Recorder {
    // Record duration, in milliseconds
    //private static long RECORD_TIME = 5000;

    // Audio source from computer.
    TargetDataLine line;
    AudioFormat format;
    DataLine.Info info;

    Recorder() {
        this.format = new AudioFormat(8000, 8, 2, true, true);
        this.info = new DataLine.Info(TargetDataLine.class, format);
    }

    //Captures and stores the audio data in file.
    void start() {
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            // This starts recording, we may use this implementation later to reconstruct the package on receiver side.
            //AudioSystem.write(ais, fileType, wavFile);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    //Finishes the recording thread and mic input.
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
}
