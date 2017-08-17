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

    private ArrayList<Coin> coinsToReturn;
    private ArrayList<Coin> insertedCoins;
    private Inventory productInventory;
    private ChangeInventory changeInventory;
    private BigDecimal insertedCoinAmount;
    private VMProducts dispensedItem = null;
    private String displayMessage;
    private boolean itemDispensed;
    private boolean priceChecked;
    private boolean itemSoldOut;

    VendingMachine() {
        coinsToReturn = new ArrayList<>();
        insertedCoins = new ArrayList<>();
        insertedCoinAmount = new BigDecimal("0.00");

        displayMessage = "INSERT COIN";
    }

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

    /**
     * Logic for figuring out if an inserted coin is valid and the value of the coin
     *
     * @param coin inserted coin into the machine
     * @return whether or not the coin is valid (true/false)
     */
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
            coinsToReturn.add(coin);
            return false;
        }

        insertedCoins.add(coin);

        return true;
    }

    /**
     * Takes care of retrieving the product (if present) and getting change
     *
     * @param selection what the user has selected
     */
    public void selectProduct(String selection) {
        Optional<VMProducts> optionalProduct = productInventory.getProduct(selection);

        if (optionalProduct.isPresent()) {
            if (insertedCoinAmount.compareTo(VendorMachinePricing.valueOf(selection).getPrice()) >= 0) {
                itemDispensed = true;
                dispensedItem = optionalProduct.get();

                //Figure out change, if needed
                BigDecimal remainder = insertedCoinAmount.subtract(
                        VendorMachinePricing.valueOf(selection).getPrice());

                if (remainder.compareTo(BigDecimal.ZERO) > 0) {
                    coinsToReturn.addAll(changeInventory.getChangeFrom(remainder));
                }
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

    public void coinReturnPressed() {
        coinsToReturn.addAll(insertedCoins);
        insertedCoinAmount = new BigDecimal("0.00");
    }

    public ArrayList<Coin> checkCoinReturn() {
        return coinsToReturn;
    }

    public void removeFromCoinReturn() {
        coinsToReturn.clear();
    }

    public BigDecimal getInsertedCoinAmount() {
        return insertedCoinAmount;
    }

    private void updateDisplay(String message) {
        this.displayMessage = message;
    }

    public VMProducts checkDispenser() {
        return dispensedItem;
    }

    //There could be no product inventory so this is something that can be added later
    //to the machine.
    public void addProductInventory(Inventory inventory) {
        this.productInventory = inventory;
    }

    //Same idea with the proudct inventory, there could be no change
    public void addChangeInventory(ChangeInventory changeInventory) {
        this.changeInventory = changeInventory;
    }
}
