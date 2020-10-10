/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.CenterPanel;
import static FormTemplate.CenterPanel.tblActSet;
import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import static Helper.Common.Variables.keySeparateInside;
import Helper.MySQL.Query;
import TableHelper.TableController;
import static TableHelper.TableController.InsertValueToLastCellInColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phamdinhlam
 */
public class RsAllocationHelper {
    
//    java.util.Timer timer = new java.util.Timer("doubleClickTimer", false);
    static boolean actTblClick=false;
    static boolean perTblClick=false;
    
    
    public static void LoadActivitySetFromTable(JTable _inputTable, JTable _outputTable)
    {
        try {
            String[] header = {"Activity set"};
            
            TableController.RemoveAllTableData(_outputTable, header);
            
            DefaultTableModel mymodel = (DefaultTableModel) _outputTable.getModel();
//            String[] rowData = new String[1]; rowData[0] = "START";
//            mymodel.addRow(rowData);
            
//            DefaultTableModel mymodel = (DefaultTableModel) _outputTable.getModel();
            // Add row click listener
            int selectedRows[] = _inputTable.getSelectedRows();
            
            if(selectedRows.length == 0)
            {
                _inputTable.setRowSelectionInterval(0, _inputTable.getRowCount()-1);
                selectedRows = _inputTable.getSelectedRows();
            }
            
            for(Integer i=0; i<selectedRows.length; i++)
            {
                Integer currentRow = selectedRows[i];
                String currentAct = _inputTable.getValueAt(currentRow, 0).toString();
                String[] data = new String[] {currentAct};
                mymodel.addRow(data);

            }
            _outputTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting())
                        try {
                            LoadPerSetFromActTable(_outputTable, CenterPanel.tblPerSet);
                    } catch (SQLException ex) {
                        Logger.getLogger(RsAllocationHelper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            
        } catch (Exception e) {MessageHelper.Error(e.toString());
        }
        
    }
    
    public static boolean AddSelectedValueFromTableToTable(JTable _inputTable, Integer _inputColumnIdx, JTable _ouputTable, Integer _ouputColumnIdx)
    {
        Integer rowIdx = _inputTable.getSelectedRow();
        if(rowIdx == -1)
            return false;
        String currentAct = _inputTable.getValueAt(rowIdx, _inputColumnIdx).toString();
        TableController.InsertValueToLastCellInColumn(_ouputTable, _ouputColumnIdx, currentAct);
        
        return true;
    }
    
        
    
    // Single click => Refresh performer
    public static void LoadPerSetFromActTable(JTable _actJTable, JTable _perJTable) throws SQLException
    {
        if(_actJTable.getSelectedRow() == -1)
            return;
        Object currentObj = _actJTable.getValueAt(_actJTable.getSelectedRow(), 0);
        if(currentObj == null)
            return;
        
        String currentAct = currentObj.toString();
        if(currentAct.equals("START"))
            return;
//        String _sqlTblName, String[] _columnToget, String _condHeader, String _condVal
        String[] _columnToget = new String[] {"Members"};
        DefaultTableModel newModel = Query.SelectMembersFromAffiliation(Variables.tblAffiliationActName, _columnToget, "ActivityName", currentAct);
//        TableController.ClearTable(_perJTable);
        _perJTable.setModel(newModel);
        if(_perJTable.getRowCount()>0)
            _perJTable.setRowSelectionInterval(0, 0);
        
    }
    
    public static void InsertTraceToInputTraceTable(JTable _singleTraceTable, JTable _outputJTable)
    {
        int totalRow = _singleTraceTable.getRowCount();
        if(totalRow<=1)
            return;
        
        Variables.stepInForPredictESN = totalRow;
        String currentTrace = _singleTraceTable.getValueAt(0, 0).toString() 
                            + keySeparateInside + _singleTraceTable.getValueAt(0, 1).toString()
                            +  keySeparateInside + _singleTraceTable.getValueAt(0, 2).toString();
        
        for(Integer i=1; i<totalRow; i++)
        {
            Object currentActObject = _singleTraceTable.getValueAt(i, 0);  
            Object currentPerObject = _singleTraceTable.getValueAt(i, 1);
            Object currentTimeObject = _singleTraceTable.getValueAt(i, 2);
            String currentAct = "NULL"; String currentPer = "NULL"; String currentTime = "NULL";
            if (currentActObject!=null)
                currentAct = currentActObject.toString();
            
            if(currentPerObject != null)
                currentPer = currentPerObject.toString();
            
            if(currentTimeObject != null)
                currentTime = currentTimeObject.toString();
             
            currentTrace += Variables.keyWordSeparate + (currentAct + Variables.keySeparateInside + currentPer + Variables.keySeparateInside + currentTime);
        }
        InsertValueToLastCellInColumn(CenterPanel.tblInputTraceList, 0, currentTrace);
    }
    
    public static void UpdateNewTraceTableWithInputTrace(JTable _table, String _traceInfo)
    {
        TableController.ClearTable(_table);
        DefaultTableModel myModel = (DefaultTableModel) _table.getModel();
        String[] traceArr = _traceInfo.split(Variables.keyWordSeparate);
        for(String event:traceArr)
        {
            String currentAct = event.split(Variables.keySeparateInside)[0];
            String currentPer = event.split(Variables.keySeparateInside)[1];
            String currentTime = event.split(Variables.keySeparateInside)[2];
            Object[] data = new Object[]{currentAct, currentPer, currentTime};
            myModel.addRow(data);
        }
    }
    
    
}
