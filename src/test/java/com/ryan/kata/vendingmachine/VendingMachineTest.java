package com.ryan.kata.vendingmachine;

import com.ryan.kata.coin.Coin;
import com.ryan.kata.inventory.ChangeInventory;
import com.ryan.kata.inventory.Inventory;
import com.ryan.kata.vmproducts.Candy;
import com.ryan.kata.vmproducts.Chips;
import com.ryan.kata.vmproducts.Cola;
import com.ryan.kata.vmproducts.VMProducts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;


public class VendingMachineTest {
    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
        Inventory inventory = new Inventory();

        ArrayList<VMProducts> chips = new ArrayList<>(1);
        chips.add(new Chips());

        ArrayList<VMProducts> colas = new ArrayList<>(1);
        colas.add(new Cola());

        ArrayList<VMProducts> candies = new ArrayList<>(1);
        candies.add(new Candy());

        inventory.addProduct("A1", colas);
        inventory.addProduct("B1", chips);
        inventory.addProduct("C1", candies);

        vendingMachine.addProductInventory(inventory);

        ArrayList<Coin> coinsForChange = new ArrayList<>(1);
        coinsForChange.add(Coin.QUARTER);
        coinsForChange.add(Coin.QUARTER);
        coinsForChange.add(Coin.DIME);
        coinsForChange.add(Coin.DIME);
        coinsForChange.add(Coin.NICKEL);

        ChangeInventory changeInventory = new ChangeInventory(coinsForChange);

