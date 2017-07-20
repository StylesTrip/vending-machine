package com.ryan.kata.inventory;

import com.ryan.kata.coin.Coin;

import java.util.ArrayList;

/**
 * Created by Styles on 7/19/17.
 */
public class ChangeInventory {
    private ArrayList<Coin> coinsForChange;

    public ChangeInventory() {
        coinsForChange = new ArrayList<>(10);
    }

    public ChangeInventory(ArrayList<Coin> coinsForChange) {
        this.coinsForChange = coinsForChange;
    }

    public boolean canMakeChange() {
        if (coinsForChange.size() == 0)
            return false;
        else
            return true;
    }
}
