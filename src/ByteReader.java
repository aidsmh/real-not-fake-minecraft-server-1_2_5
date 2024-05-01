import java.io.*;
import java.net.*;

public class ByteReader {

    private byte[] bytes;
    private int cursor;

    public ByteReader (byte[] bytes) {

        this.bytes = bytes;
        this.cursor = 0;

    }

    public byte readByte() {

        return this.bytes[this.cursor++];

    }

}