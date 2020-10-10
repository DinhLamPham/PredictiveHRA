/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Common;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author phamdinhlam
 */
public class PanelImage extends JPanel{
    
    private Image image = null;
    private String imagePath="";
    private boolean flagShowed=false;
    
    public void SetFlagShow(boolean _flag)    {flagShowed = _flag;}
    public boolean GetFlagShow()    {return flagShowed;}
    
    public void setImage(String _imagePath) throws IOException {
         File imageFile = new File(_imagePath);
            if(!imageFile.exists())
            {
                MessageHelper.Warning("Chart is not exist! Please check the log");
                System.err.println("File is not exist! "+_imagePath);
            }
            Image currentImage = ImageIO.read(imageFile);
            
            
        image = currentImage;
        imagePath = _imagePath;
        repaint();
    }
    
    public String GetCurrentImagePath()
    {return imagePath;}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {

            int imgWidth, imgHeight;
            double contRatio = (double) getWidth() / (double) getHeight();
            double imgRatio =  (double) image.getWidth(this) / (double) image.getHeight(this);

            //width limited
            if(contRatio < imgRatio){
                imgWidth = getWidth();
                imgHeight = (int) (getWidth() / imgRatio);

            //height limited
            }else{
                imgWidth = (int) (getHeight() * imgRatio);
                imgHeight = getHeight();
            }

            //to center
            int x = (int) (((double) getWidth() / 2) - ((double) imgWidth / 2));
            int y = (int) (((double) getHeight()/ 2) - ((double) imgHeight / 2));

            g.drawImage(image, x, y, imgWidth, imgHeight, this);
        }
    }
}
