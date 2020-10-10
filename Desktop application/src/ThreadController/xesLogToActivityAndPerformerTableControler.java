/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadController;

import FormTemplate.DetailPanel2;
import static Helper.Alogrithm.ActivityRelation.CreateRelationMap;
import static Helper.Alogrithm.Affiliation.CreateAffiliationMap;
import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import TableHelper.xesLogToTable;
import java.io.IOException;
import static Helper.Common.Variables.*;
import Helper.MySQL.Query;
import static Helper.MySQL.Query.SelectColumnFromTableToSet;
import static Helper.MySQL.Query.SelectDataInDBToJTableNoCond;
import TableHelper.TableController;
import java.sql.SQLException;
import static TableHelper.TableController.AddSeletedListenerToInsertSelectingRow;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static Helper.MySQL.Query.SelectDataInDBToJTableSingleCond;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.table.TableColumn;
import static Helper.Common.SetHelper.MembersToSetString;
import static Helper.MySQL.Query.SelectDataInDBToAffiliationMap;

/**
 *
 * @author phamdinhlam
 */
public class xesLogToActivityAndPerformerTableControler implements Runnable{
    
    @Override
    public void run()
    {
        // XESLog -> JTable
        try {
            String logNameToSave = logName.replace(".xes", "");
            // Check actvity set and performer set are exist or not:
            globalActivitySet = SelectColumnFromTableToSet(activitiesTableName, "ActivityName", "LogName", logNameToSave);
            
            // If not exist: Insert activity set and performer set to database
            if (globalActivitySet.isEmpty())
            {
                xesLogToTable.ToActivityTable(Variables.globalXESLogListStrings);
                xesLogToTable.ToPerformerTable(Variables.globalXESLogListStrings);
                
                // Insert activities JTable -> MySQL
                try 
                {
                boolean flagRemoveOldData = false;
                Helper.MySQL.Query.CopyJTableToMySQLTable(globalActivityTable, activityTableHeader, tblActInMySQL, flagRemoveOldData);
                Helper.MySQL.Query.CopyJTableToMySQLTable(globalPerformerTable, performerTableHeader, tblPerInMySQL, flagRemoveOldData);
                
                
                } catch (SQLException ex) {
                    MessageHelper.Error(ex.toString());
                }
                
            }
            
            String[] columnToGet = {"ActivityName", "InTotalTrace", "Occurence", "Duration"};
            globalActivityTable = new JTable(SelectDataInDBToJTableSingleCond(activitiesTableName, columnToGet, "LogName", logNameToSave));
            
            columnToGet = new String[] {"PerformerName", "InTotalTrace", "Occurence", "Duration"};
            globalPerformerTable = new JTable(SelectDataInDBToJTableSingleCond(performersTableName, columnToGet, "LogName",logNameToSave));
            
            

            // Add listener for listening seleted row change and update it in mysql DB
            AddSeletedListenerToInsertSelectingRow(DetailPanel2.tblDetailSummary, Variables.tblSlectingEventInMySQL, Variables.headerOfTblSlectingEventInMySQL);
            // Display
            TableController.ViewTableInDetailPanel(DetailPanel2.cmbSelectTable.getItemAt(0));
        
            CreateAffiliationSet();
            
//            System.out.println("The function creating affiliation has just finished!");
        
        
        } catch (Exception e) {
            MessageHelper.Error("<xesLogToActivityAndPerformerTableControler> Class: \n" + e.toString());
        }
        
        
        
    }
    
    private void CreateAffiliationSet() throws SQLException
    {
        Set<String> activityAffiliationSet = Query.SelectColumnFromTableToSet(Variables.tblAffiliationActName, "ActivityName", "LogName", Variables.logName.replace(".xes", ""));
            if(activityAffiliationSet.isEmpty()) // There is no affiliation in database => 1. Create; 2. Save to Database
            {
                //1. Create affiliation -> Map<String, Set>
                activityAffiliationMap = CreateAffiliationMap(globalXESLogListStrings, posOfAct, posOfPer);
                performerAffiliationMap = CreateAffiliationMap(globalXESLogListStrings, posOfPer, posOfAct);
                activityRelationMap = CreateRelationMap(globalXESLogListStrings);
                //2. Save to database
                Query.SaveAffiliationToMySQLTable(Variables.tblAffiliationActName, Variables.tblAffiliationActHeader, activityAffiliationMap);
                Query.SaveAffiliationToMySQLTable(Variables.tblAffiliationPerName, Variables.tblAffiliationPerHeader, performerAffiliationMap);
                Query.SaveAffiliationToMySQLTable(Variables.tblRelationActName, Variables.tblRelationActHeader, activityRelationMap);
                
//                System.out.println("The function creating affiliation has just finished!");
            }
            else // Load from database to variables
            {
                // Load activityAffiliationMap
                String[] _columnToGet = new String[] {"ActivityName", "Members"};
                Variables.activityAffiliationMap = LoadDatabaseToMapSet(tblAffiliationActName, _columnToGet);
                
                // Load performerAffiliationMap
                _columnToGet = new String[] {"PerformerName", "Members"};
                Variables.performerAffiliationMap = LoadDatabaseToMapSet(tblAffiliationPerName, _columnToGet);
                
                // Load RelationActivityMap
                _columnToGet = new String[] {"ActivityName", "Members"};
                Variables.activityRelationMap = LoadDatabaseToMapSet(tblRelationActName, _columnToGet);
            }
    }
    
    private static Map<String, Set<String>> LoadDatabaseToMapSet(String _tblName, String[] _columnToGet) throws SQLException
    {
        Map<String, Set<String>> outputMap = new HashMap<>();
        try {
            
//        (String _sqlTblName, String[] _columnToget, String _condHeader, String _condVal)
            String condHeader = "LogName"; String condVal = Variables.logName.replace(".xes", "");
            DefaultTableModel myModel = SelectDataInDBToJTableSingleCond(_tblName, _columnToGet, condHeader, condVal);
            for(Integer rowIdx=0; rowIdx<myModel.getRowCount(); rowIdx++)
            {
                String name = myModel.getValueAt(rowIdx, 0).toString();
                String members = myModel.getValueAt(rowIdx, 1).toString();
                Set<String> currentMemberSet = MembersToSetString(members);
                outputMap.put(name, currentMemberSet);
            }
            
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
        return outputMap;
    }
    
}
