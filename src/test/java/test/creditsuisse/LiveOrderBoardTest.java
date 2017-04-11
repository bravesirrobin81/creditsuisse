package test.creditsuisse;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static test.creditsuisse.LiveOrderBoard.BUY_ORDER;
import static test.creditsuisse.LiveOrderBoard.SELL_ORDER;

public class LiveOrderBoardTest {

    @Test
    public void shouldRegisterOrder() {
        final LiveOrderBoard board = new LiveOrderBoard(BUY_ORDER);

        board.registerOrder("userId", 100, 20f);

        assertEquals(1, board.getOrders().size());
        final Order order = board.getOrders().get(0);
        assertEquals("userId", order.getUserId());
        assertEquals(100, order.getPrice());
        assertEquals(20f, order.getQuantity(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotRegisterInvalidOrder() {
        final LiveOrderBoard board = new LiveOrderBoard(BUY_ORDER);

        board.registerOrder("userId", -100, 20f);
    }

    @Test
    public void shouldCancelOrder() {
        final LiveOrderBoard board = new LiveOrderBoard(BUY_ORDER);
        board.registerOrder("userId", 100, 20f);

        board.cancelOrder("userId", 100, 20f);

        assertTrue(board.getOrders().isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotCancelOrderNoPriceMatch() {
        final LiveOrderBoard board = new LiveOrderBoard(BUY_ORDER);
        board.registerOrder("userId", 100, 20f);

        board.cancelOrder("userId", 99, 20f);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotCancelOrderNoQuantityMatch() {
        final LiveOrderBoard board = new LiveOrderBoard(BUY_ORDER);
        board.registerOrder("userId", 100, 20f);

        board.cancelOrder("userId", 100, 10f);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotCancelOrderNoUserIdMatch() {
        final LiveOrderBoard board = new LiveOrderBoard(BUY_ORDER);
        board.registerOrder("userId", 100, 20f);

        board.cancelOrder("anotherUser", 100, 20f);
    }

    @Test
    public void shouldCombineOrdersAtSamePriceLevel() {
        final LiveOrderBoard board = new LiveOrderBoard(BUY_ORDER);
        board.registerOrder("userId", 100, 20f);
        board.registerOrder("userId", 100, 5f);

        final List<Price> prices = board.getOrderBoard();

        assertEquals(1, prices.size());
        assertEquals(25f, prices.get(0).getQuantity(), 0.0001);
        assertEquals(100, prices.get(0).getPrice());
    }

    @Test
    public void shouldSortOrdersDescendingPrices() {
        final LiveOrderBoard board = new LiveOrderBoard(BUY_ORDER);
        board.registerOrder("userId", 95, 20f);
        board.registerOrder("userId", 100, 5f);
        board.registerOrder("userId", 100, 5f);
        board.registerOrder("userId", 95, 20f);

        final List<Price> prices = board.getOrderBoard();

        assertEquals(2, prices.size());
        assertEquals(10f, prices.get(0).getQuantity(), 0.0001);
        assertEquals(100, prices.get(0).getPrice());

    }

    @Test
    public void shouldSortOrdersAscendingPrices() {
        final LiveOrderBoard board = new LiveOrderBoard(SELL_ORDER);
        board.registerOrder("userId", 95, 20f);
        board.registerOrder("userId", 100, 5f);
        board.registerOrder("userId", 100, 5f);
        board.registerOrder("userId", 95, 20f);

        final List<Price> prices = board.getOrderBoard();

        assertEquals(2, prices.size());
        assertEquals(40f, prices.get(0).getQuantity(), 0.0001);
        assertEquals(95, prices.get(0).getPrice());

    }

}
