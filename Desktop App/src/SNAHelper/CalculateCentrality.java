/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNAHelper;

import Helper.Common.MessageHelper;
import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.algorithm.measure.ClosenessCentrality;
import org.graphstream.algorithm.measure.DegreeCentrality;
import org.graphstream.graph.Graph;

/**
 *
 * @author phamdinhlam
 */
public class CalculateCentrality {
    
    
    public static boolean CalculateDegreeCentrality(Graph graph)
    {
        try {
            DegreeCentrality degreeCentrality = new DegreeCentrality();
            degreeCentrality.init(graph);
            degreeCentrality.compute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean CalculateClosenessCentrality(Graph graph)
    {
        try {
            ClosenessCentrality closenessC = new ClosenessCentrality();
            closenessC.init(graph);
            closenessC.compute();
            return true;
        } catch (Exception e) {
            System.err.println("Calculate Closeness centrality... Graph is not connected!");
            return false;
        }
       
    
    }
    
    public static boolean CalculateBetweennessCentrality(Graph graph)
    {
       BetweennessCentrality betweenAlgo = new BetweennessCentrality();
        try {
            betweenAlgo.init(graph);
            betweenAlgo.compute();
            return true;
        } catch (Exception e) {
            System.err.println("Calculate Betweenness centrality...Graph is not connected!");
            return false;
        }
    }
    
}
