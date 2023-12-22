package quicklauncher;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddButton extends ImageButton{
    public AddButton(String name,ImageIcon imageIcon,ToolTip tooltip) {
        super(name,imageIcon,tooltip);
    }

    @Override
    public void action() {              
        final JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("File: " + file.getName() + ".");
            System.out.println(file);
            QuickLauncher.mainPane.addApplication(file.getAbsolutePath());   
        } else {
            System.out.println("Open command cancelled by user.");
        }
        System.out.println(returnVal);
    }
    
}
