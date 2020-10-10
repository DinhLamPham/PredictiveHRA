/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.CenterPanel;
import FormTemplate.DetailPanel3_new;
import FormTemplate.InputPredictESN;
import FormTemplate.SystemProperties;
import Helper.Common.MessageHelper;
import Helper.Common.OpenRecently;
import Helper.Common.Variables;
import static Helper.Common.Variables.MLogic;
import static Helper.Common.Variables.clisten;
import static Helper.Common.Variables.gUIProp;
import static Helper.Common.Variables.graphViewerinPanel;
import static Helper.Common.Variables.predictESNInputFileName;
import static Helper.Common.Variables.pythonNoyetStatus;
import static Helper.Common.Variables.tblFunctionListName;
import static Helper.Common.Variables.tblFunctionListRunColumn;
import static Helper.Dashboard.FunctionManagerHelper.UpdateParameterForPredictESNFunction;
import static Helper.File.XESHelper.SaveListLogToFile;
import static Helper.File.XESHelper.SaveXESLogToFile;
import Helper.MySQL.Query;
import static Main.Dashboard.dashBoardFrame;
import TableHelper.TableController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.ViewerListener;
import org.graphstream.ui.swingViewer.ViewerPipe;
import static TableHelper.TableController.RemoveSelectedRows;
import com.sun.javafx.PlatformUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static Helper.Common.Variables.getFolderPathInPredictESNFunc;
import Helper.Timer.SchedulerForLoadPredictESNFunctionResultToTable;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author phamdinhlam
 */
public class CenterPanelHelper {
    private static Graph privateGraph;
    
    public static Graph GetCurrentViewGraph()
    {
        return privateGraph;
    }
    
    
    public static void HighlightNode(String currentId)
    {
        
        Node currentNode = privateGraph.getNode(currentId);
        if(currentNode == null)
            return;
        currentNode.addAttribute("ui.class", "highLight");
        
    }
    
    public static void LoadGraph(Graph g, JPanel _panelView)
    {
        privateGraph = g;
        
        if (clisten!=null){ clisten.viewClosed(null);}
        if (graphViewerinPanel!=null){_panelView.remove(graphViewerinPanel);}
        
        Viewer vwr = MLogic.simulate_graph(privateGraph);
        graphViewerinPanel = vwr.addDefaultView(false);

        graphViewerinPanel.setSize(gUIProp.width,gUIProp.height);
        graphViewerinPanel.setLocation(gUIProp.posx, gUIProp.posy);

        ViewerPipe formViewer = vwr.newViewerPipe();
        clisten = new NodeClickListener(formViewer, graphViewerinPanel, MLogic.getGraph());
        formViewer.addViewerListener(clisten);
        vwr.enableAutoLayout();
        
        // Add panel to the Dashboard frame
        _panelView.add(graphViewerinPanel);
    }
    
    public static void ResetNewTraceTable()
    {
        TableController.ClearTable(CenterPanel.tblNewTrace);
        Object data[][] = new Object[1][3];
        data[0][0] = "START"; data[0][1] = "START"; data[0][2] = "START"; 
        TableController.InitializeTableWithDataAndHeader(CenterPanel.tblNewTrace, data, Variables.newTraceTableHeader);
        
        CenterPanel.tblNewTrace.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting())
                        try {
                            RsAllocationHelper.LoadPerSetFromActTable(CenterPanel.tblNewTrace, CenterPanel.tblPerSet);
                    } catch (SQLException ex) {
                        Logger.getLogger(RsAllocationHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        
//        CenterPanel.tblNewTrace.updateUI();
    }
    
    public static void RemoveSelectedEventInNewTraceTable()
    {
        RemoveSelectedRows(CenterPanel.tblNewTrace);
    }
    
    public static void AddListenerToInputTraceListTable(JTable _inputTable)
    {
        
        _inputTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting())
                        try {
                            
                            Integer row = _inputTable.getSelectedRow();
                            if(row == -1)
                                return;
                            Object currentValObj =  _inputTable.getValueAt(row, 0);
                            if(currentValObj == null)
                                return;
                            
                            
                            String currentVal = currentValObj.toString();
                            RsAllocationHelper.UpdateNewTraceTableWithInputTrace(CenterPanel.tblNewTrace, currentVal);
                            
                    } catch (Exception ex) {
                            MessageHelper.Error(ex.toString());
                    }
                }
            });
    }
    
    public static boolean PredictESN(JTable _inputTraceTable) throws IOException, InterruptedException, SQLException
    {
        // Save input table to file.txt
        try {
//            if(_inputTraceTable.getRowCount() == 0)
//                return false;
            String _outputFileName = Variables.logName.replace(".xes", "") + predictESNInputFileName;
            String _fullFilePath = getFolderPathInPredictESNFunc(_outputFileName);

    //        SaveXESLogToFile
            List<String> traceList = TableController.TableColumnToList(_inputTraceTable, 0);
            SaveListLogToFile(traceList, _fullFilePath);

            
            Integer stepIn = Variables.stepInForPredictESN;
            
            Integer maxOutputIntegerStep = Integer.parseInt(InputPredictESN.txtMaxEvent.getText());
            
            String predictType = InputPredictESN.cmbTypePrediction.getSelectedItem().toString();
            
            
            Double probability = Double.parseDouble(InputPredictESN.txtProbability.getText());
            Double topRank = new Double(InputPredictESN.cmbTopRank.getSelectedIndex()+1);
            

    //        String String _predictType, String _feature, String _fileName
            if(predictType.equals("Probability"))
                UpdateParameterForPredictESNFunction(stepIn, maxOutputIntegerStep, predictType, probability, _outputFileName);
            else
                UpdateParameterForPredictESNFunction(stepIn, maxOutputIntegerStep, predictType, topRank, _outputFileName);
            
            return true;
        } catch (Exception e) {MessageHelper.Error("<PredictESN> function\n" + e.toString());
        }
        return false;
        
    }
    
        public static void SetRunningFunctionForPredictESN(String _funcName, JLabel _lblButton) throws IOException, SQLException
    {
        // Set run column value = notyet.
        Query.UpdateTbl(tblFunctionListName, "id", _funcName, tblFunctionListRunColumn, pythonNoyetStatus);
        
        // Start output scan.
        SchedulerForLoadPredictESNFunctionResultToTable myScheduler = new SchedulerForLoadPredictESNFunctionResultToTable
        (_lblButton, _funcName, CenterPanel.rsAllocation_tblTraceInfo);
        
    }
    
    public static void RefreshTraceDetailTable(JTable _tableToGetTraceId, List<String> logList)
    {
        try {
            Integer currentRow = _tableToGetTraceId.getSelectedRow();
            if(currentRow == -1)
                return;
            String traceId = _tableToGetTraceId.getValueAt(currentRow, 0).toString();
            DetailPanel3_newHelper.FillTraceToTable(logList.get(Integer.parseInt(traceId)),
                DetailPanel3_new.tblTracedetail);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
   
    public static void UpdateGraphvizPanelResult(String _filePath) throws IOException
    {
        File imageFile = new File(_filePath);
        if(!imageFile.exists())
            return;
        CenterPanel.panelGraphvizResult.setImage(_filePath);
        
    }
    
    
    
    
}
