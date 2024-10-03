package Payment;

import dao.Dao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deletePayment/*")
public class DeletePaymentServlet extends HttpServlet {
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long invoiceID = Integer.parseInt(req.getPathInfo().substring(1));

        PrintWriter writer = resp.getWriter();
        resp.setContentType("string");
        try {
            Dao dao = new Dao();
            dao.deletePayment(invoiceID);

            writer.println("Successfully deleted payment with InvoiceID: " + invoiceID);
        } catch (Exception e) {
            writer.println(e.getMessage());
        }

        writer.flush();
    }
}
