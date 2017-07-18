package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;
import com.ryan.kata.inventory.Inventory;
import com.ryan.kata.vmproducts.Candy;
import com.ryan.kata.vmproducts.Chips;
import com.ryan.kata.vmproducts.Cola;
import com.ryan.kata.vmproducts.VMProducts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Styles on 7/9/17.
 */
public class VendingMachine {

    private ArrayList<Coin> rejectedCoinsToReturn = new ArrayList<>();
    private ArrayList<Coin> insertedCoins = new ArrayList<>();
    private Inventory productInventory;
    private BigDecimal insertedCoinAmount = new BigDecimal("0.00");
    private VMProducts dispensedItem = null;
    private String displayMessage = "INSERT COIN";
    private boolean itemDispensed;
    private boolean priceChecked;
    private boolean itemSoldOut;

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
        } else if (itemSoldOut) {
            messageToDisplay = displayMessage;
            itemSoldOut = false;
        } else {
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
        if (insertedCoinAmount.equals(new BigDecimal("1.00"))) {
            itemDispensed = true;
            dispensedItem = new Cola();
        } else if ("B1".equalsIgnoreCase(selection)) {
            Optional<VMProducts> optionalProduct = productInventory.getProduct(selection);

            if (optionalProduct.isPresent()) {
                if (insertedCoinAmount.equals(new BigDecimal("0.50"))) {
                    itemDispensed = true;
                    dispensedItem = new Chips();
                }
            } else {
                itemSoldOut = true;
            }
        } else if (insertedCoinAmount.equals(new BigDecimal("0.65"))){
            itemDispensed = true;
            dispensedItem = new Candy();
        }

        if (itemDispensed) {
            updateDisplay("THANK YOU");
        } else if (itemSoldOut) {
            updateDisplay("SOLD OUT");
        } else {
            priceChecked = true;
            if (selection.equalsIgnoreCase("A1")) {
                updateDisplay("PRICE $1.00");
            } else if (selection.equalsIgnoreCase("B1")) {
                updateDisplay("PRICE $0.50");
            } else if (selection.equalsIgnoreCase("C1")) {
                updateDisplay("PRICE $0.65");
            }
        }
    }

    private void updateDisplay(String message) {
        this.displayMessage = message;
    }

    public VMProducts checkDispenser() {
        return dispensedItem;
    }

    public void addProductInventory(Inventory inventory) {
        this.productInventory = inventory;
    }
}
