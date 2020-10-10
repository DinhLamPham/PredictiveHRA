/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphVizHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author phamdinhlam
 */
public class Variables {
    
    public static boolean graphDirection = true;
    public static int currentDpiPos = 14 ;
    public static int[] dpiSizes = {30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 110, 120, 130, 140, 150, 200, 250, 300};
    
    public static String nodeShape;
    public static String nodeSizeString;
    public static Integer nodeSizeInteger;
    public static String nodeColor;
    public static String nodeFillColor;
    
    public static String orGateShape;
    public static String orGateSizeString;
    public static Integer orGateSizeInteger;
    public static String orGateColor;
    public static String orGateFillColor;
    
    public static String andGateShape;
    public static String andGateSizeString;
    public static Integer andGateSizeInteger;
    public static String andGateColor;
    public static String andGateFillColor;
    
    public static String loopGateShape;
    public static String loopGateSizeString;
    public static Integer loopGateSizeInteger;
    public static String loopGateColor;
    public static String loopGateFillColor;
   
    
    public static String settingINIpath="/ini/setting.xml";
    public static String nodeShapeListFile="/Template/GraphViz/listOfShape.html";
    public static String graphVizXMLTag="graphvizNode";
    
    public static String nodesep="0";
    public static String ranksep="4";
    public static String rankdir="TB";
    
    
    public static String leftSubGraphStyle="rounded,filled";
    public static String leftSubGraphFillcolor="gray";
    
    public static String leftSubnodeShape="circle";
    public static String leftSubnodeColor="orange";
    public static String leftSubnodeSizeString;
    public static Integer leftSubnodeSizeInteger;
    
    public static String leftSubnodeFillColor="blue";    
    
    
    
    public static String rightSubGraphStyle="rounded,filled";
    public static String rightSubGraphFillColor="gray";
    
    public static String rightSubnodeShape="box";
    public static String rightSubnodeColor="yellow";
    public static String rightSubnodeSizeString;
    public static Integer rightSubnodeSizeInteger;
    
    public static String rightSubnodeFillColor="yellow";  
    
    public static boolean showEdgeLabel=true;
            
    static Path currentRelativePath = Paths.get("");
    public static String settingINIFullPath = currentRelativePath.toAbsolutePath().toString()+settingINIpath;
    
   
    
}
