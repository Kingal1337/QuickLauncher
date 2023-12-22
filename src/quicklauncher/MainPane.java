package quicklauncher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.convertPointFromScreen;
import java.util.Timer;
import java.util.TimerTask;

public class MainPane extends JPanel implements MouseListener,MouseMotionListener{
    private Timer timer;
    private TimerTask task;
    
    private ArrayList<Button> allButtons;
    private int selectedIndex;
    
    public static long ticks;
    public static final int TICK = 20;
    public static final int FPS = 1000/TICK;
    
    private int mouseY;
    private int mouseX;
    
    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    
    private Settings settings;
    
    private int initY;
    private int endY;
    private boolean dragging;
    
    private JPanel appPanel;
    private JScrollPane scroll;
    
    private static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
    private static final DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
    private static Date date;
    
    private int seconds;
    public MainPane(ArrayList<Button> allButtons,Settings settings){
        setLayout(null);
        appPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D gd = (Graphics2D)g;        

                PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                Point point = pointerInfo.getLocation();
                convertPointFromScreen(point, this);
                mouseX = (int) point.getX();
                mouseY = (int) point.getY();
                
                
                PointerInfo scrollPointerInfo = MouseInfo.getPointerInfo();
                Point scrollPoint = scrollPointerInfo.getLocation();
                convertPointFromScreen(scrollPoint, scroll);
                int forScrollMouseY = (int) scrollPoint.getY();
                
                if(forScrollMouseY < 70){
                    if(scroll.getViewport().getViewPosition().getY()-settings.getScrollSpeed() <= 0){
                        
                    }
                    else{
                        scroll.getViewport().setViewPosition(new Point(0,(int)(scroll.getViewport().getViewPosition().getY()-settings.getScrollSpeed())));         
                    }
                }
                
                if(forScrollMouseY > Toolkit.getDefaultToolkit().getScreenSize().height-70){
                    scroll.getViewport().setViewPosition(new Point(0,(int)(scroll.getViewport().getViewPosition().getY()+settings.getScrollSpeed())));
                }

                selectedIndex = (mouseY)/75;
                if(mouseX > 3 && mouseX < 72){
                    if(mouseY < 0){
                        selectedIndex = -1;
                    }
                    else if(selectedIndex > allButtons.size()){ 
                        selectedIndex = -1;
                    }
                    else if(selectedIndex < 0){
                        selectedIndex = -1;
                    }                    
                }
                else{
                    selectedIndex = -1;
                }


                int size = 75;

                int startingX = 0;
                int startingY = 0;
                int x = startingX;
                int y = startingY;

                pointerInfo = MouseInfo.getPointerInfo();
                point = pointerInfo.getLocation();
                
                
                for(int i=0;i<allButtons.size();i++){
                    if(selectedIndex == i){
                        
                    }
                    else{
                        allButtons.get(i).getTooltip().update(null,false);     
                    }
                }
                
                for(int i=0;i<allButtons.size();i++){
                    if(selectedIndex == i){
                        gd.setColor(new Color(255,255,255,200));
                        gd.fillRect(x+3, y+3, 68, 68);
                    }
                    if(dragging && selectedIndex == i){
                        gd.setColor(Color.decode("#111111"));
                        gd.fillRect(x+3, y+3, 68, 68); 
                        y += size;
                    }            
                    if(allButtons.get(i) instanceof ImageButton){
                        Image icon = ((ImageButton)(allButtons.get(i))).getImageIcon().getImage();
                        int offsetX = (size/2)-(icon.getWidth(null)/2);
                        int offsetY = (size/2)-(icon.getHeight(null)/2);
                        gd.drawImage(icon, x+offsetX, y+offsetY, null);
                    }                            
                    if(selectedIndex == i){
                        allButtons.get(selectedIndex).getTooltip().update(point,true);
                    }
                    y += size;      
                }
            }
        };
        
        
        timer();
        this.allButtons = allButtons;
        this.settings = settings;
        selectedIndex = -1;
        setBackground(Color.decode("#111111"));
        addMouseListener(this);
        addMouseMotionListener(this);
        
        appPanel.setPreferredSize(new Dimension(75,(allButtons.size()*75)+75));
        appPanel.setBackground(Color.decode("#111111"));
        
        scroll = new JScrollPane(appPanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(settings.getScrollSpeed());
        scroll.setPreferredSize(new Dimension(75,Toolkit.getDefaultToolkit().getScreenSize().height-35));
        scroll.setBounds(0, 35, 75+20, Toolkit.getDefaultToolkit().getScreenSize().height-35);
        this.add(scroll);
                
        appPanel.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseDrag(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {}           
        });
        appPanel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClick(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                mouseRelease(e);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                entered();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exited();
            }
        });
        scroll.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                entered();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exited();
            }
        });
        
        DropTargetListener dragAndDropListenerAnimalImage = new DropTargetListener() {
            @Override
            public void drop(DropTargetDropEvent event) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable transferable = event.getTransferable();
                DataFlavor[] flavors = transferable.getTransferDataFlavors();
                for (DataFlavor flavor : flavors) {
                    try {
                        if (flavor.isFlavorJavaFileListType()) {
                            java.util.List<File> files = (java.util.List<File>) transferable.getTransferData(flavor);
                            if (files.size() == 1) {
                                String filePath = files.get(0).getAbsolutePath();
                                ToolTip tooltip = new ToolTip(new File(filePath).getName()+"\n"+new File(filePath).getAbsolutePath(), new Font("Gothic",1,12), 1);
                                tooltip.setOffsetX(20);
                                tooltip.setBackgroundColor(Color.BLACK);
                                tooltip.setForegroundColor(Color.WHITE);
                                QuickLaunchButton button = new QuickLaunchButton(new File(filePath).getName(),QuickLauncher.getIconImage(new File(filePath)), new File(filePath).getParent(),new File(filePath).getAbsolutePath(),tooltip);
                                int finalEndTile = selectedIndex;
                                if(finalEndTile == -1){
                                    allButtons.add(allButtons.size(), button);                                       
                                }
                                else if(finalEndTile >= allButtons.size()){
                                    allButtons.add(allButtons.size(), button);
                                }
                                else if(mouseY < 150){
                                    allButtons.add(2, button);
                                }
                                else {
                                    allButtons.add(finalEndTile, button);                
                                }
                                dragging = false;
                                FileManager.fileSaver(allButtons, settings);    
                                appPanel.setPreferredSize(new Dimension(75,(allButtons.size()*75)+75));                                                             
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                event.dropComplete(true);

            }

            @Override
            public void dragEnter(DropTargetDragEvent event) {
                dragging = true;
                entered();
            }

            @Override
            public void dragExit(DropTargetEvent event) {
            }

            @Override
            public void dragOver(DropTargetDragEvent event) {
            }

            @Override
            public void dropActionChanged(DropTargetDragEvent event) {
            }
        };
        new DropTarget(this, dragAndDropListenerAnimalImage);
        new DropTarget(scroll, dragAndDropListenerAnimalImage);
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D)g;
        
        date = new Date();
        gd.setColor(Color.WHITE);
        gd.setFont(new Font("Gothic",1,14));
        drawCenteredString(dateFormat.format(date),0,0, 75, 14, gd);
        drawCenteredString(timeFormat.format(date),0,14, 75, 14, gd);
        
    }
    
    public void addApplication(String filePath){
        ToolTip tooltip = new ToolTip(new File(filePath).getName()+"\n"+new File(filePath).getAbsolutePath(), new Font("Gothic",1,12), 1);
        tooltip.setOffsetX(20);
        tooltip.setBackgroundColor(Color.BLACK);
        tooltip.setForegroundColor(Color.WHITE);
        try {
            allButtons.add(allButtons.size(), new QuickLaunchButton(new File(filePath).getName(),QuickLauncher.getIconImage(new File(filePath)), new File(filePath).getParent(),new File(filePath).getAbsolutePath(),tooltip));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileManager.fileSaver(allButtons,settings);
        appPanel.setPreferredSize(new Dimension(75,(allButtons.size()*75)+75));
    }
    
    public void drawCenteredString(String s,int x,int y, int width, int height, Graphics2D gd) {
        FontMetrics fm = gd.getFontMetrics();
        int xx = ((width - fm.stringWidth(s)) / 2) + x;
        int yy = (fm.getAscent() + (height - (fm.getAscent() + fm.getDescent())) / 2) + y;
        gd.drawString(s, xx, yy);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClick(e);
    }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        entered();
    }
    @Override
    public void mouseExited(MouseEvent e) {
        exited();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }
    @Override
    public void mouseMoved(MouseEvent e) {}
    
    public void entered(){
        QuickLauncher.frame.toFront();
        selectedIndex = -1;
        if(settings.getPreferredDirection() == LEFT_SIDE){
            QuickLauncher.frame.setBounds(0,0,75,Toolkit.getDefaultToolkit().getScreenSize().height);
        }
        if(settings.getPreferredDirection() == RIGHT_SIDE){
            QuickLauncher.frame.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width-75,0,75,Toolkit.getDefaultToolkit().getScreenSize().height);
        }
    }
    
    public void exited(){
        for(int i=0;i<allButtons.size();i++){
            allButtons.get(i).getTooltip().update(null,false);                       
        }
        if(settings.getPreferredDirection() == LEFT_SIDE){
            QuickLauncher.frame.setBounds(-74,0,75,Toolkit.getDefaultToolkit().getScreenSize().height);
        }
        if(settings.getPreferredDirection() == RIGHT_SIDE){
            QuickLauncher.frame.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width-1,0,75,Toolkit.getDefaultToolkit().getScreenSize().height);
        }
    } 
    
    public void mouseClick(MouseEvent e){
        if(selectedIndex != -1 && selectedIndex < allButtons.size()){
            if(MouseEvent.BUTTON1 == e.getButton()){
                exited();
                allButtons.get(selectedIndex).action();
                selectedIndex = -1;
            }
            else if(MouseEvent.BUTTON3 == e.getButton()){
                int tempIndex = selectedIndex;
                if(tempIndex != 0){
                    if(allButtons.get(selectedIndex) instanceof QuickLaunchButton){
                        int selectedIndex = this.selectedIndex;
                        ButtonWindow window = new ButtonWindow(new Font("Courier New",1,12), new String[]{"Open","Open File Location","Remove"},15){
                            @Override
                            public void selector(int index){
                                if(index == 0){
                                    update(null,false);
                                    QuickLauncher.openApplication(((QuickLaunchButton)allButtons.get(selectedIndex)).getAbsoluteFilePath(), 0);
                                }
                                if(index == 1){
                                    update(null,false);
                                    if(System.getProperty("os.name").toLowerCase().startsWith("win")){
                                        QuickLauncher.openApplication(((QuickLaunchButton)allButtons.get(selectedIndex)).getAbsoluteFilePath(), 1);
                                    }
                                    else if(System.getProperty("os.name").toLowerCase().startsWith("mac")){
                                        try {            
                                            Runtime.getRuntime().exec("/usr/bin/open "+((QuickLaunchButton)allButtons.get(selectedIndex)).getAbsoluteFilePath()).waitFor();
                                        } catch (IOException ex) {

                                        } catch (InterruptedException ex) {

                                        }
                                    }
                                    else{
                                        AOptionPane.showMessageDialog("Error<br>Cannot Open File Location<br>Reason : OS not supported", "Error", true, true);
                                    }
                                }
                                if(index == 2){
                                    update(null,false);
                                    QuickLauncher.mainPane.removeApplication(selectedIndex);
                                }                                
                            }
                        };                    
                        window.setOffsetX(-10);
                        window.setOffsetY(-5);
                        window.update(e.getLocationOnScreen(), true);
                        
                    }
                }
            }
        }
    }
    
    public void mouseDrag(MouseEvent e){  
        boolean isLeftDown = SwingUtilities.isLeftMouseButton(e);
        if(isLeftDown){
            if(!dragging){
                dragging = true;
                initY = e.getY();
            }
        }
    }
    
    public void mouseRelease(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON1){
            if(dragging){
                dragging = false;
                endY = e.getY();
                int finalInitTile = ((initY)/75);
                int finalEndTile = ((endY)/75);
                System.out.println(initY);
                System.out.println(endY);
                if(finalInitTile < allButtons.size() && initY >= 150){
                    if(finalEndTile >= allButtons.size()-1){
                        QuickLaunchButton button = (QuickLaunchButton)allButtons.get(finalInitTile);
                        allButtons.remove(finalInitTile);
                        allButtons.add(allButtons.size(), button);
                    }
                    else if(endY < 150){
                        QuickLaunchButton button = (QuickLaunchButton)allButtons.get(finalInitTile);
                        allButtons.remove(finalInitTile);
                        allButtons.add(2, button);
                    }
                    else {
                        QuickLaunchButton button = (QuickLaunchButton)allButtons.get(finalInitTile);
                        allButtons.remove(finalInitTile);
                        allButtons.add(finalEndTile, button);                
                    }
                    FileManager.fileSaver(allButtons, settings);
                }
            }    
        }
    }
    
    public Settings getSettings(){
        return settings;
    }
    
    public void setSettings(Settings settings){
        this.settings = settings;
    }
    
    public void updateSettings(Settings settings){
        scroll.getVerticalScrollBar().setUnitIncrement(settings.getScrollSpeed());
        QuickLauncher.updateSide(QuickLauncher.frame,settings);
        ShortCuts.update(settings.isStartUp());
        FileManager.fileSaver(allButtons, settings);
    }
    
    public void timer(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                ticks++;
                appPanel.repaint();  
                if(ticks%5 == 0){
                    seconds++;
                }
                if(seconds == 60){
                    seconds = 0;
                    System.gc();
                }
            }
        };
        timer.schedule(task, 0, TICK);
    }
    
    public void removeApplication(int index){
        exited();
        JFrame myFrame = null;
        String[] options = {"Yes","No"};
        int code = JOptionPane.showOptionDialog(myFrame, 
        "Are you sure you to remove\n"+allButtons.get(index).getName(), 
        "", 0, JOptionPane.PLAIN_MESSAGE, 
        null, options, "PHP");
        if(code==-1){

        }
        if(code==0){
            allButtons.remove(index);
            FileManager.fileSaver(allButtons,settings);
        }
        if(code==1){

        }        
        appPanel.setPreferredSize(new Dimension(75,(allButtons.size()*75)+75));
    }
    
}
