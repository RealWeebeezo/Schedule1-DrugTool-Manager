package org.DrugTool.ActionClasses;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.DrugTool.FileManagment.FileMaster;

/**
 * Loads and manages product information (prices and mix recipes) from the master JSON file,
 * with robust error handling and JSON parsing safeguards.
 */
public class ProductHandler {
    private static final ObjectMapper mapper = new ObjectMapper();

    private final JsonNode rootNode;
    private final Map<String, Integer> productPriceMap = new HashMap<>();

    /**
     * Constructs by parsing the full JSON content from FileMaster,
     * validating structure, and building the price map.
     *
     * @throws IOException if the master file can't be read or JSON is malformed
     */
    public ProductHandler() throws IOException {
        FileMaster masterFile = new FileMaster();  // may throw IOException
        String jsonContent = masterFile.fileContent;

        if (jsonContent == null || jsonContent.trim().isEmpty()) {
            throw new IOException("Master JSON content is empty");
        }

        try {
            this.rootNode = mapper.readTree(jsonContent);
        } catch (JsonProcessingException e) {
            String snippet = jsonContent.substring(0, Math.min(200, jsonContent.length()));
            System.err.println("Invalid JSON snippet:\n" + snippet);
            throw new IOException("Failed to parse JSON content", e);
        }

        loadProductPrices();
    }

    /**
     * Extracts the "ProductPrices" array and populates productPriceMap.
     * @throws IllegalStateException if the JSON structure is unexpected
     */
    private void loadProductPrices() {
        JsonNode pricesNode = rootNode.get("ProductPrices");
        if (pricesNode == null || !pricesNode.isArray()) {
            throw new IllegalStateException("Expected 'ProductPrices' array in JSON");
        }

        for (JsonNode entry : pricesNode) {
            JsonNode nameNode = entry.get("String");
            JsonNode priceNode = entry.get("Int");
            if (nameNode == null || !nameNode.isTextual() || priceNode == null || !priceNode.isInt()) {
                System.err.println("Skipping invalid price entry: " + entry);
                continue;
            }
            productPriceMap.put(nameNode.asText().toLowerCase(), priceNode.asInt());
        }
    }

    /**
     * Returns the price of the given product (case-insensitive).
     * @throws IllegalArgumentException if the product is not found
     */
    public int getProductPrice(String productName) {
        Integer price = productPriceMap.get(productName.toLowerCase());
        if (price == null) {
            throw new IllegalArgumentException("Product not found: " + productName);
        }
        return price;
    }

    /**
     * Removes the product from the internal map (case-insensitive).
     */
    public void removeProduct(String productName) {
        productPriceMap.remove(productName.toLowerCase());
    }

    /**
     * @return the number of products currently loaded
     */
    public int getNumberOfProducts() {
        return productPriceMap.size();
    }

    /**
     * @return an unmodifiable set of loaded product names (lowercase)
     */
    public Set<String> getProductNames() {
        return Collections.unmodifiableSet(productPriceMap.keySet());
    }

    /**
     * Serializes the "MixRecipes" array from the original JSON into a pretty-printed JSON string.
     * @throws IOException if serialization fails or JSON structure is unexpected
     */
    public String getMixRecipesJson() throws IOException {
        JsonNode mixNode = rootNode.get("MixRecipes");
        if (mixNode == null || !mixNode.isArray()) {
            throw new IllegalStateException("Expected 'MixRecipes' array in JSON");
        }
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mixNode);
        } catch (JsonProcessingException e) {
            throw new IOException("Failed to serialize MixRecipes JSON", e);
        }
    }
}
