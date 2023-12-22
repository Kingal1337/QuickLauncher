package quicklauncher;
public abstract class Button {
    private String name;
    private ToolTip tooltip;
    public Button(String name,ToolTip tooltip){
        this.name = name;
        this.tooltip = tooltip;
    }
    
    public abstract void action();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ToolTip getTooltip() {
        return tooltip;
    }

    public void setTooltip(ToolTip tooltip) {
        this.tooltip = tooltip;
    }
    
    
    
}
