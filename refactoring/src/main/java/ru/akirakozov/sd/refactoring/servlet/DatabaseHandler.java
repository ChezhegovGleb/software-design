package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class DatabaseHandler {
    public static void createTableProduct() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void addProduct(String name, Long price) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                String sql = "INSERT INTO PRODUCT " +
                        "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void getProducts(HttpServletResponse response) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
                response.getWriter().println("<html><body>");

                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    response.getWriter().println(name + "\t" + price + "</br>");
                }
                response.getWriter().println("</body></html>");

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void getMaxPriceProduct(HttpServletResponse response) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            }
            response.getWriter().println("</body></html>");

            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void getMinPriceProduct(HttpServletResponse response) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                response.getWriter().println(name + "\t" + price + "</br>");
            }
            response.getWriter().println("</body></html>");

            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void getSumPriceProducts(HttpServletResponse response) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");

            if (rs.next()) {
                response.getWriter().println(rs.getInt(1));
            }
            response.getWriter().println("</body></html>");

            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void getCountProducts(HttpServletResponse response) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");

            if (rs.next()) {
                response.getWriter().println(rs.getInt(1));
            }
            response.getWriter().println("</body></html>");

            rs.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
