package Customer;

import com.google.gson.Gson;
import dao.Dao;
import Customer.model.CustomerModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/createCustomer")
public class CreateCustomerServlet extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();
        Gson gson = new Gson();

        CustomerModel customer = gson.fromJson(reader, CustomerModel.class);
//        System.out.println(customer);

        response.setContentType("string");
        try {
            Dao dao = new Dao();
            long customerID = dao.createCustomer(customer);
            writer.println("User Successfully Created with ID: " + customerID);
        } catch (Exception e ) {
            writer.println(e.getMessage());
        }
        writer.flush();
    }
}
