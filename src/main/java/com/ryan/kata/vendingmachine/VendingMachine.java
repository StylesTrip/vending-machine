package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Styles on 7/9/17.
 */
public class VendingMachine {

    private ArrayList<Coin> rejectedCoinsToReturn = new ArrayList<>();
    private BigDecimal insertedCoinAmount = new BigDecimal("0.00");

    public String display() {
        return "INSERT COIN";
    }

    public boolean acceptCoin(Coin coin) {

        if (coin.getSizeInches() == Coin.QUARTER.getSizeInches() &&
                coin.getMassGrams() == Coin.QUARTER.getMassGrams()) {
            insertedCoinAmount = insertedCoinAmount.add(new BigDecimal(".25"));
        } else if (coin.getSizeInches() == Coin.DIME.getSizeInches() &&
                coin.getMassGrams() == Coin.DIME.getMassGrams()) {
            insertedCoinAmount = insertedCoinAmount.add(new BigDecimal(".10"));
        } else if (coin.getSizeInches() == Coin.NICKEL.getSizeInches() &&
                coin.getMassGrams() == Coin.NICKEL.getMassGrams()) {
            insertedCoinAmount = insertedCoinAmount.add(new BigDecimal(".05"));
        } else {
            rejectedCoinsToReturn.add(coin);
            return false;
        }

        return true;
    }

    public ArrayList<Coin> checkCoinReturn() {
        return rejectedCoinsToReturn;
    }

    public void removeFromCoinReturn() {
        rejectedCoinsToReturn.clear();
    }

    public BigDecimal getInsertedCoinAmount() {
        return insertedCoinAmount;
    }
}
