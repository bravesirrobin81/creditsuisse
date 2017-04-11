package test.creditsuisse;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
