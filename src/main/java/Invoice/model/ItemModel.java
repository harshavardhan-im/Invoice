package Invoice.model;

public class ItemModel {
    public long ID;
    public String ItemName;
    public int Unit;
    public float SellingPrice;
    public float CostPrice;
    public String  SKU;
    public int StockOnHand;

    public String toString() {
        return "ID: " + ID + "\n" +
                "ItemName: " + ItemName + "\n" +
                "Unit: " + Unit + "\n" +
                "SellingPrice: " + SellingPrice + "\n" +
                "CostPrice: " + CostPrice + "\n" +
                "SKU: " + SKU + "\n" +
                "StockOnHand: " + StockOnHand + "\n";
    }
}
