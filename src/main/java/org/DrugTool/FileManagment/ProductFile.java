package org.DrugTool.FileManagment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFile {
    FileMaster masterFile = new FileMaster();
    //private String fileContent = masterFile.fileContent;
    private List<String> fileContent;

    //Modifies the master file into the necessary Strings by removing everything around it leaving just products
    public ProductFile() throws IOException {
        int productStart = masterFile.fileContent.indexOf("DiscoveredProducts");
        int productFinish = masterFile.fileContent.indexOf("ListedProducts");
        fileContent = toList(masterFile.fileContent.substring(productStart, productFinish));
    }

    public String getFileContent(int index){
        return fileContent.get(index);
    }

    //As it says it deletes names of products that is unwanted.
    //Todo: Try to use a list of string instead of modifying a string as a whole
    public void deleteProduct(String productName){
        //fileContent = fileContent.replace("\"" + productName + "\",", "");
    }
    //Turns fileContent into a List of string with all and only the products stored in for easy use
    public List<String> toList(String fileContent){
        //Splits all the newline part of the string and throw it to an arr
        String[] lines = fileContent.split("\\R");

        List<String> productList = new ArrayList<>();
        for (String line : lines) {
            //Remove all parentheses, comas, and spaces for proper clean up
            String cleaned = line.trim()
                    .replace(",", "")
                    .replaceAll("^\"|\"$", "");
            if (!cleaned.isEmpty()) {
                productList.add(cleaned);
            }
        }
        //Cleans up the list by removing the title of the list and brackets
        if (productList.contains("DiscoveredProducts\": [") && productList.contains("]")){
            productList.remove(productList.size() - 1);
            productList.remove(0);
        }
        return productList;
    }
    //Todo: Need to add a function that reverts the list back to the string format needed by the game
    //public String toString(){

     //   return;
    //}

    public int getListSize(){
        return fileContent.size();
    }

}
