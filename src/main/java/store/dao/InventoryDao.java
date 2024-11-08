package store.dao;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.model.Inventory;
import store.model.Promotion;

public class InventoryDao extends Dao {

    private final List<Inventory> inventories = new ArrayList<>();

    public InventoryDao() throws FileNotFoundException {
        List<String> fileData = readData("products.md");
        for (int i = 0; i < fileData.size(); i++) {
            List<String> params = Arrays.stream(fileData.get(i).split(",")).toList();
            inventories.add(new Inventory(i, params));
        }
    }

    public List<Inventory> getAll() {
        return inventories;
    }

    public List<Inventory> findByName(String name) {
        return inventories.stream()
                .filter(inventory -> name.equals(inventory.getName()))
                .toList();
    }

    public int findIdBy(String name, Promotion promotion) {
        return inventories.stream()
                .filter(inventory -> name.equals(inventory.getName()) && promotion == inventory.getPromo())
                .findFirst()
                .map(Inventory::getId)
                .orElse(-1);
    }

    public Promotion findPromotion(String itemName) {
        return inventories.stream()
                .filter(inventory -> itemName.equals(inventory.getName()) && inventory.getPromo() != Promotion.NONE)
                .findFirst()
                .map(Inventory::getPromo)
                .orElse(Promotion.NONE);
    }

    public int findPromotionQuantity(String name) {
        return inventories.stream()
                .filter(inventory -> name.equals(inventory.getName()) && inventory.getPromo() != Promotion.NONE)
                .findFirst()
                .map(Inventory::getQuantity)
                .orElse(0);
    }

    public int findNormalQuantity(String name) {
        return inventories.stream()
                .filter(inventory -> name.equals(inventory.getName()) && inventory.getPromo() == Promotion.NONE)
                .findFirst()
                .map(Inventory::getQuantity)
                .orElse(0);
    }

    public void update(int id, int newQuantity) {
        Inventory inventory = inventories.get(id);
        inventory.setQuantity(newQuantity);
        inventories.set(inventory.getId(), inventory);
    }
}
