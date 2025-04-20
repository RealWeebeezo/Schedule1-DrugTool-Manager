package org.DrugTool.FileManagment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class ProductFile {

    String fileContent;
    public ProductFile(Path targetFile) throws IOException {
        fileContent = Files.readAllLines(targetFile).stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }
    public String getFileContent(){
        return fileContent;
    }

    public String discoveredProducts(String fileContent){
        int productStart = fileContent.indexOf("DiscoveredProducts");
        int productFinish = fileContent.indexOf("ListedProducts");

        return fileContent.substring(productStart, productFinish);
    }
}
