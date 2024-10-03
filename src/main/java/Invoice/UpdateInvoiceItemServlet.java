package Invoice;

import Invoice.model.InvoiceItemModel;
import com.google.gson.Gson;
import dao.Dao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateInvoiceItem")
public class UpdateInvoiceItemServlet extends HttpServlet {
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        InvoiceItemModel invoiceItem = gson.fromJson(reader, InvoiceItemModel.class);

        resp.setContentType("string");
        try {
            Dao dao = new Dao();
            dao.updateInvoiceItem(invoiceItem);
            writer.println("InvoiceItem Successfully Updated Invoice ID: " + invoiceItem.InvoiceID);
        } catch (Exception e ) {
            writer.println(e.getMessage());
        }
        writer.flush();
    }
}
