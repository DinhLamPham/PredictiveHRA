/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormTemplate;

import Helper.Common.Variables;
import static Helper.Common.Variables.*;
import static Helper.Dashboard.FunctionManagerHelper.UpdateFunctionParameter;
import static Helper.File.XESHelper.SaveListLogToFile;
import Helper.MySQL.Query;
import Helper.Timer.SchedulerForLoadImageResult;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JTable;

/**
 *
 * @author DinhLam Pham
 */
public class InformationHelper {
    
    public static void GetViewTraceDetail(JLabel _jlabel, String _type, List<String> _logList, JTable _inputTable) throws IOException, InterruptedException, SQLException
    {
        String outputFile = CreateSubTracesLog(_logList, _inputTable);
        UpdateFunctionGetTraceDetailParas(outputFile, _type);
        
        _jlabel.setEnabled(false);
        
        // Set run status: Notyet.
        Query.UpdateTbl(tblFunctionListName, "id", getTraceDetailFuncId, tblFunctionListRunColumn, pythonNoyetStatus);
        
        // Call timer.
        String loadingImage = Variables.getFolderPathInGetTraceStatisticsFunc("blank.png");
        CenterPanel.panelImageLeft.setImage(loadingImage); CenterPanel.panelImageRight.setImage(loadingImage);
        Helper.Timer.SchedulerForLoadImageResult schedulerForLoadImageResult = 
                new SchedulerForLoadImageResult(_jlabel, getTraceDetailFuncId, CenterPanel.panelImageLeft, CenterPanel.panelImageRight);
        
    }
    
    
    public static void GetActPerStatistic(JLabel _jlabel, String _type, Set<String> _memberSet, List<String> _logList, JTable _inputTable) throws IOException, InterruptedException, SQLException
    {
        String outputFile = CreateSubTracesLog(_logList, _inputTable);
        UpdateFunctionGetActPerStatisticParas(outputFile, _type, _memberSet);
        
        _jlabel.setEnabled(false);
        
        // Set run status: Notyet.
        Query.UpdateTbl(tblFunctionListName, "id", getActPerStatisticsFuncId, tblFunctionListRunColumn, pythonNoyetStatus);
        
        // Call timer.
        String loadingImage = Variables.getFolderPathInGetTraceStatisticsFunc("blank.png");
        CenterPanel.panelImageLeft.setImage(loadingImage); CenterPanel.panelImageRight.setImage(loadingImage);
        Helper.Timer.SchedulerForLoadImageResult schedulerForLoadImageResult = 
                new SchedulerForLoadImageResult(_jlabel, getActPerStatisticsFuncId, CenterPanel.panelImageLeft, CenterPanel.panelImageRight);
        
    }
    
    // Create sub trace log from global log. Return file path
    public static String CreateSubTracesLog(List<String> _inputLog, JTable _inputTable) throws IOException, InterruptedException
    {
        String fileName = Variables.logName.replace(".xes", "") + "_subTraces.txt";
        String subLogFilePath = Variables.getFolderPathInGetTraceStatisticsFunc(fileName);
        List<String> subListLog = new LinkedList<>();
        int[] rows = _inputTable.getSelectedRows();
        for(Integer i=0; i<rows.length; i++)
        {
            Object currentValueObj = _inputTable.getValueAt(rows[i], 0);
            if(currentValueObj!=null)
                subListLog.add(_inputLog.get(Integer.parseInt(currentValueObj.toString())));
        }
        
        SaveListLogToFile(subListLog, subLogFilePath);
                
        return fileName;
    }
//    
    public static void UpdateFunctionGetTraceDetailParas(String _fileName, String _type) throws SQLException
    {
         List<String> parameterList = new LinkedList<>();
        
        parameterList.add("SubLogFile"+ Variables.keySeparateInside + _fileName);
        parameterList.add("Type" + Variables.keySeparateInside + _type);
        UpdateFunctionParameter(getTraceDetailFuncId, parameterList);
    }
    
    
    
    public static void UpdateFunctionGetActPerStatisticParas(String _fileName, String _type, Set<String> _memberSet) throws SQLException
    {
         List<String> parameterList = new LinkedList<>();
        
        parameterList.add("SubLogFile"+ Variables.keySeparateInside + _fileName);
        parameterList.add("Type" + Variables.keySeparateInside + _type);
        parameterList.add("TotalMember" + Variables.keySeparateInside + _memberSet.size());
        for(String member:_memberSet)
            parameterList.add("Member" + Variables.keySeparateInside + member);
        
        UpdateFunctionParameter(getActPerStatisticsFuncId, parameterList);
    }
    
    
    public static void UpdateFunctionGetTraceStatisticParas(String _tblInMySQLName, boolean _showAct, boolean _showPer, boolean _showDur) throws SQLException
    {
         List<String> parameterList = new LinkedList<>();
         String viewType="";
         if(_showAct)
             viewType +="_Activity";
         if(_showPer)
             viewType +="_Performer";
         if(_showDur)
             viewType +="_Duration";
        
        parameterList.add("TableName"+ Variables.keySeparateInside + _tblInMySQLName);
        parameterList.add("ViewType"+ Variables.keySeparateInside + viewType);
       
        UpdateFunctionParameter(getTracesStatisticsFuncId, parameterList);
    }
}
