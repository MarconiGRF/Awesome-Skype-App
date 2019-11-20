package hel;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.BreakIterator;
import java.util.BitSet;
import java.util.Date;

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

    BitSet MMarker = new BitSet(1);
    BitSet usefulLoad = new BitSet(7);

    BitSet sequenceNumber = new BitSet(16);

    BitSet timestamp = new BitSet(32);

    BitSet SSRCIdentifier = new BitSet(32);

    RTP_Transmissor(InetAddress ip) implements Runnable{

    }

    RTPController() throws IOException {
        //Initially every BitSet is filled with zeros.

        //First Octet - Section 1
            //1.1 - Sets RTPVersion to 2.
            this.RTPVersion.set(1, true);

            //1.2 - The Padding field is already set to false, since we don't use padding octets.
            //1.3 - The Extension Header is an optional field to IP Layer, so we don't use it (is already set to 0).

            //1.4 - We need to set CSRC Counter, which is a number between 0 to 15
            this.CSRCCount.set(1, true); //We're going to use CSRC as 2.

        //Second Octet - Section 2
            //2.1 - M Marker is to indicate significant events, so we don't need to touch it now.
            //2.2 - The Useful Load field =, we're going to use the default zero (PCM µ-law 8 kHz 64 kbits/s).

        //Third and Fourth Octet - Section 3 and 4
            //3.1 - The Sequence number is used to provide a mechanism that ensures sequencing between the RTP packages.
            //4.1 - Since in the constructor we didn't sent no packages, this number is 0 and will only grow as packages are transmitted.

        //5th to 8th octet - Section 5
            //5.1 - The SSRC Identifier is a random number of 32bits, used to identify the source of the RTP Stream.
            this.SSRCIdentifier.set(6, true); //We're going to use 64 (?).


                                                //This is a little piece of test code that you won't touch.
                                                //It's so edgy that you know you won't touch it.
                                                //Just leave it there.
                                                //¯\_(O  ͜ʖ O)_/¯
                                                Double count = 0.0;
                                                for(int i = 0; i < this.SSRCIdentifier.size(); i++){
                                                    if(this.SSRCIdentifier.get(i)) {
                                                        count = count + (Math.pow(2, i));
                                                    }
                                                }

        //9th to 12st octet - Section 6
            //6.1 Here we constantly need to set the timestamp.
        int RTPTimeStamp = (int)new Date().getTime();
    }
}
