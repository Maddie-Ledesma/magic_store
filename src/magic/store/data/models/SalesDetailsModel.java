package magic.store.data.models;

public class SalesDetailsModel {

    private int salesId;
    private String itemName;
    private double sellPrice;
    private int quantity;

    public SalesDetailsModel(int salesId, String itemName, double sellPrice, int quantity) {
        this.salesId = salesId;
        this.itemName = itemName;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
