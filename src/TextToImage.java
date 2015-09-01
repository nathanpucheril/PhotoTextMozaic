
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TextToImage {

    /**
     * 
     * @param s
     * @param fontName
     * @param fontStyle (Enum: Font.Bold, Font.Plain, etc...)
     * @param fontSize
     * @param fontColor
     * @param backgroundColor
     * @param padUp
     * @param padDown
     * @param padLeft
     * @param padRight
     * @return
     */
    private static BufferedImage makeImage(String s, String fontName, int fontSize, int fontStyle,
            Color fontColor, Color backgroundColor, boolean isSquare) {
        
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font(fontName, Font.PLAIN, fontSize);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(s);
        int height = fm.getHeight();
        
        g2d.dispose();

        if (isSquare) {
            int size = Math.max(width, height);
            img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        } else {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        g2d = img.createGraphics();
        g2d.setFont(font);
        
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        fm = g2d.getFontMetrics();
        g2d.setColor(fontColor);
        g2d.drawString(s, 0, fm.getAscent());
        g2d.dispose();
        return img;
    }
    
    public static BufferedImage getImage(String s, String font, int fontSize, int r, int g, int b) {
        return makeImage(s, font, fontSize, Font.BOLD, new Color(r, g, b), Color.WHITE,false);
    }
    
    public static BufferedImage getImage(String s, String font, int fontSize, int intensity) {
        int c = intensity;
        return makeImage(s, font, fontSize, Font.BOLD, new Color(c, c, c), Color.WHITE,false);
    }
    
    public static BufferedImage getImage(char character, String font, int fontSize, int intensity) {
        int c = intensity;
        return makeImage(Character.toString(character), font, fontSize, Font.BOLD, new Color(c, c, c), Color.WHITE,false);
    }
    
    public static BufferedImage getImageSquare(char character, String font, int fontSize, int intensity) {
        int c = intensity;
        return makeImage(Character.toString(character), font, fontSize, Font.BOLD, new Color(c, c, c), Color.WHITE,true);
    }

    public static void main(String[] args) {

        try {
            File outputfile = new File("collage.png");
            ImageIO.write(getImage("hello", "Arial", 40, 255), "png", outputfile);
        } catch(IOException e) {
            System.out.println("error");
        }
    }
}
