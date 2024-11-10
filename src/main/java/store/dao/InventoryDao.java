package store.dao;

import static store.util.constants.StringConstants.PURCHASE_UNIT_DELIMITER;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.model.Inventory;
import store.model.Promotion;

public class InventoryDao extends Dao {

    private final List<Inventory> inventories = new ArrayList<>();

    public InventoryDao() throws IOException {
        initialize();
    }
    private void initialize() throws FileNotFoundException {
        List<String> fileTable = readData("products.md");

        for (String fileRow : fileTable) {
            List<String> params = Arrays.stream(fileRow.split(PURCHASE_UNIT_DELIMITER)).toList();
            inventories.add(new Inventory(params));
            if (hasSomeBeverage(params.getFirst(), fileTable)) {
                inventories.add(new Inventory(List.of(params.getFirst(), params.get(1), "0", "null")));
            }
        }
    }
    private boolean hasSomeBeverage(String itemName, List<String> fileTable) {
        return (itemName.equals("오렌지주스") || itemName.equals("탄산수"))
                && fileTable.stream().noneMatch(fileRow -> fileRow.startsWith(itemName) && fileRow.endsWith("null"));
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
