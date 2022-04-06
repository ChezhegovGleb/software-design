package com.example.trade;

import com.example.trade.dao.TradeDao;
import com.example.trade.dao.TradeInMemoryDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TradeApplicationTests {
    @Test
    public void daoTest() {
        final TradeDao dao = new TradeInMemoryDao();

        dao.addCompany();
        dao.addStock(0, 10);

        assertEquals(10, dao.getCurrenAmount(0));
        assertEquals(TradeDao.MIN_PRICE, dao.getCurrentPrice(0));

        assertEquals(-1, dao.getCurrenAmount(1));
        assertEquals(-1, dao.getCurrentPrice(1));
    }
}
