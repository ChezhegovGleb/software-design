package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    static List<Product> getProducts() {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

                ArrayList<Product> listProducts = new ArrayList<>();

                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    listProducts.add(new Product(name, (long) price));
                }

                rs.close();
                stmt.close();
                return listProducts;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Product getMaxPriceProduct(HttpServletResponse response) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");

            Product product = null;
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                product = new Product(name, (long) price);
            }

            rs.close();
            stmt.close();
            return product;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Product getMinPriceProduct(HttpServletResponse response) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");

            Product product = null;
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                product = new Product(name, (long) price);
            }

            rs.close();
            stmt.close();
            return product;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static long getSumPriceProducts(HttpServletResponse response) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");

            long sum = 0;
            if (rs.next()) {
                sum = rs.getInt(1);
            }

            rs.close();
            stmt.close();
            return sum;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static long getCountProducts(HttpServletResponse response) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");

            long count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            stmt.close();
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}