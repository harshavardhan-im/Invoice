package dao;

import Customer.model.AddressModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressDao {
    public void create(AddressModel address, Statement st) throws SQLException {
        st.executeUpdate(
                String.format("insert into Address" +
                                "(CustomerID, AddressType, AddressName, AddressLine, Country, City, State, PinCode, Phone)" +
                                "values (%d, %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                        address.CustomerID,
                        address.AddressType,
                        address.AddressName,
                        address.AddressLine,
                        address.Country,
                        address.City,
                        address.State,
                        address.PinCode,
                        address.Phone
                )
        );
    }

    public AddressModel read(long customerID, int addressType, Statement st) throws SQLException {
        AddressModel address = null;
        ResultSet rs = st.executeQuery(
                String.format(
                        "select * from Address where CustomerID = %d and AddressType = %d",
                        customerID, addressType
                )
        );

        if (rs.next()) {
            address = new AddressModel();
            address.ID = rs.getLong("ID");
            address.CustomerID = rs.getLong("CustomerID");
            address.AddressType = rs.getInt("AddressType");
            address.AddressName = rs.getString("AddressName");
            address.AddressLine = rs.getString("AddressLine");
            address.City = rs.getString("City");
            address.State = rs.getString("State");
            address.Country = rs.getString("Country");
            address.PinCode = rs.getString("PinCode");
            address.Phone = rs.getString("Phone");
        }

        return address;
    }

    public void update(AddressModel address, Statement st) throws SQLException {
        AddressModel readAddress = read(address.CustomerID, address.AddressType, st);

        if (readAddress == null) {
            create(address, st);
        } else {
            String fields = "";
            if (address.AddressName != null) fields += "AddressName = '" + address.AddressName + "', ";
            if (address.AddressLine != null) fields += "AddressLine = '" + address.AddressLine + "', ";
            if (address.City != null) fields += "City = '" + address.City + "', ";
            if (address.State != null) fields += "State = '" + address.State + "', ";
            if (address.Country != null) fields += "Country = '" + address.Country + "', ";
            if (address.PinCode != null) fields += "PinCode = '" + address.PinCode + "', ";
            if (address.Phone != null) fields += "Phone = '" + address.Phone + "', ";

            fields += "AddressType = " + address.AddressType;

            st.executeUpdate("update Address set " + fields +
                    " where CustomerID = " + address.CustomerID +
                    " and AddressType = " + address.AddressType + ";"
            );
        }
    }

    public void delete(long customerID, Statement st) throws SQLException {
        st.executeUpdate(String.format("delete from Address where customerID = %d;", customerID));
    }
}
