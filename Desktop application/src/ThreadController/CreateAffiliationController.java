/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadController;

import Helper.Alogrithm.Affiliation;
import static Helper.Alogrithm.Affiliation.CreateAffiliationMap;
import Helper.Common.Variables;
import static Helper.Common.Variables.*;
import Helper.MySQL.Query;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phamdinhlam
 */
public class CreateAffiliationController implements Runnable{
    

    @Override
    public void run() 
    {
//        try {
//            // Check affiliation of activities are exist or not by quering in the db
//            Set<String> activityAffiliationSet = Query.SelectColumnFromTableToSet(Variables.tblAffiliationActName, "ActivityName", "LogName", Variables.logName.replace(".xes", ""));
//            if(activityAffiliationSet.isEmpty()) // There is no affiliation in database => 1. Create; 2. Save to Database
//            {
//                //1. Create affiliation -> Map<String, Set>
//                activityAffiliationMap = CreateAffiliationMap(globalXESLogListStrings, posOfAct, posOfPer);
//                performerAffiliationMap = CreateAffiliationMap(globalXESLogListStrings, posOfPer, posOfAct);
//                //2. Save to database
//                Query.SaveAffiliationToMySQLTable(Variables.tblAffiliationActName, Variables.tblAffiliationActHeader, activityAffiliationMap);
//                Query.SaveAffiliationToMySQLTable(Variables.tblAffiliationPerName, Variables.tblAffiliationPerHeader, performerAffiliationMap);
//                
////                System.out.println("The function creating affiliation has just finished!");
//            }
//            else // Load from database to variables
//            {
//                // Load activityAffiliationMap
//                
//                // Load performerAffiliationMap
//            }
//            
//
//        } catch (SQLException ex) {
//            Logger.getLogger(CreateAffiliationController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    
}
