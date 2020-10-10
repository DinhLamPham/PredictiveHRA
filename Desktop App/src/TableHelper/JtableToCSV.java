/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableHelper;

import com.opencsv.CSVWriter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.swing.JTable;
import javax.swing.table.TableModel;
//import javax.xml.ws.Response;

/**
 *
 * @author Administrator
 */
public class JtableToCSV {
    public static boolean exportJTableToCSV(JTable _tableToExport, String pathFileToExport) 
    {
        try { 
            // create FileWriter object with file as parameter 
            FileOutputStream fos = new FileOutputStream(pathFileToExport);
            OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.forName("EUC-KR"));
            CSVWriter writer = new CSVWriter(osw); 
            TableModel model = _tableToExport.getModel();
            Integer numberOfCol = model.getColumnCount(); 
            Integer numberOfRow = model.getRowCount();
            String[] header = new String[numberOfCol]; 
             // adding header to csv 
            for(Integer i=0; i<numberOfCol;i++)
                header[i] = (String) _tableToExport.getColumnModel().getColumn(i).getHeaderValue();
            writer.writeNext(header); 
            
            // Adding row value to CSV
            for(Integer i=0; i<numberOfRow; i++)
             {
                 String[] rowData = new String[numberOfCol]; 
                 for(Integer j=0; j<numberOfCol; j++)
                 {
                     Object currentCellValueObject = model.getValueAt(i, j);
                     String currentCellValueString ="";
                     if(currentCellValueObject!=null)
                         currentCellValueString = currentCellValueObject.toString();
                     rowData[j] = currentCellValueString;
                 }
                 writer.writeNext(rowData); 
             }
            writer.close(); 
            return true;
        } 
        catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } 
        
    return false;
}
 
    
    
    
}
