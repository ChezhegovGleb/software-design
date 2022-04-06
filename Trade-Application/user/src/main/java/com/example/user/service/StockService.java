package com.example.user.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

public class StockService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String host;
    private final int port;

    public StockService(final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    private HttpResponse<String> doRequest(final HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpResponse<String> getRequest(final String uri) {
        try {
            return doRequest(HttpRequest.newBuilder(new URI(uri)).GET().build());
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private HttpResponse<String> postRequest(final String uri) {
        try {
            return doRequest(HttpRequest.newBuilder(new URI(uri)).POST(BodyPublishers.noBody()).build());
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String request(final Object... args) {
        final StringBuilder builder = new StringBuilder(String.format("%s:%d/trade", host, port));
        for (final Object arg : args) {
            builder.append(arg);
        }
        return builder.toString();
    }

    private int getInteger(final HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            return Integer.parseInt(response.body());
        }
        return -1;
    }

    private double getDouble(final HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            return Double.parseDouble(response.body());
        }
        return -1;
    }

    private boolean getBoolean(final HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            return Boolean.parseBoolean(response.body());
        }
        return false;
    }

    /**
     * Returns current company's stock price
     *
     * @param companyId in int
     * @return current stock price or -1 if company with companyId doesn't exist or error occurred
     */
    public double getPrice(final int companyId) {
        final HttpResponse<String> response = getRequest(request("/get_price", "/", companyId));
        return getDouble(response);
    }

    /**
     * Returns current company's stock left amount
     *
     * @param companyId in int
     * @return current stocks or -1 if company with companyId doesn't exist or error occurred
     */
    public int getAmount(final int companyId) {
        final HttpResponse<String> response = getRequest(request("/get_amount", "/", companyId));
        return getInteger(response);
    }

    /**
     * Trys to buy company's stocks in specified amount
     *
     * @param companyId in int
     * @param amount    in int
     * @return false if company with companyId doesn't exist or doesn't have such stocks or amount is less than 1
     */
    public boolean buy(final int companyId, final int amount) {
        final HttpResponse<String> response = postRequest(request("/buy",
                "?companyId=", companyId,
                "&amount=", amount));
        return response.statusCode() == 200;
    }

    /**
     * Trys to sell company's stocks in specified amount
     *
     * @param companyId in int
     * @param amount    in int
     * @return false if company with companyId doesn't exist or amount is less than 1
     */
    public boolean sell(final int companyId, final int amount) {
        final HttpResponse<String> response = postRequest(request("/sell",
                "?companyId=", companyId,
                "&amount=", amount));
        return response.statusCode() == 200;
    }

    /**
     * Trys to change a stock's cost
     *
     * @param companyId in int
     * @param delta     in double
     * @return false if operation failed
     */
    public boolean changePrice(final int companyId, final double delta) {
        final HttpResponse<String> response = postRequest(request("/change",
                "?companyId=", companyId,
                "&delta=", delta));
        return response.statusCode() == 200;
    }

    /**
     * Adds a new company
     *
     * @return company id in int
     */
    public int addCompany() {
        final HttpResponse<String> response = postRequest(request("/add_company"));
        return getInteger(response);
    }

    public boolean addStocks(final int companyId, final int amount) {
        final HttpResponse<String> response = postRequest(request("/add_stock",
                "?companyId=", companyId,
                "&amount=", amount));
        return response.statusCode() == 200;
    }
}
