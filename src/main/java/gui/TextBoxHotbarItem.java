package gui;

public class TextBoxHotbarItem extends TextBox {

    protected int inventoryIndex;
    public TextBoxHotbarItem (float x, float y, float width, float height) {
        super(x, y, width, height, EnumGui.GUI_HOTBAR_ITEM);
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }
}
