/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNAHelper;

import static GraphStreamCoreHelper.SetGraphAttribute.SetGraphDefaultAttribute;
import Helper.Common.ToolsHelper;
import static Helper.Common.ToolsHelper.ChooseNodeLevel;
import static Helper.Common.Variables.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author DinhLam Pham
 */
public class FormatGraph {
    public static void SetDefaultStyle(Graph g)
    {
        /* Remove all old format */
        g.removeAttribute("ui.style");
        for(Node node:g)
        {
            ClearNodeAttrib(node);
        }
        
        Collection<Edge> edgeCollection = g.getEdgeSet();
        for (Iterator<Edge> iterator = edgeCollection.iterator(); iterator.hasNext();) {
            Edge currentEdge = iterator.next();
            ClearEdgeAttrib(currentEdge);
        }
        
        SetGraphDefaultAttribute(g, "SNAStyle.css");
    }
    
    public static void SetNodeFormatLevel(Graph g, String _level)
    {
        for(Node node:g)
        {
            node.addAttribute("ui.class", "level" + _level);
        }
    }
    
    public static void SetEdgeFormatLevel(Graph g, String _level)
    {
        Collection<Edge> edgeCollection = g.getEdgeSet();
        for (Iterator<Edge> iterator = edgeCollection.iterator(); iterator.hasNext();) {
            Edge currentEdge = iterator.next();
            currentEdge.addAttribute("ui.class", "level" + _level);
        }
    }
    
    public static void FormatNodeOnDegree(Graph graph, String _type) throws InterruptedException
    {
        double dominator = 1.0, _minVal=0, _maxVal=0; String attributeKey ="";
        switch(_type)
        {
            case "Degree":
                attributeKey = "degree";
                dominator = graph.getNodeCount()*(graph.getNodeCount()-1);
                _minVal = degreeRange[0];_maxVal = degreeRange[1];
                break;
            case "Closeness":
                attributeKey = "closeness";
                _minVal = closenessRange[0];_maxVal = closenessRange[1];
                break;
                
            case "Betweenness":
                attributeKey = "Cb";
                dominator = graph.getNodeCount()*(graph.getNodeCount()-1);
                _minVal = betweennessRange[0];_maxVal = betweennessRange[1];
                break;
        }
        if(_minVal==0 && _maxVal==0)
            return;
        
        for(Node currentNode:graph)
        {
            Object currentValObj = currentNode.getAttribute(attributeKey);
            if(currentValObj==null)
                break;
            double currentVal = 0.0;
            if(Helper.Common.ToolsHelper.isStringDouble(currentValObj.toString()))
                currentVal =  Double.parseDouble(currentValObj.toString());
//            currentVal = currentVal/dominator;
            
            double scaledVal = ToolsHelper.ScaleValTo0_100Range(currentVal, _minVal, _maxVal);
            double colorVal = ToolsHelper.ScaleValTo0_255Range(currentVal, _minVal, _maxVal);
            
            
            Integer level = ChooseNodeLevel(scaledVal);
            
            ClearNodeAttrib(currentNode);
            
            currentNode.addAttribute("ui.class", "level" + level);
            currentNode.setAttribute("ui.color", colorVal);
            
            Iterator<Edge> edgeIt = currentNode.getLeavingEdgeIterator();
            while(edgeIt.hasNext())
            {
                Edge thisEdge = edgeIt.next();
                ClearEdgeAttrib(thisEdge);
                
                thisEdge.addAttribute("ui.class", "level" + level);
//                thisEdge.setAttribute("ui.color", colorVal);
            }

            
        }
        
    }
    private static void ClearNodeAttrib(Node _currentNode)
    {
        try {
            if(_currentNode.getAttribute("ui.style")!=null)
            _currentNode.setAttribute("ui.style","");
        
            if(_currentNode.getAttribute("ui.color")!=null)
                _currentNode.setAttribute("ui.color","");

            if(_currentNode.getAttribute("ui.size")!=null)
                _currentNode.setAttribute("ui.size","");

            if(_currentNode.getAttribute("ui.class")!=null)
            _currentNode.setAttribute("ui.class","");

            if(_currentNode.getAttribute("ui.hide")!=null)
                _currentNode.removeAttribute("ui.hide");
        } catch (Exception e) {
        }
    }
    
