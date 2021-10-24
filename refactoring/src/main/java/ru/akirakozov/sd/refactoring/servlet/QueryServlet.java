package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.DatabaseHandler;
import ru.akirakozov.sd.refactoring.dataclasses.Product;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            Product product = DatabaseHandler.getMaxPriceProduct(response);
            HtmlPrinter.printMaxPriceProduct(response, product);
        } else if ("min".equals(command)) {
            Product product = DatabaseHandler.getMinPriceProduct(response);
            HtmlPrinter.printMinPriceProduct(response, product);
        } else if ("sum".equals(command)) {
            long sum = DatabaseHandler.getSumPriceProducts(response);
            HtmlPrinter.printSumPrice(response, sum);
        } else if ("count".equals(command)) {
            long count = DatabaseHandler.getCountProducts(response);
            HtmlPrinter.printCountProducts(response, count);
        } else {
            HtmlPrinter.printUnknownCommand(response, command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
