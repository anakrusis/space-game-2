package entity;

import org.lwjgl.system.MathUtil;
import util.CollisionUtil;
import util.MathHelper;
import world.Map;

public class EntityBuilding extends Entity {
    private boolean isCollidingGravityBody = false;

    public EntityBuilding(double x, double y, float dir, Map map) {
        super(x, y, dir, map);
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
        // TODO: Clean this up (it's mostly redundant) and re-add the Buildings despawning after a certain amt of time
//        if (this.ticksExisted > 300 && !this.isCollidingGravityBody) {
//            this.dead = true;
//        }

        this.x += this.velocity * Math.cos(this.dir);
        this.y += this.velocity * Math.sin(this.dir);

        // These are physics that don't apply to stationary bodies. Just small entities like ships, asteroids...
        if (this.getChunk() != null) {
            boolean isColliding = false;
            for (Body body : this.getChunk().getBodies()) {
                if (CollisionUtil.isEntityCollidingWithBody(this, body)) {

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
                        this.isCollidingGravityBody = true;
                    }

                }

                if (!(isColliding)) {
                    this.groundedBody = null;
                    this.grounded = false;
                    this.isCollidingGravityBody = false;
                }
            }

            if (grounded && groundedBody != null) {
                if (groundedBody instanceof BodyPlanet) {
                    BodyPlanet planet = (BodyPlanet) groundedBody;
                    int index = CollisionUtil.terrainIndexFromEntityAngle(this, planet);
                    // Empty slot ready to put a building on!
                    if (planet.getBuildings()[index] == null) {
                        planet.getBuildings()[index] = this;

                    // If this is the building at that spot, align it with the grid of terrain
                    }else if (planet.getBuildings()[index] == this){

                        double angle = planet.dir + (2 * Math.PI * ((float)(index + 0.5f) / (float)planet.terrain.length));
                        double rad = planet.radius + 0.8d;
                        this.x = (Math.cos(angle) * rad) + planet.getX();
                        this.y = (Math.sin(angle) * rad) + planet.getY();

                        double angleFromCenter = Math.atan2(this.y - planet.getY(), this.x - planet.getX());
                        this.dir = (float)angleFromCenter;

                    // If there already is another building at that spot, then refund the player
                    }else{
                        this.dead = true;
                        if (map.getPlayer() != null){
                            map.getPlayer().addMoney(50);
                        }
                    }
                }
            }

            this.ticksExisted++;
        }
    }
}
