package com.adnre.spacegame.item;

import java.util.ArrayList;

public class Items {

    public static ArrayList<Item> Items = new ArrayList<>();

    public static final Item ITEM_FACTORY = new ItemFactory("Factory");
    public static final Item ITEM_APARTMENT = new ItemApartment("Apartment");
    public static final Item ITEM_CITY_HALL = new ItemCityHall("City Hall");

    public static final Item ITEM_MINING_LASER = new ItemMiningLaser("Mining Laser");
    public static final Item ITEM_BOMB = new ItemBomb("Plasma Bomb");
    public static final Item ITEM_SPACEPORT = new ItemSpaceport("Spaceport");

    public static void register(){
    }
}
