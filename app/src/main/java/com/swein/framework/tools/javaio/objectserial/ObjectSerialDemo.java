package com.swein.framework.tools.javaio.objectserial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectSerialDemo {

    public ObjectSerialDemo() throws Exception {

        File file = new File("demo", "obj.dat");

        TestObject testObject = new TestObject();
        testObject.name = "test";

        /* storage object to file */
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(testObject);
        objectOutputStream.close();


        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        TestObject object = (TestObject) objectInputStream.readObject();
        object.print();
        objectInputStream.close();
    }
}

class TestObject implements Serializable {

    public String name;

    public void print() {
        System.out.println(name);
    }

}