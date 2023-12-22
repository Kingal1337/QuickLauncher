package quicklauncher;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jimmc.jshortcut.JShellLink;

public class ShortCuts {
    private static JShellLink link;
    private static String filePath;
    public ShortCuts() {
        
    }
    
    public static void update(int mode){
        if(mode == 0){
            System.out.println(mode);
            deleteShortcut();
        }
        else if(mode == 1){
            createDesktopShortcut();
        }
    }
    
    public static void createMacShortcut(String target, String link) throws IOException, InterruptedException {
        String[] cmd = { "ln", "-s", new File(target).getAbsolutePath(), new File(link).getAbsolutePath() };
        int exitCode = Runtime.getRuntime().exec(cmd).waitFor();
        if (exitCode != 0) {
            throw new IOException("ln signaled an error with exit code " + exitCode);
        }
    }

    public static void createDesktopShortcut() {
        File file = null;
        try {
            file = new File(ShortCuts.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(ShortCuts.class.getName()).log(Level.SEVERE, null, ex);
        }
        String fileName = file.getName();
        String temp = System.getProperty("user.dir");
        System.out.println(temp+"/"+fileName);
        try {
            link = new JShellLink();
            filePath = temp+"/"+fileName;
        } catch (Exception e) {
            
        }
        try {
            System.out.println("C:/Users/"+System.getProperty("user.name")+"/"+"AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup");
            link.setFolder("C:/Users/"+System.getProperty("user.name")+"/"+"AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup");
            link.setName("QuickLaunch.jar - Shortcut");
            link.setPath(filePath);
            link.save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void deleteShortcut(){
        File file = new File("C:/Users/"+System.getProperty("user.name")+"/"+"AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/QuickLaunch.jar - Shortcut.lnk");
        file.delete();
    }

}
