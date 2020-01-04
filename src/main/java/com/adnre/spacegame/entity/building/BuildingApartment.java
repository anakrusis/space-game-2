package com.adnre.spacegame.entity.building;

import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.RandomUtil;
import com.adnre.spacegame.world.World;

public class BuildingApartment extends EntityBuilding {

    private int population;
    private int capacity;

    public BuildingApartment(double x, double y, float dir, World world, EntityPlayer player) {
        super(x, y, dir, world, player);
        this.price = 25;
        this.capacity = 100;
        this.name = "Apartment";
    }
    public BuildingApartment(double x, double y, float dir, World world){
        this(x, y, dir, world, null);
    }

    public BuildingApartment(double x, double y, float dir, World world, int population){
        this(x, y, dir, world);
        this.population = population;
    }

    @Override
    public double[] getAbsolutePoints() {
        double[] relpoints = new double[]{
                -1, -1,
                1.5, -1,
                1.5, 1,
                -1, 1
        };
        double[] abspoints = new double[relpoints.length];
        for (int i = 0; i < abspoints.length; i += 2){
            abspoints[i] = MathHelper.rotX(this.dir,relpoints[i],relpoints[ i + 1 ]) + this.x;
            abspoints[i + 1] = MathHelper.rotY(this.dir,relpoints[i],relpoints[ i + 1 ]) + this.y;
        }
        return abspoints;
    }

    public int getPopulation() {
        return population;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void update() {
        super.update();
        if (this.ticksExisted % 200 == 39 && this.population < capacity && RandomUtil.percentChance(50) && this.isActive()){
            this.population++;
        }
    }

    public Texture getTexture(){
        return Textures.apartment;
    }

    @Override
    public ItemStack getItemDropped() {
        return new ItemStack (Items.ITEM_APARTMENT, 1);
    }
}
