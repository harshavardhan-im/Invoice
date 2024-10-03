package Invoice;

import dao.Dao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteInvoice/*")
public class DeleteInvoiceServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getPathInfo().substring(1));

        PrintWriter writer = resp.getWriter();

        try {
            Dao dao = new Dao();
            dao.deleteInvoice(id);
            writer.println("Successfully deleted Invoice with InvoiceID: " + id);
        } catch (Exception e) {
            writer.println(e.getMessage());
        } finally {
            writer.flush();
        }

    }
}
