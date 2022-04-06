package com.example.user.dao;

import com.example.user.model.Stock;
import com.example.user.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    /**
     * Adds new user
     *
     * @return new user id in int
     */
    int addUser();

    /**
     * Puts amount of money to user's wallet
     *
     * @param userId in int
     * @param amount in double
     * @return false if user with userId doesn't exist
     */
    boolean putMoney(final int userId, final double amount);

    /**
     * Returns user's bought stocks
     *
     * @param userId in int
     * @return false if user with userId doesn't exist
     */
    List<Stock> getStocks(final int userId);

    /**
     * Calculates user's money with all stocks bought by user
     *
     * @param userId in int
     * @return sum ot -1 if user with userId doesn't exist
     */
    double calcTotalMoney(final int userId);

    /**
     * Trys to purchase a specified stock by specified user
     *
     * @param companyId in int
     * @param amount    in int
     * @param userId    in int
     * @return false if process was not successful
     */
    boolean buyStock(final int companyId, final int amount, final int userId);

    /**
     * Trys to sell a specified stock by specified user
     *
     * @param companyId in int
     * @param amount    in int
     * @param userId    in int
     * @return false if process was not successful
     */
    boolean sellStock(final int companyId, final int amount, final int userId);

    /**
     * Trys to change a stock's cost
     *
     * @param companyId in int
     * @param delta     in double
     * @param userId    in int
     * @return false if operation failed
     */
    boolean changePrice(final int companyId, final double delta, final int userId);
}
