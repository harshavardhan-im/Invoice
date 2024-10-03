package dao;

import Invoice.model.InvoiceModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InvoiceDao {
    public void create(InvoiceModel invoice, Statement st) throws SQLException {
        st.executeUpdate(String.format("insert into Invoice" +
                        "(CustomerID, InvoiceNum, InvoiceDate, DueDate, ShippingCharges, Total) " +
                        "values (%d,'%s','%s','%s',%f,%f);",
                invoice.CustomerID,
                invoice.InvoiceNum,
                invoice.InvoiceDate,
                invoice.DueDate,
                invoice.ShippingCharges,
                invoice.Total
        ), Statement.RETURN_GENERATED_KEYS);
    }

    public InvoiceModel read(long id, Statement st) throws SQLException {
        ResultSet rs = st.executeQuery(String.format("select * from Invoice where ID = %d", id));
        InvoiceModel invoice = null;
        if(rs.next()) {
            invoice = new InvoiceModel();
            invoice.ID = rs.getLong("ID");
            invoice.CustomerID = rs.getLong("CustomerID");
            invoice.InvoiceNum = rs.getString("InvoiceNum");
            invoice.InvoiceDate = rs.getDate("InvoiceDate");
            invoice.DueDate = rs.getDate("DueDate");
            invoice.ShippingCharges = rs.getFloat("ShippingCharges");
            invoice.Total = rs.getFloat("Total");
        }
        return invoice;
    }

    public void update(InvoiceModel invoice, Statement st) throws SQLException {
        String fields = "";

        if (invoice.CustomerID != -1) fields += "CustomerID = " + invoice.CustomerID + ", ";
        if (invoice.InvoiceNum != null) fields += "InvoiceNum = '" + invoice.InvoiceNum + "', ";
        if (invoice.InvoiceDate != null) fields += "InvoiceDate = '" + invoice.InvoiceDate + "', ";
        if (invoice.DueDate != null) fields += "DueDate = '" + invoice.DueDate + "', ";
        if (invoice.ShippingCharges != -1) fields += "ShippingCharges = " + invoice.ShippingCharges + ", ";

        fields += "Total = " + invoice.Total + ", ";
        fields += "ID = " + invoice.ID;

        st.executeUpdate("update Invoice set " + fields + " where ID = " + invoice.ID + ";");
    }

    public void delete(long id, Statement st) throws SQLException {
        st.executeUpdate(String.format("delete from Invoice where ID = %d;", id));
    }
}
