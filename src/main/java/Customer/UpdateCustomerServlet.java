package Customer;

import dao.Dao;
import Customer.model.CustomerModel;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateCustomer")
public class UpdateCustomerServlet extends HttpServlet {
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();

        CustomerModel customer = gson.fromJson(reader, CustomerModel.class);

        response.setContentType("string");
        try {
            Dao dao = new Dao();
            dao.updateCustomer(customer);
            writer.println("Successfully updated customer with ID: " + customer.ID);
            writer.flush();
        } catch (Exception e) {
            writer.println(e.getMessage());
            writer.flush();
        }
    }
}
