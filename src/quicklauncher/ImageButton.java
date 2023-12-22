package quicklauncher;

import javax.swing.ImageIcon;

public class ImageButton extends Button{
    private ImageIcon imageIcon;

    public ImageButton(String name,ImageIcon imageIcon,ToolTip tooltip) {
        super(name, tooltip);
        this.imageIcon = imageIcon;
    }
    
    @Override
    public void action() {}

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
    
}
