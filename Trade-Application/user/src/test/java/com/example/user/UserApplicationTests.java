package com.example.user;

import com.example.user.dao.UserInMemoryDao;
import com.example.user.service.StockService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.*;

class UserApplicationTests {
    // deprecated
    @ClassRule
    public static GenericContainer simpleWebServer
            = new FixedHostPortGenericContainer("trade:1.0-SNAPSHOT")
            .withFixedExposedPort(11451, 11451)
            .withExposedPorts(11451);

    private static final StockService service = new StockService("http://localhost", 11451);;
    private static final UserInMemoryDao dao = new UserInMemoryDao(service);
    static {
        final int gazProm = service.addCompany();
        assertTrue(service.addStocks(gazProm, 15));
        assertEquals(15, service.getAmount(gazProm));
        assertEquals(0.1, service.getPrice(gazProm));
    }

    @Test
    public void newCompany() {
        assertEquals(1, service.addCompany());
    }

    @Test
    public void change() {
        assertEquals(0.1, service.getPrice(0));

        assertTrue(dao.changePrice(0, 11.9, 0));
        assertEquals(12.0, service.getPrice(0));
    }

    @Test
    public void account() {
        assertEquals(1, dao.addUser());
        assertTrue(dao.putMoney(1, 100));
        assertTrue(dao.changePrice(0, -10, 0));
        assertTrue(dao.buyStock(0, 10, 1));
        assertEquals(10, dao.getStocks(1).get(0).getAmount());
        assertEquals(100, dao.calcTotalMoney(1));
        assertFalse(dao.changePrice(0, 1.9, 1));
    }
}
