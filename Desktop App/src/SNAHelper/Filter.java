/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNAHelper;

import Helper.Common.MessageHelper;
import Helper.Common.ToolsHelper;
import static Helper.Common.Variables.betweennessRange;
import static Helper.Common.Variables.closenessRange;
import static Helper.Common.Variables.degreeRange;
import java.util.Iterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author phamdinhlam
 */
public class Filter {
    public static void FilterCentrality(String _type, Graph graph, Integer _min, Integer _max, boolean _flagshowNode, boolean _flagOutgoing, boolean _flagIncoming)
    {
        try {
            double _minVal=0, _maxVal=0; String attributeKey ="";
        switch(_type)
        {
            case "Degree":
                attributeKey = "degree";                
                _minVal = degreeRange[0];_maxVal = degreeRange[1];
                break;
            case "Closeness":
                attributeKey = "closeness";
                _minVal = closenessRange[0];_maxVal = closenessRange[1];
                break;
                
            case "Betweenness":
                attributeKey = "Cb";                
                _minVal = betweennessRange[0];_maxVal = betweennessRange[1];
                break;
        }
        if(_minVal==0 && _maxVal==0)
            return;
            
            
            for(Node node:graph)
            {
                Object currentVal = node.getAttribute(attributeKey);
                if(currentVal == null)
                {
                    MessageHelper.Warning("No centrality information on node!");
                    return;
                }
                
                double degree = Double.parseDouble(currentVal.toString());
                double scaledVal = ToolsHelper.ScaleValTo0_100Range(degree, _minVal, _maxVal);
                boolean flagRemove = true;
                if(scaledVal>=_min && scaledVal<=_max)
                    flagRemove = false;
                
                if(flagRemove && _flagshowNode)
                    node.addAttribute("ui.hide");
                else
                    if(node.getAttribute("ui.hide")!=null)
                        node.removeAttribute("ui.hide");
                
                if(_flagOutgoing) // Hide outgoing edge
                {
                    Iterator<Edge> outgoingEdgeIt =  node.getLeavingEdgeIterator();
                    while (outgoingEdgeIt.hasNext()) {
                        Edge currentEdge = outgoingEdgeIt.next();
                        
                        if(flagRemove)
                            currentEdge.addAttribute("ui.hide");
                        else
                            if(currentEdge.getAttribute("ui.hide")!=null)
                                currentEdge.removeAttribute("ui.hide");
                    }
                }

                if(_flagOutgoing) // Hide incoming edge
                {
                    Iterator<Edge> incomingEdgeIt =  node.getEnteringEdgeIterator();
                    while (incomingEdgeIt.hasNext()) {
                        Edge currentEdge = incomingEdgeIt.next();
                        if(flagRemove)
                            currentEdge.addAttribute("ui.hide");
                        else
                            if(currentEdge.getAttribute("ui.hide")!=null)
                                currentEdge.removeAttribute("ui.hide");
                    }
                }


            }
        } catch (Exception e) {
            System.out.println("SNAHelper.Filter.ByDegreeCentrality()");
            MessageHelper.Error(e.toString());
        }
    }
}
