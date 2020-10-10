/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.TrainedModel;
import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import static Helper.Common.Variables.tblTrainedModelName;
import static Helper.MySQL.Query.SelectDataInDBToJTableNoCond;
import static Helper.MySQL.Query.SelectDataInDBToJTableSingleCond;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phamdinhlam
 */
public class TrainedModelHelper {
    public static void ExportToExcel(JTable _table)
    {
        MessageHelper.Info("The function is under construction...");
    }
    
    public static void FilterTraindModel(String _modelName) throws SQLException
    {
//        (String _sqlTblName, String[] _columnToget, String _condHeader, String _condVal)
        DefaultTableModel myModel = SelectDataInDBToJTableSingleCond(tblTrainedModelName, 
                Variables.tblTrainedModelHeader, "name", _modelName);
        
        TrainedModel.tblTrainedModel.setModel(myModel);
        TrainedModel.tblTrainedModel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TrainedModel.tblTrainedModel.setAutoCreateRowSorter(true);
    }
    
    public static void ClearFilter() throws SQLException
    {
        DefaultTableModel myModel = SelectDataInDBToJTableNoCond(tblTrainedModelName);
        TrainedModel.tblTrainedModel.setModel(myModel);
        TrainedModel.tblTrainedModel.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TrainedModel.tblTrainedModel.setAutoCreateRowSorter(true);
        
    }
}
