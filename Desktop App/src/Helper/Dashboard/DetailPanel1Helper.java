/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.CenterPanel;
import FormTemplate.DetailPanel1;
import static FormTemplate.InformationHelper.UpdateFunctionGetTraceStatisticParas;
import FormTemplate.SystemProperties;
import GraphStreamCoreHelper.FormatGraphByForm;
import GraphStreamCoreHelper.xesLogToGraphStream;
import Helper.Common.MessageHelper;
import Helper.Common.TablePaging;
import Helper.Common.Variables;
import static Helper.Common.Variables.*;
import TableHelper.xesLogToTable;
import static Helper.Common.Variables.tableSizeLimit;
import Helper.MySQL.Connector;
import Helper.MySQL.Query;
import static Helper.MySQL.Query.CopySelectedRowInJTableToMySQLTable;
import Helper.Timer.SchedulerForLoadImageResult;
import TableHelper.TableController;
import ThreadController.CreateTemporaryMapTrace;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phamdinhlam
 */
public class DetailPanel1Helper {
    
    
    public static void CreateTraceSummaryTable(Integer _currentPage)
    {
        try {
            Integer virtualPage = _currentPage + 1;
            String labelString = virtualPage.toString() + "/" + totalPage;
            DetailPanel1.lblCurrentPage.setText(labelString);
            
            // posRange[0]: FromTraceId, posRange[1]: ToTraceId
            Integer[] posRange = TablePaging.CalculateFromPosToPos(globalXESLogListStrings.size(), tableSizeLimit, _currentPage);

            globalTraceTable = new JTable();
            globalTraceTable = TableHelper.xesLogToTable.ToTraceTable(globalXESLogListStrings, posRange[0], posRange[1]);
            TableController.RemoveAllTableData(DetailPanel1.tblTraceSummary, traceTableHeader);
            DetailPanel1.tblTraceSummary.setModel(Variables.globalTraceTable.getModel());
//            DetailPanel1.tblTraceSummary.setAutoCreateRowSorter(true); => Error
            DisplayTotalRow();
            
            
            // Create new thread to run calculate trace Map in background
            ThreadController.CreateTemporaryMapTrace createMap = new CreateTemporaryMapTrace(posRange[0], posRange[1]);
            Thread createMapThread = new Thread(createMap);
            createMapThread.start();
            
            
            
        } catch (Exception e) {MessageHelper.Error("<CreateTraceSummaryTable>function: \n" + e.toString());
        }
        
    }
    
    public static void DisplayTotalRow()
    {
        Integer totalRow = DetailPanel1.tblTraceSummary.getRowCount();
        DetailPanel1.lblTotalRow.setText(totalRow.toString() +  " rows");
    }
    
    public static void LoadPageInTraceTable(String _action)
    {
        try {
            Integer pageToView = TablePaging.GetPage(DetailPanel1.lblCurrentPage.getText(), _action);
            if(pageToView < 0 || pageToView >=totalPage)
                return;
            CreateTraceSummaryTable(pageToView);  // 1 -> 0. For conveniance
        } catch (Exception e) {MessageHelper.Error("<LoadPageInTraceTable>function: \n" + e.toString());
        }
    }
    
    public static void VisualizeTraceInTableToGraph(JTable _inputTable, Integer _posToView)
    {
        try {
            int[] listOfRows = _inputTable.getSelectedRows();
            Integer[] _flagView = new Integer[listOfRows.length];

            for(Integer i=0; i<listOfRows.length; i++)
            {
                String currentTraceId = _inputTable.getValueAt(listOfRows[i], 0).toString();
                _flagView[i] = Integer.parseInt(currentTraceId);
            }
            
            nodeOccurenceMap = new HashMap<>(); edgeWeightMap = new HashMap<>(); 
            globalGraph = xesLogToGraphStream.CreateGraphFromLog(nodeOccurenceMap, edgeWeightMap, globalXESLogListStrings, 
                    _posToView, _flagView, SystemProperties.cbSkipSelfLoopInSNA.isSelected());
            
            CenterPanelHelper.LoadGraph(globalGraph, CenterPanel.panelLogVisualization);
            CenterPanel.CenterPanel.setSelectedComponent(CenterPanel.panelLogVisualization);
            FormatGraphByForm.Formatting(globalGraph);
//            SNAHelper.FormatGraph.SetDefaultStyle(globalGraph);
            
            
        } catch (Exception e) {
            MessageHelper.Error("<VisualizeTraceInTableToGraph>function:\n "+e.toString());
        }
    }
    
