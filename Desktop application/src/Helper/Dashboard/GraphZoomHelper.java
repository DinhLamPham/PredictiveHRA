/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import org.graphstream.ui.swingViewer.View;

/**
 *
 * @author Administrator
 */
public class GraphZoomHelper {
    
    public static void mouseWheelMoved(MouseWheelEvent e, View vw) {
        if(vw != null){
                Integer notches = e.getWheelRotation();
                Point point = e.getPoint();
                double i = vw.getCamera().getViewPercent();
                if(i < 1){
                        if(point.getX() < 400){
                                //400 is an example of a hardcode value to change
                                if(point.getY() < 300){
                                        vw.getCamera().getViewCenter().move(-1, 1);
                                }
                                else if(point.getY() < 600){
                                        vw.getCamera().getViewCenter().move(-1, 0);
                                }
                                else{
                                        vw.getCamera().getViewCenter().move(-1, -1);
                                }
                        }
                        else if(point.getX() < 800){
                                if(point.getY() < 300){
                                        vw.getCamera().getViewCenter().move(0, 1);
                                }
                                else if(point.getY() < 600){
                                        vw.getCamera().getViewCenter().move(0, 0);
                                }
                                else{
                                        vw.getCamera().getViewCenter().move(0, -1);
                                }
                        }
                        else{
                                if(point.getY() < 300){
                                        vw.getCamera().getViewCenter().move(1, 1);
                                }
                                else if(point.getY() < 600){
                                        vw.getCamera().getViewCenter().move(1, 0);
                                }
                                else{
                                        vw.getCamera().getViewCenter().move(1, -1);
                                }
                        }
                }
                else{
                        vw.getCamera().resetView();
                }


                if(notches > 0){
                        vw.getCamera().setViewPercent(i * 1.05);
                }
                else{

                        vw.getCamera().setViewPercent(i * 0.95);
                }
        }
    }
    
    public static void keyPressed(KeyEvent e, View vw) {
        
        char character= e.getKeyChar();
        Point point = MouseInfo.getPointerInfo().getLocation();
        double i = vw.getCamera().getViewPercent();
        
        switch (character)
        {
            case '0':
                vw.getCamera().resetView();
            break;
            case '-':
                vw.getCamera().setViewPercent(i * 1.05);
            break;
            case '+':
                vw.getCamera().setViewPercent(i * 0.85);
            break;
           
        }
        
        
    }
}
