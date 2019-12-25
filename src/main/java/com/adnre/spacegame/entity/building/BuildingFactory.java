package com.adnre.spacegame.entity.building;

import com.adnre.spacegame.entity.EntityBuilding;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.entity.ParticleSmoke;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.render.Textures;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.Map;

public class BuildingFactory extends EntityBuilding {

    private int employees;
    private int capacity;
    private int output;
    private int outputInterval = 300;

    public BuildingFactory(double x, double y, float dir, Map map, EntityPlayer player) {
        super(x, y, dir, map, player);
        this.price = 50;
        this.name = "Factory";
        this.capacity = 45;
    }
    public BuildingFactory(double x, double y, float dir, Map map){
        this(x, y, dir, map, null);
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

    @Override
    public void update() {
        super.update();
        if (this.ticksExisted % 20 == 0 && this.map.getPlayer() != null && this.isActive()){
            if (this.map.getPlayer().getChunk() != null){
                if (this.map.getPlayer().getChunk() == this.getChunk()){
                    this.map.getEntities().add( new ParticleSmoke(this.x, this.y, this.dir, this.map) );
                }
            }
        }
        if (this.isActive()){
            // Quadratic equation for efficiency
            // Peak efficiency is a little bit past the middle
            this.output = -(int)((this.employees - (this.capacity * 1.1)) * (this.employees) / (this.capacity * 1.1));

            if (this.ticksExisted % outputInterval == 0 && map.getPlayer() != null){

                if (this.map.getPlayer().getNation() == this.getNation()){
                    map.getPlayer().addMoney(output);
                }

            }
        }
    }

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isActive() {
        return super.isActive() && this.employees > 0;
    }

    public int getOutput() {
        return output;
    }

    public Texture getTexture(){
        return isActive() ? Textures.factory : Textures.factory_off;
    }

    @Override
    public ItemStack getItemDropped() {
        return new ItemStack (Items.ITEM_FACTORY, 1);
    }
}
