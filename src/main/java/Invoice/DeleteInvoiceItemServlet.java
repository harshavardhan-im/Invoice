package Invoice;

import com.google.gson.Gson;
import dao.Dao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteInvoiceItem")
public class DeleteInvoiceItemServlet  extends HttpServlet {
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        DeleteReq deleteReq = gson.fromJson(reader, DeleteReq.class);

        try {
            Dao dao = new Dao();
            dao.deleteInvoiceItem(deleteReq.InvoiceID, deleteReq.ItemID);
            writer.println(
                    "Successfully deleted InvoiceItem with InvoiceID: " + deleteReq.InvoiceID +
                            " and ItemID: " + deleteReq.ItemID
            );
        } catch (Exception e) {
            writer.println(e.getMessage());
        } finally {
            writer.flush();
        }

    }
}

class DeleteReq {
    public long InvoiceID;
    public long ItemID;
}