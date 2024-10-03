package Customer.model;

public class CustomerModel {
    public long ID;
    public int CustomerType = -1;
    public String CustomerName;
    public String Email;
    public String Phone;
    public AddressModel BillingAddress;
    public AddressModel ShippingAddress;

    public String toString() {
        return "Customer " +
                "\nID: " + ID +
                "\nType: " + CustomerType +
                "\nCustomerName: " + CustomerName +
                "\nCustomerEmail: " + Email +
                "\nCustomerPhone: " + Phone;
    }
}
