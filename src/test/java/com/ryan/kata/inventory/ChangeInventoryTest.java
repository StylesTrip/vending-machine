package com.ryan.kata.inventory;

import com.ryan.kata.coin.Coin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Styles on 9/11/17.
 */
public class ChangeInventoryTest {
    private ChangeInventory changeInventory;

    @Before
    public void setUp() {

    }

    @Test
    public void test_if_can_make_change_from_nothing() {
        changeInventory = new ChangeInventory();

        Assert.assertFalse(changeInventory.canMakeChange());
    }

    @Test
    public void test_if_can_make_change_from_a_lot_of_coins() {
        ArrayList<Coin> coinsForChange = new ArrayList<>(1);
        coinsForChange.add(Coin.QUARTER);
        coinsForChange.add(Coin.QUARTER);
        coinsForChange.add(Coin.QUARTER);
        coinsForChange.add(Coin.DIME);
        coinsForChange.add(Coin.DIME);
        coinsForChange.add(Coin.DIME);
        coinsForChange.add(Coin.NICKEL);

        changeInventory = new ChangeInventory(coinsForChange);

        Assert.assertTrue(changeInventory.canMakeChange());
    }

    @Test
    public void test_make_change_for_15_cents() {
        ArrayList<Coin> coinsForChange = new ArrayList<>(1);
        coinsForChange.add(Coin.DIME);
        coinsForChange.add(Coin.DIME);
        coinsForChange.add(Coin.DIME);
        coinsForChange.add(Coin.NICKEL);

        changeInventory = new ChangeInventory(coinsForChange);

        ArrayList<Coin> changeReturned = changeInventory.getChangeFrom(new BigDecimal("0.15"));

        Assert.assertTrue(changeReturned.size() == 2);
        Assert.assertTrue(changeReturned.contains(Coin.DIME));
        Assert.assertTrue(changeReturned.contains(Coin.NICKEL));
    }

    @Test
    public void test_make_change_for_25_cents() {
        ArrayList<Coin> coinsForChange = new ArrayList<>(1);
        coinsForChange.add(Coin.QUARTER);

        changeInventory = new ChangeInventory(coinsForChange);

        ArrayList<Coin> changeReturned = changeInventory.getChangeFrom(new BigDecimal("0.25"));

        Assert.assertTrue(changeReturned.size() == 1);
        Assert.assertTrue(changeReturned.contains(Coin.QUARTER));
    }


    @Test(expected = RuntimeException.class)
    public void test_cant_make_change_for_25_cents() {
        ArrayList<Coin> coinsForChange = new ArrayList<>(1);
        coinsForChange.add(Coin.DIME);
        coinsForChange.add(Coin.NICKEL);
        coinsForChange.add(Coin.NICKEL);

        changeInventory = new ChangeInventory(coinsForChange);
        changeInventory.getChangeFrom(new BigDecimal("0.25"));
    }
}
