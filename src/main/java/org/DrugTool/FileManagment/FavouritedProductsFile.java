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
}

