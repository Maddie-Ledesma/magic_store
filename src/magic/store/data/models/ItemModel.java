package magic.store.data.models;

public class ItemModel {
    private String itemName;
    private String description;
    private int magicId;
    private int quantity;
    private int dangerLevel;
    private double sellPrice;

    public ItemModel(String itemName, String description, int magicId, int quantity, int dangerLevel, double sellPrice) {
        this.itemName = itemName;
        this.description = description;
        this.magicId = magicId;
        this.quantity = quantity;
        this.dangerLevel = dangerLevel;
        this.sellPrice = sellPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMagicId() {
        return magicId;
    }

    public void setMagicId(int magicId) {
        this.magicId = magicId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
}
