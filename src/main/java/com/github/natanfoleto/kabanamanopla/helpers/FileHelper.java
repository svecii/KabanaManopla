package com.github.natanfoleto.kabanamanopla.helpers;

import java.io.File;
import java.io.IOException;

public class FileHelper {
    public static void createFileIfNotExists(File file) {
        try {
            File parentFile = file.getParentFile();

            if (!parentFile.exists()) parentFile.mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
