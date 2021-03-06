package com.adnre.spacegame.entity;

import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.body.BodyGravityRadius;
import com.adnre.spacegame.entity.body.BodyPlanet;
import com.adnre.spacegame.entity.body.BodyStar;
import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.entity.particle.ParticleExplosion;
import com.adnre.spacegame.render.Texture;
import com.adnre.spacegame.util.Reference;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.Chunk;
import com.adnre.spacegame.world.World;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {
    protected double x;
    protected double y;
    protected float dir; // radians
    protected float[] color;

    // Physics stuff
    protected double velocity;
    protected double acceleration;
    protected boolean grounded;
    protected UUID groundedBodyUUID;
    protected float mass;
    protected double elevation;
    // Accessible for physics testing purposes
    protected double gravityAttraction;
    protected float rotSpeed; //radians per tick

    // Other stats and id stuff
    protected String name;
    public int ticksExisted;
    protected boolean dead = false;

    transient protected World world;
    private static final long serialVersionUID = 6529685098267757690L;
    protected UUID uuid;

    public Entity (double x, double y, float dir, World world){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.velocity = 0;
        this.acceleration = 0;
        this.ticksExisted = 0;
        this.world = world;
        this.grounded = false;
        this.mass = 1f;
        this.name = "Entity";
        this.color = new float[]{1f, 1f, 1f};
        this.uuid = UUID.randomUUID();
        this.rotSpeed = 0;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public float getDir(){
        return dir;
    }
    public double getVelocity() {
        return velocity;
    }
    public double getAcceleration() {
        return acceleration;
    }

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setDir(float dir){
        this.dir = dir;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public void update(){

        this.dir += rotSpeed;
        this.x += this.velocity * Math.cos(this.dir);
        this.y += this.velocity * Math.sin(this.dir);

        // These are physics that don't apply to stationary bodies. Just small entities like ships, asteroids...
        if (this.getChunk() != null) {

            boolean isColliding = false;
            for (Body body : this.getChunk().getBodies().values()) {

                if (body.canEntitiesCollide){
                    if (CollisionUtil.isColliding(this, body)) {

                        // Setting collision markers
                        if (this.velocity > 1.0 || body instanceof BodyStar) {
                            this.explode();
                        } else {
                            this.grounded = true;
                            this.groundedBodyUUID = body.getUuid();
                            isColliding = true;
                            CollisionUtil.resolveCollision(this, body);
                        }
                    }
                } else {
                    // Gravitation (simple increasing pull towards the body)
                    if (CollisionUtil.isColliding(this, body)){
                        if (body instanceof BodyGravityRadius) {
                            BodyGravityRadius bgr = (BodyGravityRadius) body;
                            Body dependentBody = bgr.getDependentBody();
                            double distance = MathHelper.distance(this.x, this.y, bgr.getX(), bgr.getY());

                            // This function returns ~0 at the edge of the gravity radius, and ~1 at the surface of the body.
                            double annulusPosition = (-1 / (bgr.getRadius() - dependentBody.getRadius())) * ( distance - dependentBody.getRadius() ) + 1;
                            double forceMagnitude;

                            // It is then mapped to a coefficient representing its "mass"
                            // which determines how much to pull in the entity every tick
                            if (dependentBody instanceof BodyStar){
                                forceMagnitude = 0.5d * annulusPosition;
                            }else{
                                forceMagnitude = 0.15d * annulusPosition;
                            }
                            this.gravityAttraction = forceMagnitude;

                            double angleFromCenter = Math.atan2(this.y - body.getY(), this.x - body.getX());
                            this.x -= forceMagnitude * Math.cos(angleFromCenter);
                            this.y -= forceMagnitude * Math.sin(angleFromCenter);
                        }
                    }
                }
            }

            // Buildings can't unground, but other entities can if they aren't colliding anymore
            if (!(isColliding) && !(this instanceof EntityBuilding)){
                this.groundedBodyUUID = null;
                this.grounded = false;
            }

            if (grounded && getGroundedBody() != null) {

                if (this.velocity > 0.2){
                    this.velocity = 0.2;
                }

                // This moves the entity along with a planet by anticipating where it will be in the next tick
                if (getGroundedBody() instanceof BodyPlanet) {
                    BodyPlanet planet = (BodyPlanet) getGroundedBody();
                    // Added on an extra step (2pi/orbitPeriod) because planets update after entities (lol)
                    double angle = planet.getOrbitAngle() + (Math.PI * 2) / planet.getOrbitPeriod();
                    double futurePlanetX = MathHelper.rotX(angle, planet.getOrbitDistance(), 0) + planet.getStar().getX();
                    double futurePlanetY = MathHelper.rotY(angle, planet.getOrbitDistance(), 0) + planet.getStar().getY();

                    this.x += (futurePlanetX - planet.getX());
                    this.y += (futurePlanetY - planet.getY());
                }

                Body body = getGroundedBody();
                // This moves the entity along with any rotating body
                this.dir += body.getRotSpeed();
                this.x = MathHelper.rotX(body.getRotSpeed(), this.x - body.getX(), this.y - body.getY()) + body.getX();
                this.y = MathHelper.rotY(body.getRotSpeed(), this.x - body.getX(), this.y - body.getY()) + body.getY();
            }
        }
        this.ticksExisted++;
    }

    public void moveToIndexOnPlanet(int index, BodyPlanet planet){
        float angle = (float) (planet.getDir() + (2f * Math.PI * ((index + 0.5f) / planet.getTerrainSize())));
        double rad = planet.getRadius() + CollisionUtil.heightFromEntityAngle(this, planet) + 1;
        this.x = MathHelper.rotX(angle, rad, 0) + planet.getX();
        this.y = MathHelper.rotY(angle, rad, 0) + planet.getY();

        double angleFromCenter = Math.atan2(this.y - planet.getY(), this.x - planet.getX());
        this.dir = (float)angleFromCenter;
    }

    public boolean isDead(){
        return dead;
    }

    public Chunk getChunk(){
        int chunkx = (int)Math.floor( this.x / Reference.CHUNK_SIZE );
        int chunky = (int)Math.floor( this.y / Reference.CHUNK_SIZE );
        if (world != null){
            if (chunkx >= 0 && chunky >= 0 && chunkx < world.getChunks().length && chunky < world.getChunks()[0].length){
                return this.world.getChunks()[chunkx][chunky];
            }else{
                return null;
            }
        }
        return null;
    }

    // This method is used for collision handling.
    // The default for an unspecified entity is just to return its position (meaning it only collides as a single point).
    // If it has a definite shape then it should override this and use that!

    public double[] getAbsolutePoints(){
        return new double[] { this.x , this.y };
    }

    // Is the entity grounded on a body?
    public boolean isGrounded() {
        return grounded;
    }

    // If so, which body?
    public Body getGroundedBody(){
        if (!grounded){
            return null;
        }else{
            return this.getChunk().getBodies().get(groundedBodyUUID);
        }
    }

    public Body getNearestBody(){
        double distance = 1000000000;
        Body nearestbody = null;
        if (this.getChunk() != null){
            for (Body body: this.getChunk().getBodies().values()){
                double cd = MathHelper.distance(body.getX(), body.getY(), this.x, this.y);
                if (cd < distance){
                    distance = cd;
                     nearestbody = body;
                }
            }
            return nearestbody;
        }
        return null;
    }

    public UUID getGroundedBodyUUID() {
        return groundedBodyUUID;
    }

    public void setGroundedBodyUUID(UUID groundedBodyUUID) {
        this.groundedBodyUUID = groundedBodyUUID;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public void explode(){
        this.dead = true;

        for (int i = 0; i < 20; i++){
            float randomdir = (float)(2 * Math.PI) * (float)Math.random();
            this.world.spawnEntity(new ParticleExplosion(this.x, this.y, randomdir, this.world));
        }
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getGravityAttraction() {
        return gravityAttraction;
    }

    public Texture getTexture(){
        return null;
    }

    public float[] getColor() {
        return color;
    }

    public float getRotSpeed() {
        return rotSpeed;
    }

    public void setRotSpeed(float rotSpeed) {
        this.rotSpeed = rotSpeed;
    }
}
