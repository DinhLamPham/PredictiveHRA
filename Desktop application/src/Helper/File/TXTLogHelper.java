/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.File;

import Helper.Common.Variables;
import static Helper.Common.Variables.flagAnalizeSuccess;
import static Helper.Common.Variables.keySeparateInside;
import ProcessingBar.ProgressHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author DinhLam Pham
 */
public class TXTLogHelper {
    public static List<String> ReadLogFromTXTFormat(String _filePath) throws FileNotFoundException
    {
        List<String> outputLog = new LinkedList<>();
        File inputLog = new File(_filePath);
        if(!inputLog.exists())
        {
            return outputLog;
        }
        
        ProgressHelper.RunProgress("Reading file (txt format)...");
        
        try 
        {
            Scanner scanner = new Scanner(new File(_filePath));
            Variables.keyWordSeparate = scanner.nextLine();
            keySeparateInside = scanner.nextLine();
            
            Integer count=0;
            
            while (scanner.hasNextLine()) {
                    String currentLine = scanner.nextLine();
                    outputLog.add(currentLine);
                    if (count<99)
                        count++;
                    else
                        count=1;
                    ProgressHelper.SynchronizeProgress(count, 100);
            }
            scanner.close();
            ProgressHelper.SynchronizeProgress(100, 100);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        
        flagAnalizeSuccess = true;
        return outputLog;
    }
}
