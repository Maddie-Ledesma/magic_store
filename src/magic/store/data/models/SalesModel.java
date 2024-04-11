package magic.store.data.models;

import java.util.Date;

public class SalesModel {

    private int id;
    private int costumerId;
    private Date salesDate;

    public SalesModel(int id, int costumerId, Date salesDate) {
        this.id = id;
        this.costumerId = costumerId;
        this.salesDate = salesDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(int costumerId) {
        this.costumerId = costumerId;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }
}
