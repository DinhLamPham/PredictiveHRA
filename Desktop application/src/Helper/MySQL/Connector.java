/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.MySQL;
import Helper.Common.MessageHelper;
import static Helper.MySQL.Setting.*;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * 
 *
 * @author phamdinhlam
 */
public class Connector {
    
    public static boolean TestConnection()
    {   
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(connection, user, password);
            CloseConnection();
            return true;
            }
        catch(ClassNotFoundException e){
            return false;
            }
        catch(SQLException e){
            
            return false;
            }
    }
    
    public static void OpenConnection() throws SQLException{
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(connection, user, password);
            }
        catch(ClassNotFoundException e){
            MessageHelper.Error(e.toString());
            }
        catch(SQLException e){
            MessageHelper.Error(e.toString());
            }
        }
    public static boolean ConnectionIsClosed() throws SQLException
    {
        return con.isClosed();
    }

    public static void CloseConnection(){
        try{
            if(!con.isClosed()){
                con.close();
                }
            }
        catch(NullPointerException e){
            MessageHelper.Error(e.toString());
            }
        catch(SQLException e){
            MessageHelper.Error(e.toString());
            }
        }
    
    public static void InsertRowToTable(String _mySQLTable, String[] _tblHeader, String[] _data) throws SQLException{
        try{
            //using PreparedStatement
            if (ConnectionIsClosed())
                Connector.OpenConnection();
            String tblColumn =_mySQLTable + "(" + String.join(", ", _tblHeader) + ")";
            String[] mark = new String[_tblHeader.length];
            for(int i=0; i<mark.length; i++)
                mark[i] = "?";
            String tblRowVal = " values(" +  String.join(", ", mark) + ")" ;
            String sqlString = "insert into " + tblColumn + tblRowVal;
            sqlCmd = con.prepareStatement(sqlString); 
            for(int i=0; i<_data.length; i++)
            {
                sqlCmd.setString(i+1, _data[i]);
            }
            sqlCmd.executeUpdate();
            }
        catch(SQLException e){
            System.err.println(e.toString());
            }
            if (!ConnectionIsClosed())
                Connector.CloseConnection();
        }
    
    // Delete all data in MySQL Table
    public static void RemoveAllTableData(String _mySQLTblName) throws SQLException
    {
        try {
            String sqlString = "delete from " + _mySQLTblName;
        sqlCmd = con.prepareStatement(sqlString); 
        sqlCmd.executeUpdate();
        } catch (SQLException e) {
            MessageHelper.Error(e.toString());
        }
    }
    
}
