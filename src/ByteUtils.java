import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteUtils {

    public static byte[] concatByteArrays(byte[]... arrays) {
        int totalLength = 0;
        for (byte[] array : arrays) {
            totalLength += array.length;
        }

        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }

    public static String readByteArray(byte[] array) {

        String result = "";

        result += Arrays.toString(array);

        return result;

    }

    public static String readAsString(byte[] array) {
        return array.toString();
    }

}
