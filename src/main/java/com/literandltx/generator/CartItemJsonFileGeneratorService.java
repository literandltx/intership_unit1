package com.literandltx.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.literandltx.model.CartItem;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class CartItemJsonFileGeneratorService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Random random = new Random();
    private static final String PRESENT = "dummy"; // Dummy value to ignore 0 array index

    private final String[] titles = {PRESENT, "Spirited Away", "The Shawshank Redemption", "The Legend of Zelda: Breath of the Wild"};
    private final String[] descriptions = {PRESENT, "A Japanese animated fantasy film", "A drama film based on a Stephen King novella", "An action-adventure video game"};
    private final String[] groups = {PRESENT, "Anime", "Movie", "Game", "Book"};
    private final String[][] labels = {
            {PRESENT, PRESENT},
            {"fantasy", "adventure", "supernatural"},
            {"drama", "prison", "redemption"},
            {"action", "adventure", "open-world"},
            {"fiction", "classic", "novel"}
    };

    public static void main(String[] args) {
        final int numFiles = 128;
        final int items = 100_000;
        final String generatedFilesPath = "path/to/generated/files/";
        CartItemJsonFileGeneratorService generatorService = new CartItemJsonFileGeneratorService();

        generatorService.generateAndWriteJsonFiles(generatedFilesPath, numFiles, items);
    }

    public void generateAndWriteJsonFiles(final String folderPath, final int numFiles, final int quantity) {
        final File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs(); // Create folder if it doesn't exist
        }

        for (int i = 1; i <= numFiles; i++) {
            final String filename = folderPath + File.separator + "example" + i + ".json";
            final int numItems = random.nextInt(quantity) + 1;

            generateAndWriteJsonFile(filename, numItems);
        }

        System.out.println("Finished generating " + numFiles + " files"); // log
    }

    private void generateAndWriteJsonFile(final String filename, final int itemQuantity) {
        final List<CartItem> cartItems = new ArrayList<>();

        for (int i = 0; i < itemQuantity; i++) {
            int index = random.nextInt(titles.length);
            index = index == 0 ? 1 : index;

            final double rank = Math.round(random.nextDouble() * 10 * 100.0) / 100.0;
            final String title = titles[index];
            final String description = descriptions[index];
            final String group = groups[index];
            final String[] itemLabels = labels[index];

            final String labelString = String.join(", ", itemLabels);

            final CartItem cartItem = new CartItem(title, description, group, labelString, rank);
            cartItems.add(cartItem);
        }

        try {
            mapper.writeValue(new File(filename), new ArrayList<>(cartItems));
            System.out.println("JSON file created successfully: " + filename); // log
        } catch (IOException e) {
            throw new RuntimeException("Could not create JSON file: " + filename, e); // log
        }
    }
}

