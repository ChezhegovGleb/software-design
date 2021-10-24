package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HtmlPrinter {
    public static void printProducts(HttpServletResponse response, List<Product> listProducts) throws IOException {
        response.getWriter().println("<html><body>");
        for (Product product : listProducts) {
            response.getWriter().println(product.name + "\t" + product.price + "</br>");
        }
        response.getWriter().println("</body></html>");
    }

    public static void printMaxPriceProduct(HttpServletResponse response, Product product) throws IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Product with max price: </h1>");
        if (product != null) {
            response.getWriter().println(product.name + "\t" + product.price + "</br>");
        }
        response.getWriter().println("</body></html>");
    }

    public static void printMinPriceProduct(HttpServletResponse response, Product product) throws IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Product with min price: </h1>");
        if (product != null) {
            response.getWriter().println(product.name + "\t" + product.price + "</br>");
        }
        response.getWriter().println("</body></html>");
    }

    public static void printSumPrice(HttpServletResponse response, long sum) throws IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("Summary price: ");
        response.getWriter().println(sum);
        response.getWriter().println("</body></html>");
    }

    public static void printCountProducts(HttpServletResponse response, long count) throws IOException {
        response.getWriter().println("<html><body>");
        response.getWriter().println("Number of products: ");
        response.getWriter().println(count);
        response.getWriter().println("</body></html>");
    }

    public static void printOk(HttpServletResponse response) throws IOException {
        response.getWriter().println("OK");
    }

    public static void printUnknownCommand(HttpServletResponse response, String command) throws IOException {
        response.getWriter().println("Unknown command: " + command);
    }
}
