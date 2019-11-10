public class BodyStar extends Body {
    public BodyStar(double x, double y, float dir, Chunk chunk) {
        super(x, y, dir, chunk,10);

        this.rotSpeed = 0.01f;
        this.terrain = new float[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    }

    @Override
    public void update() {
        super.update();
    }
}
