package org.DrugTool.Util;

public class ProductHelperClass {
    public ProductHelperClass(){}
    public static String findWantedContent(String fileContent, String indexOfStart, String indexOfEnd){
        int productStart = fileContent.indexOf(indexOfStart);
        int productFinish = fileContent.indexOf(indexOfEnd);
        return fileContent.substring(productStart, productFinish);
    }

}
