package org.DrugTool.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ProductHelperClass {

    public ProductHelperClass(){}

    public static String findWantedContent(String fileContent, String indexOfStart, String indexOfEnd){
        int productStart = fileContent.indexOf(indexOfStart);
        int productFinish = fileContent.indexOf(indexOfEnd);
        return fileContent.substring(productStart, productFinish);
    }
    //Brings what is cleaned up into a list and have it more refined
    //NOTE: When filling in file name DO NOT put the Word file in it for Ex.("ProductPrice" NOT "ProductPriceFile")
    public static List<String> toList(String fileContent, String fileName){
        //Splits all the newline part of the string and throw it to an arr
        String[] lines = fileContent.split("\\R");

        List<String> productList = new ArrayList<>();
        for (String line : lines) {
            //Remove all parentheses, comas, and spaces for proper clean up
            String cleaned = line.trim()
                    .replace(",", "")
                    .replace("{", "")
                    .replace("}", "")
                    .replaceAll("^\"|\"$", "");
            if (!cleaned.isEmpty()) {
                productList.add(cleaned);
            }
        }
        //Cleans up the list by removing the title of the list and brackets
        if (productList.contains(fileName + "\": [") && productList.contains("]")){
            productList.remove(productList.size() - 1);
            productList.remove(0);
        }
        return productList;
    }
    //Adds the little bit to the String to make the game happy
    //NOTE: When filling in file name DO NOT put the Word file in it for Ex.("ProductPrice" NOT "ProductPriceFile")
    public static String toGameFormatSimple(List<String> fileContent, String fileName){
        return "\n    \"" + fileName + "\": [\n" + (toGameFormatComplex(fileContent)) + "\n    ],";
        
    }
    
    public static String toGameFormatComplex(List<String> fileContentList){
        StringJoiner joiner = new StringJoiner("\n");
        int tracker = 0;
        for (String fileContentLists : fileContentList) {
            if(tracker != (fileContentList.size() - 1)) {
                joiner.add("        \"" + fileContentLists + "\","); // Adding the quotes and commas
            } else {
                joiner.add("        \"" + fileContentLists + "\""); //Makes sure the last product doesn't have the comma
            }
            tracker++;
        }
        return joiner.toString();
    }

}
