package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DatabaseHandler;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        DatabaseHandler.addProduct(name, price);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        HtmlPrinter.printOk(response);
    }
}
