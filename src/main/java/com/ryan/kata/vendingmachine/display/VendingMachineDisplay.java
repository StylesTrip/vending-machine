package com.ryan.kata.vendingmachine.display;

import com.ryan.kata.vendingmachine.pricing.VendorMachinePricing;

import java.math.BigDecimal;

/**
 * Handles the display duties for the Vending Machine.
 * Each method updates the internal message and a call to display will return
 * the current message
 */
public class VendingMachineDisplay {
    private String displayMessage;

    public VendingMachineDisplay(boolean canMakeChange) {
        defaultMessage(canMakeChange);
    }

    public void defaultMessage(boolean canMakeChange) {
       displayMessage = canMakeChange ? "INSERT COIN" : "EXACT CHANGE ONLY";
    }

    public String display() {
        return displayMessage;
    }

    public void itemSoldOut() {
        displayMessage = "SOLD OUT";
    }

    public void amountAddedCheck(BigDecimal amountToDisplay) {
        displayMessage = "Amount: " + amountToDisplay;
    }

    public void itemPriceChecked(String selection) {
        displayMessage =  "PRICE $" + VendorMachinePricing.valueOf(selection).getPrice();
    }

    public void itemDispensed() {
        displayMessage = "THANK YOU";
    }
}
