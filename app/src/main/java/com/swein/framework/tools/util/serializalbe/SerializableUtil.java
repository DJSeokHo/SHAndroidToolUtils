package com.swein.framework.tools.util.serializalbe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by seokho on 09/02/2018.
 */

public class SerializableUtil {

    public static byte[] serializeObjectToBytes(Object object) throws IOException {
        byte[] bytes;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        bytes = byteArrayOutputStream.toByteArray ();
        objectOutputStream.close();
        byteArrayOutputStream.close();

        return bytes;
    }

    public static Object antiSerializeBytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object object;

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream (bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream (byteArrayInputStream);
        object = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();

        return object;
    }

}
