package com.adnre.spacegame.entity;

import com.adnre.spacegame.entity.body.BodyGravityRadius;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.ChunkChangeBuildingPlace;
import com.adnre.spacegame.world.World;
import com.adnre.spacegame.world.Nation;

import java.util.UUID;

public class EntityBuilding extends Entity {

    private int planetIndex = -1;
    transient protected EntityPlayer playerPlaced;
    protected int price;

    public EntityBuilding(double x, double y, float dir, World world, EntityPlayer playerPlaced) {
        super(x, y, dir, world);
        this.playerPlaced = playerPlaced;
        this.name = "Building";
    }
    public EntityBuilding(double x, double y, float dir, World world){
        this(x, y, dir, world,null);
    }

    @Override
    public double[] getAbsolutePoints() {
        double[] relpoints = new double[]{
            -0.5, -0.5,
            0.5, -0.5,
            0.5, 0.5,
            -0.5, 0.5
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
                            this.getChunk().getChunkChangelog().add(new ChunkChangeBuildingPlace(this));

                            // If the planet is unclaimed, the first player to build on it claims it.
                            if (planet.getNation() == null){
                                planet.setNationUUID(world.getPlayerNation().getUuid());
                            }
                        }

                    // If this is the building at that spot, align it with the grid of terrain
                    }else if (planet.getBuildingUUIDs()[index].equals(this.uuid)){

                        float angle = (float) (planet.dir + (2f * Math.PI * ((index + 0.5f) / planet.terrain.length)));
                        double rad = planet.radius + CollisionUtil.heightFromEntityAngle(this, planet) + 0.8;
                        this.x = MathHelper.rotX(angle, rad, 0) + planet.getX();
                        this.y = MathHelper.rotY(angle, rad, 0) + planet.getY();

                        double angleFromCenter = Math.atan2(this.y - planet.getY(), this.x - planet.getX());
                        this.dir = (float)angleFromCenter;

                    // If there already is another building at that spot, then give the player back their item
                    }else{
                        this.dead = true;
                        if (this.world.getPlayer() != null){
                            this.world.getPlayer().addInventory(this.getItemDropped());
                        }
                    }
                }
            }
        }
    }

    public int getPlanetIndex() {
        return planetIndex;
    }

    public EntityPlayer getPlayerPlaced() {
        return playerPlaced;
    }

    public void setPlayerPlaced(EntityPlayer playerPlaced) {
        this.playerPlaced = playerPlaced;
    }

    public int getPrice() {
        return price;
    }

    public boolean isActive() {
        return this.isGrounded();
    }

    public Texture getTexture(){
        return null;
    }

    public Nation getNation() {
        if (this.getGroundedBody() instanceof BodyPlanet){
            return ((BodyPlanet) this.getGroundedBody()).getNation();
        }else{
            return null;
        }
    }
    public ItemStack getItemDropped(){
        return null;
    }

}
