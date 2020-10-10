/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.File;

import Helper.Common.MessageHelper;
import ProcessingBar.ProgressHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phamdinhlam
 */
public class ExportXML implements Runnable{
    public static String filePath;
    public static List<String> listString;
    
    public static void Initial(String _filePath, List<String> _listString)
    {
        filePath=_filePath; 
        listString = new LinkedList<String>(); 
        listString=_listString;
    }
    @Override
    public void run()
    {
        try {
            WriteXMLListToFile();
        } catch (IOException ex) {
            MessageHelper.Error(ex.toString());
        }
    }
    
    //********************** WRITE XML List String to FILE ********************
    public static void WriteXMLListToFile() throws FileNotFoundException, IOException
    {
//        try {
//            Integer listSize = listString.size();
//            if(listSize==0)
//                return;
//            File fout = new File(filePath+graphMLExtension);
//            
//            FileOutputStream fos = new FileOutputStream(fout);
//            OutputStreamWriter osw = new OutputStreamWriter(fos);
//            ProgressHelper.RunProgress("Writing to file...");
//            
//            for(Integer i=0;i<listSize;i++)
//            {
//                String currentLine=listString.get(i);
//                osw.write(currentLine);
//                ProgressHelper.SynchronizeProgress(i, listSize); // Synchronize progress status (How many percentage?)
//            }
//            ProgressHelper.SynchronizeProgress(listSize, listSize);
//            MessageHelper.Notice("Done", "Notice");
//            
//            osw.close();
//        } catch (Exception e) {
//             MessageHelper.Error(e);
//        }
        
    }
}
