package com.swein.framework.tools.javaio.iostream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOStreamDemo {

    public IOStreamDemo() throws IOException {

        File dir = new File("demo");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(dir, "file.dat");
        if (!file.exists()) {
            file.createNewFile();
        }

        printHexByte(file.getPath());

        printHexByteArray(file.getPath());


        /* append : true - 文件存在则追加内容 */
        FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());

        fileOutputStream.write('A');
        fileOutputStream.write('B');
        byte[] gbk = "试试看".getBytes("gbk");
        fileOutputStream.write(gbk);
        fileOutputStream.close();

        printHexByte(file.getPath());

        printHexByteArray(file.getPath());

        File destFile = new File(dir, "dest.dat");

        copyFile(file, destFile);


        File fileDos = new File(dir, "dos.dat");
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(fileDos.getPath()));
        dataOutputStream.writeInt(10);
        dataOutputStream.writeInt(-10);
        dataOutputStream.writeLong(10l);
        dataOutputStream.writeDouble(10.5);
        dataOutputStream.writeUTF("神奇"); // utf-8
        dataOutputStream.writeChars("神奇神奇"); // utf-16be

        dataOutputStream.close();

        printHexByte(fileDos.getPath());

        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(fileDos.getPath()));
        int i = dataInputStream.readInt();
        System.out.println(i);
        i = dataInputStream.readInt();
        System.out.println(i);
        long l = dataInputStream.readLong();
        System.out.println(l);
        double d = dataInputStream.readDouble();
        System.out.println(d);
        String s = dataInputStream.readUTF();
        System.out.println(s);

        dataInputStream.close();

        File destFileBuff = new File(dir, "destFileBuff.dat");
        copyFileByBuffer(file, destFileBuff);

    }

    private void printHexByte(String getPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getPath);
        int b;

        while ((b = fileInputStream.read()) != -1) {
            System.out.print(Integer.toHexString(b) + " ");
        }

        System.out.println("\n");

        fileInputStream.close();
    }

    private void printHexByteArray(String getPath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getPath);

        byte[] buff = new byte[20 * 1024];

        int bytes = 0;

        while ((bytes = fileInputStream.read(buff, 0, buff.length)) != -1) {
            for (int i = 0; i < bytes; i++) {
                System.out.print(Integer.toHexString(buff[i] & 0xff) + " ");
            }
        }

        System.out.println("\n");

        fileInputStream.close();
    }

    private void copyFile(File srcFile, File destFile) throws IOException {

        if(!srcFile.exists()) {

            return;
        }

        if(!srcFile.isFile()) {

            return;
        }

        FileInputStream fileInputStream = new FileInputStream(srcFile);
        FileOutputStream fileOutputStream = new FileOutputStream(destFile);

        byte[] buff = new byte[8 * 1024];
        int bytes;

        while ((bytes = fileInputStream.read(buff, 0, buff.length)) != -1) {
            fileOutputStream.write(buff, 0, bytes);
            fileOutputStream.flush();
        }

        fileInputStream.close();
        fileOutputStream.close();
    }

    private void copyFileByBuffer(File srcFile, File destFile) throws IOException {

        if(!srcFile.exists()) {

            return;
        }

        if(!srcFile.isFile()) {

            return;
        }

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));

        int bytes;

        while ((bytes = bufferedInputStream.read()) != -1) {
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
        }

        bufferedInputStream.close();
        bufferedOutputStream.close();

    }

}
