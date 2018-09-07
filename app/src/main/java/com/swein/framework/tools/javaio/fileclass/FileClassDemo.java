package com.swein.framework.tools.javaio.fileclass;

import java.io.File;
import java.io.IOException;

public class FileClassDemo {

    public FileClassDemo() {

        File fileT = new File("/Users/seokho/Desktop/worksapce/SWein/Android/SHAndroidToolUtils/app/src/main/java/com/swein/framework/tools/javaio/fileclass");

        System.out.println(fileT.exists());
        System.out.println(fileT.isDirectory());
        System.out.println(fileT.isFile());

        File fileF = new File("/Users/seokho/Desktop/worksapce/SWein/Android/SHAndroidToolUtils/app/src/main/java/com/swein/framework/tools/javaio/fileclass/123");

        System.out.println(fileF.exists());

        if(!fileF.exists()) {
            fileF.mkdir();
        }
        else {
            fileF.delete();
        }

        File file = new File("/Users/seokho/Desktop/worksapce/SWein/Android/SHAndroidToolUtils/app/src/main/java/com/swein/framework/tools/javaio/fileclass", "haha.txt");
//        File file = new File("/Users/seokho/Desktop/worksapce/SWein/Android/SHAndroidToolUtils/app/src/main/java/com/swein/framework/tools/javaio/fileclass/haha.txt");

        if(!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            file.delete();
        }

    }
}
