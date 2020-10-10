/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Common;

/**
 *
 * @author phamdinhlam
 */
public class TablePaging {
    public static Integer[] CalculateFromPosToPos(Integer _totalRow, Integer _rowLimit, Integer _currentPage)
    {
        Integer[] result = new Integer[2];
        result[0] = _currentPage * _rowLimit;
        result[1] = _currentPage * _rowLimit + _rowLimit - 1;        
        
        return result;
    }
    
    // For updating view of Table summary trace infomation 
    public static Integer GetPage(String _labelString, String _action)
    {
        String currentPageStr = _labelString.split("/")[0];
        if(!ToolsHelper.isStringInteger(currentPageStr))
            return -1;
        Integer currentPageInt = Integer.parseInt(currentPageStr) - 1;
        
        if(_action.equals("currence"))
            return currentPageInt;
        
        if(_action.equals("prev"))
            currentPageInt--;
        if(_action.equals("next"))
            currentPageInt++;
        
        return currentPageInt;
        
    }
}
