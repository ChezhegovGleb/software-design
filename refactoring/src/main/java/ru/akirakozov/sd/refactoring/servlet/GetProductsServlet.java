package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DatabaseHandler;
import ru.akirakozov.sd.refactoring.dataclasses.Product;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> listProducts = DatabaseHandler.getProducts();

        HtmlPrinter.printProducts(response, listProducts);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
