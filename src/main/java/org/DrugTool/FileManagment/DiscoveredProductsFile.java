package org.DrugTool.FileManagment;

import java.io.IOException;
import java.util.List;

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

    public int getListSize(){
        return fileContent.size();
    }

    public List<String> getList(){
        return fileContent;
    }

    public String getFileContent(int index){
        return fileContent.get(index);
    }

    public String getGameStringFormat(){
        return "\n    \"DiscoveredProducts\": [\n" + ProductHelperClass.toGameFormatComplex(fileContent) + "\n    ],";
    }
}