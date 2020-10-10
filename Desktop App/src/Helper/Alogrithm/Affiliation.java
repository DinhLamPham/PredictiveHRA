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
public class Affiliation {
    public static Map<String, Set<String>> CreateAffiliationMap(List<String> _logList, Integer _posOfLeft, Integer _posOfRight)
    {
        Map<String, Set<String>> affiliationMap = new HashMap<>();
        
        Set<String> startSet = new HashSet<>();
        startSet.add("START");
        affiliationMap.put("START", startSet);
        
        for(Integer i=0; i<_logList.size(); i++)
        {
            String[] currentTrace = _logList.get(i).split(Variables.keyWordSeparate);
            for(String event:currentTrace)
            {
                String leftNode = event.split(Variables.keySeparateInside)[_posOfLeft];
                String rightNode = event.split(Variables.keySeparateInside)[_posOfRight];
                 Set<String> currentRightSet = new HashSet<>();
                if(!affiliationMap.containsKey(leftNode))
                {
                    currentRightSet.add(rightNode);
                    affiliationMap.put(leftNode, currentRightSet);
                }
                else
                {
                   currentRightSet = affiliationMap.get(leftNode); currentRightSet.add(rightNode);
                   affiliationMap.put(leftNode, currentRightSet);
                }
            }
        }
        return affiliationMap;
    }
    
}
