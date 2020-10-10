/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import FormTemplate.*;
import static Helper.Common.MathHelper.MaxValue;
import static Helper.Common.Variables.MLogic;
import static Helper.Common.Variables.gUIProp;
import Helper.Dashboard.GraphUIProperty;
import Helper.Dashboard.MainLogic;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author phamdinhlam
 */
public class Dashboard extends JFrame {
    
    
    
    private  Integer dashboard_width, dashboard_height;
    private  Integer leftPanel_width, leftPanel_height;
    private  Integer detailPanel_width, detailPanel_height;
    private  Integer centerPanel_width, centerPanel_height;
    Integer borderSize = 2, edgeSize = 30, tasbarSize=100;
    
    
    
    
    public static LeftPanel leftPanel;
    public static DetailPanel1 detailPanel1;
    public static DetailPanel2 detailPanel2;
    public static DetailPanel3 detailPanel3;
    public static DetailPanel3_new detailPanel3_new;
    public static CenterPanel centerPanel;
    public static JFrame  dashBoardFrame;
    
    
    
    public Dashboard()
    {
        Dimension screen_dim = Toolkit.getDefaultToolkit().getScreenSize();
        dashboard_width = screen_dim.width;
        dashboard_height = screen_dim.height - tasbarSize;

        // Initialize graph UI property object to pass to sim
        Integer graphBox_width = (int)(0.8 * dashboard_width);
        
        Integer graphBox_height = (int)(0.55 * dashboard_height);
        
        gUIProp = new GraphUIProperty();
        gUIProp.height = graphBox_height;
        gUIProp.width = graphBox_width;
        gUIProp.posx = 0;
        gUIProp.posy = 0;
        MLogic= new MainLogic();
        
        
         // Initialize left panel
        int leftPanelMinWidth = 270;
        
        leftPanel = new LeftPanel();
        leftPanel_width = MaxValue(dashboard_width - (graphBox_width + borderSize + edgeSize), leftPanelMinWidth);        
//        leftPanel_height = dashboard_height - (borderSize + edgeSize + 10);
        leftPanel_height = graphBox_height;
        leftPanel.setLocation(10, 0);
        leftPanel.setSize(leftPanel_width, leftPanel_height);
        
        
        
        // Initialize Center Panel
        centerPanel = new CenterPanel();
        centerPanel_width = graphBox_width;
        centerPanel_height = graphBox_height;
        centerPanel.setLocation(10 + leftPanel_width + borderSize, 0);
        centerPanel.setSize(centerPanel_width, centerPanel_height);
        
        detailPanel_width = (int)((dashboard_width - (leftPanel_width + borderSize + edgeSize))/3);
        // Initialize Detail panel 1       
        int adjWidth1 = 350, minPanel1Width = 350, panel1Width = MaxValue(minPanel1Width, detailPanel_width - 2 - adjWidth1);
        detailPanel1 = new DetailPanel1();
        
        borderSize +=5;
        detailPanel_height = dashboard_height - graphBox_height - (borderSize + edgeSize + 10);
        detailPanel1.setSize(panel1Width, detailPanel_height);
        detailPanel1.setLocation(10, graphBox_height + borderSize);
        
        // Initialize Detail panel 2       
        int adjWidth2 = 400, minPanel2Width = 350, panel2Width =  MaxValue(minPanel2Width, detailPanel_width - 2 + adjWidth1 - adjWidth2);
        detailPanel2 = new DetailPanel2();
        detailPanel2.setSize(panel2Width, detailPanel_height);
        detailPanel2.setLocation(10+ panel1Width, graphBox_height + borderSize);
        
       // Initialize Detail panel 3       
        detailPanel3_new = new DetailPanel3_new();
        detailPanel3_new.setSize(dashboard_width - panel1Width - panel2Width - 20, detailPanel_height);
        detailPanel3_new.setLocation(10+ panel1Width + panel2Width, graphBox_height + borderSize);
        
        
        dashBoardFrame = new JFrame();
        dashBoardFrame.setSize(dashboard_width, dashboard_height);
//        dashBoardFrame.getContentPane().setLayout(new GridLayout(1, 1));
        dashBoardFrame.getContentPane().setLayout(null);
        
        dashBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        dashBoardFrame.add(leftPanel);
        dashBoardFrame.add(detailPanel1);
        dashBoardFrame.add(detailPanel2);
        dashBoardFrame.add(detailPanel3_new);
        dashBoardFrame.add(centerPanel);
        dashBoardFrame.setVisible(true);
//        LoadGraph(g);
    }
    
    
    
}
