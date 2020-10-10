/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Common;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author phamdinhlam
 */
public class SetHelper {
    public static Set<String> MembersToSetString(String _members)
    {
        Set<String> outputSet = new HashSet<>();
        String[] memberArray = _members.split(Variables.keySeparateInside);
        for(Integer i=0; i<memberArray.length; i++)
            outputSet.add(memberArray[i]);
        
        return outputSet;
    }
    
    public static Set<Integer> MembersToSetInteger(String _members)
    {
        Set<Integer> outputSet = new HashSet<>();
        String[] memberArray = _members.split(Variables.keySeparateInside);
        for(Integer i=0; i<memberArray.length; i++)
            try {
                outputSet.add(Integer.parseInt(memberArray[i]));
            } catch (Exception e) {
            }
        
        return outputSet;
    }
}
