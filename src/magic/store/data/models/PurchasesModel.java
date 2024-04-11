package magic.store.data.models;

import java.util.Date;

public class PurchasesModel {

    private int id;
    private String itemName;
    private int quantity;
    private double payPrice;
    private Date purchaseDate;

    public PurchasesModel(int id, String itemName, int quantity, double payPrice, Date purchaseDate) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.payPrice = payPrice;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
