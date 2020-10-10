/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphStreamCoreHelper;

import Helper.Common.MessageHelper;
import Helper.Common.ToolsHelper;
import static Helper.Common.Variables.*;
import java.util.Map;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import java.util.Iterator;

/**
 *
 * @author phamdinhlam
 */
public class GetGraphAttribute {
    
    
    public static Integer GetEdgeValueInteger(Graph graph, String edgeName)
    {
        Integer edgeValueInteger =0;
        Edge tempEdge = null;
        String edgeValueString;
        tempEdge = graph.getEdge(edgeName);
        if(tempEdge!=null)
        {
            edgeValueString = tempEdge.getAttribute("ui.label");
            if(!ToolsHelper.isStringInteger(edgeValueString))
            {
                MessageHelper.Warning("The edge " + edgeName +" doesn't contain any interger value");
                return 0;
            }
            else
            {
                edgeValueInteger = Integer.parseInt(edgeValueString);
                return edgeValueInteger;
            }
        }
        else
            return 0;
        
    }
    public static Integer GetSelfLoopValue(Graph graph, String node)
    {
        Integer result=0;
        Edge edge = graph.getEdge(node+keySeparateInside+node);
        if(edge==null)
            return 0;
        result = GetEdgeValueInteger(graph, node+keySeparateInside+node);
        return result;
    }
    
}
