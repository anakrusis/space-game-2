package com.adnre.spacegame.item;

import java.util.ArrayList;

public class Items {

    public static ArrayList<Item> Items = new ArrayList<>();

    public static final Item ITEM_FACTORY = new ItemFactory("Factory");
    public static final Item ITEM_APARTMENT = new ItemApartment("Apartment");
    public static final Item ITEM_MINING_LASER = new ItemMiningLaser("Mining Laser");

    public static void register(){
        Items.add(ITEM_FACTORY);
        Items.add(ITEM_APARTMENT);
        Items.add(ITEM_MINING_LASER);
    }
}
