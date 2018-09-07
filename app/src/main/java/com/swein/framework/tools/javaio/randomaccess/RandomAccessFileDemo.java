package com.swein.framework.tools.javaio.randomaccess;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class RandomAccessFileDemo {

    public RandomAccessFileDemo() throws IOException {

        File dir = new File("demo");
        if(!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(dir, "file.dat");
        if(!file.exists()) {
            file.createNewFile();
        }

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

        System.out.println(randomAccessFile.getFilePointer());

        randomAccessFile.write('A');
        System.out.println(randomAccessFile.getFilePointer());

        randomAccessFile.write('B');

//        int i = 99999;
//
//        randomAccessFile.writeInt(i);

        String s = "字";
        byte[] gbk = s.getBytes("gbk");
        randomAccessFile.write(gbk);

        System.out.println(randomAccessFile.length());

        // reset position
        randomAccessFile.seek(0);

        byte[] buf = new byte[(int)randomAccessFile.length()];
        randomAccessFile.read(buf);
        System.out.println(Arrays.toString(buf));
        String string = new String(buf, "gbk");
        System.out.println(string);


        // close it when done
        randomAccessFile.close();
    }
}
