package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;
import com.ryan.kata.inventory.ChangeInventory;
import com.ryan.kata.inventory.Inventory;
import com.ryan.kata.vendingmachine.display.VendingMachineDisplay;
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
    private VendingMachineDisplay vendingMachineDisplay;
    private boolean itemDispensed;

    VendingMachine() {
        coinsToReturn = new ArrayList<>();
        insertedCoins = new ArrayList<>();
        insertedCoinAmount = new BigDecimal("0.00");

        if (changeInventory != null) {
            vendingMachineDisplay = new VendingMachineDisplay(changeInventory.canMakeChange());
        } else {
            vendingMachineDisplay = new VendingMachineDisplay(false);
        }
    }

    public String display() {
        String messageToDisplay = vendingMachineDisplay.display();

        //reset to default after we display whatever message we needed to
        //Unless there's coins inserted then we need to let the customer know
        if (insertedCoinAmount.compareTo(BigDecimal.ZERO) > 0) {
            vendingMachineDisplay.amountAddedCheck(insertedCoinAmount);
        } else {
            vendingMachineDisplay.defaultMessage(changeInventory.canMakeChange());
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
        vendingMachineDisplay.amountAddedCheck(insertedCoinAmount);

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

            if (!changeInventory.canMakeChange()) {
                determineVendingWithNoChange(selection, optionalProduct);
            } else {
                determineVendingWithChange(selection, optionalProduct);
            }
        } else {
            vendingMachineDisplay.itemSoldOut();
        }

        if (itemDispensed) {
            vendingMachineDisplay.itemDispensed();
            insertedCoinAmount = new BigDecimal("0.00");
            changeInventory.addCoinsToChange(insertedCoins);
            insertedCoins.clear();
        }
    }

    private void determineVendingWithNoChange(String selection, Optional<VMProducts> optionalProduct) {
        //When we say EXACT CHANGE, we mean it so return all coins inserted if change cannot be made for selected item
        if (insertedCoinAmount.compareTo(VendorMachinePricing.valueOf(selection).getPrice()) > 0) {
            coinsToReturn.addAll(insertedCoins);
            insertedCoins.clear();
            insertedCoinAmount = new BigDecimal("0.00");
        } else if (insertedCoinAmount.compareTo(VendorMachinePricing.valueOf(selection).getPrice()) == 0) {
            itemDispensed = true;
            dispensedItem = optionalProduct.get();
        } else {
            vendingMachineDisplay.itemPriceChecked(selection);
        }
    }

    private void determineVendingWithChange(String selection, Optional<VMProducts> optionalProduct) {
        if (insertedCoinAmount.compareTo(VendorMachinePricing.valueOf(selection).getPrice()) >= 0) {
            itemDispensed = true;
            dispensedItem = optionalProduct.get();

            //Figure out change, if needed
            BigDecimal remainder = insertedCoinAmount.subtract(
                    VendorMachinePricing.valueOf(selection).getPrice());

            if (remainder.compareTo(BigDecimal.ZERO) > 0) {
                coinsToReturn.addAll(changeInventory.getChangeFrom(remainder));
            }
        } else {
            vendingMachineDisplay.itemPriceChecked(selection);
        }
    }

    public void coinReturnPressed() {
        coinsToReturn.addAll(insertedCoins);
        insertedCoins.clear();
        insertedCoinAmount = new BigDecimal("0.00");
        vendingMachineDisplay.defaultMessage(changeInventory.canMakeChange());
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

    public VMProducts checkDispenser() {
        return dispensedItem;
    }

    //There could be no product inventory so this is something that can be added later
    //to the machine.
    public void addProductInventory(Inventory inventory) {
        this.productInventory = inventory;
    }

    //Same idea with the product inventory, there could be no change
    public void addChangeInventory(ChangeInventory changeInventory) {
        this.changeInventory = changeInventory;
        vendingMachineDisplay.defaultMessage(changeInventory.canMakeChange());
    }
}
