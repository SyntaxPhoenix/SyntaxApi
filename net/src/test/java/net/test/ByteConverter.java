package net.test;

public class ByteConverter {

    private static final char[] CHARS = "0123456789abcdef".toCharArray();

    public static String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte value : bytes) {
            builder.append(CHARS[(0xF0 & value) >> 4]).append(CHARS[0x0F & value]);
        }
        return builder.toString();
    }

    public static byte[] fromHex(String hex) {
        int length = hex.length();
        if (length % 2 != 0) {
            return new byte[0];
        }
        byte[] output = new byte[length / 2];
        for (int index = 0; index < length; index += 2) {
            output[index / 2] = (byte) ((Character.digit(hex.charAt(index), 16) << 4) | Character.digit(hex.charAt(index + 1), 16));
        }
        return output;
    }

}
