package org.DrugTool.FileManagment;

import java.io.IOException;

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
        fileContent = fileContent.replace(productName, "");
    }

}
