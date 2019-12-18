import entity.Entity;
import entity.EntityBuilding;
import entity.EntityCursor;
import entity.body.BodyPlanet;
import entity.building.BuildingApartment;
import entity.building.BuildingFactory;
import gui.EnumGui;
import gui.TextBox;
import gui.TextBoxHotbarItem;
import item.ItemStack;

import java.util.ArrayList;

public class GuiHandler {
    static EntityCursor cursor = SpaceGame.map.getCursor();

    // This is how all gui elements are dynamically updated
    // Todo make this a part of each individual element?
    // This is hard because it requires information which is not package accessible

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
                        if (((BodyPlanet) e).getNation() != null){

                            if (e == SpaceGame.map.getHomePlanet()){
                                tx.addTextBody("\nHome of the \n" + ((BodyPlanet) e).getNation().getName());
                            }else{
                                tx.addTextBody("\nClaimed by the \n" + ((BodyPlanet) e).getNation().getName());
                            }

                        }else{
                            tx.addTextBody("\nUnclaimed");
                        }
                        tx.addTextBody("\n");

                        tx.addTextBody("\nPopulation: " + ((BodyPlanet) e).getPopulation());
                        tx.addTextBody("\nSize: " + ((BodyPlanet) e).getTerrainSize());
                    }else if (e instanceof BuildingApartment) {

                        tx.addTextBody("\nPopulation: " + ((BuildingApartment) e).getPopulation());
                        tx.addTextBody("/" + ((BuildingApartment) e).getCapacity());

                    }else if (e instanceof BuildingFactory) {

                        tx.addTextBody("\nEmployees: " + ((BuildingFactory) e).getEmployees());
                        tx.addTextBody("/" + ((BuildingFactory) e).getCapacity());
                        tx.addTextBody("\nOutput: $" + ((BuildingFactory) e).getOutput());
                    }
                }
            } else if (tx.getGuiID() == EnumGui.GUI_HOTBAR_ITEM) {
                TextBoxHotbarItem tbhi = (TextBoxHotbarItem)tx;
                if (SpaceGame.map.getPlayer() != null){
                    ItemStack item = SpaceGame.map.getPlayer().getInventory()[tbhi.getInventoryIndex()];
                    if (item != null){
                        tx.setHeader("\n\n" + item.getAmount());
                    }

                    // Lights up the hotbar item which is currently selected by the player, if any
                    if (SpaceGame.map.getPlayer().getCurrentItemSlot() == tbhi.getInventoryIndex()){
                        tbhi.setBgColor(new float[]{ 0.9f, 0.9f, 1f });
                    }else{
                        tbhi.setBgColor(new float[]{ 0.50f, 0.55f, 0.65f });
                    }
                }
            }
        }
    }
}
