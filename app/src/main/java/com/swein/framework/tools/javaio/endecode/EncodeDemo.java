package com.swein.framework.tools.javaio.endecode;

import java.io.UnsupportedEncodingException;

public class EncodeDemo {

    public EncodeDemo() throws UnsupportedEncodingException {
         /*
            gbk编码 中文 占用 2个字节，英文 占用 1个字节
            utf-8编码 中文 占用 3个字节，英文 占用 1个字节
            utf-16be编码 中文 占用 2个字节，英文 占用 2个字节
         */

        String s = "我是谁?Who am I?ABC";

        /* 该项目默认编码utf-8 */
        byte[] bytesDefault = s.getBytes();

        /* 指定编码 */
        byte[] bytesGBK = s.getBytes("gbk");
        byte[] bytesURF16BE = s.getBytes("utf-16be");

        for(byte b : bytesDefault) {
            System.out.print(getByteHexString(b, true) + " ");
        }

        System.out.println("\n");

        for(byte b : bytesGBK) {
            System.out.print(getByteHexString(b, true) + " ");
        }

        System.out.println("\n");

        for(byte b : bytesURF16BE) {
            System.out.print(getByteHexString(b, true) + " ");
        }

        System.out.println("\n");

        /* same default utf-8, good */
        String stringDefault = new String(bytesDefault);

        /* same gbk, good */
        String stringGBK = new String(bytesGBK, "gbk");

        /* source is utf-8, new is utf-16be, not good */
        String stringUTF16BE = new String(bytesDefault, "utf-16be");

        System.out.println(stringDefault);
        System.out.println(stringGBK);
        System.out.println(stringUTF16BE);
    }


    private String getByteHexString(byte b, boolean clearFFFFFF) {

        if(clearFFFFFF) {
            /* 去掉前几位ffffff */
            return Integer.toHexString(b & 0xff);
        }
        else {
            return Integer.toHexString(b);
        }

    }
}
