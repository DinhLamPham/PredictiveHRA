/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNA;

import java.util.Iterator;
import java.util.Objects;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author phamdinhlam
 */
public class GenerateRandomGraph {
    
    public static Graph RandomGraph()
    {
            Graph g = new SingleGraph("Graph of Log");
             g.addNode("A");
             g.addNode("B");
             g.addNode("C");
             g.addNode("D");
             g.addNode("E");
             g.addNode("G");
             g.addNode("F");
             g.addNode("I");
             g.addNode("K");
             
             g.addNode("OrOpen1");
             g.addNode("OrClose1");
             g.addEdge("AAndOpen", "A", "OrOpen1", true);
             g.addEdge("AndOpen1B", "OrOpen1", "B",true);
             g.addEdge("AndOpen1C", "OrOpen1", "C",true);
//             g.addEdge("AE", "A", "E",true);
             g.addEdge("AF", "A", "F",true);
             g.addEdge("GI", "G", "I",true);
             g.addEdge("AG", "A", "G",true);
             g.addEdge("IK", "I", "K",true);
             
             g.addEdge("BD", "B", "D",true);
             g.addEdge("AD", "A", "D",true);
             g.addEdge("CE", "C", "E",true);
             
             g.addEdge("DAndClose1", "D", "OrClose1",true);
             g.addEdge("EAndClose1", "E", "OrClose1",true);
             
             g.addEdge("AndClose1F", "OrClose1", "F",true);
             
             for(Node node:g)
             {
                 node.setAttribute("ui.label", node.getId());
                 
                 Iterator<Edge> edgeIterator = node.getLeavingEdgeIterator();
                 while (edgeIterator.hasNext()) {
                     Edge next = edgeIterator.next();
                     next.setAttribute("ui.label", "2.0");
                 }
             }
        return g;
    }
    public static Graph simpleGraph()
    {
        Graph g = new SingleGraph("Graph of Log");
        g.addNode("A");
        g.addNode("B");
        g.addEdge("AB", "A", "B",true);
        g.getEdge("AB").setAttribute("ui.label", "ABCDEF");
        return g;
    }
    public static Graph BigGraph(Integer size)
    {
        Graph g = new SingleGraph("Graph of Log");
        g.setAttribute("ui.quality");
        g.setAttribute("ui.antialias");
        g.setAttribute("ui.stylesheet", "url('data/style.css')");
        g.display();
        
        
        
        for(Integer i=0;i<size;i++)
        {
            String nodeId = i.toString();
            g.addNode(nodeId);
        }
        for(Integer i=0;i<(size-1)/15;i++)
            for(Integer j=i+1;j<size; j++)
                if(!Objects.equals(i, j) && g.getEdge(i.toString()+j.toString())==null)
                {
                    g.addEdge(i.toString()+j.toString(), i.toString(), j.toString());
                    pause(200);
                }
        return g;
    }
    
    public static void pause(long millis) {
    try {
                Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
