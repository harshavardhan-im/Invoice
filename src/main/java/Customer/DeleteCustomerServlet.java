package Customer;

import dao.Dao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteCustomer/*")
public class DeleteCustomerServlet extends HttpServlet {
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));

        PrintWriter writer = response.getWriter();

        try {
            Dao dao = new Dao();
            dao.deleteCustomer(id);
            writer.println("Successfully deleted customer with id: " + id);
        } catch (Exception e) {
            writer.println(e.getMessage());
        } finally {
            writer.flush();
        }

    }
}
