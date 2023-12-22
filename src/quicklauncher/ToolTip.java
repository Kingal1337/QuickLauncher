package quicklauncher;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ToolTip extends PopupWindow{
    private int singleLineHeight;
    private String[] messages;
    private String message;
    private int length;
    private int lineGap;
    
    private Font font;
    
    private Color foregroundColor;
    
    public ToolTip(String message,Font font,int lineGap){
        super(0,0,0,0);
        this.message = message;
        this.lineGap = lineGap;
        foregroundColor = Color.BLACK;
        this.font = font;
        messages = this.message.split("\n");
        length = Text.getLargestNumber(messages, font);
        setWidth(length);
        singleLineHeight = Text.getHeightOfString(font);
        setHeight(Text.getHeightOfString(font)*messages.length);  
    }
    
    
    @Override
    public void paintMethod(Graphics2D gd){        
        if(getX()+getWidth()+20 >= Toolkit.getDefaultToolkit().getScreenSize().width){
            setX(getX()-getWidth());
        }
        int tempX = 0;
        int tempY = 0;
        gd.setColor(foregroundColor);
        tempY+=singleLineHeight/2+2;
        gd.setFont(font);
        for(int i=0;i<messages.length;i++){
            gd.drawString(messages[i],tempX,tempY+lineGap);
            tempY+=singleLineHeight;
        }  
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessages(String message) {
        this.message = message;
        messages = this.message.split("\n");
        updateFont();
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;    
        updateFont();
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
    
    public void updateFont(){
        length = Text.getLargestNumber(messages, font);
        setWidth(length);
        singleLineHeight = Text.getHeightOfString(font);
        setHeight(Text.getHeightOfString(font)*messages.length);
    }
    
    
}
