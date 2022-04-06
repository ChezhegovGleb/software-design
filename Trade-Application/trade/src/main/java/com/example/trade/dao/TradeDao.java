package com.example.trade.dao;

public interface TradeDao {
    /**
     * The minimal price of all stocks
     */
    double MIN_PRICE = 0.1;

    /**
     * Adds new company
     *
     * @return added company id
     */
    int addCompany();

    /**
     * Adds stock in company
     *
     * @param companyId in int
     * @param amount    in int
     * @return existence of company with id = companyId
     */
    boolean addStock(final int companyId, final int amount);

    /**
     * Returns current company <em>stock amount</em>
     *
     * @param companyId in int
     * @return amount in int, -1 if company with companyId doesn't exist
     */
    int getCurrenAmount(final int companyId);

    /**
     * Returns current company <em>stock price</em>.
     *
     * @param companyId in int
     * @return price in double, -1 if company with companyId doesn't exist
     */
    double getCurrentPrice(final int companyId);

    /**
     * Try to buy a specified amounts of company's stock
     *
     * @param companyId in int
     * @param amount    in int
     * @return true if derived parameters are legit and company has that amount od stocks
     */
    boolean buyStock(final int companyId, final int amount);

    /**
     * Sells a specified amount of company's stock
     *
     * @param companyId in int
     * @param amount    in int
     * @return true if company with companyId exists
     */
    boolean sellStock(final int companyId, final int amount);

    /**
     * Adds derived delta to a price of company's stock.
     * If the price is negative after operation, it will const MIN_PRICE
     *
     * @param companyId in int
     * @param delta     in double
     * @return true if company with companyId exists
     */
    boolean changePrice(final int companyId, final double delta);
}
