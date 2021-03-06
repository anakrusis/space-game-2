package com.adnre.spacegame.entity;

import com.adnre.spacegame.entity.body.Body;
import com.adnre.spacegame.entity.building.EntityBuilding;
import com.adnre.spacegame.gui.TextBox;
import com.adnre.spacegame.util.CollisionUtil;
import com.adnre.spacegame.util.MathHelper;
import com.adnre.spacegame.world.World;

import java.util.UUID;

public class EntityCursor extends Entity {
    Entity selectedEntity;
    TextBox selectedGuiElement;
    private double screenX;
    private double screenY;

    public EntityCursor(double x, double y, float dir, World world) {
        super(x, y, dir, world);
    }

    @Override
    public void update() {
        // Has none of the physics of the base entity.

        // Selects an entity by colliding with it
        this.selectedEntity = null;
        if (this.getChunk() != null){
            for (java.util.Map.Entry<UUID, Body> e : getChunk().getBodies().entrySet()) {
                Body body = e.getValue();
                if (CollisionUtil.isColliding(this, body) && body.canEntitiesCollide){
                    this.selectedEntity = body;
                }
            }
            for (java.util.Map.Entry<UUID, Entity> e : this.world.getEntities().entrySet()){
                Entity entity = e.getValue();
                if (entity instanceof EntityBuilding && CollisionUtil.isEntityCollidingWithEntity(this, entity)){
                    this.selectedEntity = entity;
                }
            }
        }
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
    }

    // The only reason the cursor has these abs points was for debug rendering
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

    public void setScreenX(double screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(double screenY) {
        this.screenY = screenY;
    }

    public double getScreenX() {
        return screenX;
    }
    public double getScreenY() {
        return screenY;
    }
}
