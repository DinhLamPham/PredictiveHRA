/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphStreamCoreHelper;

import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import GraphStreamCoreHelper.SetGraphAttribute;
import static Helper.Common.Variables.keySeparateInside;
import static Helper.Common.Variables.posOfAct;
import static Helper.Common.Variables.posOfPer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author phamdinhlam
 */
public class xesLogToGraphStream {
    public static Graph CreateGraphFromLog(Map<String, Integer> _nodeOccurenceMap, Map<String, Integer> _edgeWeightMap,
                                                        List<String> _inputList, Integer _posToView, Integer[] _flagView, boolean _skipSelfLoop)
    {
        Graph graph = new SingleGraph("My Graph"); 
//        _nodeOccurenceMap = new HashMap<>(); _edgeWeightMap = new HashMap<>(); 
        try {
            for(Integer i=0; i<_flagView.length; i++)
            {
                String[] currentTrace = _inputList.get(_flagView[i]).split(Variables.keyWordSeparate);
                
                for(int j=0; j<currentTrace.length; j++)
                {  //0: START; last-1: END
                    String currentNode = currentTrace[j].split(keySeparateInside)[_posToView];
                    String nextNode = "";
                    String currentEdge = "";
                    if (j<currentTrace.length - 1)  // END -> No edge more!
                    {
                        nextNode = currentTrace[j+1].split(keySeparateInside)[_posToView];
                        currentEdge = currentNode + Variables.keySeparateInside + nextNode;
                        if(currentNode.equals(nextNode) && _skipSelfLoop)
                            continue;
                    }
                    

                    // Insert Node
                    if (!_nodeOccurenceMap.containsKey(currentNode))
                    {
                        _nodeOccurenceMap.put(currentNode, 1);
                        graph.addNode(currentNode);
                    }
                    else
                        _nodeOccurenceMap.put(currentNode, _nodeOccurenceMap.get(currentNode) + 1);

                    // Insert Edge
                    if(j<currentTrace.length - 1)
                    {
                        if (!_edgeWeightMap.containsKey(currentEdge))
                            _edgeWeightMap.put(currentEdge, 1);
                        else
                            _edgeWeightMap.put(currentEdge, _edgeWeightMap.get(currentEdge) + 1);
                    }
                }
            }
            // Insert Edge to Graphstream
            SetGraphAttribute.InsertEdgeListToGraph(graph, _edgeWeightMap);
            
            // Add node label with occurence. Use function or not?
            SetGraphAttribute.SetNodeVirtualName(graph, _nodeOccurenceMap);
            
            
            
        } catch (Exception e) {MessageHelper.Error(e.toString());
        }
        return graph;
    }
}
