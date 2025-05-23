package org.DrugTool.FileManagment;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Collectors;
//Class that dynamically searches the folder structure and locates the target file
//NOTE Target file is still statically defined
public class FileMaster {
    public String fileContent;
    private final Path filepath;
    public FileMaster() throws IOException {
        Path baseDir;

        // Testing Mode Environment Variable for my Mac, Ignores automatically for Windows
        String isTestMode = System.getenv("TEST_MODE");

        if ("true".equalsIgnoreCase(isTestMode)) {
            // Mac testing path
            baseDir = Path.of(System.getProperty("user.home"),
                    "Library", "Containers", "com.isaacmarovitz.Whisky", "Bottles");
        } else {
            // Windows default save path
            baseDir = Path.of(System.getProperty("user.home"),
                    "AppData", "LocalLow", "TVGS", "Schedule I", "Saves");
        }

        Path targetFile = findSaveFile(baseDir);
        filepath = findSaveFile(baseDir);
        if (targetFile == null) {
            throw new IOException("ProductsTestFile.json not found under base directory.");
        }

        fileContent = Files.readAllLines(targetFile)
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }
    //Todo: need to have all other classes done to piece everything together
    public void pushChanges(String gameFormattedDiscoveredProducts) throws IOException {
        fileContent = "{\n" + "    \"DataType\": \"ProductManagerData\",\n" + "    \"DataVersion\": 0,\n" + "    \"GameVersion\": \"0.3.4f4\"," + gameFormattedDiscoveredProducts
        + "\n    \"ListedProducts\": [],\n    \"ActiveMixOperation\": {\n        \"ProductID\": \"\",\n        \"IngredientID\": \"\"\n    },\n    \"IsMixComplete\": false,\n";
        try {
            Files.write(filepath, fileContent.getBytes());
            System.out.println("File updated successfully!");
        } catch (IOException e) {
            System.out.println("Error was discovered: " + e.getMessage());
        }
    }

    private Path findSaveFile(Path baseDir) throws IOException {
        try (var stream = Files.walk(baseDir)) {
            return stream
                    .filter(p -> p.getFileName().toString().equals("ProductsTestFile.json"))
                    .findFirst()
                    .orElse(null);
        }
    }
}