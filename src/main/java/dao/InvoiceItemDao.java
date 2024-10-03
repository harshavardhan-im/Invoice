package dao;

import Invoice.model.InvoiceItemModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InvoiceItemDao {
    public void create(InvoiceItemModel invoiceItem, Statement st) throws SQLException {
        st.executeUpdate(String.format("insert into InvoiceItem" +
                        "(InvoiceID, ItemID, Quantity, Price) " +
                        "values (%d,%d,%d,%f);",
                invoiceItem.InvoiceID,
                invoiceItem.ItemID,
                invoiceItem.Quantity,
                invoiceItem.Price
        ), Statement.RETURN_GENERATED_KEYS);
    }

    public ArrayList<InvoiceItemModel> read(long invoiceID, Statement st) throws SQLException {
        ResultSet rs = st.executeQuery(String.format("select * from InvoiceItem where InvoiceID = %d", invoiceID));
        ArrayList<InvoiceItemModel> invoiceItems = new ArrayList<>();
        while (rs.next()) {
            InvoiceItemModel invoiceItem = new InvoiceItemModel();
            invoiceItem.InvoiceID = rs.getLong("InvoiceID");
            invoiceItem.ItemID = rs.getLong("ItemID");
            invoiceItem.Quantity = rs.getInt("Quantity");
            invoiceItem.Price = rs.getFloat("Price");
            invoiceItems.add(invoiceItem);

        }
        return invoiceItems;
    }

    public void update(InvoiceItemModel invoiceItem, Statement st) throws SQLException {
        String fields = "";

        if (invoiceItem.Quantity != -1) fields += "Quantity = " + invoiceItem.Quantity + ",";
        if (invoiceItem.Price != -1) fields += "Price = " + invoiceItem.Price + ",";
        fields += "InvoiceID = " + invoiceItem.InvoiceID;

        st.executeUpdate("update InvoiceItem set " + fields +
                " where InvoiceID = " + invoiceItem.InvoiceID +
                " and ItemID = " + invoiceItem.ItemID + ";");
    }

    public void delete(long invoiceID, long itemId, Statement st) throws SQLException {
        st.executeUpdate(String.format(
                "delete from InvoiceItem where InvoiceID = %d and ItemID = %d;",
                invoiceID, itemId
        ));
    }

    public void delete(long invoiceID, Statement st) throws SQLException {
        st.executeUpdate(String.format(
                "delete from InvoiceItem where InvoiceID = %d",
                invoiceID
        ));
    }
}
