package quicklauncher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class PopupWindow {
    private int x;
    private int y;
    private int width;
    private int height;
    private JFrame frame;
    private JPanel panel;
    
    private Color backgroundColor;
    private Color borderColor;
    
    private int offsetX;
    private int offsetY;

    public PopupWindow(int x, int y, int width, int height) {
        offsetX = 0;
        offsetY = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backgroundColor = Color.WHITE;
        this.borderColor = Color.BLACK;
        frame = new JFrame();
        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D gd = (Graphics2D)g;
                
                paintMethod(gd);                
            }
        };   
        panel.setBackground(backgroundColor);
        panel.setPreferredSize(new Dimension(width,height));
        frame.setAlwaysOnTop(true);
        frame.setType(JFrame.Type.UTILITY);
        frame.setUndecorated(true);
        frame.setBounds(x,y,width,height);
        frame.add(panel);
    }
    
    public void update(Point mouseXY,boolean show){
        int xx = 0;
        int yy = 0;
        if(mouseXY != null){
            xx = mouseXY.x;
            yy = mouseXY.y;
        }
        if(show){
            setX(xx);
            setY(yy);
            
            panel.repaint();
            if(getX()+getWidth() >= Toolkit.getDefaultToolkit().getScreenSize().width){
                setX(getX()-(getWidth()+(offsetX*2)));
            }
            frame.setBounds(getX()+offsetX,getY()+offsetY,getWidth(),getHeight());
            frame.setVisible(true);
        }
        else{
            frame.setVisible(false);            
        }
    }
    
    public abstract void paintMethod(Graphics2D gd);

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        updateColors();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
    
    private void updateColors(){        
        panel.setBackground(backgroundColor);
    }
    

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
        
    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
    
    
    
}
