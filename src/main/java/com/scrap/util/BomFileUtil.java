package com.scrap.util;

import org.apache.commons.codec.binary.Hex;

import java.io.*;

/**
 * Created by lballena on 19/01/2016.
 */
public class BomFileUtil {

    private final static String BOM = "efbbbf";

    public static File removeBom(File file) {
        String fileString = readFile(file);
        String hexString = Hex.encodeHexString(fileString.getBytes());
        String hexStringWithoutBom = hexString.replaceFirst(BOM, "");
        try{
            file = fromBytes(file,Hex.decodeHex(hexStringWithoutBom.toCharArray()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }

    private static String readFile(File file) {
        BufferedReader br = null;
        StringBuilder content = new StringBuilder();
        try {
            String sCurrentLine;

            br = new BufferedReader(new FileReader(file.getAbsolutePath()));

            while ((sCurrentLine = br.readLine()) != null) {
                content.append(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return content.toString();
    }

    private static File fromBytes(File file, byte[] bytes) throws IOException {
        FileOutputStream fis = new FileOutputStream(file);
        try {
            fis.write(bytes);
            return file;
        } finally {
            fis.close();
        }
    }

}
