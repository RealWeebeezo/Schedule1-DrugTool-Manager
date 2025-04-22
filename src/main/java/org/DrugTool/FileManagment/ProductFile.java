package org.DrugTool.FileManagment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ProductFile {
    FileMaster masterFile = new FileMaster();
    private final List<String> fileContent;

    //Modifies the master file into the necessary Strings by removing everything around it leaving just products
    public ProductFile() throws IOException {
        int productStart = masterFile.fileContent.indexOf("DiscoveredProducts");
        int productFinish = masterFile.fileContent.indexOf("ListedProducts");
        fileContent = toList(masterFile.fileContent.substring(productStart, productFinish));
    }

    //As it says it deletes names of products that is unwanted.
    public void removeProduct(String productName){
        fileContent.remove(productName);
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

    public String toString(List<String> products){
        StringJoiner joiner = new StringJoiner("\n");
        int tracker = 0;
        for (String product : products) {
            if(tracker != (products.size() - 1)) {
                joiner.add("        \"" + product + "\","); // Adding the quotes and commas
            } else {
                joiner.add("        \"" + product + "\""); //Makes sure the last product doesn't have the comma
            }
            tracker++;
        }
        return joiner.toString();
    }

    public int getListSize(){
        return fileContent.size();
    }

    public List<String> getList(){
        return fileContent;
    }

    public String getFormatedListString(){
        return toString(fileContent);
    }

    public String getFileContent(int index){
        return fileContent.get(index);
    }

    public String getGameStringFormat(){
        return "\"DiscoveredProducts\": [\n" + (toString(fileContent)) + "\n    ],";
    }
}