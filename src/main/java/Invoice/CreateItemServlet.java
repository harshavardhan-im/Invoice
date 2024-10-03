package Invoice;

import Invoice.model.ItemModel;
import com.google.gson.Gson;
import dao.Dao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/createItem")
public class CreateItemServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();

        ItemModel item = gson.fromJson(reader, ItemModel.class);

        response.setContentType("string");
        try {
            Dao dao = new Dao();
            long itemID = dao.createItem(item);
            writer.println("Item Successfully Created with ID: " + itemID);
        } catch (Exception e ) {
            writer.println(e.getMessage());
        }
        writer.flush();
    }
}
