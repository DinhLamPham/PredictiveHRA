/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadController;

import static Helper.File.XESHelper.SaveXESLogToFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phamdinhlam
 */
public class SaveFileController implements Runnable{
    private static List<String> inputLog;
    private static File fileToSave;

    @Override
    public void run() {
        try {
            SaveXESLogToFile(inputLog, fileToSave);
                    } catch (IOException ex) {
            Logger.getLogger(SaveFileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SaveFileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public SaveFileController(List<String> _input, File _file)
    {
        inputLog = _input; fileToSave = _file;
    }
    
}
