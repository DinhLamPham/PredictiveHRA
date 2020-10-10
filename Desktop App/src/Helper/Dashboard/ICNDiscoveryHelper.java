/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Dashboard;

import FormTemplate.CenterPanel;
import GraphStreamCoreHelper.FormatGraphByForm;
import static GraphStreamCoreHelper.SetGraphAttribute.SetGraphDefaultAttribute;
import Helper.Common.Variables;
import static Helper.Common.Variables.globalGraph;
import static Helper.Common.Variables.steadyGraph;
import Helper.GraphStream.MakeSteadyGraph;
import Helper.ICN.ICNBuilderV1;
import java.util.Iterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author phamdinhlam
 */
public class ICNDiscoveryHelper {
    
    public static void ICNDiscovery() throws InterruptedException
    {
        if(Variables.globalGraph.getNodeCount()==0)
            return;
        steadyGraph = MakeSteadyGraph.MakeGraph(globalGraph);
//        SetGraphDefaultAttribute(steadyGraph, "graph_style.css");
        
        
        CenterPanelHelper.LoadGraph(steadyGraph, CenterPanel.panelLogVisualization);
        FormatGraphByForm.Formatting(steadyGraph);
        
    }
    
    public static void GraphShowHideNodeLabel(Graph graph, boolean _flagShowNode, boolean  _flagShowGate)
    {
        if(graph.getNodeCount()==0)
            return;
        for(Node node:graph)
        {
            if(!ICNBuilderV1.CheckNodeIsGate(node.getId())) // Normal node
            {
                if(_flagShowNode)
                    node.addAttribute("ui.style", "text-visibility-mode: normal;");
                else
                    node.addAttribute("ui.style", "text-visibility-mode: hidden;");
            }
            else  // Gate
            {
                if(_flagShowGate)
                    node.addAttribute("ui.style", "text-visibility-mode: normal;");
                else
                    node.addAttribute("ui.style", "text-visibility-mode: hidden;");
            }
        }
    }
    
    public static void GraphShowHideEdgeLabel(Graph graph, boolean _flagShowEdge)
    {
        if(graph.getNodeCount()==0)
            return;
        
        Iterator<Edge> edgeIt = graph.getEdgeIterator();
        while (edgeIt.hasNext()) {
            Edge currentEdge = edgeIt.next();
            if(_flagShowEdge)
                currentEdge.addAttribute("ui.style", "text-visibility-mode: normal;");
            else
                currentEdge.addAttribute("ui.style", "text-visibility-mode: hidden;");
        }
        
        
    }
}
