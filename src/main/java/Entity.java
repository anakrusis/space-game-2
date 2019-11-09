public class Entity {
    private double x;
    private double y;
    private float dir; // radians
    private double velocity;

    public Entity (double x, double y, float dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.velocity = 0;
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
}
