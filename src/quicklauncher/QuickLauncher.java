package quicklauncher;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class QuickLauncher {
    public static QuickLauncher quick;
    public static JFrame frame;
    public static MainPane mainPane;
    public static void main(String[] args) {
        FileManager.folderMaker();
        quick = new QuickLauncher();
        quick.preStarter();
    }
    
    public void preStarter(){
//        Font gothicFont = new Font("Courier New",1,12);
        Font gothicFont = new Font("Gothic",1,12);
        ArrayList<String> allFilePathsInString = FileManager.getApplications(FileManager.folderPath);
        ArrayList<Button> allFilePaths = new ArrayList<>();
        if(allFilePathsInString != null){
            for (int i=3;i<allFilePathsInString.size();i++) {
                File file = new File(allFilePathsInString.get(i));
                if(file.exists()){
                    ToolTip tooltip = new ToolTip(file.getName()+"\n"+file.getAbsolutePath(), gothicFont, 1);
                    tooltip.setOffsetX(20);
                    tooltip.setBackgroundColor(Color.BLACK);
                    tooltip.setForegroundColor(Color.WHITE);
                    try {
                        allFilePaths.add(new QuickLaunchButton(file.getName(),QuickLauncher.getIconImage(file), file.getParent(),file.getAbsolutePath(),tooltip));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(QuickLauncher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        try {
            ToolTip tooltip = new ToolTip("Settings", gothicFont, 1);
            tooltip.setOffsetX(20);
            tooltip.setBackgroundColor(Color.BLACK);
            tooltip.setForegroundColor(Color.WHITE);
            allFilePaths.add(0,new SettingsButton("Settings", new ImageIcon(ImageIO.read(getClass().getResource("/res/setting.png"))),tooltip));
        } catch (IOException ex) {
            Logger.getLogger(QuickLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ToolTip tooltip = new ToolTip("Add App", gothicFont, 1);
            tooltip.setOffsetX(20);
            tooltip.setBackgroundColor(Color.BLACK);
            tooltip.setForegroundColor(Color.WHITE);
            allFilePaths.add(1,new AddButton("Add", new ImageIcon(ImageIO.read(getClass().getResource("/res/addButton.png"))),tooltip));
        } catch (IOException ex) {
            Logger.getLogger(QuickLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        Settings settings = new Settings(0,5,0);
        if(allFilePathsInString != null){
            settings.setPreferredDirection(Integer.parseInt(allFilePathsInString.get(0)));
            settings.setScrollSpeed(Integer.parseInt(allFilePathsInString.get(1)));
            settings.setStartUp(Integer.parseInt(allFilePathsInString.get(2)));
        }
        appStarter(settings,allFilePaths);
    }
    
    public void appStarter(Settings settings,ArrayList<Button> allFilePaths){
        frame = new JFrame();
        mainPane = new MainPane(allFilePaths,settings);
        
        frame.add(mainPane);
        frame.setUndecorated(true);
        frame.setType(javax.swing.JFrame.Type.UTILITY);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(75,Toolkit.getDefaultToolkit().getScreenSize().height));
        frame.setResizable(false);
        if(MainPane.LEFT_SIDE == settings.getPreferredDirection()){
            frame.setBounds(-74,0,75,Toolkit.getDefaultToolkit().getScreenSize().height);
        }
        else if(MainPane.RIGHT_SIDE == settings.getPreferredDirection()){
            frame.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width-1,0,75,Toolkit.getDefaultToolkit().getScreenSize().height);        
        }
        frame.pack();
        
        frame.setVisible(true);        
    }
    
    public static ImageIcon getIconImage(File file) throws FileNotFoundException{
        ImageIcon icon = null;
        if(System.getProperty("os.name").toLowerCase().contains("win")){
            sun.awt.shell.ShellFolder sf = 
                    sun.awt.shell.ShellFolder.getShellFolder(file);
            icon = new ImageIcon(sf.getIcon(true));
        }
        if(icon == null){
            if(file.isFile()){
                try {
                    icon = new ImageIcon(ImageIO.read(QuickLauncher.class.getResourceAsStream("/res/file.png")));
                } catch (IOException ex) {
                    
                }
            }
            if(file.isDirectory()){
                try {
                    icon = new ImageIcon(ImageIO.read(QuickLauncher.class.getResourceAsStream("/res/folder_icon.png")));
                } catch (IOException ex) {
                    
                }
            }
            else{
                try {
                    icon = new ImageIcon(ImageIO.read(QuickLauncher.class.getResourceAsStream("/res/no_image_found.png")));
                } catch (IOException ex) {
                    
                }                
            }
        }
        return icon;
    }
    
    public static void openApplication(String filePath, int mode) {
        if(mode == 0){
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException ex) {
                Logger.getLogger(QuickLauncher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(mode == 1){
            try {
                Runtime.getRuntime().exec("explorer.exe /select,"+filePath);
            } catch (IOException ex) {
                Logger.getLogger(QuickLauncher.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }
    
    public static void updateSide(JFrame frame,Settings settings){
        if(MainPane.LEFT_SIDE == settings.getPreferredDirection()){
            frame.setBounds(-74,0,75,Toolkit.getDefaultToolkit().getScreenSize().height);
        }
        else if(MainPane.RIGHT_SIDE == settings.getPreferredDirection()){
            frame.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width-1,0,75,Toolkit.getDefaultToolkit().getScreenSize().height);        
        }        
    }
    
}
