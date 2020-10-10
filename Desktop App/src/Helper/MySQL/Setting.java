/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author phamdinhlam
 */
public class Setting {
    public static String driver = "com.mysql.jdbc.Driver";
    public static String db = "ProcessMining";
    public static String connection = "jdbc:mysql://localhost:3306/" + db;
    public static String user = "root";                  
    public static String password = "123456";       
    public static Connection con = null;
    public static Statement state = null;
    public static ResultSet result;
    public static PreparedStatement sqlCmd;

    
}
