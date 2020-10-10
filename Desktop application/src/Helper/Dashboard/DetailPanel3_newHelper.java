/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.DetailPanel1;
import FormTemplate.DetailPanel3_new;
import static FormTemplate.DetailPanel3_new.lblRunPredict;
import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import static Helper.Common.Variables.*;
import Helper.MySQL.Query;
import Helper.Timer.SchedulerForLoadImageResult;
import Helper.Timer.SchedulerForLoadPredictNextFunctionResultToTable;
import TableHelper.TableController;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author phamdinhlam
 */
public class DetailPanel3_newHelper {
    public static void FillTraceToTable(String _trace, JTable _table)
    {
        TableController.ClearTable(_table);
        DefaultTableModel tableModel = (DefaultTableModel) _table.getModel();
        
        String[] traceArray = _trace.split(keyWordSeparate);
        Integer order = 0;
        for(Integer i=0; i<traceArray.length; i++)
        {
            order++;
            String[] currentEvent = traceArray[i].split(keySeparateInside);
            String currentAct = currentEvent[posOfAct]; String currentPerformer = currentEvent[posOfPer];
            String currentTime = currentEvent[posOfTime];
            String data[] = {order.toString(), currentAct, currentPerformer, currentTime};
            tableModel.addRow(data);
        }
       _table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(_table.getRowCount() == 0 || _table.getSelectedRow()== -1)
                    return;
                String currentGraphType = DetailPanel1.cmbTypeToVisualize.getSelectedItem().toString();
                Object currentValId;
                if(currentGraphType.equals("Activity"))
                    currentValId = _table.getValueAt(_table.getSelectedRow(), 1); // ActivityId
                else
                    currentValId = _table.getValueAt(_table.getSelectedRow(), 2); // PerformerId
                if(currentValId==null)
                    return;

                String currentId = currentValId.toString();
                CenterPanelHelper.HighlightNode(currentId);
            }
            });  
        
        
    }
    
    
    public static void UpdateParameterForPredictFunction() throws SQLException
    {
        List<String> parameterList = new LinkedList<>();
        
        int[] rows = DetailPanel3_new.tblTracedetail.getSelectedRows();
        if(rows.length == 0)
        {
            MessageHelper.Warning("Please select some input steps!");
            return;
        }
        
        String _predictType = DetailPanel3_new.cmbPredictType.getSelectedItem().toString();
        String _inputFeature = (DetailPanel3_new.cmbInputFeature.getSelectedItem().toString());
        Integer _stepIn = rows.length;
        
        if(_predictType.equals("Activity_Performer") && Integer.parseInt(_inputFeature)==1)
        {
            MessageHelper.Warning("Predict activity and performer must use atleast 2 input features meanwhile selecting only 1!");
            return;
        }
        
        String _stepOut = (DetailPanel3_new.cmbN_Out.getSelectedItem().toString());
        
        parameterList.add("LogFile!!" + Variables.logName.replace(".xes", ""));
        parameterList.add("StepIn!!" + _stepIn.toString());
        parameterList.add("StepOut!!" + _stepOut);
        parameterList.add("PredictType!!" + _predictType);
        parameterList.add("Feature!!" + _inputFeature);
        
        for(Integer i=0; i<rows.length; i++)
        {
            String currentAct = DetailPanel3_new.tblTracedetail.getValueAt(rows[i], 1).toString();
            String currentPer = DetailPanel3_new.tblTracedetail.getValueAt(rows[i], 2).toString();
            if (Integer.parseInt(_inputFeature)==1)
            {
                // Per / Act
                if(_predictType.equals("Activity"))  // 1 feature => Act predict Act
                    parameterList.add("Input!!" + currentAct);
                else
                    parameterList.add("Input!!" + currentPer); // // 1 feature => Per predict Per
            }
            else
            if (Integer.parseInt(_inputFeature)==2)
                {parameterList.add("Input!!" + currentAct + keySeparateInside +  currentPer);}
        }
        lblRunPredict.setEnabled(false);
        FunctionManagerHelper.UpdateFunctionParameter(predictNextFunctionId, parameterList);
        
    }
    
        public static void SetRunningFunctionForSinglePrediction(String _funcName, JLabel _lblButton) throws IOException, SQLException
    {
        // Set run column value = notyet.
        Query.UpdateTbl(tblFunctionListName, "id", _funcName, tblFunctionListRunColumn, pythonNoyetStatus);
        
        // Start output scan.
        Helper.Timer.SchedulerForLoadPredictNextFunctionResultToTable myScheduler = new SchedulerForLoadPredictNextFunctionResultToTable(_lblButton, _funcName, DetailPanel3_new.tblPredictOutput);
        
    }
    
    public static String GetResultPath(String _funcName) throws SQLException
    {
        String result =""; String _columnResult = "output";
        result = Query.GetColumnValueFromFunctionList(_funcName, _columnResult);
        return result;
    }
    
    public static void SetLabelActionEmotion(JLabel _myLabel, boolean _actionEnable)
    {
        _myLabel.setEnabled(_actionEnable);
//        if(!_actionEnable)
//            _myLabel.set
    }
    
}
