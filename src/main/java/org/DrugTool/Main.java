package org.DrugTool;

import org.DrugTool.FileManagment.ProductFile;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            ProductFile productFile = new ProductFile();
            System.out.print(productFile.getFormatedListString());
        } catch (Exception e) {
            System.out.println("Error was discovered: " + e.getMessage());
            System.exit(1);
        }
    }
}