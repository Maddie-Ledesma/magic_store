package magic.store.data.models;

public class ItemModel {
    private String itemName;
    private String description;
    private int magicId;
    private int quantity;
    private int dangerLevel;
    private double sellPrice;

    private String magicName;

    public ItemModel(
            String itemName,
            String description,
            int magicId,
            int quantity,
            int dangerLevel,
            double sellPrice,
            String magicName
    ) {
        this.itemName = itemName;
        this.description = description;
        this.magicId = magicId;
        this.quantity = quantity;
        this.dangerLevel = dangerLevel;
        this.sellPrice = sellPrice;
        this.magicName = magicName;
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

    public String getMagicName() {
        return magicName;
    }

    public void setMagicName(String magicName) {
        this.magicName = magicName;
    }

    @Override
    public String toString() {
        return "itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", magicId=" + magicId +
                ", quantity=" + quantity +
                ", dangerLevel=" + dangerLevel +
                ", sellPrice=" + String.format("%.2f", sellPrice);
    }
}
