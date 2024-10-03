package Invoice.model;

import java.sql.Date;
import java.util.ArrayList;

public class InvoiceModel {
    public long ID;
    public long CustomerID = -1;
    public String InvoiceNum;
    public Date InvoiceDate;
    public Date DueDate;
    public float ShippingCharges = -1;
    public float Total = -1;

    public ArrayList<InvoiceItemModel> InvoiceItems = null;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("ID: " + ID + "\n" +
                "CustomerID: " + CustomerID + "\n" +
                "InvoiceNum: " + InvoiceNum + "\n" +
                "InvoiceDate: " + InvoiceDate.toString() + "\n" +
                "DueDate: " + DueDate.toString() + "\n" +
                "ShippingCharges: " + ShippingCharges + "\n" +
                "Total: " + Total + "\n" +
                "InvoiceItems: \n");
        if (InvoiceItems != null) {
            for (InvoiceItemModel invoiceItem : InvoiceItems) {
                str.append(invoiceItem.toString());
            }
        }
        return str.toString();
    }
}
