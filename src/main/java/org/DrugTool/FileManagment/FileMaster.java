package org.DrugTool.FileManagment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileMaster {
    public String fileContent;
    Path targetFile = Path.of("C:\\Users\\Weebeezo\\AppData\\LocalLow\\TVGS\\Schedule I\\Saves\\76561199192092218\\SaveGame_1\\Products\\ProductsTestFile.Json");
    public FileMaster() throws IOException {
        fileContent = Files.readAllLines(targetFile).stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
