package org.DrugTool.FileManagment;

import org.DrugTool.Util.ProductHelperClass;

import java.io.IOException;
import java.util.List;

public class ListedProductsFile {
    FileMaster masterFile = new FileMaster();

    private final List<String> fileContent;

    //Modifies the master file into the necessary Strings by removing everything around it leaving just products
    public ListedProductsFile() throws IOException {
        fileContent = ProductHelperClass.toList(ProductHelperClass.findWantedContent(masterFile.fileContent, "ProductPrices", "FavouritedProducts"), "ProductPrice");
    }
}
