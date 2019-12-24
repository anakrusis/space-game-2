package com.adnre.spacegame.entity;

import com.adnre.spacegame.entity.body.BodyGravityRadius;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.item.ItemStack;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.Map;
import com.adnre.spacegame.world.Nation;

public class EntityBuilding extends Entity {

    private int planetIndex = -1;
    protected EntityPlayer playerPlaced;
    protected int price;

    public EntityBuilding(double x, double y, float dir, Map map, EntityPlayer playerPlaced) {
        super(x, y, dir, map);
        this.playerPlaced = playerPlaced;
        this.name = "Building";
    }
    public EntityBuilding(double x, double y, float dir, Map map){
        this(x, y, dir, map,null);
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
        if (this.ticksExisted > 1000 && this.groundedBody == null) {
            this.dead = true;
        }

        this.x += this.velocity * Math.cos(this.dir);
        this.y += this.velocity * Math.sin(this.dir);

        // These are physics that don't apply to stationary bodies. Just small entities like ships, asteroids...
        if (this.getChunk() != null) {
            boolean isColliding = false;
            for (Body body : this.getChunk().getBodies()) {
                if (CollisionUtil.isEntityCollidingWithEntity(this, body)) {

                    // Setting collision markers
                    if (body.canEntitiesCollide) {
                        if (this.velocity > 1.5) {
                            this.explode();
                        } else {
                            this.grounded = true;
                            this.groundedBody = body;
                            isColliding = true;
                        }
                    }

                    if (body instanceof BodyStar) {
                        this.explode();

                    } else if (body instanceof BodyGravityRadius) {
                        double forceMagnitude = 0.1d;
                        double angleFromCenter = Math.atan2(this.y - body.getY(), this.x - body.getX());
                        this.x -= forceMagnitude * Math.cos(angleFromCenter);
                        this.y -= forceMagnitude * Math.sin(angleFromCenter);
                    }

                }
                // Buildings can't get ungrounded once grounded
            }

            if (grounded && groundedBody != null) {
                if (groundedBody instanceof BodyPlanet) {
                    BodyPlanet planet = (BodyPlanet) groundedBody;
                    int index = CollisionUtil.terrainIndexFromEntityAngle(this, planet);

                    if (planet.getBuildings() == null){
                        planet.setBuildings(new EntityBuilding[planet.getTerrainSize()]);
                    }
                    // Empty slot ready to put a building on!
                    if (planet.getBuildings()[index] == null) {
                        if (this.planetIndex == -1){
                            planet.getBuildings()[index] = this;
                            this.planetIndex = index;

                            // If the planet is unclaimed, the first player to build on it claims it.
                            if (planet.getNation() == null){
                                planet.setNation(map.getPlayer().getNation());
                            }
                        }

                    // If this is the building at that spot, align it with the grid of terrain
                    }else if (planet.getBuildings()[index] == this){

                        float angle = (float) (planet.dir + (2f * Math.PI * ((index + 0.5f) / planet.terrain.length)));
                        double rad = planet.radius + CollisionUtil.heightFromEntityAngle(this, planet) + 0.8;
                        this.x = MathHelper.rotX(angle, rad, 0) + planet.getX();
                        this.y = MathHelper.rotY(angle, rad, 0) + planet.getY();

                        double angleFromCenter = Math.atan2(this.y - planet.getY(), this.x - planet.getX());
                        this.dir = (float)angleFromCenter;

                    // If there already is another building at that spot, then give the player back their item
                    }else{
                        this.dead = true;
                        if (this.playerPlaced != null){
                            this.playerPlaced.addInventory(this.getItemDropped());
                        }
                    }
                }
            }

            this.ticksExisted++;
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
        if (this.groundedBody instanceof BodyPlanet){
            return ((BodyPlanet) this.groundedBody).getNation();
        }else{
            return null;
        }
    }
    public ItemStack getItemDropped(){
        return null;
    }
}
