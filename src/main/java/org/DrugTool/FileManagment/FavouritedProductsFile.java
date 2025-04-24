package org.DrugTool.FileManagment;

import org.DrugTool.Util.ProductHelperClass;

import java.io.IOException;
import java.util.List;

public class FavouritedProductsFile {
    FileMaster masterFile = new FileMaster();

    private final List<String> fileContent;

    //Modifies the master file into the necessary Strings by removing everything around it leaving just products
    public FavouritedProductsFile() throws IOException {
        fileContent = ProductHelperClass.toList(ProductHelperClass.findWantedContent(masterFile.fileContent, "ProductPrices", "FavouritedProducts"), "ProductPrice");
    }

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
        return "\n    \"FavouriteProducts\": [\n" + ProductHelperClass.toGameFormatComplex(fileContent) + "\n    ],";
    }
}

