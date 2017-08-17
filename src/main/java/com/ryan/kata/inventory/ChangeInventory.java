package com.ryan.kata.inventory;

import com.ryan.kata.coin.Coin;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;


public class ChangeInventory {
    private ArrayList<Coin> coinsForChange;

    public ChangeInventory() {
        coinsForChange = new ArrayList<>(10);
    }

    public ChangeInventory(ArrayList<Coin> coinsForChange) {
        this.coinsForChange = coinsForChange;
    }

    /**
     * Goes off the assumption of making enough change based on:
     * 1 Nickel, 1 Dime/2 Nickels, 2 Quarters
     *
     * @return whether or not change can be made (true/false)
     */
    public boolean canMakeChange() {
        if (!coinsForChange.contains(Coin.NICKEL)) {
            return false;
        }
        if (Collections.frequency(coinsForChange, Coin.DIME) < 1 &&
                Collections.frequency(coinsForChange, Coin.NICKEL) < 2) {
            return false;
        }
        if (Collections.frequency(coinsForChange, Coin.QUARTER) < 2) {
            return false;
        }

            return true;
    }

    /**
     * Figuring out the best way to do change. A wikipedia search says using a greedy way,
     * like the US versions of vending machines will work.
     *
     * @param remainder amount that change needs to be made for
     * @return the list of coins for change
     */
    public ArrayList<Coin> getChangeFrom(BigDecimal remainder) {
        ArrayList<Coin> coinsToReturn = new ArrayList<>();

        while (remainder.compareTo(BigDecimal.ZERO) > 0) {
            if (coinsForChange.contains(Coin.QUARTER)) {
                if (remainder.compareTo(new BigDecimal("0.25")) >= 0) {
                    coinsForChange.remove(Coin.QUARTER);
                    coinsToReturn.add(Coin.QUARTER);

                    remainder = remainder.subtract(new BigDecimal("0.25"));
                }
            } if (coinsForChange.contains(Coin.DIME)) {
                if (remainder.compareTo(new BigDecimal("0.10")) >= 0) {
                    coinsForChange.remove(Coin.DIME);
                    coinsToReturn.add(Coin.DIME);

                    remainder = remainder.subtract(new BigDecimal("0.10"));
                }
            } if (coinsForChange.contains(Coin.NICKEL)) {
                if (remainder.compareTo(new BigDecimal("0.05")) >= 0) {
                    coinsForChange.remove(Coin.NICKEL);
                    coinsToReturn.add(Coin.NICKEL);

                    remainder = remainder.subtract(new BigDecimal("0.05"));
                }
            }
        }

        return coinsToReturn;
    }
}
