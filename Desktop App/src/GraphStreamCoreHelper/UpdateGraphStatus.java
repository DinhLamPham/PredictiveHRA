/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphStreamCoreHelper;

import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author Administrator
 */
public class UpdateGraphStatus {

    /**
     * Refresh GraphStream with the state of devices
     * @param _graph
     * @param _myDeviceList
     */
//    public static void UpdateGraphNodeBaseOnDeviceStatus(Graph _graph, List<myDeviceStatus> _myDeviceList)
    {
//        Integer listSize = _myDeviceList.size();
//        for(Integer i=0; i<listSize; i++)
//        {
//            myDeviceStatus currentDeviceStatus = _myDeviceList.get(i);
//            String deviceName = currentDeviceStatus.GetName();
//            String deviceValue = currentDeviceStatus.GetValue();
//             UpdateGraphNodeWithState(_graph, deviceName, deviceValue);
//        }
    }
    
    /**
     * Update the Color of Node in Graph based on the value of devices.
     * @param _graph
     * @param _deviceName
     * @param _deviceValue
     */
    public static void UpdateGraphNodeWithState(Graph _graph, String _deviceName, String _deviceValue)
    {
//        Node currentNode = _graph.getNode(_deviceName);
//        if(currentNode ==null)  /// Insert Node, Create Edge
//        {
//            String sonNode = _deviceName;
//            _graph.addNode(sonNode);
//            List<myNode_Son> myNodeSonList  = Variables.myGraphVariable.GetMyFatherAndSonList();
//            String father = myGraphHelper.FindFatherOfSon(myNodeSonList, sonNode);
//            if(_graph.getNode(father)!=null) // Create Edge
//                _graph.addEdge(sonNode+Variables.keyConnectNode+father, sonNode, father, true);
//            else
//                 MessageHelper.Warning("Cannot find category of this node: " + _deviceName + " please check the data", "Logic error");
//        }
//        
//         if(_deviceValue.equals("null"))  // => ColorOffWorkValue
//                GraphStreamHelper.SetGraphAttribute.ChangeNodeColour(_graph, _deviceName, Variables.nodeOffWorkColor);
//        else // => ColorWorkingValue
//                GraphStreamHelper.SetGraphAttribute.ChangeNodeColour(_graph, _deviceName, Variables.nodeWorkingColor);
    }
    
}
