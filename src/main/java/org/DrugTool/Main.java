package org.DrugTool;

import org.DrugTool.FileManagment.ProductFile;
import java.nio.file.Path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
        Path tempPathVar = Path.of("C:\\Users\\Weebeezo\\AppData\\LocalLow\\TVGS\\Schedule I\\Saves\\76561199192092218\\SaveGame_1\\Products\\ProductsTestFile.Json");
        ProductFile productFile = new ProductFile(tempPathVar);
            System.out.print(productFile.discoveredProducts(productFile.getFileContent()));
        } catch (Exception e) {
            System.out.println("Error was discovered: " + e.getMessage());
            System.exit(1);
        }
    }
}