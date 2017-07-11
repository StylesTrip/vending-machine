package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Styles on 7/3/17.
 */
public class VendingMachineTest {
    VendingMachine vendingMachine;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void test_vending_machine_displays_insert_coin_with_no_coins_inserted() {
        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_accepts_valid_coin() {
        Assert.assertTrue(vendingMachine.acceptCoin(Coin.QUARTER));
    }

    @Test
    public void test_vending_machine_rejects_invalid_coin_penny() {
        Assert.assertFalse(vendingMachine.acceptCoin(Coin.PENNY));
    }

    @Test
    public void test_vending_machine_has_rejected_coins_in_coin_return() {
        vendingMachine.acceptCoin(Coin.QUARTER);
        vendingMachine.acceptCoin(Coin.PENNY);
        vendingMachine.acceptCoin(Coin.PENNY);

        ArrayList<Coin> returnedCoins = vendingMachine.checkCoinReturn();
        Assert.assertEquals(2, returnedCoins.size());

        returnedCoins.forEach( coin -> Assert.assertTrue(coin == Coin.PENNY));
    }
}
