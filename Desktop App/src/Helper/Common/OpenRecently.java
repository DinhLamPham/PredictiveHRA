/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Common;

import Helper.Common.MessageHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 *
 * @author phamdinhlam
 */
public class OpenRecently {
    public static String recentlyFile="NONE";
    public static String recentlySettingPath="/ini/recentlypath.ini";
    static String defaultPath=Paths.get("").toAbsolutePath().toString() ;
    static String recentlySettingFullPath = defaultPath+ recentlySettingPath;
    
    public static File GetWorkingDirectory() throws IOException
    {
        String recentlyPath=ReadRecentlyPath();
        File file = new File(recentlyPath);
        if(!file.exists())
            file = new File(recentlySettingFullPath);
        
        return file;
    }
    
   
    
    public static String ReadRecentlyPath() throws FileNotFoundException, IOException
    {
        String result="";
        int count=0;
        File file = new File(recentlySettingFullPath);
        if(file.exists() && !file.isDirectory()) 
        {
            LineIterator it = FileUtils.lineIterator(file,"UTF-8");
             try {
                while (it.hasNext()) {
                        count++;
                        String line = it.nextLine();
                        if(count==1)
                        {
                            if(new File(line).exists())
                                result=line;
                            else
                                result=defaultPath;
                        }
                            
                    }
            } catch (Exception e) {
            }
        }
        else
        {
            result=defaultPath;
            UpdateRenctlyPath(result);
        }
       
        return result;
    }
    public static void UpdateRenctlyPath(String content) throws FileNotFoundException, IOException
    {
        try {
            File file = new File(content);
            File currentPath = new File(file.getParent());
            String currentFolder= currentPath.getAbsolutePath();
            
            String currentfile = file.getAbsolutePath();
            File fout = new File(recentlySettingFullPath);
            
            FileOutputStream fos = new FileOutputStream(fout);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
                osw.write(currentfile);
            osw.close();
            } catch (Exception e) {
                MessageHelper.Warning(e.toString());
            }
        
    }
    
}
