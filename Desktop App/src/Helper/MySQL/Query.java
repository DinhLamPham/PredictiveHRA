/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.MySQL;

import Helper.Common.MessageHelper;
import static Helper.Common.SetHelper.MembersToSetInteger;
import Helper.Common.TimeHelper;
import Helper.Common.Variables;
import static Helper.MySQL.Setting.con;
import static Helper.MySQL.Setting.sqlCmd;
import TableHelper.TableController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phamdinhlam
 */
public class Query {
    
    public static void SaveActPerWithTraceMapToMySQLTable(String _tblName, String[] _tblHeader, Map<String, Set<Integer>> affiliationMap) throws SQLException
    {
        Connector.OpenConnection();
        
        for (Map.Entry<String, Set<Integer>> entrySet : affiliationMap.entrySet()) {
            String affiliationKey = entrySet.getKey();
            Set<Integer> affiliationMembers = entrySet.getValue();
            String memberValue = ""; Object[] arrayMembers = affiliationMembers.toArray();
            memberValue = arrayMembers[0].toString();
            for(Integer i=1; i<arrayMembers.length; i++)
                memberValue += Variables.keySeparateInside + arrayMembers[i].toString();
            
            String[] dataRow = new String[] {Variables.logName.replace(".xes", ""), affiliationKey, memberValue};
            Connector.InsertRowToTable(_tblName, _tblHeader, dataRow);
            
        }
        
        Connector.CloseConnection();
    }
    
    
    public static void SaveAffiliationToMySQLTable(String _tblName, String[] _tblHeader, Map<String, Set<String>> affiliationMap) throws SQLException
    {
        Connector.OpenConnection();
        
        for (Map.Entry<String, Set<String>> entrySet : affiliationMap.entrySet()) {
            String affiliationKey = entrySet.getKey();
            Set<String> affiliationMembers = entrySet.getValue();
            String memberValue = ""; Object[] arrayMembers = affiliationMembers.toArray();
            memberValue = arrayMembers[0].toString();
            for(Integer i=1; i<arrayMembers.length; i++)
                memberValue += Variables.keySeparateInside + arrayMembers[i].toString();
            
            String[] dataRow = new String[] {Variables.logName.replace(".xes", ""), affiliationKey, memberValue};
            Connector.InsertRowToTable(_tblName, _tblHeader, dataRow);
            
        }
        
        Connector.CloseConnection();
    }
    
    // Copy all data from JTable to MySQL table
    public static void CopyJTableToMySQLTable(JTable _inputJTable, String[] _tblHeader, String _mySQLTable, boolean flagRemoveOldData) throws SQLException
    {
        // Insert selected row to DB
        Connector.OpenConnection();
        // Remove all Table Data first
        if (flagRemoveOldData)
            Connector.RemoveAllTableData(_mySQLTable);
        int totalRow = _inputJTable.getRowCount();
        
        for(Integer i=0; i<totalRow; i++)
        {
            String[] currentRow = new String[_tblHeader.length];
            for(Integer j=0; j< _tblHeader.length; j++)
            {
                currentRow[j] = _inputJTable.getValueAt(i, j).toString();
            }
            Connector.InsertRowToTable(_mySQLTable, _tblHeader, currentRow);
        }
        Connector.CloseConnection();
    }
    
    public static void CopySelectedRowInJTableToMySQLTable(JTable _inputJTable, String[] _tblHeader, String _mySQLTable, boolean flagRemoveOldData) throws SQLException
    {
        // Insert selected row to DB
        Connector.OpenConnection();
        // Remove all Table Data first
        if (flagRemoveOldData)
            Connector.RemoveAllTableData(_mySQLTable);
        int totalRow = _inputJTable.getRowCount();
        
        int[] rows = _inputJTable.getSelectedRows();
        for(int row:rows)
        {
            String[] currentRow = new String[_tblHeader.length];
            for(Integer j=0; j< _tblHeader.length; j++)
            {
                currentRow[j] = _inputJTable.getValueAt(row, j).toString();
            }
            Connector.InsertRowToTable(_mySQLTable, _tblHeader, currentRow);
        }
        Connector.CloseConnection();
    }
    
    
    // Save the XES Log list string to MySQL Table.
    public static void SaveXESTraceToDB(List<String> _inputXESLog, String _mySQLTable) throws SQLException
    {
        Integer logSize = _inputXESLog.size();
        String logId = TimeHelper.GetIdByDateTime();
        Connector.OpenConnection();
        
        Connector.RemoveAllTableData(_mySQLTable);
        
        for(Integer i=0; i<logSize; i++)
        {
            String currentTrace = _inputXESLog.get(i);
            String[] row = {logId, Variables.logName, currentTrace};
            Connector.InsertRowToTable(_mySQLTable, Variables.tblXESLogHeader, row);
        }
        
        Connector.CloseConnection();
    }
   
