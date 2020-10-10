/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.DetailPanel1;
import FormTemplate.DetailPanel2;
import FormTemplate.LeftPanel;
import Helper.Common.MessageHelper;
import Helper.Common.OpenRecently;
import Helper.Common.Variables;
import Helper.File.XESReadingHelper;
import ThreadController.ReadFileController;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import static Helper.Common.Variables.*;
import ThreadController.xesLogToActivityAndPerformerTableControler;
import TableHelper.TableController;
import static TableHelper.TableController.RemoveAllTableData;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import static FormTemplate.DetailPanel1.tblTraceSummary;
import FormTemplate.TrainedModel;
import static Helper.Common.ToolsHelper.InitComboFromSet;
import static Helper.File.TXTLogHelper.ReadLogFromTXTFormat;
import Helper.GraphStream.MakeSteadyGraph;
import Helper.MySQL.Query;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import static Helper.MySQL.Query.SelectDataInDBToJTableNoCond;
import ThreadController.CreateAffiliationController;
import java.util.HashSet;
import java.util.Set;
import org.graphstream.graph.Graph;
import static Helper.GraphStream.MakeSteadyGraph.RecoveryEssentialEdge;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import javax.swing.JFrame;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.graphstream.graph.Edge;

/**
 *
 * @author phamdinhlam
 */
public class LeftPanelHelper {
    
    private static javax.swing.JFileChooser fileChooser;   
    public static String rawFileName = "";
    public static void OpenRawDataFile() throws IOException
    {
        try {
            File workingDirectory = new File(System.getProperty("user.dir"));
        
            try {
                workingDirectory = OpenRecently.GetWorkingDirectory();
            } catch (IOException ex) {
                System.err.println(ex.toString());
            }

            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(workingDirectory);

            Integer returnVal = fileChooser.showOpenDialog(fileChooser);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                rawFileName = file.getAbsolutePath();
                Variables.logName = file.getName();

                ThreadController.ReadFileController readFileController = new ReadFileController();
                Thread threadReadFile = new Thread(readFileController);
                threadReadFile.start();

            } else {
                System.out.println("File access cancelled by user.");
            }
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
    }
    
    public static void ReadRawFile() throws IOException, InterruptedException
    {
        if ("".equals(rawFileName)) {
            return;
        }
        boolean allowFileExtension = false;
        if (rawFileName.toLowerCase().contains(".xes") || rawFileName.toLowerCase().contains(".txt")) {
            allowFileExtension = true;
        }
        if(!allowFileExtension)
        {
            MessageHelper.Warning("Wrong file extesion format!");
            return;
        }
        
        globalXESLogListStrings = new LinkedList<String>();
        flagAnalizeSuccess = false;        

//        
        if(rawFileName.contains(".xes"))
        {
            XESReadingHelper helper = new XESReadingHelper();
            
            try {
                globalXESLogListStrings = helper.ReadXESLog(rawFileName, eventKeyToGet, keyWordSeparate, keySeparateInside);
                OpenRecently.UpdateRenctlyPath(rawFileName);

            } catch (Exception e) {
                MessageHelper.Warning(e.toString());
            }
        }
        else
            if(rawFileName.contains(".txt"))
            {
                globalXESLogListStrings = ReadLogFromTXTFormat(rawFileName);
            }
        
        
        CaculateTotalPage();
        
        /*--------------- After finishing data reading, View the first trace; Call the different Thread to analysis activities and pers-------------*/
        DetailPanel1Helper.CreateTraceSummaryTable(0);
        
        // Add listener for listening seleted row change
        //TableController.AddSeletedListenerToJTable(DetailPanel1.tblTraceSummary, Variables.tblSlectingTraceInMySQL, Variables.headerOfTblSlectingTraceInMySQL);
        
        // Display graph of first Trace
        DetailPanel1.tblTraceSummary.getSelectionModel().setSelectionInterval(0, 0);
        DetailPanel1Helper.VisualizeTraceInTableToGraph(tblTraceSummary, Variables.posOfAct);
        
        // Call Thread Transfer XESLog -> JTable
        xesLogToActivityAndPerformerTableControler xesLogToActivityTableControler = new xesLogToActivityAndPerformerTableControler();
        Thread readXESLogToActivityTable = new Thread(xesLogToActivityTableControler);
        readXESLogToActivityTable.start();
        
        
        // Step 1: Create thread Calculate affiliation Map
//        Continue from here
//        CreateAffiliationController createAffiliationController = new CreateAffiliationController();
//        Thread createAffiliationThread = new Thread(createAffiliationController);
//        createAffiliationThread.start();

    }
    public static void CaculateTotalPage()
    {
        totalPage = (int)(globalXESLogListStrings.size()/tableSizeLimit) + 1;
        if(globalXESLogListStrings.size()>0)
            if(totalPage == 0)
                totalPage = 1;
    }
    
