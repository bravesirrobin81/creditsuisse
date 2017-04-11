package test.creditsuisse;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Implementation
 *
 * Assumptions:
 * 1. Order quantity and price must be +ve; no counter-examples given in requirements
 * 2. Price is an integer; no counter-examples are given in requirements
 * 3. Buy and sell orders are displayed separately; inferred from different sorting requirements
 *
 */
public class LiveOrderBoard {

    private List<Order> buyOrders = new ArrayList<>();

    public void registerBuyOrder(String userId, int price, float quantity) {
        buyOrders.add(new Order(userId, price, quantity));
    }

    public List<Order> getBuyOrders() {
        return unmodifiableList(buyOrders);
    }
}
