package store.dao;

import static store.util.constants.StringConstants.DELIMITER;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.model.Inventory;
import store.model.Promotion;

public class InventoryDao extends Dao {

    private final List<Inventory> inventories;

    public InventoryDao() throws IOException {
        this.inventories = initialize();
    }
    private List<Inventory> initialize() throws FileNotFoundException {
        List<String> fileData = readData("products.md");
        ArrayList<Inventory> inventories = new ArrayList<>();

        for (String fileDatum : fileData) {
            List<String> params = Arrays.stream(fileDatum.split(DELIMITER)).toList();
            inventories.add(new Inventory(params));
        }
        return inventories;
    }

    public List<Inventory> getAll() {
        return inventories;
    }

    public List<Inventory> findByName(String name) {
        return inventories.stream()
                .filter(inventory -> name.equals(inventory.getName()))
                .toList();
    }

    public Integer findIdBy(String name, Promotion promotion) {
        return inventories.stream()
                .filter(inventory -> name.equals(inventory.getName()) && promotion == inventory.getPromo())
                .findFirst()
                .map(inventories::indexOf)
                .orElse(-1);
    }

    public Promotion findPromotion(String itemName) {
        return inventories.stream()
                .filter(inventory -> itemName.equals(inventory.getName()) && inventory.getPromo() != Promotion.NONE)
                .findFirst()
                .map(Inventory::getPromo)
                .orElse(Promotion.NONE);
    }

    public Integer findPromotionQuantity(String name) {
        return inventories.stream()
                .filter(inventory -> name.equals(inventory.getName()) && inventory.getPromo() != Promotion.NONE)
                .findFirst()
                .map(Inventory::getQuantity)
                .orElse(0);
    }

    public Integer findNormalQuantity(String name) {
        return inventories.stream()
                .filter(inventory -> name.equals(inventory.getName()) && inventory.getPromo() == Promotion.NONE)
                .findFirst()
                .map(Inventory::getQuantity)
                .orElse(0);
    }

    public void update(Integer id, Integer newQuantity) {
        Inventory inventory = inventories.get(id);
        inventory.setQuantity(newQuantity);
        inventories.set(inventories.indexOf(inventory), inventory);
    }
}
