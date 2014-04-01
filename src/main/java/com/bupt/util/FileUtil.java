package com.bupt.util;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-31
 * Time: 上午12:45
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {

    public static void copy(InputStream from,OutputStream to) throws IOException {
        byte[]bytes = new byte[1024];
        int length = -1;
        while((length=from.read(bytes))!=-1){
            to.write(bytes,0,length);
            to.flush();
        }
        from.close();
        to.close();

    }
    public static String getFilePrefix(String fileName){
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
    }

    public static String getFileSufix(String fileName){
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
    }

    public static void copyFile(String inputFile,String outputFile) throws FileNotFoundException {
        File sFile = new File(inputFile);
        File tFile = new File(outputFile);
        FileInputStream fis = new FileInputStream(sFile);
        FileOutputStream fos = new FileOutputStream(tFile);
        int temp = 0;
        try {
            while ((temp = fis.read()) != -1) {
                fos.write(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
