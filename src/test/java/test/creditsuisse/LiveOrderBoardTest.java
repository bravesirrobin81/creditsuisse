package test.creditsuisse;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LiveOrderBoardTest {

    @Test
    public void shouldRegisterBuyOrder() {
        final LiveOrderBoard board = new LiveOrderBoard();

        board.registerBuyOrder("userId", 100, 20f);

        assertEquals(1, board.getBuyOrders().size());
        final Order order = board.getBuyOrders().get(0);
        assertEquals("userId", order.getUserId());
        assertEquals(100, order.getPrice());
        assertEquals(20f, order.getQuantity(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotRegisterInvalidBuyOrder() {
        final LiveOrderBoard board = new LiveOrderBoard();

        board.registerBuyOrder("userId", -100, 20f);
    }

    @Test
    public void shouldCancelBuyOrder() {
        final LiveOrderBoard board = new LiveOrderBoard();
        board.registerBuyOrder("userId", 100, 20f);

        board.cancelBuyOrder("userId", 100, 20f);

        assertTrue(board.getBuyOrders().isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotCancelBuyOrderNoPriceMatch() {
        final LiveOrderBoard board = new LiveOrderBoard();
        board.registerBuyOrder("userId", 100, 20f);

        board.cancelBuyOrder("userId", 99, 20f);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotCancelBuyOrderNoQuantityMatch() {
        final LiveOrderBoard board = new LiveOrderBoard();
        board.registerBuyOrder("userId", 100, 20f);

        board.cancelBuyOrder("userId", 100, 10f);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotCancelBuyOrderNoUserIdMatch() {
        final LiveOrderBoard board = new LiveOrderBoard();
        board.registerBuyOrder("userId", 100, 20f);

        board.cancelBuyOrder("anotherUser", 100, 20f);
    }

    @Test
    public void shouldCombineOrdersAtSamePriceLevel() {
        final LiveOrderBoard board = new LiveOrderBoard();
        board.registerBuyOrder("userId", 100, 20f);
        board.registerBuyOrder("userId", 100, 5f);

        final List<Price> prices = board.getOrderBoard();

        assertEquals(1, prices.size());
        assertEquals(25f, prices.get(0).getQuantity(), 0.0001);
        assertEquals(100, prices.get(0).getPrice());
    }

    @Test
    public void shouldSortOrdersDescendingPrices() {
        final LiveOrderBoard board = new LiveOrderBoard();
        board.registerBuyOrder("userId", 95, 20f);
        board.registerBuyOrder("userId", 100, 5f);
        board.registerBuyOrder("userId", 100, 5f);
        board.registerBuyOrder("userId", 95, 20f);

        final List<Price> prices = board.getOrderBoard();

        assertEquals(2, prices.size());
        assertEquals(10f, prices.get(0).getQuantity(), 0.0001);
        assertEquals(100, prices.get(0).getPrice());

    }
}
