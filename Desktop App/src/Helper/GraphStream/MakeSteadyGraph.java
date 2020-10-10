/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.GraphStream;

import Helper.Common.Variables;
import java.util.Collection;
import java.util.Iterator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author phamdinhlam
 */
public class MakeSteadyGraph {
    public static Graph MakeGraph(Graph inputGraph)
    {
        Graph resultGraph = new SingleGraph("Steady Graph");
        for(Node node:inputGraph)
        {
            resultGraph.addNode(node.getId());
            
            // Copy attributes
            Iterator<String> attributeIterable = node.getAttributeKeySet().iterator();
            while (attributeIterable.hasNext()) {
                String currentAttribute = attributeIterable.next();
                String currentAttribVal = node.getAttribute(currentAttribute);
                resultGraph.getNode(node.getId()).addAttribute(currentAttribute, currentAttribVal);
            }
        }
        
        // Iterate through edge set. Only copy one direction Edge!
        Iterator<Edge> edgeIter = inputGraph.getEdgeIterator();
        while (edgeIter.hasNext()) {
            Edge currentEdge = edgeIter.next();
            Edge reversEdge = inputGraph.getEdge(currentEdge.getNode1().getId() + Variables.keySeparateInside + currentEdge.getNode0().getId());
            if( reversEdge== null)
            {
                resultGraph.addEdge(currentEdge.getId(), currentEdge.getNode0().getId(), currentEdge.getNode1().getId(), true); //Copy Edge
                
                // Copy attributes
                Iterator<String> currentEdgeAttrib = currentEdge.getAttributeKeySet().iterator();
                while (currentEdgeAttrib.hasNext()) {
                    String currentAttrib = currentEdgeAttrib.next();
                    String currentAttribVal = currentEdge.getAttribute(currentAttrib);
                    resultGraph.getEdge(currentEdge.getId()).addAttribute(currentAttrib, currentAttribVal);
                    
                }
                
                
            }
            
        }
        return resultGraph;
    }
    public static void RecoveryEssentialEdge(Graph steadyGraph, Graph inputGraph)
    {
        for(Node node:steadyGraph)
        {
            if(node.getInDegree()==0 || node.getOutDegree()==0) // Only
            {
                Iterator<Edge> edgeCollection = inputGraph.getNode(node.getId()).getEdgeSet().iterator();
                while (edgeCollection.hasNext()) {
                    Edge currentEdge = edgeCollection.next();
                    if(steadyGraph.getEdge(currentEdge.getId()) == null && !currentEdge.getNode0().getId().equals(currentEdge.getNode1().getId()))
                    {
                        steadyGraph.addEdge(currentEdge.getId(), currentEdge.getNode0().getId(), currentEdge.getNode1().getId(), true);
                        
                        // Copy attributes
                        Iterator<String> currentEdgeAttrib = currentEdge.getAttributeKeySet().iterator();
                        while (currentEdgeAttrib.hasNext()) {
                            String currentAttrib = currentEdgeAttrib.next();
                            String currentAttribVal = currentEdge.getAttribute(currentAttrib);
                            steadyGraph.getEdge(currentEdge.getId()).addAttribute(currentAttrib, currentAttribVal);
                            steadyGraph.getEdge(currentEdge.getId()).addAttribute("ui.class", "redEdge"); 
                            
                        }
                        
                    }
                    
                }
            }
        }
    }
}
