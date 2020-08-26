/*
 * Ну вы же понимаете, что код здесь только мой?
 * Well, you do understand that the code here is only mine?
 */

package net.steelswing.pe.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author MrJavaCoder
 */
public class Utils {

    public static final File TEMP_FOLDER = new File(System.getProperty("user.home") + File.separator + ".pe");

    public static File getFileFromJar(String src) throws Exception {
        File source = new File(src);
        File file = new File(TEMP_FOLDER + File.separator + source.getName().hashCode() + ".pe");
        if (!TEMP_FOLDER.exists()) {
            TEMP_FOLDER.mkdirs();
        }

        try (FileOutputStream out = new FileOutputStream(file)) {
            IOUtils.write(getResourceAsBytes(src), out);
        }

        return file;
    }

    public static String getResourceAsFile(String resourcePath) {
        String result = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
            if (in != null) {
                Scanner s = new Scanner(in).useDelimiter("\\A");
                result = s.hasNext() ? s.next() : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] getResourceAsBytes(String resourcePath) {
       InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
        byte[] bytes = new byte[0];
        try {
            bytes = getBytesFromInputStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static byte[] getBytesFromInputStream(InputStream in) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        if (in != null) {
            byte[] buffer = new byte[0xFFFF];
            for (int len = in.read(buffer); len != -1; len = in.read(buffer)) {
                os.write(buffer, 0, len);
            }
        }
        return os.toByteArray();
    }
}
