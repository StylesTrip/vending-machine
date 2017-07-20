package com.ryan.kata.vendingmachine.pricing;

import java.math.BigDecimal;

public enum VendorMachinePricing {

    //The logic behind this enum is that Vending Machines don't know
    //exactly what product is in it for selction. It just knows the selection number
    //So here I am mimicking that idea

    A1 (new BigDecimal("1.00")),
    B1 (new BigDecimal("0.50")),
    C1 (new BigDecimal("0.65"));

    private final BigDecimal price;

    VendorMachinePricing(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
