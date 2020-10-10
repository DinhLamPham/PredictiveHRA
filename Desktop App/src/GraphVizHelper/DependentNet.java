/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphVizHelper;

import Helper.ICN.ICNBuilderV1;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phamdinhlam
 */
public class DependentNet {
    
   
    
    public static GenerateGraphViz GenerateDependentNetInGraphVizFormat(Map<String, Map<String,Integer>> dependentMap)
    {
        GenerateGraphViz gv = new GenerateGraphViz();
        gv.addln(gv.startUndirect_graph());
        gv.addln("rankdir = "+ Variables.rankdir + ";");
        gv.addln("graph [nodesep=" + Variables.nodesep + ", ranksep= "+ Variables.ranksep +"];");
        gv.addln("node [fixedsize=true];");
        gv.addln(gv.NodeFormatForGraph(Variables.nodeSizeString, Variables.nodeShape, Variables.nodeColor, Variables.nodeFillColor));
        
        // Format Node
        List<String> nodeList = new LinkedList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : dependentMap.entrySet()) 
        {
            String key = entry.getKey();
            Map<String, Integer> currentFatherMap = entry.getValue();
            String currentSon = key;
            String line="";
            if(!nodeList.contains(currentSon))
            {
                nodeList.add(currentSon);
                GenerateGraphViz.InsertNodeToGraphViz(gv, currentSon);
            }
            for (Map.Entry<String, Integer> entry1 : currentFatherMap.entrySet()) {
                String currentFather = entry1.getKey();
                if(!nodeList.contains(currentFather))
                {
                    nodeList.add(currentFather);
                    GenerateGraphViz.InsertNodeToGraphViz(gv, currentFather);
                }
            }
        }
        
        for (Map.Entry<String, Map<String, Integer>> entry : dependentMap.entrySet()) 
        {
            String key = entry.getKey();
            Map<String, Integer> currentFatherMap = entry.getValue();
            String currentSon = key;
            for (Map.Entry<String, Integer> entry1 : currentFatherMap.entrySet()) {
                String currentFather = entry1.getKey();
                // currentSon -- currentFather;
                String line = "\""+currentSon+"\"" + " -- " + "\""+currentFather+"\";";
                gv.addln(line);
            }
            
        }
        
         gv.addln(gv.end_graph());
        return  gv;
    }
}
