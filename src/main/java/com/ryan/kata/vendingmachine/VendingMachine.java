package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;

/**
 * Created by Styles on 7/9/17.
 */
public class VendingMachine {

    public String display() {
        return "INSERT COIN";
    }

    public boolean acceptCoin(Coin coin) {
        if (coin.getSizeInches() == Coin.PENNY.getSizeInches()  &&
                coin.getMassGrams() == Coin.PENNY.getMassGrams()) {
            return false;
        }

        return true;
    }
}