    public static void InsertSelectingRow(JTable _jtable, String _mySQLTable, String[] _header) throws SQLException
    {
        Connector.OpenConnection();
        Connector.RemoveAllTableData(_mySQLTable);
        
        int[] arraySelected = _jtable.getSelectedRows();
        
        for(Integer i=0;i<arraySelected.length;i++)
        {
            String column0Val = _jtable.getValueAt(arraySelected[i], 0).toString();
            String[] data = new String[_header.length];  // Only one column
            data[0] = column0Val;
            if(_header.length==2)
                data[1] = Variables.currentViewTable;
                
            Connector.InsertRowToTable(_mySQLTable, _header, data);
        }
        Connector.CloseConnection();
    }
    
    public static List<String> GetFunctionList(String _mySQLTable, String _column) throws SQLException
    {
        Connector.OpenConnection();
        List<String> resultList = new LinkedList<>();
        String sqlString = "Select " + _column + " from " + _mySQLTable;
        sqlCmd = con.prepareStatement(sqlString); 
         ResultSet resultQuery = sqlCmd.executeQuery();
         while(resultQuery.next())
         {
             String currentValue = resultQuery.getString(_column);
             resultList.add(currentValue);
         }

        Connector.CloseConnection();
        return resultList;
    }
    
    
    public static Set<String> SelectColumnFromTableToSet(String _tblName, String _columnVal, String _columnCond, String _cond) throws SQLException
    {
        String sql = "SELECT " + _columnVal + " From " + _tblName + 
                " WHERE " + _columnCond + "='" + _cond + "'";
        
        Connector.OpenConnection();
        Set<String> resultSet = new HashSet<>();
        sqlCmd = con.prepareStatement(sql); 
         ResultSet resultQuery = sqlCmd.executeQuery();
         while(resultQuery.next())
         {
             String currentValue = resultQuery.getString(_columnVal);
             resultSet.add(currentValue);
         }
        
        Connector.CloseConnection();
        return resultSet;
    }
    
    
    public static DefaultTableModel SelectDataInDBToJTableNoCond(String _sqlTblName) throws SQLException
    {
        Connector.OpenConnection();
        String sqlString = "Select * from " + _sqlTblName;
        sqlCmd = con.prepareStatement(sqlString); 
        ResultSet resultQuery = sqlCmd.executeQuery();
        DefaultTableModel myModel = TableController.CreateTableFromQuerySet(resultQuery);
        
        Connector.CloseConnection();
        return myModel;
    }
    
    public static DefaultTableModel SelectMembersFromAffiliation(String _sqlTblName, String[] _columnToget, String _condHeader, String _condVal) throws SQLException
    {
        Connector.OpenConnection();
        String columnHeader = _columnToget[0];
        for (Integer i=1; i<_columnToget.length; i++)
            columnHeader += ", " + _columnToget[i];
        String sqlString = "Select " + columnHeader + " from " + _sqlTblName + " WHERE " + _condHeader + "='"+ _condVal 
                + "' and Logname='" + Variables.logName.replace(".xes", "") + "'";
        sqlCmd = con.prepareStatement(sqlString); 
        ResultSet resultQuery = sqlCmd.executeQuery();
        resultQuery.first();
        String[] members = resultQuery.getString(columnHeader).split(Variables.keySeparateInside);
        String[][] data = new String[members.length][1];
        for(Integer i=0; i<members.length; i++)
        {
            data[i][0] = members[i];
        }
        
        DefaultTableModel myModel;
        myModel = new DefaultTableModel(data, _columnToget){
            @Override
            public boolean isCellEditable(int row, int col)
            {
                boolean editFlag = false;
//                if(col==0 || col==1 || col==2 || col==3 || col==4)
//                    editFlag=false;
                return editFlag;
            };
        };
        
        Connector.CloseConnection();
        return myModel;
    }
    
    
    public static DefaultTableModel SelectDataInDBToJTableSingleCond(String _sqlTblName, String[] _columnToget, String _condHeader, String _condVal) throws SQLException
    {
        Connector.OpenConnection();
        String columnHeader = _columnToget[0];
        for (Integer i=1; i<_columnToget.length; i++)
            columnHeader += ", " + _columnToget[i];
        String sqlString = "Select " + columnHeader + " from " + _sqlTblName + " WHERE " + _condHeader + "='"+ _condVal + "'";
        sqlCmd = con.prepareStatement(sqlString); 
        ResultSet resultQuery = sqlCmd.executeQuery();
        DefaultTableModel myModel = TableController.CreateTableFromQuerySet(resultQuery);
        
        Connector.CloseConnection();
        return myModel;
    }
    
