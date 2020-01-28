package com.adnre.spacegame.entity;

import com.adnre.spacegame.entity.part.Part;
import com.adnre.spacegame.world.World;

import java.util.ArrayList;

public class EntityShip extends Entity {

    protected float fuel;
    protected float fuelCapacity;
    ArrayList<Part> parts;

    public EntityShip(double x, double y, float dir, World world) {
        super(x, y, dir, world);
        parts = new ArrayList<>();
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    @Override
    public void update() {
        super.update();
        for (Part part : parts){
            part.update();
        }
    }
}
