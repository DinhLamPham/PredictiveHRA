/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Python;

import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phamdinhlam
 */
public class RunPython extends Thread {
    private static String pythonFileNameFullPath="";
    
    public void run(){
        try {
            CallPython();
        } catch (IOException ex) {
            Logger.getLogger(RunPython.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RunPython.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void SetFileToRun(String _funcName)
    {   Variables.pythonRunningFile = _funcName + ".py";
        pythonFileNameFullPath = Variables.pythonFolder + Variables.pythonRunningFile;
        
    }
    
    public static void CallPython() throws IOException, InterruptedException
    {
        try {
                    String pythonFile = pythonFileNameFullPath;
                    File f = File.createTempFile(pythonFile, ".py");
                    // Check file exist?
                    if(!f.exists())
                    {
                        MessageHelper.Warning("Python file doesn't exist! Please contact the developer");
                        return;
                    }

                       Process p = Runtime.getRuntime().exec("python "+pythonFile);
                      // --------Cal Threadl display waiting windows
                      
                       
                      //-------------------------------------------Runtime.getRuntime().exec ImportError: No module named

                      p.waitFor();
                      
                      

                        //-----------------------------------Python Error---------------------------------
                      if(p.exitValue()!=0)  //
                      {
                          
                           BufferedReader bufferErrorStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                           String line, errorString="Python function Error! Please check the log."; String errorAllContent="";
                            while ((line = bufferErrorStream.readLine()) != null) 
                            {System.out.println(line); errorAllContent+=line+"\n";}
                            bufferErrorStream.close();
                          MessageHelper.Warning(errorString);
                          Helper.Python.ErrorHandle.ShowError(errorAllContent);
                      }
                      
                     //----------------------------------------------------------------------------------
                        // Python program have finished already.
                        BufferedReader bufferInputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        List<String> pythonOutputParameterList = new LinkedList<>();
                        String line;
                        while ((line = bufferInputStream.readLine()) != null) 
                                pythonOutputParameterList.add(line);
                        bufferInputStream.close();
                    //------------------------------- Python running file: Mining external process
                        

                    p.destroy();
            
        } catch (Exception e) {
            MessageHelper.Error(e.toString()); 
            
        }
    }
}
