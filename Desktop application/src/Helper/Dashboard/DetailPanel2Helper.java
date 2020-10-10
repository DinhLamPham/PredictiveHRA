/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.DetailPanel1;
import Helper.Common.MathHelper;
import Helper.Common.MessageHelper;
import Helper.Common.TablePaging;
import static Helper.Common.Variables.totalPage;
import static Helper.Dashboard.DetailPanel1Helper.CreateTraceSummaryTable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phamdinhlam
 */
public class DetailPanel2Helper {
    
    
    
    
    public static void FilterTraceByMemberName(List<String> _nameList, Map<String, Set<Integer>> filterMap, JTable _viewingTable, boolean _andFilter)
    {
        if(filterMap.size() == 0)
        {
            MessageHelper.Warning("I haven't finished calculating trace map yet! please wait for a while to use this function...");
            return;
        }
        
        try {
            Set<Integer> traceSet = new HashSet<>();
            for(String currentName: _nameList)
            {
                Set<Integer> thisSet = filterMap.get(currentName);
                if(thisSet == null)
                    continue;
                if(!_andFilter)
                    traceSet = MathHelper.SetPlus(traceSet, thisSet);
                else
                    if(traceSet.isEmpty())
                        traceSet = thisSet;
                    else
                        traceSet = MathHelper.SetIntersection(traceSet, thisSet);
            }
            
            
            DefaultTableModel _viewingTableModelmodel = (DefaultTableModel) _viewingTable.getModel();
             Integer numberOfCol = _viewingTableModelmodel.getColumnCount(); Integer numberOfRow = _viewingTableModelmodel.getRowCount();
             for(Integer i=numberOfRow-1; i>=0; i--)
             {
                 Integer thisTraceId = Integer.parseInt(_viewingTable.getValueAt(i, 0).toString());
                 if(!traceSet.contains(thisTraceId))
                     _viewingTableModelmodel.removeRow(i);
             }
            
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
    }
    
    public static void ShowAll()
    {
         try {
            Integer pageToView = TablePaging.GetPage(DetailPanel1.lblCurrentPage.getText(), "currence");
            if(pageToView < 0 || pageToView >=totalPage)
                return;
            CreateTraceSummaryTable(pageToView);  // 1 -> 0. For conveniance
        } catch (Exception e) {MessageHelper.Error("<LoadPageInTraceTable>function: \n" + e.toString());
        }
    }
}
