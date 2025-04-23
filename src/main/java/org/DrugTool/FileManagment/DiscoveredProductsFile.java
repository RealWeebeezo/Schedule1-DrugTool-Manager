package org.DrugTool.FileManagment;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

import org.DrugTool.Util.ProductHelperClass;


public class DiscoveredProductsFile {
    FileMaster masterFile = new FileMaster();
    private final List<String> fileContent;

    //Modifies the master file into the necessary Strings by removing everything around it leaving just products
    public DiscoveredProductsFile() throws IOException {
        fileContent = ProductHelperClass.toList(ProductHelperClass.findWantedContent(masterFile.fileContent, "DiscoveredProducts", "ListedProducts"), "DiscoveredProducts");
    }

    //As it says it deletes names of products that is unwanted.
    public void removeProduct(String productName){
        fileContent.remove(productName);
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
        return "\n    \"DiscoveredProducts\": [\n" + (toString(fileContent)) + "\n    ],";
    }
}