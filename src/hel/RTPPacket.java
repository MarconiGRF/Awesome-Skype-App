package hel;

import java.util.Arrays;
import java.util.BitSet;

public class RTPPacket {
    //size of the RTP header:
    static int HEADER_SIZE = 12;

    //Fields that compose the RTP header
    public int Version;
    public int Padding;
    public int Extension;
    public int CC;
    public int Marker;
    public int PayloadType;
    public int SequenceNumber;
    public int TimeStamp;
    public int Ssrc;

    //Bitstream of the RTP header
    public byte[] header;

    //size of the RTP payload
    public int payload_size;
    //Bitstream of the RTP payload
    public byte[] payload;

    //--------------------------
    //Constructor of an RTPpacket object from header fields and payload bitstream
    //--------------------------
    public RTPPacket(int PType, int Framenb, int Time, byte[] data, int data_length){
        //fill by default header fields:
        Version = 2;
        Padding = 0;
        Extension = 0;
        CC = 0;
        Marker = 0;
        Ssrc = 1337;    // Identifies the server

        //fill changing header fields:
        SequenceNumber = Framenb;
        TimeStamp = Time;
        PayloadType = PType;

        //build the header bistream:
        header = new byte[HEADER_SIZE];

        //fill the header array of byte with RTP header fields
        header[0] = (byte)(Version << 6 | Padding << 5 | Extension << 4 | CC);
        header[1] = (byte)(Marker << 7 | PayloadType & 0x000000FF);
        header[2] = (byte)(SequenceNumber >> 8);
        header[3] = (byte)(SequenceNumber & 0xFF);
        header[4] = (byte)(TimeStamp >> 24);
        header[5] = (byte)(TimeStamp >> 16);
        header[6] = (byte)(TimeStamp >> 8);
        header[7] = (byte)(TimeStamp & 0xFF);
        header[8] = (byte)(Ssrc >> 24);
        header[9] = (byte)(Ssrc >> 16);
        header[10] = (byte)(Ssrc >> 8);
        header[11] = (byte)(Ssrc & 0xFF);

        //fill the payload bitstream:
        payload_size = data_length;
        payload = new byte[data_length];

        //fill payload array of byte from data (given in parameter of the constructor)
        payload = Arrays.copyOf(data, payload_size);
    }

    public int getpacket(byte[] packet)
    {
        //construct the packet = header + payload
        for (int i=0; i < HEADER_SIZE; i++)
            packet[i] = header[i];
        for (int i=0; i < payload_size; i++)
            packet[i+HEADER_SIZE] = payload[i];

        //return total size of the packet
        return(payload_size + HEADER_SIZE);
    }

}