    public static void ViewTraindModel() throws SQLException
    {
        TrainedModel frmTrainedModel = new TrainedModel();
//        TableController.InitializeTable(TrainedModel.tblTrainedModel, tblTrainedModelHeader);
        DefaultTableModel myModel = SelectDataInDBToJTableNoCond(tblTrainedModelName);
        TrainedModel.tblTrainedModel.setModel(myModel);
        TrainedModel.tblTrainedModel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TrainedModel.tblTrainedModel.setAutoCreateRowSorter(true);
        frmTrainedModel.setVisible(true);
        
//        Set<String> SelectColumnFromTableToSet(String _tblName, String _columnVal, String _columnCond, String _cond) 
        Set<String> logNameSet = new HashSet<>();
        for(Integer i=0; i<myModel.getRowCount(); i++)
        {
            Object currentNameObj = myModel.getValueAt(i, 1);
            if(currentNameObj!=null)
                logNameSet.add(currentNameObj.toString());
        }
        InitComboFromSet(TrainedModel.cmbLogName, logNameSet);
    }
    
    public static void ExportESN(Graph graph)
    {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Integer rowNum = 0;
        XSSFSheet sheet=null;
        sheet = workbook.createSheet("Performer to performer");
        
        Row row = sheet.createRow(rowNum++);
        Integer colNum=0;
        Cell cell = row.createCell(colNum++);
        cell.setCellValue("From performer");
        cell = row.createCell(colNum++);
        cell.setCellValue("To performer");
        cell = row.createCell(colNum++);
        cell.setCellValue("Occured");
        
        Iterator<Edge> edgeIt = graph.getEdgeIterator();
        while (edgeIt.hasNext()) {
            Edge currentEdge = edgeIt.next();
            if(currentEdge.getNode0().getId().equals("START")|| currentEdge.getNode0().getId().equals("END"))
                continue;
            if(currentEdge.getNode1().getId().equals("START")|| currentEdge.getNode1().getId().equals("END"))
                continue;
            
            colNum = 0; row = sheet.createRow(rowNum++);
            cell = row.createCell(colNum++);
            cell.setCellValue(currentEdge.getNode0().getId());
            
            cell = row.createCell(colNum++);
            cell.setCellValue(currentEdge.getNode1().getId());
            
            cell = row.createCell(colNum++);
            String weight = currentEdge.getAttribute("ui.label");
            cell.setCellValue(weight);
            
        }
        
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        Integer userSelection = fileChooser.showSaveDialog(parentFrame);
        String filePath="";
        if (userSelection == JFileChooser.APPROVE_OPTION) 
        {
            File fileToSave = fileChooser.getSelectedFile();
            filePath = fileToSave.getAbsolutePath();
        }
        
        try 
        {
            FileOutputStream outputStream = new FileOutputStream(filePath+".xlsx");
            workbook.write(outputStream);
            workbook.close();
            Helper.Common.MessageHelper.Info("File " + filePath+".xlsx was created successful");
        } 
        catch (FileNotFoundException e) 
        {
            Helper.Common.MessageHelper.Warning(e.toString());
        } 
        catch (IOException e) {
            Helper.Common.MessageHelper.Warning(e.toString());
        }
        
        
    }
    
}
