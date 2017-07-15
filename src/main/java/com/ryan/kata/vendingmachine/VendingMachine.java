package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;
import com.ryan.kata.vmproducts.Chips;
import com.ryan.kata.vmproducts.Cola;
import com.ryan.kata.vmproducts.VMProducts;

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
    private boolean itemDispensed;
    private boolean priceChecked;

    public String display() {
        String messageToDisplay;

        if (itemDispensed){
            messageToDisplay = displayMessage;
            itemDispensed = false;
            insertedCoinAmount = new BigDecimal("0.00");
            displayMessage = "INSERT COIN";
        } else if (priceChecked) {
            messageToDisplay = displayMessage;
            priceChecked = false;
        } else{
            messageToDisplay = (insertedCoinAmount.compareTo(BigDecimal.ZERO) > 0) ? "Amount: " + insertedCoinAmount : "INSERT COIN";
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

        if (!insertedCoinAmount.equals(new BigDecimal("1.00"))) {
            priceChecked = true;
            updateDisplay("PRICE $1.00");
        }
    }

    private void updateDisplay(String message) {
        this.displayMessage = message;
    }

    public VMProducts checkDispenser() {
        if (insertedCoinAmount.equals(new BigDecimal("1.00"))) {
            itemDispensed = true;
            updateDisplay("THANK YOU");
            return new Cola();
        } else {
            return new Chips();
        }
    }
}
