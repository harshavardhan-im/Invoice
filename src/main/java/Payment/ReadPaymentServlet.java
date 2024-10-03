package Payment;

import Payment.model.PaymentModel;
import com.google.gson.Gson;
import dao.Dao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/readPayment/*")
public class ReadPaymentServlet  extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long paymentID = Integer.parseInt(req.getPathInfo().substring(1));
        Gson gson = new Gson();

        PrintWriter writer = resp.getWriter();

        try {
            resp.setContentType("application/json");
            Dao dao = new Dao();
            PaymentModel payment = dao.readPayment(paymentID);
            String jsonResp = gson.toJson(payment);

            writer.println(jsonResp);
        } catch (Exception e) {
            resp.setContentType("string");
            writer.println(e.getMessage());
        }

        writer.flush();
    }
}
