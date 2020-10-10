/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Common;

import java.awt.Color;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JComboBox;

/**
 *
 * @author phamdinhlam
 */
public class ToolsHelper {
    public static boolean isStringInteger(String number)
    {
        try{
            Integer.parseInt(number);
        }catch(Exception e ){
            return false;
        }
        return true;
    }
    
    public static boolean isStringDouble(String number)
    {
        try{
            Double.parseDouble(number);
        }catch(Exception e ){
            return false;
        }
        return true;
    }
    
    
    
    
    public final static String ColortoHexString(Color colour) throws NullPointerException {
        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
        if (hexColour.length() < 6) {
          hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
        }
        return "#" + hexColour;
      }
    
    
    public final static String toHexString(Color colour) throws NullPointerException {
        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
        if (hexColour.length() < 6) {
          hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
        }
        return "#" + hexColour;
      }
    
    public static void InitComboFromSet(JComboBox _combobox, Set<String> _inputSet)
    {
        _combobox.removeAllItems();
        for (Iterator<String> iterator = _inputSet.iterator(); iterator.hasNext();) {
            String next = iterator.next();
            _combobox.addItem(next);
        }
    }
    
    public static double ScaleValTo0_100Range(double _currentVal, double _minVal, double _maxVal)
    {
        return 100*(_currentVal - _minVal)/(_maxVal-_minVal);
    }
    
    public static double ScaleValTo0_255Range(double _currentVal, double _minVal, double _maxVal)
    {
        return 255*(_currentVal - _minVal)/(_maxVal-_minVal);
    }
    
    
    public static Integer ChooseNodeLevel(double _percentage)
    {
        if(_percentage>=0 && _percentage<10)
            return 1;
        if(_percentage>=10 && _percentage<20)
            return 2;
        if(_percentage>=20 && _percentage<30)
            return 3;
        if(_percentage>=30 && _percentage<40)
            return 4;
        if(_percentage>=40 && _percentage<50)
            return 5;
        
        if(_percentage>=50 && _percentage<60)
            return 6;
        if(_percentage>=60 && _percentage<70)
            return 7;
        if(_percentage>=70 && _percentage<80)
            return 8;
        if(_percentage>=80 && _percentage<90)
            return 9;
        if(_percentage>=90 && _percentage<100)
            return 10;
       
        
        return 10;
    }
    
    
}
