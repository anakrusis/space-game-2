package entity.building;

import entity.EntityBuilding;
import entity.EntityPlayer;
import entity.ParticleSmoke;
import util.MathHelper;
import world.Map;

public class BuildingFactory extends EntityBuilding {
    public BuildingFactory(double x, double y, float dir, Map map, EntityPlayer player) {
        super(x, y, dir, map, player);
        this.price = 50;
        this.name = "Factory";
    }
    @Override
    public double[] getAbsolutePoints() {
        double[] relpoints = new double[]{
                -1, -1,
                1.5, -1,
                1.5, 1,
                -1, 1
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
        super.update();
        if (this.ticksExisted % 20 == 0 && this.map.getPlayer() != null){
            if (this.map.getPlayer().getChunk() != null){
                if (this.map.getPlayer().getChunk() == this.getChunk()){
                    this.map.getEntities().add( new ParticleSmoke(this.x, this.y, this.dir, this.map) );
                }
            }
        }
        if (this.isGrounded()){
            if (this.groundedBody.getDir() % Math.PI <= this.groundedBody.getRotSpeed()){
                if (this.map.getPlayer() != null){
                    this.map.getPlayer().addMoney(2);
                }
            }
        }
    }
}