        vendingMachine.addChangeInventory(changeInventory);
    }

    @Test
    public void test_vending_machine_displays_insert_coin_with_no_coins_inserted() {
        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_accepts_valid_coin() {
        Assert.assertTrue(vendingMachine.acceptCoin(Coin.QUARTER));
    }

    @Test
    public void test_vending_machine_rejects_invalid_coin_penny() {
        Assert.assertFalse(vendingMachine.acceptCoin(Coin.PENNY));
    }

    @Test
    public void test_vending_machine_has_rejected_coins_in_coin_return() {
        addCoinToVendingMachine(Coin.QUARTER, 1);
        addCoinToVendingMachine(Coin.PENNY, 2);


        ArrayList<Coin> returnedCoins = vendingMachine.checkCoinReturn();
        Assert.assertEquals(2, returnedCoins.size());

        returnedCoins.forEach( coin -> Assert.assertTrue(coin == Coin.PENNY));
    }

    @Test
    public void test_customer_removes_coins_from_coin_return() {
        addCoinToVendingMachine(Coin.PENNY, 2);

        vendingMachine.removeFromCoinReturn();

        ArrayList<Coin> returnedCoins = vendingMachine.checkCoinReturn();
        Assert.assertEquals(0, returnedCoins.size());
    }

    @Test
    public void test_nothing_happens_when_taking_From_empty_coin_return() {
        vendingMachine.removeFromCoinReturn();

        ArrayList<Coin> returnedCoins = vendingMachine.checkCoinReturn();
        Assert.assertEquals(0, returnedCoins.size());
    }

    @Test
    public void test_amount_changes_when_valid_coins_accepted() {
        addCoinToVendingMachine(Coin.QUARTER, 1);
        addCoinToVendingMachine(Coin.NICKEL, 1);
        addCoinToVendingMachine(Coin.DIME, 1);

        Assert.assertEquals(new BigDecimal(".40"), vendingMachine.getInsertedCoinAmount());
    }

    @Test
    public void test_display_updates_when_valid_coins_accepted() {
        addCoinToVendingMachine(Coin.QUARTER, 1);
        addCoinToVendingMachine(Coin.NICKEL, 1);
        addCoinToVendingMachine(Coin.PENNY, 1);

        Assert.assertEquals("Amount: 0.30", vendingMachine.display());
    }

    @Test
    public void test_coin_return_pressed_returns_coins() {
        addCoinToVendingMachine(Coin.QUARTER, 1);
        addCoinToVendingMachine(Coin.NICKEL, 1);
        addCoinToVendingMachine(Coin.PENNY, 1);

        vendingMachine.coinReturnPressed();

        ArrayList<Coin> returnedCoins = vendingMachine.checkCoinReturn();

        Assert.assertEquals(3, returnedCoins.size());
    }

    @Test
    public void test_coin_return_pressed_updates_display_to_INSERT_COIN() {
        addCoinToVendingMachine(Coin.QUARTER, 1);
        vendingMachine.coinReturnPressed();

        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_price_when_item_is_selected_with_no_money_inserted() {
        vendingMachine.selectProduct("A1");

        Assert.assertEquals("PRICE $1.00", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_INSERT_COIN_when_checked_twice_with_no_money_inserted_and_item_selected() {
        vendingMachine.selectProduct("A1");

        Assert.assertEquals("PRICE $1.00", vendingMachine.display());
        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_price_of_item_then_amount_accepted_when_not_enough_money_inserted() {
        addCoinToVendingMachine(Coin.DIME, 1);
        vendingMachine.selectProduct("A1");

        Assert.assertEquals("PRICE $1.00", vendingMachine.display());
        Assert.assertEquals("Amount: 0.10", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_dispenses_cola_when_inserted_amount_matches_price() {
        addCoinToVendingMachine(Coin.QUARTER, 4);

        vendingMachine.selectProduct("A1");
        VMProducts dispensedProduct = vendingMachine.checkDispenser();

        Assert.assertTrue(dispensedProduct instanceof Cola);
    }

    @Test
    public void test_vending_machine_displays_thank_you_after_dispensing_cola() {
        addCoinToVendingMachine(Coin.QUARTER, 4);

        vendingMachine.selectProduct("A1");
        vendingMachine.checkDispenser();

        Assert.assertEquals("THANK YOU", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_INSERT_COIN_after_thank_you_from_dispensing() {
        addCoinToVendingMachine(Coin.QUARTER, 4);

        vendingMachine.selectProduct("A1");
        vendingMachine.checkDispenser();

        Assert.assertEquals("THANK YOU", vendingMachine.display());
        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void testing_vending_machine_dispenses_chips_when_proper_amount_added() {
        addCoinToVendingMachine(Coin.QUARTER, 2);

        vendingMachine.selectProduct("B1");
        VMProducts dispensedProduct = vendingMachine.checkDispenser();

        Assert.assertTrue(dispensedProduct instanceof Chips);
    }

    @Test
    public void testing_vending_machine_dispenses_candy_when_proper_amount_added() {
        addCoinToVendingMachine(Coin.QUARTER, 2);
        addCoinToVendingMachine(Coin.NICKEL, 1);
        addCoinToVendingMachine(Coin.DIME, 1);

        vendingMachine.selectProduct("C1");
        VMProducts dispensedProduct = vendingMachine.checkDispenser();

        Assert.assertTrue(dispensedProduct instanceof Candy);
    }


    @Test
    public void test_vending_machine_displays_correct_price_for_each_item() {
        vendingMachine.selectProduct("A1");
        Assert.assertEquals("PRICE $1.00", vendingMachine.display());

        vendingMachine.selectProduct("B1");
        Assert.assertEquals("PRICE $0.50", vendingMachine.display());

        vendingMachine.selectProduct("C1");
        Assert.assertEquals("PRICE $0.65", vendingMachine.display());

    }

    @Test
    public void test_vending_machine_displays_correct_price_for_two_items_then_inserted_accepted_amount() {
        vendingMachine.acceptCoin(Coin.NICKEL);
        vendingMachine.selectProduct("A1");
        Assert.assertEquals("PRICE $1.00", vendingMachine.display());

        vendingMachine.selectProduct("B1");
        Assert.assertEquals("PRICE $0.50", vendingMachine.display());

        Assert.assertEquals("Amount: 0.05", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_sold_out_of_chips_displays_SOLD_OUT() {
        Inventory inventory = new Inventory();

        inventory.addProduct("B1", new ArrayList<>());
        vendingMachine.addProductInventory(inventory);

        addCoinToVendingMachine(Coin.QUARTER, 2);

        vendingMachine.selectProduct("B1");
        Assert.assertEquals("SOLD OUT", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_SOLD_OUT_then_INSERT_COIN() {
        Inventory inventory = new Inventory();

        inventory.addProduct("B1", new ArrayList<>());
        vendingMachine.addProductInventory(inventory);

        vendingMachine.selectProduct("B1");
        Assert.assertEquals("SOLD OUT", vendingMachine.display());
        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_SOLD_OUT_then_AMOUNT_added() {
        Inventory inventory = new Inventory();

        inventory.addProduct("B1", new ArrayList<>());
        vendingMachine.addProductInventory(inventory);

        addCoinToVendingMachine(Coin.QUARTER, 1);

        vendingMachine.selectProduct("B1");
        Assert.assertEquals("SOLD OUT", vendingMachine.display());
        Assert.assertEquals("Amount: 0.25", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_SOLD_OUT_for_cola() {
        Inventory inventory = new Inventory();

        inventory.addProduct("A1", new ArrayList<>());
        vendingMachine.addProductInventory(inventory);

        vendingMachine.selectProduct("A1");
        Assert.assertEquals("SOLD OUT", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_SOLD_OUT_then_INSERT_COIN_for_cola() {
        Inventory inventory = new Inventory();

        inventory.addProduct("A1", new ArrayList<>());
        vendingMachine.addProductInventory(inventory);

        vendingMachine.selectProduct("A1");
        Assert.assertEquals("SOLD OUT", vendingMachine.display());
        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_SOLD_OUT_then_AMOUNT_added_for_cola() {
        Inventory inventory = new Inventory();

        inventory.addProduct("A1", new ArrayList<>());
        vendingMachine.addProductInventory(inventory);

        addCoinToVendingMachine(Coin.DIME, 1);

        vendingMachine.selectProduct("A1");
        Assert.assertEquals("SOLD OUT", vendingMachine.display());
        Assert.assertEquals("Amount: 0.10", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_SOLD_OUT_then_INSERT_COIN_for_chips() {
        Inventory inventory = new Inventory();

        inventory.addProduct("C1", new ArrayList<>());
        vendingMachine.addProductInventory(inventory);

        vendingMachine.selectProduct("C1");
        Assert.assertEquals("SOLD OUT", vendingMachine.display());
        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_displays_SOLD_OUT_then_AMOUNT_added_for_chips() {
        Inventory inventory = new Inventory();

        inventory.addProduct("C1", new ArrayList<>());
        vendingMachine.addProductInventory(inventory);

        vendingMachine.acceptCoin(Coin.NICKEL);

        vendingMachine.selectProduct("C1");
        Assert.assertEquals("SOLD OUT", vendingMachine.display());
        Assert.assertEquals("Amount: 0.05", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_display_for_off_the_wall_selection() {
        vendingMachine.selectProduct("Z1");

        Assert.assertEquals("SOLD OUT", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_display_EXACT_CHANGE_ONLY_when_no_change_present() {
        ChangeInventory emptyChangeInventory = new ChangeInventory();

        vendingMachine.addChangeInventory(emptyChangeInventory);
        Assert.assertEquals("EXACT CHANGE ONLY", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_display_INSERT_COIN_if_change_available_for_all_products() {
        Assert.assertEquals("INSERT COIN", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_display_EXACT_CHANGE_ONLY_when_not_enough_change_available() {
        Assert.assertEquals("INSERT COIN", vendingMachine.display());

        addCoinToVendingMachine(Coin.QUARTER, 2);
        addCoinToVendingMachine(Coin.DIME, 2);

        vendingMachine.selectProduct("C1");

        Assert.assertEquals("THANK YOU", vendingMachine.display());
        Assert.assertEquals("EXACT CHANGE ONLY", vendingMachine.display());
    }

    @Test
    public void test_vending_machine_get_nickel_as_change() {
        addCoinToVendingMachine(Coin.QUARTER, 2);
        addCoinToVendingMachine(Coin.DIME, 2);

        vendingMachine.selectProduct("C1");
        ArrayList<Coin> coinsReturned = vendingMachine.checkCoinReturn();

        Assert.assertEquals(1, coinsReturned.size());
        Assert.assertTrue(coinsReturned.get(0).equals(Coin.NICKEL));
    }

    @Test
    public void test_vending_machine_get_multiple_coins_no_dimes_as_change() {
        ArrayList<Coin> coins = new ArrayList<>();
        coins.add(Coin.QUARTER);
        coins.add(Coin.QUARTER);
        coins.add(Coin.NICKEL);
        coins.add(Coin.NICKEL);

        ChangeInventory changeInventory = new ChangeInventory(coins);
        vendingMachine.addChangeInventory(changeInventory);

        addCoinToVendingMachine(Coin.QUARTER, 4);

        vendingMachine.selectProduct("C1");
        ArrayList<Coin> coinsReturned = vendingMachine.checkCoinReturn();

        Assert.assertTrue(coinsReturned.size() > 1);
        Assert.assertTrue(coinsReturned.contains(Coin.QUARTER));
        Assert.assertTrue(coinsReturned.contains(Coin.NICKEL));
    }

    @Test
    public void test_exact_change_only_customer_adds_too_many_coins() {
        ChangeInventory changeInventory = new ChangeInventory();
        vendingMachine.addChangeInventory(changeInventory);

        addCoinToVendingMachine(Coin.QUARTER, 3);
        vendingMachine.selectProduct("C1");

        ArrayList<Coin> coinsReturned = vendingMachine.checkCoinReturn();
        Assert.assertTrue(coinsReturned.size() == 3);
    }

    private void addCoinToVendingMachine(Coin coin, int numberToAdd) {
        for (int i = 0; i < numberToAdd; i++) {
            vendingMachine.acceptCoin(coin);
        }
    }
}
