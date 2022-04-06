package com.example.user.controller;

import com.example.user.dao.UserDao;
import com.example.user.dao.UserInMemoryDao;
import com.example.user.model.Stock;
import com.example.user.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final StockService service = new StockService("http://localhost", 11451);
    private final UserDao dao = new UserInMemoryDao(service);

    @PostMapping("/new")
    public ResponseEntity<Integer> newUser() {
        return ResponseEntity.ok(dao.addUser());
    }

    private ResponseEntity<?> processBooleanMethod(final boolean result) {
        if (result) {
            return ResponseEntity.ok(null);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/put_money")
    public ResponseEntity<?> putMoney(final int userId, final double amount) {
        return processBooleanMethod(dao.putMoney(userId, amount));
    }

    @GetMapping("/stocks/{userId}")
    public ResponseEntity<?> getStocks(final @PathVariable int userId) {
        final List<Stock> stocks = dao.getStocks(userId);
        if (stocks != null) {
            return ResponseEntity.ok(stocks);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/calc_total/{userId}")
    public ResponseEntity<?> getTotalAmount(final @PathVariable int userId) {
        final double sum = dao.calcTotalMoney(userId);
        if (sum == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(sum);
    }

    @PostMapping("/buy_stocks")
    public ResponseEntity<?> buy(final int userId,
                                 final int companyId,
                                 final int amount) {
        return processBooleanMethod(dao.buyStock(companyId, amount, userId));
    }

    @PostMapping("/sell_stocks")
    public ResponseEntity<?> sell(final int userId,
                                  final int companyId,
                                  final int amount) {
        return processBooleanMethod(dao.sellStock(companyId, amount, userId));
    }
}
