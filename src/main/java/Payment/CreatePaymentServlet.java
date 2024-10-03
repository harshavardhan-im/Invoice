package Payment;

import Payment.model.PaymentModel;
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

@WebServlet("/createPayment")
public class CreatePaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        PaymentModel payment = gson.fromJson(reader, PaymentModel.class);

        resp.setContentType("string");
        try {
            Dao dao = new Dao();
            long paymentID = dao.createPayment(payment);
            writer.println("Invoice Successfully Created with ID: " + paymentID);
        } catch (Exception e ) {
            writer.println(e.getMessage());
        }
        writer.flush();
    }
}
