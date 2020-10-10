/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadController;

import Helper.Common.MessageHelper;
import Helper.Dashboard.LeftPanelHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phamdinhlam
 */
public class ReadFileController implements Runnable{
    
    @Override
    public void run()
    {
        try {
            LeftPanelHelper.ReadRawFile();
        } catch (IOException ex) {
            MessageHelper.Error(ex.toString());
        } catch (InterruptedException ex) {
            Logger.getLogger(ReadFileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
