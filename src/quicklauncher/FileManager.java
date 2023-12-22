package quicklauncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    public static FileManager fileManager = new FileManager();
    public static String folderPath;
    public static final String extension = "aql";
    public static final String fileName = "QuickLauncherFile";
    private static final String folderName = "QuickLauncherFiles";
    public FileManager(){
        
    }
    
    public static void folderMaker(){
        String preFolderPath = System.getProperty("user.dir");
        File file = new File(preFolderPath+"/"+folderName);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
                folderPath = preFolderPath+"/"+folderName;
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        else{
            folderPath = preFolderPath+"/"+folderName;            
        }
    }
    
    public static ArrayList<String> getApplications(String folderPath){
        File file = new File(folderPath+"/"+fileName+"."+extension);
        ArrayList<String> allFilePaths = new ArrayList<>();
        if(!file.exists()){
            return null;
        }
        else{
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    allFilePaths.add(sCurrentLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allFilePaths;
    }
    
    public static void fileSaver(ArrayList<Button> buttons,Settings settings){
        File file = new File(folderPath+"/"+fileName+"."+extension);
        String content = "";
        content+=settings.getPreferredDirection()+"\n";
        content+=settings.getScrollSpeed()+"\n";
        content+=settings.isStartUp()+"\n";
        for(int i=0;i<buttons.size();i++){
            if(buttons.get(i) instanceof QuickLaunchButton){
                content += ((QuickLaunchButton)(buttons.get(i))).getRelativeFilePath()+"/"+buttons.get(i).getName()+"\n";
            }
        }
        content = content.trim();

        try (FileOutputStream fop = new FileOutputStream(file)) {

            if (!file.exists()) {
                file.createNewFile();
            }

            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
