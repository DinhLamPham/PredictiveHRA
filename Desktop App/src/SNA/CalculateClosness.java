/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNA;

import static SNA.GenerateRandomGraph.pause;
import java.util.HashMap;
import java.util.Map;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.algorithm.PageRank;
import org.graphstream.algorithm.measure.ClosenessCentrality;
import org.graphstream.algorithm.measure.DegreeCentrality;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.networksimplex.DynamicOneToAllShortestPath;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author DinhLam Pham
 */
public class CalculateClosness {
    public static void main(String[] args) throws InterruptedException
    {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("test");
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.stylesheet", "url('style/style_shp.css')");
        graph.display();
        
        DynamicOneToAllShortestPath algorithm = new DynamicOneToAllShortestPath(null);
        algorithm.init(graph);
        algorithm.setSource("5");
        algorithm.setAnimationDelay(20); 
        
        
        // add some nodes and edges
        Generator generator = new DorogovtsevMendesGenerator();
        generator.addSink(graph);
        generator.begin();
        for (int i = 1; i <= 10; i++) {
                Integer id = i;
                generator.nextEvents();
                algorithm.compute();
        }
        
       ClosenessCentrality closenessC = new ClosenessCentrality();
       closenessC.init(graph);
       closenessC.compute();
       
       
       Map<String, Float> CCHashMapMap = new HashMap<>();
        for (Node thisNode:graph) {
                
                thisNode.addAttribute("ui.label", thisNode.getId());
                CCHashMapMap.put(thisNode.getId(), thisNode.getAttribute("closeness"));

        }
        printMap(CCHashMapMap);
       
       System.out.println("Finished!");
        
        
    }
    
    public static <K, V> void printMap(Map<K, V> map) throws InterruptedException {
        Thread.sleep(1000);
        System.err.println("-----------------------------");
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.print("Key : " + entry.getKey()
                    + " Value : " + entry.getValue() +"; ");
        }
        System.err.println("-----------------------------");
    }
}