    private static void ClearEdgeAttrib(Edge _currentEdge)
    {
        try {
            if(_currentEdge.getAttribute("ui.style")!=null)
            _currentEdge.setAttribute("ui.style","");
        
            if(_currentEdge.getAttribute("ui.color")!=null)
                _currentEdge.setAttribute("ui.color","");

            if(_currentEdge.getAttribute("ui.size")!=null)
                _currentEdge.setAttribute("ui.size","");

            if(_currentEdge.getAttribute("ui.class")!=null)
                _currentEdge.setAttribute("ui.class","");

            if(_currentEdge.getAttribute("ui.hide")!=null)
                _currentEdge.removeAttribute("ui.hide");
        } catch (Exception e) {
        }
    }
    
    public static void GraphLabelToggle(Graph graph, boolean _flagShow)
    {
        if(graph.getNodeCount()==0)
            return;
        for(Node node:graph)
        {
            if(_flagShow)
                node.addAttribute("ui.style", "text-visibility-mode: normal;");
            else
                node.addAttribute("ui.style", "text-visibility-mode: hidden;");
        }
    }
    public static void HightLightByNodeId(Graph graph, String _nodeId, boolean _showRelativeNode, boolean _flagShowOtherNode, boolean _flagIncoming, boolean _flagOutgoing)
    {
        if(graph.getNode(_nodeId)==null)
            return;
        try {
            for(Node node:graph) // Hide all other node and all Edges
            {
                String otherNodeStringId = node.getId();
                ShowHideNode(graph, otherNodeStringId, _flagShowOtherNode);
                Iterator<Edge> edgeIt = node.getEdgeIterator();
                    while (edgeIt.hasNext()) {
                        Edge currentEdge = edgeIt.next();
                        ShowHideEdge(graph, currentEdge, false); // hide every edge!
                    }
            }
            
            // Show captain node!
            if(graph.getNode(_nodeId).getAttribute("ui.hide")!=null)
                 graph.getNode(_nodeId).removeAttribute("ui.hide");
            
            // Process for ralative group
            Iterator<Edge> edgeList = graph.getNode(_nodeId).getEdgeIterator();
            while (edgeList.hasNext()) {
                Edge currentEdge = edgeList.next();
                
                if(currentEdge.getNode0().getId().equals(_nodeId)) // Node0 is captain => Outgoing Edge
                {
                    ShowHideNode(graph, currentEdge.getNode1().getId(), _showRelativeNode);
                    ShowHideEdge(graph, currentEdge, _flagOutgoing);
                }
                else // Incoming edge
                {
                    ShowHideNode(graph, currentEdge.getNode0().getId(), _showRelativeNode);
                    ShowHideEdge(graph, currentEdge, _flagIncoming);
                }
            }
        } catch (Exception e) {
        }
        
    }
    
    public static void ShowHideNode(Graph graph, String _nodeId, boolean _flag)
    {
        if(_flag)
        {
            if(graph.getNode(_nodeId).getAttribute("ui.hide")!=null)
                graph.getNode(_nodeId).removeAttribute("ui.hide");
        }
        else
        {
            graph.getNode(_nodeId).addAttribute("ui.hide");
        }
    }
    
    public static void ShowHideEdge(Graph graph, Edge _edge, boolean _flag)
    {
        if(_flag)
        {
            if(_edge.getAttribute("ui.hide")!=null)
                _edge.removeAttribute("ui.hide");
        }
        else
        {
            _edge.addAttribute("ui.hide");
        }
    }
    
}
