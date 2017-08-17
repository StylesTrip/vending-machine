package com.ryan.kata.inventory;

import com.ryan.kata.coin.Coin;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Styles on 7/19/17.
 */
public class ChangeInventory {
    private ArrayList<Coin> coinsForChange;

    public ChangeInventory() {
        coinsForChange = new ArrayList<>(10);
    }

    public ChangeInventory(ArrayList<Coin> coinsForChange) {
        this.coinsForChange = coinsForChange;
    }

    public boolean canMakeChange() {
        if (coinsForChange.size() == 0)
            return false;
        else
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
            } else if (coinsForChange.contains(Coin.DIME)) {
                if (remainder.compareTo(new BigDecimal("0.10")) >= 0) {
                    coinsForChange.remove(Coin.DIME);
                    coinsToReturn.add(Coin.DIME);

                    remainder = remainder.subtract(new BigDecimal("0.10"));
                }
            } else if (coinsForChange.contains(Coin.NICKEL)) {
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
