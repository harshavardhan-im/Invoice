package dao;

import Invoice.model.ItemModel;

import java.sql.SQLException;
import java.sql.Statement;

public class ItemDao {
    public void create(ItemModel item, Statement st) throws SQLException {
        st.executeUpdate(String.format("insert into Item" +
                        "(ItemName, Unit, SellingPrice, CostPrice, SKU, StockOnHand) " +
                        "values ('%s',%d,%f,%f,'%s',%d);",
                item.ItemName,
                item.Unit,
                item.SellingPrice,
                item.CostPrice,
                item.SKU,
                item.StockOnHand
        ), Statement.RETURN_GENERATED_KEYS);
    }

}
