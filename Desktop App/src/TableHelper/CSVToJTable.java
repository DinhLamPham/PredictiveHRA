/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableHelper;

import Helper.Common.MessageHelper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class CSVToJTable {
    public static JTable ReadCSVFile(String _fileName)
    {
        JTable table = new JTable();
         DefaultTableModel model = null;
        try {
            // Read CSV File
             String line;Integer currentRow=-1;
                Charset inputCharset = Charset.forName("EUC-KR");
               FileInputStream fis = new FileInputStream( _fileName);
               InputStreamReader isr = new InputStreamReader(fis, inputCharset);
               BufferedReader myReader = new BufferedReader(isr);
               while((line = myReader.readLine())!=null)
               {
                   line = line.replace("\"", "");
                   String[] data=line.split(",");
                    currentRow++;
                    if(currentRow==0)  // Header row => Initialize Table
                    {
                        TableController.InitializeBlankTableWithHeader(table, data);
                        model = (DefaultTableModel) table.getModel();
                    }
                    else
                        model.addRow(data);
               }
               myReader.close();
            
            
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
        
        return table;
    }
}
