package org.DrugTool.FileManagment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFile {
    FileMaster masterFile = new FileMaster();
    private String fileContent = masterFile.fileContent;


    public ProductFile() throws IOException {
        int productStart = fileContent.indexOf("DiscoveredProducts");
        int productFinish = fileContent.indexOf("ListedProducts");
        fileContent = fileContent.substring(productStart, productFinish);
    }

    public String getFileContent(){
        return fileContent;
    }

    public String discoveredProducts(String fileContent){
        int productStart = fileContent.indexOf("[");
        int productFinish = fileContent.indexOf("]");

        return fileContent.substring(productStart, productFinish);
    }

    public void deleteProduct(String productName){
        fileContent = fileContent.replace("\"" + productName + "\",", "");
    }
    public List<String> toList(String fileContent){
        String[] lines = fileContent.split("\\R");

        List<String> productList = new ArrayList<>();
        for (String line : lines) {
            String cleaned = line.trim()
                    .replace(",", "")     // remove comma
                    .replaceAll("^\"|\"$", ""); // remove leading and trailing quotes
            if (!cleaned.isEmpty()) {
                productList.add(cleaned);
            }
        }
        return productList;
    }

}
