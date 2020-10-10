/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.FunctionManager;
import static FormTemplate.FunctionManager.tblFuncList;
import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import static Helper.Common.Variables.predictESNFunctionId;
import static Helper.Common.Variables.tblFunctionListHeader;
import Helper.MySQL.Connector;
import Helper.MySQL.Query;
import static Helper.MySQL.Setting.con;
import static Helper.MySQL.Setting.sqlCmd;
import TableHelper.TableController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import static Helper.Common.Variables.tblFunctionListName;

/**
 *
 * @author phamdinhlam
 */
public class FunctionManagerHelper {
    
    public static void InsertNewFunc()
    {
        FunctionManager.txtFuncId.setText("");
        FunctionManager.txtFuncId.setEnabled(true);
        
        FunctionManager.txtFuncName.setText("");
        FunctionManager.txtFuncName.setEnabled(true);
        
        FunctionManager.txtFuncPara.setText("");
        FunctionManager.txtFuncDescribe.setText("");
    }
    
    public static void UpdateFunctionParameter(String _functionId, List<String> _parameterList) throws SQLException
    {
        Connector.OpenConnection();
        String parameter = "";
        for(Integer i=0; i<_parameterList.size(); i++)
        {
            parameter += _parameterList.get(i);
            if(i<_parameterList.size()-1)
                parameter += "\n";
        }
        Query.UpdateTbl(tblFunctionListName, "id", _functionId, "parameter", parameter);
        
        Connector.CloseConnection();
    }
    
    public static void Save() throws SQLException
    {
        String id = FunctionManager.txtFuncId.getText(); // "id"
        String name = FunctionManager.txtFuncName.getText(); // "name"
        String parameter = FunctionManager.txtFuncPara.getText(); // "parameter"
        String describer = FunctionManager.txtFuncDescribe.getText(); // "describer"
        String[] row = {id, name, parameter, describer};
        Connector.OpenConnection();
        List<String> funcIdList = Query.GetFunctionList(tblFunctionListName, "id");
        
        // If exist => Update
        if(funcIdList.contains(id))
        {
            Query.UpdateTbl(tblFunctionListName, "id", id, "name", name);
            Query.UpdateTbl(tblFunctionListName, "id", id, "parameter", parameter);
            Query.UpdateTbl(tblFunctionListName, "id", id, "describer", describer);
        }
        else // Else => Insert
        {
            Connector.InsertRowToTable(Variables.tblFunctionListName, tblFunctionListHeader, row);
        }
        Connector.CloseConnection();
        DisplayFunctionList();
    }
    
    public static void DisplayFunctionList()
    {
        try {
            TableController.CopyJTableToJTable(FunctionManagerHelper.GetFunctionTable(), tblFuncList, tblFunctionListHeader);
            
            FunctionManagerHelper.addSelectingListener(tblFuncList);
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
    }
    
    public static JTable GetFunctionTable() throws SQLException
    {
        Connector.OpenConnection();
        String sqlString = "Select " 
                + tblFunctionListHeader[0] + ", "
                + tblFunctionListHeader[1] + ", "
                + tblFunctionListHeader[2] + ", "
                + tblFunctionListHeader[3] + " "
                + "from " + tblFunctionListName;
        sqlCmd = con.prepareStatement(sqlString); 
         ResultSet resultQuery = sqlCmd.executeQuery();
         
         JTable myTable = new JTable();
         TableHelper.TableController.InitializeBlankTableWithHeader(myTable, tblFunctionListHeader);
         DefaultTableModel model = (DefaultTableModel) myTable.getModel();
         
         
         while(resultQuery.next())
         {
             String id = resultQuery.getString(tblFunctionListHeader[0]); // "id"
             String name = resultQuery.getString(tblFunctionListHeader[1]); // "name"
             String parameter = resultQuery.getString(tblFunctionListHeader[2]); // "parameter"
             String describer = resultQuery.getString(tblFunctionListHeader[3]); // "describer"
             String[] row = {id, name, parameter, describer};
             model.addRow(row);
         }

        Connector.CloseConnection();
        
        return myTable;
    }
    
    public static void addSelectingListener(JTable _jtable)
        {
            
            _jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent lse) 
                        {
                            if (!lse.getValueIsAdjusting()) {
                                Integer currentRow = _jtable.getSelectedRow();
                                FunctionManager.txtFuncId.setText(_jtable.getValueAt(currentRow, 0).toString());
                                FunctionManager.txtFuncName.setText(_jtable.getValueAt(currentRow, 1).toString());
                                FunctionManager.txtFuncPara.setText(_jtable.getValueAt(currentRow, 2).toString());
                                FunctionManager.txtFuncDescribe.setText(_jtable.getValueAt(currentRow, 3).toString());
                            }
                        }

                    });
        }
    
    public static void RemoveFunction() throws SQLException
    {
        Integer rowId = tblFuncList.getSelectedRow();
        if (rowId == -1)
            return;
        String functionId = (String) tblFuncList.getValueAt(rowId, 0);
        // Delete
        Query.DeleteRow(tblFunctionListName, "id", functionId);
        //Update
        DisplayFunctionList();
    }
    
    public static void UpdateParameterForPredictESNFunction(Integer _stepIn, Integer _maxLoopStep, String _predictType, Double _predictTypeVal, String _fileName) throws SQLException
    {
        List<String> parameterList = new LinkedList<>();
        
        parameterList.add("LogFile!!" + Variables.logName.replace(".xes", ""));
        parameterList.add("StepIn!!" + _stepIn.toString());
        parameterList.add("MaxLoopStep!!" + _maxLoopStep);
        parameterList.add("Predicttype!!" + _predictType);
        parameterList.add("PredictTypeVal!!" + _predictTypeVal);
        parameterList.add("FileName!!" + _fileName);
        
        UpdateFunctionParameter(predictESNFunctionId, parameterList);
        
    }
    
}
