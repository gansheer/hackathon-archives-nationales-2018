package fr.archives.nat;

import java.io.File;
import java.nio.file.Paths;

enum XmlFiles {

    FRAN_IR_056040("data/FRAN_IR_056040.xml"),

    FRAN_IR_056870("data/FRAN_IR_056870.xml");

    private final File file;

    XmlFiles(String location) {
        this.file = Paths.get("src/main/resources/" + location).toAbsolutePath().normalize().toFile();
    }

    public File getFile() {
        return file;
    }

}