package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Styles on 7/9/17.
 */
public class VendingMachine {

    private ArrayList<Coin> rejectedCoinsToReturn = new ArrayList<>();
    private ArrayList<Coin> insertedCoins = new ArrayList<>();
    private BigDecimal insertedCoinAmount = new BigDecimal("0.00");
    private String displayMessage = "INSERT COIN";

    public String display() {
        String messageToDisplay;

        if (insertedCoinAmount.compareTo(BigDecimal.ZERO) > 0) {
            messageToDisplay = "Amount: " + insertedCoinAmount;
        } else {
            messageToDisplay = displayMessage;
            displayMessage = "INSERT COIN";
        }

        return messageToDisplay;
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

        insertedCoins.add(coin);

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

    public void coinReturnPressed() {
        rejectedCoinsToReturn.addAll(insertedCoins);
        insertedCoinAmount = new BigDecimal("0.00");
    }

    public void selectProduct(String selection) {

        updateDisplay("PRICE $1.00");
    }

    private void updateDisplay(String message) {
        this.displayMessage = message;
    }
}
