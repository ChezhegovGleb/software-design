package com.example.user.dao;

import com.example.user.model.Role;
import com.example.user.model.Stock;
import com.example.user.model.User;
import com.example.user.service.StockService;

import java.util.ArrayList;
import java.util.List;

public class UserInMemoryDao implements UserDao {
    private final List<User> users = new ArrayList<>();
    private final StockService service;

    public UserInMemoryDao(final StockService service) {
        this.service = service;
        // Add admin by default
        users.add(new User(0, Double.MAX_VALUE, Role.ADMIN));
    }

    @Override
    public int addUser() {
        users.add(new User(users.size()));
        return users.size() - 1;
    }

    private boolean isUserNotExists(final int userId) {
        return userId >= users.size();
    }

    @Override
    public boolean putMoney(final int userId, final double amount) {
        if (isUserNotExists(userId)) {
            return false;
        }
        users.get(userId).putMoney(amount);
        return true;
    }

    @Override
    public List<Stock> getStocks(final int userId) {
        if (isUserNotExists(userId)) {
            return null;
        }
        return users.get(userId).getStocks();
    }

    @Override
    public double calcTotalMoney(final int userId) {
        if (isUserNotExists(userId)) {
            return -1;
        }

        double sum = users.get(userId).getMoney();
        for (final Stock stock : users.get(userId).getStocks()) {
            sum += stock.getAmount() * service.getPrice(stock.getId());
        }
        return sum;
    }

    @Override
    public boolean buyStock(final int companyId, final int amount, final int userId) {
        if (isUserNotExists(userId)) {
            return false;
        }

        final User user = users.get(userId);
        final double price = service.getPrice(companyId);
        if (user.getMoney() < price * amount) {
            return false;
        }

        if (service.buy(companyId, amount)) {
            user.addStock(companyId, amount);
            user.putMoney(-price * amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean sellStock(final int companyId, final int amount, final int userId) {
        if (isUserNotExists(userId)) {
            return false;
        }

        final User user = users.get(userId);
        final Stock owns = user.findStock(companyId);
        if (owns.getAmount() < amount) {
            return false;
        }

        if (service.sell(companyId, amount)) {
            user.addStock(companyId, -amount);
            user.putMoney(service.getPrice(companyId) * amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean changePrice(final int companyId, final double delta, final int userId) {
        if (isUserNotExists(userId)) {
            return false;
        }

        final User user = users.get(userId);
        if (user.getRole() != Role.ADMIN) {
            return false;
        }

        return service.changePrice(companyId, delta);
    }
}
