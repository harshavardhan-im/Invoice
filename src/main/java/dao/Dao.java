package dao;

import Customer.model.CustomerModel;
import Invoice.model.InvoiceItemModel;
import Invoice.model.InvoiceModel;
import Invoice.model.ItemModel;
import Payment.model.PaymentModel;

import java.sql.*;

public class Dao {
    private final Connection con;

    public Dao() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/Invoice";
        String user = "root";
        String password = "";

        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long createCustomer(CustomerModel customer) throws SQLException {
        long customerID = -1;

        try {
            con.setAutoCommit(false);

            Statement st = con.createStatement();
            CustomerDao customerDao = new CustomerDao();
            AddressDao addressDao = new AddressDao();

            customerDao.create(customer, st);
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                customerID = rs.getInt(1);
            }

            if (customerID == -1) {
                throw new RuntimeException("Cannot retrieve customerID");
            }

            if (customer.ShippingAddress == null) {
                throw new RuntimeException("Shipping Address is Required");
            }

            if (customer.BillingAddress != null) {
                customer.BillingAddress.CustomerID = customerID;
                customer.BillingAddress.AddressType = 0;
                addressDao.create(customer.BillingAddress, st);
            }
            if (customer.ShippingAddress != null) {
                customer.ShippingAddress.CustomerID = customerID;
                customer.ShippingAddress.AddressType = 1;
                addressDao.create(customer.ShippingAddress, st);
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new RuntimeException(e);
        }

        return customerID;
    }

