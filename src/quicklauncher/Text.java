package quicklauncher;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;

public class Text {
    public static int getLengthOfString(String string, Font font) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        FontMetrics fm = img.getGraphics().getFontMetrics(font);
        int width = fm.stringWidth(string);
        return width;
    }

    public static int getHeightOfString(Font font) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        FontMetrics fm = img.getGraphics().getFontMetrics(font);
        int height = fm.getHeight();
        return height;
    }

    public static int getLargestNumber(String[] strings, Font font) {
        int largeNumber = getLengthOfString(strings[0], font);
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings.length; j++) {
                if (i != j) {
                    if (getLengthOfString(strings[i], font) < getLengthOfString(strings[j], font)) {
                        if (getLengthOfString(strings[j], font) > largeNumber) {
                            largeNumber = getLengthOfString(strings[j], font);
                        }
                    }
                }
            }
        }
        return largeNumber;
    }
}
