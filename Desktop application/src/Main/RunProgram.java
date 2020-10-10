/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import FormTemplate.DetailPanel3;
import FormTemplate.FunctionManager;

import FormTemplate.SystemProperties;
import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import static Helper.Common.Variables.globalGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import static Helper.Common.Variables.*;
import Helper.Dashboard.LeftPanelHelper;
import Helper.MySQL.Connector;
import Helper.Timer.SchedulerForLoadImageResult;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author phamdinhlam
 */
public class RunProgram{
    public static void main(String[] args) {
        try {
            InitializeSystem();
            InitEventKeyToGet();
            Dashboard myDashboard = new Dashboard();
            ClearCache();
            
            if(!Connector.TestConnection())
                MessageHelper.Warning("I can't connect to MySQL database!\n"
                    + "Funtions working with database will not work properly");
            
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
    }
    
    private static void ClearCache()
    {
    }
    
    
    private static void InitializeSystem() throws SQLException, SAXException, ParserConfigurationException, IOException
    {
        Variables.graphPropertiesFrame = new SystemProperties();
        functionManagerFrame = new FunctionManager();
        frmGraphviz = new GraphVizHelper.SetupGraphvizProperties();
        
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        

    }
    public static void InitEventKeyToGet()
    {
        eventKeyToGet = new String[]{activityCode, performerCode, timeStampCode};
    }

}
