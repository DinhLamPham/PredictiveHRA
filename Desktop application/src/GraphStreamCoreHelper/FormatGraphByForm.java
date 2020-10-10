/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphStreamCoreHelper;

import FormTemplate.DetailPanel1;
import FormTemplate.DetailPanel2;
import FormTemplate.SystemProperties;
import static GraphStreamCoreHelper.SetGraphAttribute.*;
import Helper.Common.ToolsHelper;
import static Helper.Common.ToolsHelper.ColortoHexString;
import org.graphstream.graph.Graph;
import static Helper.Common.Variables.*;
import static Helper.ICN.ICNBuilderV1.CheckNodeIsAndGate;
import static Helper.ICN.ICNBuilderV1.CheckNodeIsGate;
import static Helper.ICN.ICNBuilderV1.CheckNodeIsLoopGate;
import static Helper.ICN.ICNBuilderV1.CheckNodeIsOrGate;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

/**
 *
 * @author phamdinhlam
 */
public class FormatGraphByForm {
    
    // Format graph using GraphStreamProperties Form
    public static void Formatting(Graph graph)
    {
        if(graph.getNodeCount()==0)
            return;
        // Format Node
        String nodeColor = ColortoHexString(SystemProperties.lblNormalNodeColor.getForeground());
        String gateColor = "";
        Integer nodeSize = (SystemProperties.cmbNodeSize.getSelectedIndex() + 1) * 5;
        Integer gateSize = (SystemProperties.cmbGateSize.getSelectedIndex() + 1) * 4;
        
        String gateType = SystemProperties.cmbGateType.getSelectedItem().toString();
        String nodeType;        
        if(DetailPanel1.cmbTypeToVisualize.getSelectedIndex() == 0) // View Activiy
            nodeType = SystemProperties.cmbActNodeType.getSelectedItem().toString();
        else // View Performer. index = 1
            nodeType = SystemProperties.cmbPerNodeType.getSelectedItem().toString();

        ChangeBackgroundColor(graph, ColortoHexString(SystemProperties.lblBackGroundColor.getForeground()));
        
        for(Node node: graph)
        {
            node.removeAttribute("ui.class");
            nodeColor = ColortoHexString(SystemProperties.lblNormalNodeColor.getForeground());
            if(node.getId().equals("START") || node.getId().equals("END"))
                SetNodeAttribute(graph, node.getId(), nodeSize, 
                                        ColortoHexString(SystemProperties.lblStartEndNodeColor.getForeground()),
                                        ColortoHexString(SystemProperties.lblStartEndNodeColor.getForeground()),
                                        FormTemplate.SystemProperties.cmbStartEndType.getSelectedItem().toString(), 
                                        "");
            else
            {
                if(!CheckNodeIsGate(node.getId()))  // Normal node
                    SetGraphAttribute.SetNodeAttribute(graph, node.getId(), nodeSize, 
                                        nodeColor,
                                        nodeColor,
                                        nodeType, 
                                        "");
                else // Gate
                {
                    if(CheckNodeIsAndGate(node.getId()))
                        gateColor = ColortoHexString(SystemProperties.lblAndGateColor.getForeground());
                    if(CheckNodeIsOrGate(node.getId()))
                        gateColor = ColortoHexString(SystemProperties.lblOrGateColor.getForeground());
                    if(CheckNodeIsLoopGate(node.getId()))
                        gateColor = ColortoHexString(SystemProperties.lblLoopGateColor.getForeground());
                    
                    SetGraphAttribute.SetNodeAttribute(graph, node.getId(), gateSize, 
                                        gateColor,
                                        gateColor,
                                        gateType, 
                                        "");
                }
            }
                
        }
        // Format Edge
        ChangeAllEdgeFormat(graph, ColortoHexString(SystemProperties.lblEdgeColor.getForeground()));
    }
    
    
}
