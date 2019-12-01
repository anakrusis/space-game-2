import entity.Entity;
import entity.EntityBuilding;
import entity.EntityCursor;
import entity.body.BodyPlanet;
import entity.building.BuildingApartment;
import entity.building.BuildingFactory;
import gui.EnumGui;
import gui.TextBox;

import java.util.ArrayList;

public class GuiHandler {
    static EntityCursor cursor = SpaceGame.map.getCursor();

    public static void update( ArrayList<TextBox> elements){
        for (int i = 0; i < elements.size(); i ++){
            TextBox tx = elements.get(i);
            tx.setHeader("");
            tx.setTextBody("");

            if (tx.getGuiID() == EnumGui.GUI_SELECTED_ENTITY){
                Entity e = cursor.getSelectedEntity();
                if (e != null){
                    tx.setHeader(e.getName() + ":");

                    // All building properties
                    if (e instanceof EntityBuilding){
                        if (((EntityBuilding) e).getPlayerPlaced() != null){
                            tx.addTextBody("\nBuilt by " + ((EntityBuilding) e).getPlayerPlaced().getName());
                        }
                        if (((EntityBuilding) e).isActive()){
                            tx.addTextBody("\nActive");
                        }else{
                            tx.addTextBody("\nInactive");
                        }
                    }

                    // Unique single-class properties
                    if (e instanceof BodyPlanet){
                        tx.addTextBody("\nPopulation: " + ((BodyPlanet) e).getPopulation());
                    }else if (e instanceof BuildingApartment) {
                        tx.addTextBody("\nPopulation: " + ((BuildingApartment) e).getPopulation());
                        tx.addTextBody("/" + ((BuildingApartment) e).getCapacity());
                    }else if (e instanceof BuildingFactory) {
                        tx.addTextBody("\nEmployees: " + ((BuildingFactory) e).getEmployees());
                        tx.addTextBody("/" + ((BuildingFactory) e).getCapacity());
                    }
                }
            }
        }
    }
}
