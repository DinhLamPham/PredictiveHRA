/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadController;

import FormTemplate.SNAFrame;
import Helper.Common.Variables;
import static Helper.Common.Variables.*;
import SNAHelper.CalculateCentrality;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JComboBox;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author phamdinhlam
 */
public class CalculateGraphCentrality implements Runnable{
    public static boolean flagDegree, flagCloseness, flagBetweenness = false;
    

    @Override
    public void run() {
//        try {
//            globalGraph.removeNode("START");globalGraph.removeNode("END");
//        } catch (Exception e) {
//        }
//        
        
        
        flagDegree = false; flagCloseness = false; flagBetweenness = false;
        if(CalculateCentrality.CalculateDegreeCentrality(globalGraph))
            flagDegree = true;
        if(CalculateCentrality.CalculateClosenessCentrality(Variables.globalGraph))
            flagCloseness = true;
        if(CalculateCentrality.CalculateBetweennessCentrality(Variables.globalGraph))
            flagBetweenness = true;
        
        InitCombobox(SNAFrame.cmbCentralityType);
        
        degreeRange = new double[] {100,-100};
        closenessRange = new double[] {100,-100};
        betweennessRange = new double[] {100,-100};
        
        
        for(Node currentNode:globalGraph)
        {
            if(flagDegree)
            {
                Object objDegree = currentNode.getAttribute("degree");
                double degreeVal = 0.0;
                if(Helper.Common.ToolsHelper.isStringDouble(objDegree.toString()))
                    degreeVal =  Double.parseDouble(objDegree.toString());
                if(degreeVal<degreeRange[0])
                    degreeRange[0] = degreeVal;
                if(degreeVal>degreeRange[1])
                    degreeRange[1] = degreeVal;
            }
            
            if(flagCloseness)
            {
                Object objCloseness = currentNode.getAttribute("closeness");
                double closenessVal = 0.0;
                if(Helper.Common.ToolsHelper.isStringDouble(objCloseness.toString()))
                    closenessVal =  Double.parseDouble(objCloseness.toString());
                if(closenessVal<closenessRange[0])
                    closenessRange[0] = closenessVal;
                if(closenessVal>closenessRange[1])
                    closenessRange[1] = closenessVal;
            }
            
            
            if(flagBetweenness)
            {
                Object objBetweenness = currentNode.getAttribute("Cb");
                double betweenessVal = 0.0;
                if(Helper.Common.ToolsHelper.isStringDouble(objBetweenness.toString()))
                    betweenessVal =  Double.parseDouble(objBetweenness.toString());
                if(betweenessVal<betweennessRange[0])
                    betweennessRange[0] = betweenessVal;
                if(betweenessVal>betweennessRange[1])
                    betweennessRange[1] = betweenessVal;
            }
            
        }
        
    }
    public static void InitCombobox(JComboBox _combobox)
    {
        _combobox.removeAllItems();
        _combobox.addItem("Normal");
        if(flagDegree)
            _combobox.addItem("Degree centrality");
        if(flagCloseness)
            _combobox.addItem("Closeness centrality");
        if(flagBetweenness)
            _combobox.addItem("Betweenness centrality");
    }
    
    
    
}
