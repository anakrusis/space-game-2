package entity.building;

import entity.EntityBuilding;
import entity.EntityPlayer;
import util.MathHelper;
import world.Map;

public class BuildingApartment extends EntityBuilding {

    private int population;

    public BuildingApartment(double x, double y, float dir, Map map, EntityPlayer player) {
        super(x, y, dir, map, player);
        this.price = 25;
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

    public int getPopulation() {
        return population;
    }
}
