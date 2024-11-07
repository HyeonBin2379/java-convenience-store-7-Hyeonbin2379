package store.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import store.dao.InventoryDao;
import store.model.Inventory;

public class FileController {

    private static final String DIRECTORY_PATH = "src/main/resources/";

    public List<String> readData(String filename) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileReader(DIRECTORY_PATH + filename));
        List<String> fileData = new ArrayList<>();

        fileReader.nextLine();
        while (fileReader.hasNext()) {
            fileData.add(fileReader.nextLine());
        }
        return fileData;
    }

    public InventoryDao loadInventoryData() throws IOException {
        List<String> fileData = readData("products.md");
        InventoryDao inventoryDao = new InventoryDao();
        for (int i = 0; i < fileData.size(); i++) {
            List<String> params = Arrays.stream(fileData.get(i).split(",")).toList();
            inventoryDao.add(new Inventory(i, params));
        }
        return inventoryDao;
    }
}
