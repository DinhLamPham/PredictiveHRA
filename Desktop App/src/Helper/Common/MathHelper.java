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
public class MathHelper {
    
    public static double CubeCalculator(double a)
    {
        double result=0;
        result=a*a*a;
        return result;
    }
    public static Integer DoubleToInteger (Double a)
    {
       return (Integer) a.intValue();
    }
    public static Integer MinValue(Integer a, Integer b)
    {
        if (a<b)
            return a;
        return b;
    }
    public static Integer MaxValue(Integer a, Integer b)
    {
        if (a>b)
            return a;
        return b;
    }
    
    public static Set<Integer> SetPlus(Set<Integer> set1, Set<Integer> set2)
    {
        for(Integer member: set2)
            set1.add(member);
        return set1;
    }
    //SetIntersection
    public static Set<Integer> SetIntersection(Set<Integer> set1, Set<Integer> set2)
    {
        Set<Integer> result = new HashSet<>();
        for(Integer member: set2)
            if(set1.contains(member))
                result.add(member);
                
        return result;
    }
}
