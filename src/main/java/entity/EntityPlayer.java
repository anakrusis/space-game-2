package entity;

import util.MathHelper;
import util.Reference;
import world.Map;

public class EntityPlayer extends Entity {
    public float money;
    int currentItemSlot = 2;

    public EntityPlayer (double x, double y, float dir, Map map){
        super(x,y,dir,map);
        if (Reference.DEASTL_MODE){
            this.money = 69000000;
        }else if (Reference.DEBUG_MODE){
            this.money = 1000;
        }else{
            this.money = 0;
        }
        this.name = "Player";
    }

    @Override
    public double[] getAbsolutePoints() {
        double point1x = MathHelper.rotX(this.dir,-0.5d,0.4d) + this.x;
        double point1y = MathHelper.rotY(this.dir,-0.5d,0.4d) + this.y;

        double point2x = MathHelper.rotX(this.dir,0.8d,0.0d) + this.x;
        double point2y = MathHelper.rotY(this.dir,0.8d,0.0d) + this.y;

        double point3x = MathHelper.rotX(this.dir,-0.5d,-0.4d) + this.x;
        double point3y = MathHelper.rotY(this.dir,-0.5d,-0.4d) + this.y;

        return new double[]{ point1x, point1y, point2x, point2y, point3x, point3y };
    }

    @Override
    public void update() {
        super.update();
        if (this.velocity > 0.1 && this.ticksExisted % 10 == 0){
            float dir = (float) (this.dir - Math.PI + (Math.random() * 0.5f));
            ParticleSmoke smoke = new ParticleSmoke(this.x, this.y, dir, this.map);
            this.map.getEntities().add(smoke);
        }
    }

    @Override
    public void explode() {
        super.explode();
        this.map.playerLastDeathTime = this.map.mapTime;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void addMoney(float money){
        this.money += money;
    }

    public int getCurrentItemSlot() {
        return currentItemSlot;
    }

    public void setCurrentItemSlot(int currentItemSlot) {
        this.currentItemSlot = currentItemSlot;
    }
}
