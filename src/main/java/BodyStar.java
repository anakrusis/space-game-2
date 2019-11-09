public class BodyStar extends Body {
    public BodyStar(double x, double y, float dir, Chunk chunk) {
        super(x, y, dir, chunk,1);
    }

    @Override
    public void update() {
        super.update();
        this.setDir(this.dir + 0.01f);
    }
}
