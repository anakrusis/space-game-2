public class Camera {
    private double x;
    private double y;
    private double zoom;

    public Camera (double x, double y, double zoom){
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public double rotX(float angle, double x, double y){
        return (x * Math.cos(angle)) - (y * Math.sin(angle));
    }

    public double rotY(float angle, double x, double y){
        return (x * Math.sin(angle)) + (y * Math.cos(angle));
    }
}
