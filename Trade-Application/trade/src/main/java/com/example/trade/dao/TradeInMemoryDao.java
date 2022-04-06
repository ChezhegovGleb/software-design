package com.example.trade.dao;

import java.util.ArrayList;
import java.util.List;

public class TradeInMemoryDao implements TradeDao {
    private final List<Integer> amounts = new ArrayList<>();
    private final List<Double> prices = new ArrayList<>();

    private void ensureData() {
        assert amounts.size() == prices.size();
    }

    private boolean isCompanyNotExists(final int companyId) {
        return companyId >= amounts.size();
    }

    @Override
    public int addCompany() {
        ensureData();
        amounts.add(0);
        prices.add(MIN_PRICE);

        return amounts.size() - 1;
    }

    @Override
    public boolean addStock(final int companyId, final int amount) {
        ensureData();
        if (isCompanyNotExists(companyId)) {
            return false;
        }

        amounts.set(companyId, amounts.get(companyId) + amount);
        return true;
    }

    @Override
    public int getCurrenAmount(final int companyId) {
        ensureData();
        if (isCompanyNotExists(companyId)) {
            return -1;
        }
        return amounts.get(companyId);
    }

    @Override
    public double getCurrentPrice(final int companyId) {
        ensureData();
        if (isCompanyNotExists(companyId)) {
            return -1;
        }
        return prices.get(companyId);
    }

    @Override
    public boolean buyStock(final int companyId, final int amount) {
        ensureData();
        if (isCompanyNotExists(companyId) || amount < 0 || amounts.get(companyId) < amount) {
            return false;
        }

        amounts.set(companyId, amounts.get(companyId) - amount);
        return true;
    }

    @Override
    public boolean sellStock(final int companyId, final int amount) {
        ensureData();
        if (isCompanyNotExists(companyId) || amount < 0) {
            return false;
        }

        amounts.set(companyId, amounts.get(companyId) + amount);
        return false;
    }

    @Override
    public boolean changePrice(final int companyId, final double delta) {
        ensureData();
        if (isCompanyNotExists(companyId)) {
            return false;
        }

        final double result = prices.get(companyId) + delta;
        prices.set(companyId, result <= 0 ? MIN_PRICE : result);
        return true;
    }
}