    public CustomerModel readCustomer(long id) {
        CustomerModel customer;

        try {
            Statement st = con.createStatement();

            CustomerDao customerDao = new CustomerDao();
            AddressDao addressDao = new AddressDao();

            customer = customerDao.read(id, st);
            customer.BillingAddress = addressDao.read(id, 0, st);
            customer.ShippingAddress = addressDao.read(id, 1, st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    public void updateCustomer(CustomerModel customer) throws SQLException {
        try {
            con.setAutoCommit(false);

            Statement st = con.createStatement();
            CustomerDao customerDao = new CustomerDao();
            AddressDao addressDao = new AddressDao();

            customerDao.update(customer, st);

            if (customer.BillingAddress != null) {
                customer.BillingAddress.CustomerID = customer.ID;
                customer.BillingAddress.AddressType = 0;
                addressDao.update(customer.BillingAddress, st);
            }
            if (customer.ShippingAddress != null) {
                customer.ShippingAddress.CustomerID = customer.ID;
                customer.ShippingAddress.AddressType = 1;
                addressDao.update(customer.ShippingAddress, st);
            }

            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(long id) throws SQLException {
        try {
            con.setAutoCommit(false);

            Statement st = con.createStatement();
            CustomerDao customerDao = new CustomerDao();
            AddressDao addressDao = new AddressDao();


            CustomerModel customer = customerDao.read(id, st);
            if (customer == null) {
                throw new RuntimeException("Customer with ID does not exist");
            }

            addressDao.delete(customer.ID, st);
            customerDao.delete(customer.ID, st);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new RuntimeException(e);
        }
    }

    public long createInvoice(InvoiceModel invoice) throws SQLException {
        long invoiceID = -1;

        try {
            con.setAutoCommit(false);

            Statement st = con.createStatement();
            InvoiceDao invoiceDao  = new InvoiceDao();
            InvoiceItemDao invoiceItemDao = new InvoiceItemDao();


            float total = 0;

            for (InvoiceItemModel invoiceItem: invoice.InvoiceItems) {
                total += invoiceItem.Price * invoiceItem.Quantity;
            }

            invoice.Total = total + invoice.ShippingCharges;

            invoiceDao.create(invoice, st);
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                invoiceID = rs.getInt(1);
            }

            if (invoiceID == -1) {
                throw new RuntimeException("Cannot create Invoice");
            }

            if (invoice.InvoiceItems != null) {
                for (InvoiceItemModel invoiceItem : invoice.InvoiceItems) {
                    invoiceItem.InvoiceID = invoiceID;
                    invoiceItemDao.create(invoiceItem, st);
                }
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new RuntimeException(e);
        }

        return invoiceID;
    }

    public long createItem(ItemModel item) throws SQLException {
        long itemID = -1;

        try {
            con.setAutoCommit(false);

            Statement st = con.createStatement();
            ItemDao itemDao = new ItemDao();

            itemDao.create(item, st);
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                itemID = rs.getInt(1);
            }

            if (itemID == -1) {
                throw new RuntimeException("Cannot create Item");
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new RuntimeException(e);
        }


        return itemID;
    }

    public InvoiceModel readInvoice(long id) {
        InvoiceModel invoice;

        try {
            Statement st = con.createStatement();

            InvoiceDao invoiceDao = new InvoiceDao();
            InvoiceItemDao invoiceItemDao = new InvoiceItemDao();

            invoice = invoiceDao.read(id, st);

            if (invoice != null) {
                invoice.InvoiceItems = invoiceItemDao.read(id, st);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return invoice;
    }

    public void updateInvoice(InvoiceModel invoice) throws SQLException {
        try {
            con.setAutoCommit(false);

            Statement st = con.createStatement();
            InvoiceDao invoiceDao = new InvoiceDao();
            InvoiceItemDao invoiceItemDao = new InvoiceItemDao();

            float total = 0;
            invoice.InvoiceItems = invoiceItemDao.read(invoice.ID, st);
            for (InvoiceItemModel invoiceItem : invoice.InvoiceItems) {
                total += invoiceItem.Price * invoiceItem.Quantity;
            }

            invoice.Total = total;
            invoiceDao.update(invoice, st);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw new RuntimeException(e);
        }

    }

    public void updateInvoiceItem(InvoiceItemModel invoiceItem) throws SQLException {
        try {
            con.setAutoCommit(false);

            Statement st = con.createStatement();
            InvoiceItemDao invoiceItemDao = new InvoiceItemDao();

            InvoiceModel invoice = new InvoiceModel();
            invoice.ID = invoiceItem.InvoiceID;

            invoiceItemDao.update(invoiceItem, st);
            updateInvoice(invoice);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw new RuntimeException(e);
        }
    }

    public void deleteInvoice(long id) throws SQLException {
        try {
            con.setAutoCommit(false);
            Statement st = con.createStatement();

            InvoiceDao invoiceDao = new InvoiceDao();
            InvoiceItemDao invoiceItemDao = new InvoiceItemDao();

            invoiceItemDao.delete(id, st);
            deletePayment(id);
            invoiceDao.delete(id, st);

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw new RuntimeException(e);
        }
    }

    public void deleteInvoiceItem(long invoiceID, long itemID) throws SQLException {
        try {
            con.setAutoCommit(false);
            Statement st = con.createStatement();

            InvoiceItemDao invoiceItemDao = new InvoiceItemDao();
            invoiceItemDao.delete(invoiceID, itemID, st);

            InvoiceModel invoice = new InvoiceModel();
            invoice.ID = invoiceID;

            updateInvoice(invoice);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw new RuntimeException(e);
        }
    }

    public long createPayment(PaymentModel payment) throws SQLException {
        long paymentID = -1;

        try {
            con.setAutoCommit(false);

            Statement st = con.createStatement();

            PaymentDao paymentDao = new PaymentDao();
            paymentDao.create(payment, st);

            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                paymentID = rs.getInt(1);
            }

            if (paymentID == -1) {
                throw new RuntimeException("Cannot retrieve customerID");
            }

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw new RuntimeException(e);
        }

        return paymentID;
    }

    public PaymentModel readPayment(long invoiceID) {
        PaymentModel payment;

        try {
            Statement st = con.createStatement();

            PaymentDao paymentDao = new PaymentDao();
            payment = paymentDao.read(invoiceID, st);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return payment;
    }

    public void updatePayment(PaymentModel payment) throws SQLException {
        try {
            con.setAutoCommit(false);

            Statement st = con.createStatement();
            PaymentDao paymentDao = new PaymentDao();

            paymentDao.update(payment, st);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new RuntimeException(e);
        }
    }

    public void deletePayment(long invoiceID) throws SQLException {
        try {
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            PaymentDao paymentDao = new PaymentDao();

            paymentDao.delete(invoiceID, st);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new RuntimeException(e);
        }
    }
}
