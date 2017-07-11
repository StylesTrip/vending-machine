package com.ryan.kata.coin;

/**
 * Created by Styles on 7/9/17.
 */
public enum Coin {

    QUARTER (.955, 5.670),
    DIME (.705, 2.268),
    NICKEL (.835, 5.000),
    PENNY (.750, 2.500);

    private double sizeInches;
    private double massGrams;

    Coin(double sizeInches, double massGrams) {
        this.sizeInches = sizeInches;
        this.massGrams = massGrams;
    }

    public double getSizeInches() {
        return sizeInches;
    }

    public double getMassGrams() {
        return massGrams;
    }
}
