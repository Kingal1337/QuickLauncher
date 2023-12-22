package quicklauncher;

import javax.swing.ImageIcon;

public class QuickLaunchButton extends ImageButton{
    private String relativeFilePath;
    private String absoluteFilePath;
    public QuickLaunchButton(String name,ImageIcon image,String relativeFilePath,String absoluteFilePath,ToolTip tooltip) {
        super(name,image,tooltip);
        this.relativeFilePath = relativeFilePath;
        this.absoluteFilePath = absoluteFilePath;
    }

    public String getRelativeFilePath() {
        return relativeFilePath;
    }

    public void setRelativeFilePath(String relativeFilePath) {
        this.relativeFilePath = relativeFilePath;
    }

    public String getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public void setAbsoluteFilePath(String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
    }

    @Override
    public void action() {
        QuickLauncher.openApplication(getAbsoluteFilePath(),0);
    }
    
}
