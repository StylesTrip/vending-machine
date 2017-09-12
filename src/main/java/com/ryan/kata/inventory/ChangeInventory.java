package com.ryan.kata.inventory;

import com.ryan.kata.coin.Coin;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


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
        int numQuarters = 0;
        int numDimes = 0;
        int numNickels = 0;

        for (Coin c : coinsForChange) {
            if (c == Coin.QUARTER) {
                numQuarters++;
            } else if ( c == Coin.DIME) {
                numDimes++;
            } else if ( c == Coin.NICKEL) {
                numNickels++;
            }
        }

        return determineChange(numQuarters, numDimes, numNickels);
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

    /**
     * Determines whether or not we can make change.
     * The highest number we will make change for is a dollar
     *
     * @param numQuarters quarters machine has
     * @param numDimes dimes machine has
     * @param numNickels nickels machine has
     * @return true/false
     */
    private boolean determineChange(int numQuarters, int numDimes, int numNickels) {
        BigDecimal incrementalChange;
        boolean changeMade;

        //Idea here is to see if we can make change from the lowest amount (.05)
        //to our ceiling(1.00) using the greedy way used in getting the coins in the method above
        for (double d = 0.05; d <= 1.00; d+=0.05) {
            int tempQuarters = numQuarters;
            int tempDimes = numDimes;
            int tempNickels = numNickels;
            incrementalChange = new BigDecimal(d);
            incrementalChange = incrementalChange.setScale(2, RoundingMode.HALF_UP);
            BigDecimal amountToMakeChangeFrom = incrementalChange;

            while (amountToMakeChangeFrom.compareTo(BigDecimal.ZERO) > 0) {
                changeMade = false;

                if (amountToMakeChangeFrom.compareTo(new BigDecimal("0.25")) >= 0) {
                    if (tempQuarters > 0) {
                        tempQuarters--;
                        amountToMakeChangeFrom = amountToMakeChangeFrom.subtract(new BigDecimal("0.25"));
                        changeMade = true;
                    }
                }

                if (!changeMade && amountToMakeChangeFrom.compareTo(new BigDecimal("0.10")) >= 0) {
                    if (tempDimes > 0) {
                        tempDimes--;
                        amountToMakeChangeFrom = amountToMakeChangeFrom.subtract(new BigDecimal("0.10"));
                        changeMade = true;
                    }
                }

                if (!changeMade && amountToMakeChangeFrom.compareTo(new BigDecimal("0.05")) >= 0) {
                    if (tempNickels > 0) {
                        tempNickels--;
                        amountToMakeChangeFrom = amountToMakeChangeFrom.subtract(new BigDecimal("0.05"));
                        changeMade = true;
                    }
                }

                if(!changeMade) {
                    //We weren't able to subtract the amount, therefore no change can be made
                    return false;
                }
            }
        }

        return true;
    }

    public void addCoinsToChange(ArrayList<Coin> coins) {
        coinsForChange.addAll(coins);
    }
}
