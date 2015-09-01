import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class TextMozaic {

    private CyclicLinkedList<Character> textList = new CyclicLinkedList<Character>();
    private BufferedImage parentImg = null;
    private BufferedImage mosaic = null;
    private boolean built = false;
    private HashMap<Character, HashMap<Integer, BufferedImage>> charToImg = new HashMap<Character, HashMap<Integer, BufferedImage>>();

    public TextMozaic(String s, BufferedImage img) {
        for (char c : s.toCharArray()) {
            textList.add(c);
        }
        parentImg = img;
    }

    public BufferedImage bwbuild() {
        long t = System.nanoTime();
        int stepSize = 5;
        for(int j = 0; j < parentImg.getHeight(); j+= stepSize) {
            BufferedImage horizImg = null;
            for (int i = 0; i < parentImg.getWidth(); i+= stepSize) {
                int intensity = ImageAnalytics.getAvgImageIntensity(parentImg, i, j, stepSize, stepSize);
     
                char nextChar = textList.next();
                BufferedImage charImg;
                if (charToImg.get(nextChar) != null && charToImg.get(nextChar).get(intensity) != null) {
                    charImg = charToImg.get(nextChar).get(intensity);
                    System.out.println("test"); 
                } else {
                    HashMap<Integer, BufferedImage> temp;
                    charImg = TextToImage.getImageSquare(nextChar, "Courier", 14, Math.min(255, intensity + 30));
                    if (charToImg.get(charToImg) == null) {
                        temp = new HashMap<Integer, BufferedImage>();
                        temp.put(intensity, charImg);
                        charToImg.put(nextChar, temp);
                    } else {
                        temp = charToImg.get(nextChar);
                        temp.put(intensity, charImg);
                    }
                }
                if (i == 0) {
                    horizImg = charImg;
                } else {
                    horizImg = ImageStitcher.stitch(horizImg, charImg, ImageStitcher.StitchMode.HORIZONTAL);
                }
            }
            if (j == 0) {
                mosaic = horizImg;
            } else {
                mosaic = ImageStitcher.stitch(mosaic, horizImg, ImageStitcher.StitchMode.VERTICAL);
            }
        }
        built = true;
        System.out.println(System.nanoTime() - t);
        return mosaic;
    }

    public BufferedImage getMozaic() {
        if (built) {
            return mosaic;
        } else {
            throw new NullPointerException();
        }
    }


    public static void main(String[] args) {
        File imageFile = null;
        JFileChooser fileSelecter = new JFileChooser();
        fileSelecter.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
        fileSelecter.setDialogTitle("Pick the Main Image");

        int fileChooserState = fileSelecter.showOpenDialog(null);
        if (fileChooserState == JFileChooser.APPROVE_OPTION) { // if acceptable file is chosen shows user what file they picked in JOptionPane window
            JOptionPane.showMessageDialog(null,"You opened file: " + fileSelecter.getSelectedFile().getPath());
            imageFile  = fileSelecter.getSelectedFile();
        } else {
            System.out.println("Image not Chosen");
        }
        try {
            BufferedImage image = ImageIO.read(imageFile);
            TextMozaic tm = new TextMozaic("23", image);
            BufferedImage mosaic = tm.bwbuild();

            try {
                File outputfile = new File("collage.png");
                ImageIO.write(mosaic, "png", outputfile);
            } catch(IOException e) {
                System.out.println("error");
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
