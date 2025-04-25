package org.DrugTool;


import org.DrugTool.FileManagment.*;

public class Main {
    public static void main(String[] args) {
        try {
            DiscoveredProductsFile discoveredProductsFile = new DiscoveredProductsFile();
            System.out.println(discoveredProductsFile.getListSize());

        } catch (Exception e) {
            System.out.println("Error was discovered: " + e.getMessage());
            System.exit(1);
        }
    }
}