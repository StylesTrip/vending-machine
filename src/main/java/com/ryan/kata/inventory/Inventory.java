package com.ryan.kata.inventory;

import com.ryan.kata.vmproducts.Chips;
import com.ryan.kata.vmproducts.VMProducts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Styles on 7/16/17.
 */
public class Inventory {

    private Map<String, ArrayList<VMProducts>> products;

    public Inventory() {
        products = new HashMap<>(9);
    }

    /**
     * Grab the product based on the selection. For a vending machine inventory system, the inventory
     * doesn't have an exact idea of what product is in it. Just a letter/number system that corresponds to
     * what it holds.
     *
     * @param selection
     * @return Optional VMProduct. The item may or may not be in the inventory
     */
    public Optional<VMProducts> getProduct(String selection) {
        ArrayList<VMProducts> productSelection = products.get(selection);
        Optional<VMProducts> productToReturn = Optional.empty();

        //Grabbing, returning, removing the first product (if found) in the list, it's the first one seen
        if (productSelection.size() > 0) {
            productToReturn = Optional.of(productSelection.get(0));
            productSelection.remove(0);
        }

        return productToReturn;
    }

    public void addProduct(String selectionNumber, ArrayList<VMProducts> product) {
        products.put(selectionNumber, product);
    }
}
