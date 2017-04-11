package test.creditsuisse;

import org.junit.Assert;
import org.junit.Test;

public class OrderTest {
    @Test
    public void shouldCreateOrder() {
        final Order order = new Order("userId", 100, 1f);

        Assert.assertEquals("userId", order.getUserId());
        Assert.assertEquals(100, order.getPrice());
        Assert.assertEquals(1f, order.getQuantity(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateOrderWithNegativeQuantity() {
        new Order("userId", 100, -1f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateOrderWithNegativePrice() {
        new Order("userId", -100, 1f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateOrderWithZeroPrice() {
        new Order("userId", 0, 1f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateOrderWithZeroQuantity() {
        new Order("userId", 100, -1f);
    }
}
