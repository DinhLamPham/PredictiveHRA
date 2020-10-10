/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

STEP TO CREATE NEW TABLE:
1. Create new table model: myModel
    1.1. From Mysql: CreateTableFromQuerySet()
    1.2. From Header and blank data: InitializeTable

2. Update JTableX in Frame: JTableX.setmodel(myModel)

 */
package TableHelper;

import FormTemplate.DetailPanel1;
import FormTemplate.DetailPanel2;
import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import static Helper.Common.Variables.globalTraceTable;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import static FormTemplate.DetailPanel1.tblTraceSummary;
import static FormTemplate.DetailPanel2.tblDetailSummary;
import static Helper.Common.Variables.tblFunctionListHeader;
import Helper.MySQL.Query;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

/**
 *
 * @author Administrator
 */
public class TableController {
    
    
           /*
            Convert Table Column to Linked list
           */
        public static List<String> TableColumnToList(JTable _inputTable, Integer _columnIdx)
        {
            List<String> outputList = new LinkedList<>();

            try {
                DefaultTableModel model = (DefaultTableModel) _inputTable.getModel();
                for(int i=0; i<model.getRowCount(); i++)
                {
                    Object currentValObject = model.getValueAt(i, _columnIdx);
                    if(currentValObject != null)
                        outputList.add(currentValObject.toString());
                }
            } catch (Exception e) {MessageHelper.Error("<TableColumnToList> function\n"+e.toString());
            }
            return outputList;
        }
    
            
        public static DefaultTableModel CreateTableFromQuerySet(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);

    }
    
    
        public static void CopyJTableToJTable(JTable _tblSource, JTable _tblDest, String[] _tblHeader)
        {
            RemoveAllTableData(_tblDest, _tblHeader);
            SetTableHeader(_tblHeader, _tblDest);
            
            DefaultTableModel sourceModel = (DefaultTableModel) _tblSource.getModel();
            DefaultTableModel destModel = (DefaultTableModel) _tblDest.getModel();

            Vector headerVector = new Vector(_tblHeader.length);
            for(Integer i=0; i<_tblHeader.length; i++)
                headerVector.add(_tblHeader[i]);
            
            destModel.setDataVector(sourceModel.getDataVector(), headerVector);
            
        }
    
        public static void AddSeletedListenerToInsertSelectingRow(JTable _jtable, String _tblMySQL, String[] _header)
        {
            
            _jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent lse) 
                        {
                            if (!lse.getValueIsAdjusting()) {
                                try {
                                    Query.InsertSelectingRow(_jtable, _tblMySQL, _header);
                                } catch (SQLException ex) {
                                    MessageHelper.Error(ex.toString());
                                }
                            }
                        }

                    });
        }
    
    
        public static void ViewTableInDetailPanel(String _tableName)
        {
            switch(_tableName)
            {
                case "Activity":
                    if(Variables.globalActivityTable==null)
                        return;
                    Variables.currentViewTable = "Activity";
                    tblDetailSummary.setModel(Variables.globalActivityTable.getModel());
                    
//                    DetailPanel2.tblDetailSummary.setAutoCreateRowSorter(true);
                    
                    
                    break;
                case "Performer":
                    if(Variables.globalPerformerTable==null)
                        return;
                    Variables.currentViewTable = "Performer";
                    tblDetailSummary.setModel(Variables.globalPerformerTable.getModel());
//                    DetailPanel2.tblDetailSummary.setAutoCreateRowSorter(true);
                    
                    
                    break;
            }
//            
        }
        
    
        public static void SetTableHeader(String[] _header, JTable _table)
        {
            try {
                for(Integer i=0; i<_table.getColumnCount();i++)
                _table.getColumnModel().getColumn(i).setHeaderValue(_header[i]);
            } catch (Exception e) {MessageHelper.Error("<SetTableHeader>" + e.toString());
            }
        }
        
        
        public static void RemoveAllTableData(JTable _table, String[] _header)
        {
            try {
                    DefaultTableModel model = new DefaultTableModel(_header, 0){
                        @Override
                          public boolean isCellEditable(int row, int col)
                          {
                              boolean editFlag = false;
//                              if(col==0 || col==1 || col==2 || col==3 || col==4)
//                                  editFlag=false;
                              return editFlag;
                          };
                    };
                    _table.setModel(model);
            } catch (Exception e) {
//                System.err.println("Function: RemoveAllTableData\n" + e.toString());
//                System.err.println("Number row: " + _table.getModel().getRowCount());
            }
            
        }
        
        public static void RemoveSelectedRows(JTable _table)
        {
            try {
                int[] rows = _table.getSelectedRows();
//                if(rows.length == 0)
//                    return;
                DefaultTableModel myModel = (DefaultTableModel) _table.getModel();
                for(Integer i=rows.length-1; i>=0; i--)
                {
                    Integer currentRow = rows[i];
//              if(currentRow>0)
                    myModel.removeRow(currentRow);

                }
            } catch (Exception e) {
                MessageHelper.Error(e.toString());
            }

            
        }
        
        public static void InitializeTableWithDataAndHeader(JTable _table, Object[][] _data, String[] _header)
        {
            try {
                  _table.setModel(
                      new javax.swing.table.DefaultTableModel(_data,_header)
                      {
                          @Override
                          public boolean isCellEditable(int row, int col)
                          {
                              boolean editFlag = false;
                              return editFlag;
                          };
                          
                      }
                );
                _table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                _table.setAutoscrolls(true);
            } catch (Exception e) {MessageHelper.Error("<InitializeTable> \n" + e.toString());
            }
        }
        
        
        public static void InitializeBlankTableWithHeader(JTable _table, String[] _header)
        {
            try {
                  _table.setModel(
                      new javax.swing.table.DefaultTableModel(null,_header)
                      {
                          // Disable edit cells in column 0 and column 1
                          @Override
                          public boolean isCellEditable(int row, int col)
                          {
                              boolean editFlag = false;
//                              if(col==0 || col==1 || col==2 || col==3 || col==4)
//                                  editFlag=false;
                              return editFlag;
                          };
                          
                      }
                );
//                _table.setAutoCreateRowSorter(true);
                _table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    //            _table.getColumn(_header[0]).setPreferredWidth(iDColumnWidth);
    //            _table.getColumn(_header[1]).setPreferredWidth(fileNameColumnWidth);
                _table.setAutoscrolls(true);
            } catch (Exception e) {MessageHelper.Error("<InitializeTable> \n" + e.toString());
            }
        }
        
        public static void PrintTable(JTable _table)
        {
             DefaultTableModel model = (DefaultTableModel) _table.getModel();
             Integer numberOfCol = model.getColumnCount(); Integer numberOfRow = model.getRowCount();
             for(Integer i=0; i<numberOfRow; i++)
             {
                 for(Integer j=0; j<numberOfCol; j++)
                 {
                     Object currentCellValueObject = model.getValueAt(i, j);
                     String currentCellValueString ="";
                     if(currentCellValueObject!=null)
                         currentCellValueString = currentCellValueObject.toString();
                     System.out.print(currentCellValueString + " ");
                 }
                 System.out.println();
             }
        }
        // Copy all selected file in Table list file in Right panel to a list<String>
        public static List<String> CopySelectedFileToList(JTable _table, List<String> _listFullPathFileInFolder, Date _fromDate, Date _toDate)
        {
            List<String> listFilteredFile = new LinkedList<String>();
            DefaultTableModel model = (DefaultTableModel) _table.getModel();
             Integer numberOfCol = model.getColumnCount(); Integer numberOfRow = model.getRowCount();
             for(Integer i=0; i<numberOfRow; i++)
             {
                 String currentRowValue =""; boolean flagIsRowSelected=false;
                 for(Integer j=0; j<numberOfCol; j++)
                 {
                     Object currentCellValueObject = model.getValueAt(i, j);
                     String currentCellValueString ="blank";
                     if(currentCellValueObject!=null)
                         currentCellValueString = currentCellValueObject.toString();
                     
//                     if(j==1) // Copy full pathFile => Get full Path of File
//                         currentCellValueString = _listFullPathFileInFolder.get(i);                         
                     if(currentRowValue.equals(""))
                         currentRowValue =  currentCellValueString;
                     else
                            if(j<6)  // j==6 ==> Cellvalue = Selected/Not selected => dont use this value.
                                currentRowValue = currentRowValue + Variables.keyWordSeparate + currentCellValueString;
                     
                     if(j==6 && currentCellValueString.equals("true"))
                         flagIsRowSelected=true;
                 }
                 if(flagIsRowSelected)
                 {
                     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     String fromDateString  = dateFormat.format(_fromDate); 
                     String toDateString  = dateFormat.format(_toDate); 
                     
                     currentRowValue = currentRowValue + Variables.keyWordSeparate + fromDateString + Variables.keySeparateInside + toDateString;
                     listFilteredFile.add(currentRowValue);
                 }
             }
            return listFilteredFile;
        }
        
        
        
        public static void RemoveSelectedRow(JTable _table)
        {
             DefaultTableModel model = (DefaultTableModel) _table.getModel();
             Integer rowIndex = _table.getSelectedRow();
             if(rowIndex==-1 )
                 return;
             model.removeRow(rowIndex);
        }
        public static void ClearTable(JTable _table)
        {
             DefaultTableModel model = (DefaultTableModel) _table.getModel();
             _table.getSelectionModel().clearSelection();  // Important activity before deleting?
             if(model.getRowCount()>0)
                model.setRowCount(0); 

//               _table.updateUI();
        }
        
        
    public static boolean CheckIsEmptyTable(JTable _table)
    {
         DefaultTableModel myTableModel = (DefaultTableModel) _table.getModel();
        Integer numberOfRows = myTableModel.getRowCount();
        if(numberOfRows==0)
            return true;
        return false;
    }
    
   
    public static void InsertValueToLastCellInColumn(JTable _table, Integer _columnIdx, String _value)
    {
        // Find the last row which empty in table
        DefaultTableModel myModel = (DefaultTableModel) _table.getModel();
        Integer rowIdx = 0; boolean flagCreateRow = true;
        for(Integer i=myModel.getRowCount()-1; i>0; i--)
        {
            Object currentVal = _table.getValueAt(i, _columnIdx);
            if(currentVal == null)
            {rowIdx = i; flagCreateRow = false;}
            else
                break;
        }
        if(flagCreateRow)
        {
            rowIdx = myModel.getRowCount();
            myModel.setRowCount(myModel.getRowCount() + 1);
        }
        
        // Input insert value
        _table.setValueAt(_value, rowIdx, _columnIdx);
    }
    
    public static boolean InitTableFromListString(JTable _table, List<String> _inputList)
    {
        try {
//            TableController.ClearTable(_table);
            DefaultTableModel myModel = (DefaultTableModel) _table.getModel();
            for(Integer i=0; i<_inputList.size(); i++)
            {
                String[] rowData= new String[1];
                rowData[0] = _inputList.get(i);
                myModel.addRow(rowData);
            }
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
            return false;
        }
        return true;
    }
    
        
}
