package cn.jxh.job.utils;

import java.io.UnsupportedEncodingException;

public class Base64Utils {


    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    static private byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++) {
            codes[i] = -1;
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            codes[i] = (byte) (i - 'A');
        }
        for (int i = 'a'; i <= 'z'; i++) {
            codes[i] = (byte) (26 + i - 'a');
        }
        for (int i = '0'; i <= '9'; i++) {
            codes[i] = (byte) (52 + i - '0');
        }
        codes['+'] = 62;
        codes['/'] = 63;
    }

    public static String String2Base(String data) {
        byte[] ByteData;
        String StringData = "";
        try {
            ByteData = data.getBytes("ISO-8859-1");
            char[] out = new char[((ByteData.length + 2) / 3) * 4];
            for (int i = 0, index = 0; i < ByteData.length; i += 3, index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = (0xFF & (int) ByteData[i]);
                val <<= 8;
                if ((i + 1) < ByteData.length) {
                    val |= (0xFF & (int) ByteData[i + 1]);
                    trip = true;
                }
                val <<= 8;
                if ((i + 2) < ByteData.length) {
                    val |= (0xFF & (int) ByteData[i + 2]);
                    quad = true;
                }
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 1] = alphabet[val & 0x3F];
                val >>= 6;
                out[index + 0] = alphabet[val & 0x3F];
            }
            StringData = new String(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return StringData;
    }

    public static String Base2String(String data) {
        byte[] ByteData;
        String StringData = "";
        try {
            ByteData = data.getBytes("ISO-8859-1");
            int len = ((ByteData.length + 3) / 4) * 3;
            if (ByteData.length > 0 && ByteData[ByteData.length - 1] == '=') {
                --len;
            }
            if (ByteData.length > 1 && ByteData[ByteData.length - 2] == '=') {
                --len;
            }
            byte[] out = new byte[len];
            int shift = 0;
            int accum = 0;
            int index = 0;
            for (int ix = 0; ix < ByteData.length; ix++) {
                int value = codes[ByteData[ix] & 0xFF];
                if (value >= 0) {
                    accum <<= 6;
                    shift += 6;
                    accum |= value;
                    if (shift >= 8) {
                        shift -= 8;
                        out[index++] = (byte) ((accum >> shift) & 0xff);
                    }
                }
            }
            if (index != out.length) {
                throw new Error("miscalculated data length!");
            }
            StringData = new String(out);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return StringData;
    }

}
