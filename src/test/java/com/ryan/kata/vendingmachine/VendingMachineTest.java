package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Styles on 7/3/17.
 */
public class VendingMachineTest {
    @Test
    public void test_vending_machine_displays_insert_coin_with_no_coins_inserted() {
        VendingMachine vendingMachine = new VendingMachine();
        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_accepts_valid_coin() {
        VendingMachine vendingMachine = new VendingMachine();
        Assert.assertTrue(vendingMachine.acceptCoin(Coin.QUARTER));
    }
}
