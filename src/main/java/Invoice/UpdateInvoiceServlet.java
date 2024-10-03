package Invoice;

import Invoice.model.InvoiceModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Dao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateInvoice")
public class UpdateInvoiceServlet extends HttpServlet {
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        InvoiceModel invoice = gson.fromJson(reader, InvoiceModel.class);

        resp.setContentType("string");
        try {
            Dao dao = new Dao();
            dao.updateInvoice(invoice);
            writer.println("Successfully updated Invoice with ID: " + invoice.ID);
        } catch (Exception e) {
            writer.println(e.getMessage());
        }
        writer.flush();
    }
}
