package com.adnre.spacegame.entity.building;

import com.adnre.spacegame.entity.Entity;
import com.adnre.spacegame.entity.EntityPlayer;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.World;
import com.adnre.spacegame.world.Nation;

import java.util.UUID;

public class EntityBuilding extends Entity {

    private int planetIndex = -1;
    protected int price;
    protected UUID nationUUID;

    public EntityBuilding(double x, double y, float dir, World world, UUID nationUUID) {
        super(x, y, dir, world);
        this.name = "Building";
        this.nationUUID = nationUUID;
    }
    public EntityBuilding(double x, double y, float dir, World world){
        this(x, y, dir, world,world.getPlayer().getNationUUID());
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

        // This is a huge optimization and prevents default entity physics from acting on
        // grounded bodies. They do however still need to step their TicksExisted even when grounded
        if (!grounded){
            super.update();
        }else{
            this.ticksExisted++;
        }

        if (this.ticksExisted > 1000 && this.getGroundedBody() == null) {
            this.dead = true;
        }

        // These are physics that don't apply to stationary bodies. Just small entities like ships, asteroids...
        if (this.getChunk() != null) {
            if (grounded && getGroundedBody() != null) {
                if (getGroundedBody() instanceof BodyPlanet) {
                    BodyPlanet planet = (BodyPlanet) getGroundedBody();
                    int index = CollisionUtil.terrainIndexFromEntityAngle(this, planet);

                    // Empty slot ready to put a building on!
                    if (planet.getBuildingUUIDs()[index] == null) {
                        if (this.planetIndex == -1){
                            planet.getBuildingUUIDs()[index] = this.uuid;
                            this.planetIndex = index;

                            // If the planet is unclaimed, the first player to build on it claims it.
                            if (planet.getNation() == null){
                                planet.setNationUUID(world.getPlayerNation().getUuid());
                            }
                        }

                    // If this is the building at that spot, align it with the grid of terrain
                    }else if (planet.getBuildingUUIDs()[index].equals(this.uuid)){

                        moveToIndexOnPlanet(index, planet);

                    // If there already is another building at that spot, then give the player back their item
                    }else{
                        if (this.planetIndex == -1){
                            this.dead = true;
                            if (this.world.getPlayer() != null){
                                this.world.getPlayer().addInventory(this.getItemDropped());
                            }
                        }
                    }
                }
            }
        }

        // I don't know why but you need both this and the above code (which does the same thing?)
        if (planetIndex > -1 && this.getGroundedBody() != null){
            moveToIndexOnPlanet(planetIndex, (BodyPlanet) this.getGroundedBody());
        }
    }

    public int getPlanetIndex() {
        return planetIndex;
    }

    public void setPlanetIndex(int planetIndex) {
        this.planetIndex = planetIndex;
    }

    public int getPrice() {
        return price;
    }

    public boolean isActive() {
        return this.isGrounded();
    }

    public Nation getNation() {
        return world.getNations().get(nationUUID);
    }

    public UUID getNationUUID() {
        return nationUUID;
    }

    public ItemStack getItemDropped(){
        return null;
    }

    @Override
    public float[] getColor() {
        if (getNation() != null) {
            return getNation().getColor();
        }else{
            return new float[]{1f, 1f, 1f};
        }
    }

    public Texture getWindowTexture(){
        return null;
    }

    public void moveToIndexOnPlanet(int index, BodyPlanet planet){
        float angle = (float) (planet.getDir() + (2f * Math.PI * ((index + 0.5f) / planet.getTerrainSize())));
        double rad = planet.getRadius() + CollisionUtil.heightFromEntityAngle(this, planet) + 1;
        this.x = MathHelper.rotX(angle, rad, 0) + planet.getX();
        this.y = MathHelper.rotY(angle, rad, 0) + planet.getY();

        double angleFromCenter = Math.atan2(this.y - planet.getY(), this.x - planet.getX());
        this.dir = (float)angleFromCenter;
    }
}
