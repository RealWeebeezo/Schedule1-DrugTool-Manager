package org.DrugTool.FileManagment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Loads and manages the "DiscoveredProducts" list from the master JSON file
 * with robust error handling and direct Jackson parsing.
 */
public class DiscoveredProductsFile {
    private static final ObjectMapper mapper = new ObjectMapper();

    private final JsonNode rootNode;
    private final List<String> discoveredProducts;

    /**
     * Constructs by parsing the full JSON content from FileMaster,
     * extracting the "DiscoveredProducts" array.
     *
     * @throws IOException if reading or parsing fails, or structure is unexpected
     */
    public DiscoveredProductsFile() throws IOException {
        // Read raw JSON from master file
        FileMaster masterFile = new FileMaster();  // may throw IOException
        String jsonContent = masterFile.fileContent;
        if (jsonContent == null || jsonContent.trim().isEmpty()) {
            throw new IOException("Master JSON content is empty");
        }

        // Parse into a JsonNode, logging snippet on error
        try {
            this.rootNode = mapper.readTree(jsonContent);
        } catch (JsonProcessingException e) {
            String snippet = jsonContent.substring(0, Math.min(200, jsonContent.length()));
            System.err.println("Invalid JSON snippet:\n" + snippet);
            throw new IOException("Failed to parse master JSON content", e);
        }

        // Extract "DiscoveredProducts" array
        JsonNode arrayNode = rootNode.get("DiscoveredProducts");
        if (arrayNode == null || !arrayNode.isArray()) {
            throw new IllegalStateException("Expected 'DiscoveredProducts' array in JSON");
        }

        // Build immutable list of product names
        List<String> temp = new ArrayList<>();
        for (JsonNode entry : arrayNode) {
            if (entry.isTextual()) {
                temp.add(entry.asText());
            } else {
                System.err.println("Skipping non-text entry in DiscoveredProducts: " + entry);
            }
        }
        this.discoveredProducts = Collections.unmodifiableList(temp);
    }

    /**
     * @return an unmodifiable list of discovered products
     */
    public List<String> getList() {
        return discoveredProducts;
    }

    /**
     * @return the number of discovered products
     */
    public int getListSize() {
        return discoveredProducts.size();
    }

    /**
     * @param index index of the product
     * @return the product at the given index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public String getFileContent(int index) {
        return discoveredProducts.get(index);
    }

    /**
     * Serializes the "DiscoveredProducts" array back into game JSON format.
     *
     * @return pretty-printed JSON array of discovered products
     * @throws IOException if serialization fails
     */
    public String getGameStringFormat() throws IOException {
        JsonNode arrayNode = rootNode.get("DiscoveredProducts");
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
        } catch (JsonProcessingException e) {
            throw new IOException("Failed to serialize DiscoveredProducts JSON", e);
        }
    }
}
