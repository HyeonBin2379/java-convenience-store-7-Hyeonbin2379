package store.dao;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.model.Inventory;
import store.model.Promotion;

public class InventoryDao extends Dao<Inventory> {

    private static final String COLUMN_DELIMITER = ",";

    private final List<Inventory> inventories = new ArrayList<>();

    @Override
    public void initialize() throws FileNotFoundException {
        List<String> fileTable = readData("products.md");

        for (String fileRow : fileTable) {
            List<String> params = Arrays.stream(fileRow.split(COLUMN_DELIMITER)).toList();
            inventories.add(new Inventory(params));
            if (hasOnlyPromotionInventory(params.getFirst(), params.get(2), fileTable)) {
                inventories.add(new Inventory(List.of(params.getFirst(), params.get(1), "0", "null")));
            }
        }
    }
    private boolean hasOnlyPromotionInventory(String itemName, String promotionName, List<String> fileTable) {
        return !promotionName.equals("null")
                && fileTable.stream().noneMatch(fileRow -> fileRow.startsWith(itemName) && fileRow.endsWith("null"));
    }

    @Override
    public List<Inventory> getAll() {
        return inventories;
    }

    public List<Inventory> findByName(String name) {
        return inventories.stream()
                .filter(inventory -> inventory.isMatched(name))
                .toList();
    }

    public Integer findIdBy(String itemName, Promotion promotion) {
        return inventories.stream()
                .filter(inventory -> inventory.isMatched(itemName) && inventory.isMatched(promotion))
                .findFirst()
                .map(inventories::indexOf)
                .orElse(-1);
    }

    public Promotion findPromotion(String itemName) {
        return inventories.stream()
                .filter(inventory -> inventory.isMatched(itemName) && inventory.isPromotionNotNull())
                .findFirst()
                .map(Inventory::getPromotion)
                .orElse(Promotion.NULL);
    }

    public Integer findPromotionQuantity(String itemName) {
        return inventories.stream()
                .filter(inventory -> inventory.isMatched(itemName) && inventory.isPromotionNotNull())
                .findFirst()
                .map(Inventory::getQuantity)
                .orElse(0);
    }

    public Integer findNormalQuantity(String itemName) {
        return inventories.stream()
                .filter(inventory -> inventory.isMatched(itemName) && !inventory.isPromotionNotNull())
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
