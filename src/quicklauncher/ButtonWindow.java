package quicklauncher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.SwingUtilities.convertPointFromScreen;

public abstract class ButtonWindow extends PopupWindow implements MouseMotionListener,MouseListener{
    private String[] buttonNames;
    private Color foregroundColor;
    private Color hoverColor;
    private Font font;
    private int length;
    private int stringHeight;
    private int lineGap;
    private int selectedIndex;
    
    private int mouseY;
    public ButtonWindow(Font font, String[] buttonNames,int lineGap) {
        super(0,0,0,0);
        selectedIndex = -1;
        hoverColor = Color.LIGHT_GRAY;
        foregroundColor = Color.BLACK;
        this.font = font;
        this.buttonNames = buttonNames;
        this.lineGap = lineGap;
        stringHeight = Text.getHeightOfString(font);
        length = Text.getLargestNumber(buttonNames, font);
        setWidth(length);
        setHeight((stringHeight+lineGap)*buttonNames.length);
        getPanel().addMouseListener(this);
        getPanel().addMouseMotionListener(this);
    }
    
    @Override
    public void paintMethod(Graphics2D gd) {       
        int increment = stringHeight+lineGap;
        int x = 0;
        int y = 0;
        gd.setFont(font);
        for(int i=0;i<buttonNames.length;i++){
            if(selectedIndex == i){
                gd.setColor(hoverColor);
                gd.fillRect(x, y, length, stringHeight+lineGap);
            }
            gd.setColor(foregroundColor);
            gd.drawString(buttonNames[i],x,(y+stringHeight)+(lineGap/2));
            y+=increment;            
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point point = pointerInfo.getLocation();
        convertPointFromScreen(point, getPanel());
        mouseY = (int) point.getY();
        
        selectedIndex = mouseY/(stringHeight+lineGap);
        getPanel().repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point point = pointerInfo.getLocation();
        convertPointFromScreen(point, getPanel());
        mouseY = (int) point.getY();
        
        selectedIndex = mouseY/(stringHeight+lineGap);
        System.out.println(selectedIndex);
        selector(selectedIndex);
        
    }
    
    

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        update(new Point(getX(),getY()),true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        update(null,false);
    }
    
    public void setForegroundColor(Color foregroundColor){
        this.foregroundColor = foregroundColor;
    }
    
    public abstract void selector(int selectedIndex);
    
    
    
}
