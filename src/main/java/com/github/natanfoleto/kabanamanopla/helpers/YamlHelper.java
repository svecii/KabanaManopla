package com.github.natanfoleto.kabanamanopla.helpers;

import com.github.natanfoleto.kabanamanopla.KabanaManopla;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlHelper {
    KabanaManopla main = KabanaManopla.getInstance();

    boolean isNewFile;
    File directory;
    File file;
    FileConfiguration fileConfiguration;


    public YamlHelper(String directory, String fileName, boolean isNewFile) {
        this.isNewFile = isNewFile;

        String fileNameExt = fileName + ".yml";

        createDirectory(directory);
        createFile(directory, fileNameExt);

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void createDirectory(String directoryName) {
        directory = main.getDataFolder();

        if(directoryName != null) {
            directory = new File(main.getDataFolder(), directoryName.replace("/", File.separator));
            directory.mkdirs();
        }
    }

    public void createFile(String directory, String fileName) {
        file = new File(this.directory, fileName);

        if(!file.exists()) {
            if (this.isNewFile) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                main.saveResource(directory != null ? directory + File.separator + fileName : fileName, false);
            }
        }
    }

    public FileConfiguration getFile() { return fileConfiguration; }

    public void saveFile() {
        try {
            fileConfiguration.save(file);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void reloadFile() { fileConfiguration = YamlConfiguration.loadConfiguration(file); }

    public void setPropertyFile(String property, Object value) { fileConfiguration.set(property, value); }
}
