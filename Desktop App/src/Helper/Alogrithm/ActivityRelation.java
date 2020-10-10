/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Alogrithm;

import Helper.Common.Variables;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author DinhLam Pham
 */
public class ActivityRelation {
    public static Map<String, Set<String>> CreateRelationMap(List<String> _logList)
    {
        Map<String, Set<String>> relationMap = new HashMap<>();
        for(Integer i=0; i<_logList.size(); i++)
        {
            String[] currentTrace = _logList.get(i).split(Variables.keyWordSeparate);
            for(Integer j=0; j<currentTrace.length-1; j++)
            {
                String leftNode = currentTrace[j].split(Variables.keySeparateInside)[Variables.posOfAct];
                String rightNode = currentTrace[j+1].split(Variables.keySeparateInside)[Variables.posOfAct];
                Set<String> currentRightSet = new HashSet<>();
                if(!relationMap.containsKey(leftNode))
                {
                    currentRightSet.add(rightNode);
                    relationMap.put(leftNode, currentRightSet);
                }
                else
                {
                   currentRightSet = relationMap.get(leftNode); currentRightSet.add(rightNode);
                   relationMap.put(leftNode, currentRightSet);
                }
            }
           
        }
        String key = "END"; Set<String> endSet = new HashSet<>(); endSet.add("END");
        relationMap.put(key, endSet);
        return relationMap;
    }
    
}
