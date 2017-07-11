package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;

import java.util.ArrayList;

/**
 * Created by Styles on 7/9/17.
 */
public class VendingMachine {

    private ArrayList<Coin> rejectedCoinsToReturn = new ArrayList<>();

    public String display() {
        return "INSERT COIN";
    }

    public boolean acceptCoin(Coin coin) {

        if (coin.getSizeInches() == Coin.PENNY.getSizeInches()  &&
                coin.getMassGrams() == Coin.PENNY.getMassGrams()) {
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
}
