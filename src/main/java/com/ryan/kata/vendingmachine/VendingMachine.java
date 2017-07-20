package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;
import com.ryan.kata.inventory.ChangeInventory;
import com.ryan.kata.inventory.Inventory;
import com.ryan.kata.vendingmachine.pricing.VendorMachinePricing;
import com.ryan.kata.vmproducts.VMProducts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

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
    private ChangeInventory changeInventory;

    public String display() {
        String messageToDisplay;

        if (itemDispensed) {
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
            if (insertedCoinAmount.compareTo(BigDecimal.ZERO) > 0) {
                messageToDisplay = "Amount: " + insertedCoinAmount;
            } else {
                messageToDisplay = changeInventory.canMakeChange() ? "INSERT COIN" : "EXACT CHANGE ONLY";
            }
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
        Optional<VMProducts> optionalProduct = productInventory.getProduct(selection);

        if (optionalProduct.isPresent()) {
            if (insertedCoinAmount.equals(VendorMachinePricing.valueOf(selection).getPrice())) {
                itemDispensed = true;
                dispensedItem = optionalProduct.get();
            }
        } else {
            itemSoldOut = true;
        }

        if (itemDispensed) {
            updateDisplay("THANK YOU");
        } else if (itemSoldOut) {
            updateDisplay("SOLD OUT");
        } else {
            priceChecked = true;

            updateDisplay("PRICE $" + VendorMachinePricing.valueOf(selection).getPrice());
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

    public void addChangeInventory(ChangeInventory changeInventory) {
        this.changeInventory = changeInventory;
    }
}
