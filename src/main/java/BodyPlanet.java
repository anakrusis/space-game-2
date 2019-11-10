public class BodyPlanet extends Body {

    // The star which the planet orbits around, how far away it orbits, and how often
    BodyStar star;
    float orbitDistance;
    int orbitPeriod;

    public BodyPlanet(double x, double y, float dir, Chunk chunk, float radius, BodyStar star) {
        super(x, y, dir, chunk, radius);
        this.star = star;
        this.orbitDistance = 20;
        this.orbitPeriod = 100;
        this.rotSpeed = 0.05f;

        this.terrain = new float[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    }

    @Override
    public void update() {
        super.update();

        float angle = Main.MAP_TIME * (float)(Math.PI / 2) / this.orbitPeriod;

        this.x = Main.camera.rotX(angle, this.orbitDistance,0) + this.star.getX();
        this.y = Main.camera.rotY(angle, this.orbitDistance, 0) + this.star.getY();
    }
}
