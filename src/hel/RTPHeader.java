package hel;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Date;

public class RTPHeader {
    byte firstOctet = 66; //RTPVersion[2] + Padding [1] + Extension Header [1] + Payload Type [4]
    byte secondOctet = 0; //M Marker[1] + Payload Type [7]
    byte thirdOctet = 0; //Sequence Number part 1 [8]
    byte fourthOctet = 0; //Sequence Number part 2 [8]

    byte fifthOctet; //Timestamp part 1 [8]
    byte sixthOctet; //Timestamp part 2 [8]
    byte seventhOctet; //Timestamp part 3 [8]
    byte eighthOctet; //Timestamp part 4 [8]

    byte ninthOctet = 64;
    byte tenthOctet = 0;
    byte eleventhOctet = 0;
    byte twelfthOctet = 0;

    RTPHeader() throws IOException {
        //Initially every BitSet is filled with zeros.

        //First Octet - Section 1
        //1.1 - Sets RTPVersion to 2.
        //1.2 - The Padding field is already set to 00, since we don't use padding octets.
        //1.3 - The Extension Header is an optional field to IP Layer, so we don't use it (is already set to 0).
        //1.4 - We need to set CSRC Counter, which is a number between 0 to 15, we're going to use CSRC as 4.

        //Second Octet - Section 2
        //2.1 - M Marker is to indicate significant events, so we don't need to touch it now.
        //2.2 - The Useful Load field =, we're going to use the default zero (PCM µ-law 8 kHz 64 kbits/s).

        //Our second octet is filled with zeros and will not change during the transmission, which means that
        //we can build a dumb byte array filled with zeros to represent these two fields.

        //Third and Fourth Octet - Section 3 and 4
        //3.1 - The Sequence number is used to provide a mechanism that ensures sequencing between the RTP packages.
        //4.1 - Since in the constructor we didn't sent no packages, this number is 0 and will only grow as packages are transmitted.

        //5th to 8th octet - Section 5
        //The TimeStamp
        byte[] tsbuff = ByteBuffer.allocate(Long.BYTES).putLong(new Date().getTime()).array();
        this.ninthOctet = tsbuff[4];
        this.tenthOctet = tsbuff[5];
        this.eleventhOctet = tsbuff[6];
        this.twelfthOctet = tsbuff[7];

        //9th to 12st octet - Section 6
        //9.1 - The SSRC Identifier is a random number of 32bits, used to identify the source of the RTP Stream.
        //We're going to use 64 (?).

        //This is a little piece of test code that you won't touch.
        //It's so edgy that you know you won't get your eyes on it, BECAUSE IT DOESN'T EVEN EXIST.
        //Just leave it there.
        //¯\_(O  ͜ʖ O)_/¯
        //Double count = 0.0;
        //for(int i = 0; i < firstOctect.size(); i++){
        //    if(firstOctect.get(i)) {
        //        count = count + (Math.pow(2, i));
        //    }
        //}
    }

    void increment(byte[] bits) {
        bits[0]++;
        if(bits[0] == 0) {
            bits[1]++;
        }
    }

    byte[] getHeader() {
        byte[] returnable = new byte[12];
        returnable[0] = this.firstOctet;
        returnable[1] = this.secondOctet;
        returnable[2] = this.thirdOctet;
        returnable[3] = this.fourthOctet;
        returnable[4] = this.fifthOctet;
        returnable[5] = this.sixthOctet;
        returnable[6] = this.seventhOctet;
        returnable[7] = this.eighthOctet;
        returnable[8] = this.ninthOctet;
        returnable[9] = this.tenthOctet;
        returnable[10] = this.eleventhOctet;
        returnable[11] = this.twelfthOctet;

        return returnable;
    }
}
