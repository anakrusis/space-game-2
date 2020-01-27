package com.adnre.spacegame.entity;

import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.entity.particle.ParticleSmoke;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.item.Items;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.world.World;
import com.adnre.spacegame.world.Nation;

import java.util.UUID;

public class EntityPlayer extends EntityShip {
    private float money;


    private ItemStack[] inventory = new ItemStack[9];
    private int currentItemSlot = 0;

    private UUID nationUUID;
    private boolean isToolActive;

    public EntityPlayer (double x, double y, float dir, World world){
        super(x,y,dir, world);
        if (Reference.DEASTL_MODE){
            this.money = 69000000;
        }else if (Reference.DEBUG_MODE){
            this.money = 1000;
        }else{
            this.money = 0;
        }
        this.fuelCapacity = 100;
        this.fuel = fuelCapacity;

        this.name = "Player";
        this.nationUUID = null;

        // Testing out inventory slots
        this.inventory[0] = new ItemStack(Items.ITEM_MINING_LASER, 1);
        this.inventory[1] = new ItemStack(Items.ITEM_FACTORY, 99);
        this.inventory[2] = new ItemStack(Items.ITEM_APARTMENT, 99);

        this.addInventory( new ItemStack (Items.ITEM_BOMB, 99) );
        this.addInventory( new ItemStack (Items.ITEM_SPACEPORT, 99));
    }

    @Override
    public double[] getAbsolutePoints() {
        double point1x = MathHelper.rotX(this.dir,-0.5d,0.4d) + this.x;
        double point1y = MathHelper.rotY(this.dir,-0.5d,0.4d) + this.y;

        double point2x = MathHelper.rotX(this.dir,0.8d,0.0d) + this.x;
        double point2y = MathHelper.rotY(this.dir,0.8d,0.0d) + this.y;

        double point3x = MathHelper.rotX(this.dir,-0.5d,-0.4d) + this.x;
        double point3y = MathHelper.rotY(this.dir,-0.5d,-0.4d) + this.y;

        return new double[]{ point1x, point1y, point2x, point2y, point3x, point3y };
    }

    @Override
    public void update() {
        super.update();
        if (this.velocity > 0.1 && this.ticksExisted % 10 == 0){
            float dir = (float) (this.dir - Math.PI + (Math.random() * 0.5f));
            ParticleSmoke smoke = new ParticleSmoke(this.x, this.y, dir, this.world);
            this.world.spawnEntity(smoke);
        }

        // The player cleans their own inventory of <=0 qty com.adnre.spacegame.item stacks
        for (int i = 0; i < inventory.length; i++){
            if (inventory[i] != null){
                if (inventory[i].getAmount() <= 0){
                    inventory[i] = null;
                }
            }
        }

        if (isToolActive()){
            if (MathHelper.distance(world.getCursor().getX(), world.getCursor().getY(), x, y) < Reference.TOOL_USE_RADIUS){

            // The laser is used to destroy buildings and pick them up
                if (world.getCursor().getSelectedEntity() instanceof EntityBuilding){
                    EntityBuilding building = (EntityBuilding) world.getCursor().getSelectedEntity();

                    if (!building.dead){
                        this.addInventory(building.getItemDropped());
                    }
                    building.dead = true;
                }
        // The player's laser never works if it's outside of the radius
            }else{
                this.setToolActive(false);
            }
        }
    }

    @Override
    public void explode() {
        super.explode();
        this.world.playerLastDeathTime = this.world.mapTime;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void addMoney(float money){
        this.money += money;
    }

    public int getCurrentItemSlot() {
        return currentItemSlot;
    }

    public void setCurrentItemSlot(int currentItemSlot) {
        this.currentItemSlot = currentItemSlot;
    }

    public void setNationUUID(UUID uuid) {
        this.nationUUID = uuid;
    }

    public UUID getNationUUID() {
        return nationUUID;
    }

    public Nation getNation() {
        return world.getNations().get(nationUUID);
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public void addInventory(ItemStack item){
        // First, look for matching itemstacks and ensuring that the stack size is not too large.
        for (int i = 0; i < inventory.length; i++){
            if (inventory[i] != null){
                if (inventory[i].getItem().getId() == item.getItem().getId() && inventory[i].getAmount() < inventory[i].getItem().getMaxStackSize()){

                    // Adding the item
                    inventory[i].addAmount(item.getAmount());
                    return;
                }
            }
        }
        // If no matching itemstacks exist, make a new one at the first blank spot
        for (int i = 0; i < inventory.length; i++){
            if (inventory[i] == null){
                inventory[i] = new ItemStack(item.getItem(), item.getAmount());
                return;
            }
        }
        // Todo: If no blank spot available, then idk
    }

    public boolean isToolActive() {
        return isToolActive;
    }

    public void setToolActive(boolean toolActive) {
        isToolActive = toolActive;
    }

    public ItemStack getCurrentItemStack(){
        return inventory[currentItemSlot];
    }

    @Override
    public float[] getColor() {
        return this.getNation().getColor();
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }
    public void addFuel(float amount){
        this.fuel += amount;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }
}
