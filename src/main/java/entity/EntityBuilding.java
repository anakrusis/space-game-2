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
                // This moves the player along with a planet by anticipating where it will be in the next tick
                if (groundedBody instanceof BodyPlanet) {
                    BodyPlanet planet = (BodyPlanet) groundedBody;
                    float angle = planet.getOrbitAngle();
                    double futurePlanetX = MathHelper.rotX(angle, planet.orbitDistance, 0) + planet.star.getX();
                    double futurePlanetY = MathHelper.rotY(angle, planet.orbitDistance, 0) + planet.star.getY();

                    this.x += (futurePlanetX - planet.getX());
                    this.y += (futurePlanetY - planet.getY());
                }
                Body body = groundedBody;

                // This moves the player along with any rotating body
                this.x = MathHelper.rotX(body.rotSpeed, this.x - body.getX(), this.y - body.getY()) + body.getX();
                this.y = MathHelper.rotY(body.rotSpeed, this.x - body.getX(), this.y - body.getY()) + body.getY();

                // TODO Fix this system of determining heightmap. Seems to not work
                // (maybe should be measured on a ray out from the player. Internal angles would glitch it out!)
                double angleFromCenter = Math.atan2(this.y - body.getY(), this.x - body.getX());
                int index = CollisionUtil.terrainIndexFromEntityAngle(this, body);
                double radius = body.getRadius() + body.getTerrain()[index] + 0.8d;
                this.dir = (float)angleFromCenter;

                this.x = (Math.cos(angleFromCenter) * radius) + body.getX();
                this.y = (Math.sin(angleFromCenter) * radius) + body.getY();
            }

            this.ticksExisted++;
        }
    }
}
