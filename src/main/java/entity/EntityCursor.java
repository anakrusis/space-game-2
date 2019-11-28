package entity;

import util.CollisionUtil;
import util.MathHelper;
import world.Map;

public class EntityCursor extends Entity {
    Entity selectedEntity;

    public EntityCursor(double x, double y, float dir, Map map) {
        super(x, y, dir, map);
    }

    @Override
    public void update() {
        // Has none of the physics of the base entity

        if (this.getChunk() != null){
            for (int i = 0; i < this.getChunk().getBodies().size(); i++){
                Body body = this.getChunk().getBodies().get(i);
                if (CollisionUtil.isEntityCollidingWithEntity(this, body)){
                    this.selectedEntity = body;
                }
            }
        }
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
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
}
