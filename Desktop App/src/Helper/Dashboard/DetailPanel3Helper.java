/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.DetailPanel3;
import static Helper.Common.Variables.*;
import Helper.MySQL.Query;
import Helper.Timer.SchedulerForLoadImageResult;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author phamdinhlam
 */
public class DetailPanel3Helper {
    public static void BindingFunctionToCombobox() throws SQLException
    {
        List<String> functionList = new LinkedList<>();
        functionList = Query.GetFunctionList(tblFunctionListName, "id");
        DetailPanel3.cmbPyFunc.removeAllItems();
        for(String x: functionList)
        {
            DetailPanel3.cmbPyFunc.addItem(x);
        }
    }
    

}
