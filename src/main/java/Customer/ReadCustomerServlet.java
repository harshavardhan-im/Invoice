package Customer;

import com.google.gson.Gson;
import dao.Dao;
import Customer.model.CustomerModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/readCustomer/*")
public class ReadCustomerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        Gson gson = new Gson();

        PrintWriter writer = response.getWriter();

        try {
            Dao dao = new Dao();
            CustomerModel customer = dao.readCustomer(id);
            String jsonResp = gson.toJson(customer);
            response.setContentType("application/json");
            writer.println(jsonResp);
        } catch (Exception e) {
            response.setContentType("string");
            writer.println(e.getMessage());
        }
        writer.flush();
    }
}