    public static void TraceStatistic(JLabel _jLabel, boolean _showAct, boolean _showPer, boolean _showDur) throws SQLException, IOException
    {
        if(DetailPanel1.tblTraceSummary.getRowCount() == 0)
            return;
        
        if(DetailPanel1.tblTraceSummary.getSelectedRowCount() == 0)
            DetailPanel1.tblTraceSummary.setRowSelectionInterval(0, DetailPanel1.tblTraceSummary.getRowCount() - 1);
        
        //Save JTable to Tble.
        _jLabel.setEnabled(false);
        CopySelectedRowInJTableToMySQLTable(DetailPanel1.tblTraceSummary, traceTableHeader, traceTableName, true);
        
        UpdateFunctionGetTraceStatisticParas(traceTableName, _showAct, _showPer, _showDur);
        
        
        
        // Set run status: Notyet.
        Query.UpdateTbl(tblFunctionListName, "id", getTracesStatisticsFuncId, tblFunctionListRunColumn, pythonNoyetStatus);
        
        // Call timer.
        String loadingImage = Variables.getFolderPathInGetTraceStatisticsFunc("blank.png");
        CenterPanel.panelImageLeft.setImage(loadingImage); CenterPanel.panelImageRight.setImage(loadingImage);
        Helper.Timer.SchedulerForLoadImageResult schedulerForLoadImageResult = 
                new SchedulerForLoadImageResult(_jLabel, getTracesStatisticsFuncId, CenterPanel.panelImageLeft, CenterPanel.panelImageRight);
    }
    
    public static void SaveTemproraryMemberTraceMap(Integer _fromId, Integer _toId) throws SQLException
    {
        // Activity Trace Map
        globalActWithTraceMap = new HashMap<>(); //...1
        globalPerWithTraceMap = new HashMap<>();
        // Create Trace Map
        for(Integer pos=_fromId; pos<=_toId; pos++)  //...
            {
                if(pos==globalXESLogListStrings.size())
                    break;
                String trace = Variables.globalXESLogListStrings.get(pos); // ...
                String[] currentTrace = trace.split(keyWordSeparate);

                for(int j=1; j<currentTrace.length-1; j++)
                {
                    String[] currentGroup = currentTrace[j].split(keySeparateInside);
                    String currentActivity = currentGroup[posOfAct];
                    String currentPerformer = currentGroup[posOfPer];
                    
                    if(!globalActWithTraceMap.containsKey(currentActivity))
                    {
                        Set<Integer> newTraceSet = new HashSet<>(); newTraceSet.add(pos); // ...
                        globalActWithTraceMap.put(currentActivity, newTraceSet); // ...
                    }
                    else
                    {
                        Set<Integer> thisTraceSet = globalActWithTraceMap.get(currentActivity); thisTraceSet.add(pos); // ...
                        globalActWithTraceMap.put(currentActivity, thisTraceSet); //...
                    }
                    
                    if(!globalPerWithTraceMap.containsKey(currentPerformer))
                    {
                        Set<Integer> newTraceSet = new HashSet<>(); newTraceSet.add(pos); // ...
                        globalPerWithTraceMap.put(currentPerformer, newTraceSet); // ...
                    }
                    else
                    {
                        Set<Integer> thisTraceSet = globalPerWithTraceMap.get(currentPerformer); thisTraceSet.add(pos); // ...
                        globalActWithTraceMap.put(currentPerformer, thisTraceSet); //...
                    }
                }
        }
        /*
        // =======================================Save trace map to Mysql=====================================
        // Save ActinTraceMap .......
        Query.SaveActPerWithTraceMapToMySQLTable(activityWithTraceTableName, activityWithTraceTableHeader, globalActWithTraceMap);

        // Save PerInTraceMap
        Query.SaveActPerWithTraceMapToMySQLTable(performerWithTraceTableName, performerWithTraceTableHeader, globalPerWithTraceMap);
        
        */
        
        /* ========================Load from MySQL to Variable: globalActWithTraceMap, globalPerWithTraceMap============================
        // Load table in mysql to activiyTraceMap
        globalActWithTraceMap = SelectDataInDBToAffiliationMap(activityWithTraceTableName, activityWithTraceTableHeader, 1, 2, "LogName", logNameToSave);
        // Load table in mysql to performerTraceMap
        globalPerWithTraceMap = SelectDataInDBToAffiliationMap(performerWithTraceTableName, performerWithTraceTableHeader, 1, 2, "LogName", logNameToSave);
        
        */
        
    }
    
    
}
