package Customer.model;

public class AddressModel {
    public long ID;
    public long CustomerID;
    public int AddressType = -1;
    public String AddressName;
    public String AddressLine;
    public String Country;
    public String City;
    public String State;
    public String PinCode;
    public String Phone;

    public String toString() {
        return String.format("CustomerID %d %s:\n%s,\n%s,\n%s,\n%s,\n%s,\n%s,\n%s",
                CustomerID,
                AddressType == 0 ? "BillingAddress" : "ShippingAddress",
                AddressName,
                AddressLine,
                City,
                State,
                Country,
                PinCode,
                Phone
                );
    }
}
