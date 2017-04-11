package test.creditsuisse;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

/**
 * Implementation
 *
 * Assumptions:
 * 1. Order quantity and price must be +ve; no counter-examples given in requirements
 * 2. Price is an integer; no counter-examples are given in requirements
 * 3. Buy and sell orders are displayed separately; inferred from different sorting requirements, in this case requiring two instances of LiveOrderBoard to track.
 * 4. No consideration has been given to mulit-threaded access.
 *
 */
public class LiveOrderBoard {
    public static final Comparator<Price> BUY_ORDER = (p, o) -> -Integer.compare(p.getPrice(), o.getPrice());
    public static final Comparator<Price> SELL_ORDER = Comparator.comparingInt(Price::getPrice);
    private final Comparator<Price> ordering;

    public LiveOrderBoard(Comparator<Price> orderType) {
        this.ordering = orderType;
    }

    private List<Order> buyOrders = new ArrayList<>();

    public void registerOrder(String userId, int price, float quantity) {
        buyOrders.add(new Order(userId, price, quantity));
    }

    public void cancelOrder(String userId, int price, float quantity) {
        if(!buyOrders.removeIf(o -> o.getPrice() == price && o.getQuantity() == quantity && o.getUserId().equals(userId))) {
            throw new IllegalStateException("No matching order found");
        }
    }

    public List<Order> getOrders() {
        return unmodifiableList(buyOrders);
    }

    public List<Price> getOrderBoard() {
        final Map<Integer, Float> quantities = buyOrders.stream()
                .collect(Collectors.groupingBy(Order::getPrice, Collectors.reducing(0f, Order::getQuantity, Float::sum)));
        final List<Price> prices = (quantities.entrySet().stream().map(e -> new Price(e.getKey(), e.getValue())).collect(Collectors.toList()));
        prices.sort(ordering);
        return prices;
    }
}
