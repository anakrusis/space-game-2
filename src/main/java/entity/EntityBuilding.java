package entity;

import org.lwjgl.system.MathUtil;
import util.MathHelper;
import world.Map;

public class EntityBuilding extends Entity {
    public EntityBuilding(double x, double y, float dir, Map map) {
        super(x, y, dir, map);
    }

    @Override
    public double[] getAbsolutePoints() {
        double[] abspoints = new double[]{
            this.x - 0.5, this.y - 0.5,
            this.x + 0.5, this.y - 0.5,
            this.x + 0.5, this.y + 0.5,
            this.x - 0.5, this.y + 0.5
        };
        return abspoints;
    }
}
