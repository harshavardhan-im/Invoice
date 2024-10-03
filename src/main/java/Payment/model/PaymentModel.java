package Payment.model;

import java.sql.Date;

public class PaymentModel {
    public long ID;
    public long InvoiceID = -1;
    public String PaymentNum;
    public Date PaymentDate;
    public int PaymentMode = -1;
    public float AmountReceived = -1;
    public float AmountWithheld = -1;

    @Override
    public String toString() {
        return "ID: " + ID + "\n" +
                "InvoiceID: " + InvoiceID + "\n" +
                "PaymentNum: " + PaymentNum + "\n" +
                "PaymentDate: " + PaymentDate.toString() + "\n" +
                "PaymentMode: " + PaymentMode + "\n" +
                "AmountReceived: " + AmountReceived + "\n" +
                "AmountWithheld: " + AmountWithheld + "\n";
    }
}
