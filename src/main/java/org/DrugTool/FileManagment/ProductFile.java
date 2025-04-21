package org.DrugTool.FileManagment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFile {
    FileMaster masterFile = new FileMaster();
    private String fileContent = masterFile.fileContent;

    //Modifies the master file into the necessary Strings by removing everything around it leaving just products
    public ProductFile() throws IOException {
        int productStart = fileContent.indexOf("DiscoveredProducts");
        int productFinish = fileContent.indexOf("ListedProducts");
        fileContent = fileContent.substring(productStart, productFinish);
    }

    public String getFileContent(){
        return fileContent;
    }
    //Trims the fileContent down a little more to just get the names of products(Might not be a necessary function)
    public String discoveredProducts(String fileContent){
        int productStart = fileContent.indexOf("[");
        int productFinish = fileContent.indexOf("]");

        return fileContent.substring(productStart, productFinish);
    }
    //As it says it deletes names of products that is unwanted.
    //Todo: Try to use a list of string instead of modifing a string as a whole
    public void deleteProduct(String productName){
        fileContent = fileContent.replace("\"" + productName + "\",", "");
    }
    //Turns fileContent into a List of string with all and only the products stored in for easy use
    public List<String> toList(String fileContent){
        String[] lines = fileContent.split("\\R");

        List<String> productList = new ArrayList<>();
        for (String line : lines) {
            String cleaned = line.trim()
                    .replace(",", "")
                    .replaceAll("^\"|\"$", "");
            if (!cleaned.isEmpty()) {
                productList.add(cleaned);
            }
        }
        return productList;
    }
    //Todo: Need to add a function that reverts the list back to the string format needed by the game


}
