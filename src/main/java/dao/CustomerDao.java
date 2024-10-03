package dao;

import Customer.model.CustomerModel;

import java.sql.*;

public class CustomerDao {
    public void create(CustomerModel customer, Statement st) throws SQLException {
        st.executeUpdate(String.format("insert into Customer" +
                        "(CustomerType, CustomerName, Email, Phone) " +
                        "values (%d,'%s','%s', '%s');",
                customer.CustomerType,
                customer.CustomerName,
                customer.Email,
                customer.Phone
        ), Statement.RETURN_GENERATED_KEYS);
    }

    public CustomerModel read(long id, Statement st) throws SQLException {
        ResultSet rs = st.executeQuery(String.format("select * from Customer where ID = %d", id));
        CustomerModel customer = null;
        if(rs.next()) {
            customer = new CustomerModel();
            customer.ID = rs.getLong("ID");
            customer.CustomerType = rs.getInt("CustomerType");
            customer.CustomerName = rs.getString("CustomerName");
            customer.Email = rs.getString("Email");
            customer.Phone = rs.getString("Phone");
        }
        return customer;
    }

    public void update(CustomerModel customer, Statement st) throws SQLException {
        String fields = "";

        if (customer.CustomerType != -1) fields += "CustomerType = " + customer.CustomerType + ", ";
        if (customer.CustomerName != null) fields += "CustomerName = " + customer.CustomerName + ", ";
        if (customer.Email != null) fields += "Email = " + customer.Email + ", ";
        if (customer.Phone != null) fields += "Phone = " + customer.Phone + ", ";

        fields += "ID = " + customer.ID;

        st.executeUpdate("update Customer set " + fields + " where ID = " + customer.ID + ";");
    }

    public void delete(long id, Statement st) throws SQLException {
        st.executeUpdate(String.format("delete from Customer where ID = %d;", id));
    }
}
