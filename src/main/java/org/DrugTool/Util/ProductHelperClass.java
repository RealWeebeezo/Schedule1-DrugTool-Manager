package org.DrugTool.Util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProductHelperClass {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ProductHelperClass() {}

    /**
     * Extracts a list of strings from the JSON content for the given key.
     * Logs a snippet on error and throws a RuntimeException if parsing fails.
     */
    public static List<String> extractListFromJson(String fileContent, String key) {
        try {
            JsonNode root = objectMapper.readTree(fileContent);
            JsonNode arrayNode = root.path(key);
            if (!arrayNode.isArray()) {
                throw new IllegalArgumentException("Expected an array for key: " + key);
            }
            List<String> result = new ArrayList<>();
            for (JsonNode element : arrayNode) {
                result.add(element.asText());
            }
            return result;
        } catch (IOException e) {
            String snippet = (fileContent == null) ? ""
                    : fileContent.substring(0, Math.min(200, fileContent.length()));
            System.err.println("Failed to parse JSON. Snippet:\n" + snippet);
            throw new RuntimeException("Failed to parse JSON for key=" + key, e);
        }
    }

    /**
     * Reads the JSON file into a string and extracts the list for the given key.
     */
    public static List<String> extractListFromJson(File jsonFile, String key) {
        try {
            String content = new String(
                    Files.readAllBytes(Paths.get(jsonFile.toURI())),
                    StandardCharsets.UTF_8
            );
            return extractListFromJson(content, key);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + jsonFile.getAbsolutePath(), e);
        }
    }

    /**
     * Creates a JSON object with a single array field under the given key.
     */
    public static String createJsonArray(String key, List<String> items) {
        try {
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for (String item : items) {
                arrayNode.add(item);
            }
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(
                            objectMapper.createObjectNode().set(key, arrayNode)
                    );
        } catch (IOException e) {
            throw new RuntimeException("Failed to create JSON array for key=" + key, e);
        }
    }

    /**
     * Finds and returns the substring between start and end markers in the given content.
     */
    public static String findWantedContent(String fileContent, String indexOfStart, String indexOfEnd) {
        int startIdx = fileContent.indexOf(indexOfStart);
        if (startIdx < 0) {
            throw new IllegalArgumentException("Start marker not found: " + indexOfStart);
        }
        startIdx += indexOfStart.length();

        int endIdx = fileContent.indexOf(indexOfEnd, startIdx);
        if (endIdx < 0) {
            throw new IllegalArgumentException("End marker not found: " + indexOfEnd);
        }

        return fileContent.substring(startIdx, endIdx);
    }

    /**
     * Reads the file and finds the substring between the given markers.
     */
    public static String findWantedContent(File file, String indexOfStart, String indexOfEnd) {
        try {
            String content = new String(
                    Files.readAllBytes(Paths.get(file.toURI())),
                    StandardCharsets.UTF_8
            );
            return findWantedContent(content, indexOfStart, indexOfEnd);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read file for findWantedContent: " + file.getAbsolutePath(), e
            );
        }
    }
}
