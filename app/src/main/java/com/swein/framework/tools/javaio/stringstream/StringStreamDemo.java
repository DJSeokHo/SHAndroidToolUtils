package com.swein.framework.tools.javaio.stringstream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class StringStreamDemo {

    public StringStreamDemo() throws IOException {

        File file = new File("demo", "wenjian.txt");

        FileInputStream fileInputStream = new FileInputStream(file.getPath());
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "gbk");

        File fileNew = new File("demo", "wenjiancopy.txt");
//        copyFile(file, fileNew, null);
        copyFile(file, fileNew, "gbk");

//        int c;
//        while ((c = inputStreamReader.read()) != -1) {
//            System.out.print((char) c);
//        }


        char[] buffer = new char[8 * 1024];
        int cb;
        while ((cb = inputStreamReader.read(buffer, 0, buffer.length)) != -1) {
            String s = new String(buffer, 0, cb);
            System.out.print(s);
        }

    }

    private void copyFile(File srcFile, File destFile, String charset) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(srcFile);

        InputStreamReader inputStreamReader;

        if(charset == null || "".equals(charset)) {
            inputStreamReader = new InputStreamReader(fileInputStream);
        }
        else {
            inputStreamReader = new InputStreamReader(fileInputStream, charset);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(destFile);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);


        char[] buffer = new char[8 * 1024];
        int cb;
        while ((cb = inputStreamReader.read(buffer, 0, buffer.length)) != -1) {
            String s = new String(buffer, 0, cb);
            System.out.print(s);

            outputStreamWriter.write(buffer, 0, cb);
            outputStreamWriter.flush();
        }

        inputStreamReader.close();
        outputStreamWriter.close();
    }

}
