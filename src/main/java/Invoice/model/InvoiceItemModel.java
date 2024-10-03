package Invoice.model;

public class InvoiceItemModel {
    public long InvoiceID;
    public long ItemID;
    public int Quantity = -1;
    public float Price = -1;

    public String toString() {
        return "InvoiceID: " + InvoiceID + "\n" +
                "ItemID: " + ItemID + "\n" +
                "Quantity: " + Quantity + "\n" +
                "Price: " + Price + "\n";
    }
}