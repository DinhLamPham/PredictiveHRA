/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Common;

import Helper.Common.MessageHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 *
 * @author phamdinhlam
 */
public class FileReadWrite {
    static String graphMLExtension=".graphml";
    
    public static String FileReadline(String filePath)
    {
        File f = new File(filePath);
        try {
            LineIterator it = FileUtils.lineIterator(f,"UTF-8");
             try {
                while (it.hasNext()) {
                        String line = it.nextLine();
                        return line;
                    }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        return "";
    }
    public static void WriteNodeId(String filePath, String nodeId) throws FileNotFoundException, IOException
    {
        try {
            File fout = new File(filePath);
	FileOutputStream fos = new FileOutputStream(fout);
	OutputStreamWriter osw = new OutputStreamWriter(fos);
        osw.write(nodeId);
	osw.close();
        } catch (Exception e) {
            MessageHelper.Warning(e.toString());
        }
        
    }
    
    public static void WriteViewTraceInfo(String filePath, String nodeHexColor, String edgeHexColor, String edgeWidth, Integer timeDelay, String keyWordSeparate, String trace) throws FileNotFoundException, IOException
    {
        try {
            File fout = new File(filePath);
	FileOutputStream fos = new FileOutputStream(fout);
	OutputStreamWriter osw = new OutputStreamWriter(fos);
        
            PrintWriter writer = new PrintWriter(osw);   
            writer.println(nodeHexColor);
            writer.println(edgeHexColor);
            writer.println(edgeWidth);
            writer.println(timeDelay.toString());
            writer.println(keyWordSeparate);
            writer.println(trace);
            writer.flush();
            writer.close();
	osw.close();
        } catch (Exception e) {
            MessageHelper.Warning(e.toString());
        }
        
    }
    
    public static List<String> ReadTraceProcess(String filePath)
    {
        Integer lineCount=-1;
        String traceInfo="";
        List<String> outputString = new LinkedList<>();
        File f = new File(filePath);
        try {
            LineIterator it = FileUtils.lineIterator(f,"UTF-8");
             try {
                while (it.hasNext()) {
                    lineCount++;
                    String line = it.nextLine();
                    if(lineCount<=4)
                        outputString.add(line);
                    else
                        traceInfo+=line;
                        
                    }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        outputString.add(traceInfo); //Line5: Trace infomation
        return outputString;
    }
    public static String ReadXMLTemplate(String filePath)
    {
         String outputString = "";
        File f = new File(filePath);
        try {
            LineIterator it = FileUtils.lineIterator(f,"UTF-8");
             try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    outputString+=line;
                        
                    }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        return outputString;
    }
}