    public static Map<String, Set<Integer>> SelectDataInDBToAffiliationMap
        (String _sqlTblName, String[] _columnToget, Integer _keyColumnIdx, Integer _memberColumnIdx, String _condHeader, String _condVal) throws SQLException
    {
        Connector.OpenConnection();
        Map<String, Set<Integer>> resultMap = new HashMap<>();
        String columnHeader = _columnToget[0];
        for (Integer i=1; i<_columnToget.length; i++)
            columnHeader += ", " + _columnToget[i];
        String sqlString = "Select " + columnHeader + " from " + _sqlTblName + " WHERE " + _condHeader + "='"+ _condVal + "'";
        sqlCmd = con.prepareStatement(sqlString); 
        ResultSet resultQuery = sqlCmd.executeQuery();
        DefaultTableModel myModel = TableController.CreateTableFromQuerySet(resultQuery);
        
        for(Integer i=0; i<myModel.getRowCount(); i++)
        {
            Object currentKeyObj = myModel.getValueAt(i, _keyColumnIdx);
            Object currentMemberObj = myModel.getValueAt(i, _memberColumnIdx);
            if(currentKeyObj!=null)
            {
                String currentKeyStr = currentKeyObj.toString();
                Set<Integer> traceIdSet = MembersToSetInteger(currentMemberObj.toString());
                resultMap.put(currentKeyStr, traceIdSet);
            }
        }
        
        Connector.CloseConnection();
        return resultMap;
    }
    
    public static String GetColumnValueFromFunctionList(String _funcName, String _columnToGet) throws SQLException
    {
        String _mySQLTable = Variables.tblFunctionListName, _columnCondition = "id";
        Connector.OpenConnection();
        String result = "";
        String sqlString = "Select " + _columnToGet + " from " + _mySQLTable + " where " + _columnCondition + " ='" + _funcName + "'" ;
        sqlCmd = con.prepareStatement(sqlString); 
        ResultSet resultQuery = sqlCmd.executeQuery();
        resultQuery.first();
        result = resultQuery.getString(_columnToGet);
         

        Connector.CloseConnection();
        return result;
    }
    
    public static void UpdateTbl(String _tblName, String _condHeader, String _condValue, String _updateHeader, String _updateValue) throws SQLException
    {
        try {
            Connector.OpenConnection();
            String sql = "UPDATE " + _tblName + " SET " + _updateHeader +  " = '" + _updateValue + "'" + " WHERE " + _condHeader + "='" + _condValue + "'";
            sqlCmd = con.prepareStatement(sql); 
            sqlCmd.executeUpdate();
            Connector.CloseConnection();
        } catch (Exception e) {MessageHelper.Error(e.toString());
        }
    }
    
    public static void DeleteRow(String _tblName, String _headerCond, String _valueCond) throws SQLException
    {
        try {
            Connector.OpenConnection();
            //DELETE FROM `table_name` [WHERE condition];
            String sql = "DELETE FROM " + _tblName + " WHERE " + _headerCond + "='" + _valueCond + "'";
            sqlCmd = con.prepareStatement(sql); 
            sqlCmd.executeUpdate();
            Connector.CloseConnection();
        } catch (Exception e) {MessageHelper.Error(e.toString());
        }
    }
   
  
}
