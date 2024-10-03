package dao;

import Payment.model.PaymentModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentDao {
    public void create(PaymentModel payment, Statement st) throws SQLException {
        st.executeUpdate(String.format("insert into Payment" +
                        "(InvoiceID, PaymentNum, PaymentDate, PaymentMode, AmountReceived, AmountWithheld) " +
                        "values (%d,'%s','%s',%d,%f,%f);",
                payment.InvoiceID,
                payment.PaymentNum,
                payment.PaymentDate,
                payment.PaymentMode,
                payment.AmountReceived,
                payment.AmountWithheld
        ), Statement.RETURN_GENERATED_KEYS);
    }

    public PaymentModel read(long invoiceID, Statement st) throws SQLException {
        PaymentModel payment = null;
        ResultSet rs = st.executeQuery(String.format("Select * from Payment where InvoiceID = %d", invoiceID));

        if (rs.next()) {
            payment = new PaymentModel();
            payment.ID = rs.getLong("ID");
            payment.InvoiceID = rs.getLong("InvoiceID");
            payment.PaymentNum = rs.getString("PaymentNum");
            payment.PaymentDate = rs.getDate("PaymentDate");
            payment.PaymentMode = rs.getInt("PaymentMode");
            payment.AmountReceived = rs.getFloat("AmountReceived");
            payment.AmountWithheld = rs.getFloat("AmountWithheld");
        }

        return payment;
    }

    public void update(PaymentModel payment, Statement st) throws SQLException {
        String fields = "";

        if (payment.InvoiceID != -1) fields += "InvoiceID = " + payment.InvoiceID + ", ";
        if (payment.PaymentNum != null) fields += "PaymentNum = '" + payment.PaymentNum + "', ";
        if (payment.PaymentDate != null) fields += "PaymentDate = '" + payment.PaymentDate + "', ";
        if (payment.PaymentMode != -1) fields += "PaymentMode = " + payment.PaymentMode + ", ";
        if (payment.AmountReceived != -1) fields += "AmountReceived = " + payment.AmountReceived + ", ";
        if (payment.AmountWithheld != -1) fields += "AmountWithheld = " + payment.AmountWithheld + ", ";

        fields += "ID = " + payment.ID;

        st.executeUpdate("update Payment set " + fields + " where ID = " + payment.ID + ";");
    }

    public void delete(long invoiceID, Statement st) throws SQLException {
        st.executeUpdate("delete from Payment"+ " where InvoiceID = " + invoiceID + ";");
    }
}
