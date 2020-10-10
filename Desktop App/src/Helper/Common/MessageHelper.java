/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Common;
import FormTemplate.Info;
import FormTemplate.Warning;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author phamdinhlam
 */
public class MessageHelper {
    
    public static void Info(String content)
    {
        JFrame infoFrame = new Info();
        infoFrame.setVisible(true);
        FormTemplate.Info.txtInfo.setText(content);
        infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public static void Warning(String content)
    {
        JFrame warningFrame = new Warning();
        warningFrame.setVisible(true);
        FormTemplate.Warning.txtInfo.setText(content);
        warningFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
    
    public static void Error(String content)
    {
        JFrame errorFrame = new FormTemplate.Error();
        errorFrame.setVisible(true);
        FormTemplate.Error.txtInfo.setText(content);
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
}
