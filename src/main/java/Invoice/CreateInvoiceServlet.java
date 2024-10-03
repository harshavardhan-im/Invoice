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

@WebServlet("/createInvoice")
public class CreateInvoiceServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        PrintWriter writer = response.getWriter();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        InvoiceModel invoice = gson.fromJson(reader, InvoiceModel.class);

        response.setContentType("string");
        try {
            Dao dao = new Dao();
            long invoiceID = dao.createInvoice(invoice);
            writer.println("Invoice Successfully Created with ID: " + invoiceID);
        } catch (Exception e ) {
            writer.println(e.getMessage());
        }
        writer.flush();
    }
}
