package store.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.model.Inventory;

public class InventoryDao {

    private final Map<Integer, Inventory> inventories = new LinkedHashMap<>();

    public Map<Integer, Inventory> getAll() {
        return inventories;
    }

    public List<Inventory> findBy(String name) {
        return inventories.values().stream()
                .filter(inventory -> name.equals(inventory.getName()))
                .toList();
    }

    public void add(Inventory inventory) {
        inventories.put(inventory.getId(), inventory);
    }

    public void update(Inventory inventory, int newQuantity) {
        inventory.setQuantity(newQuantity);
        inventories.put(inventory.getId(), inventory);
    }
}
