/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphStreamCoreHelper;

import Helper.Common.MathHelper;
import Helper.Common.MessageHelper;
import Helper.Common.ToolsHelper;
import static Helper.Common.ToolsHelper.isStringInteger;
import Helper.Common.Variables;
//import Helper.Common.ExcelHelper;
import com.google.common.collect.HashBiMap;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import static Helper.Common.Variables.*;

/**
 *
 * @author phamdinhlam
 */
public class SetGraphAttribute {
    static String[] arrayColour = {};
    
    static Map<String,String> globalEdge = new HashMap<>();
    public static Map<String,String> WTNEdgeWithActivities = new HashMap<>();
    static Map<String,Integer> mapNodeWithNumberofFromFather = new HashMap<>();
    static Map<String,Integer> mapNodeWithNumberOfToSon = new HashMap<>();
    static Map<String,String> mapStoreCurrentDisplayNode = new HashMap<>();
    static String[] arrayColor = {"#8cd8cd", "#a8a31c", "#d62308"};
    
    
    public static void SetGraphDefaultAttribute(Graph graph, String _styleFile)
    {
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        if(_styleFile!=null)
            graph.setAttribute("ui.stylesheet", "url('style/" + _styleFile + "')");
    }
    
    public static void HighlightNode(Graph graph, String nodeId, String newColor)
    {
        
        Node node = graph.getNode(nodeId);
        if(node==null)
            return;
        String currentAttribute = node.getAttribute("ui.style");
        Integer left = currentAttribute.indexOf("#");
        String currentColor="";
        try {
            if(currentAttribute.length()>left+7)
            currentColor= currentAttribute.substring(left, left+7);
        } catch (Exception e) {
            System.out.println(currentAttribute);
        }
        
        
        if(newColor.equals(currentColor))
            newColor = arrayColor[0];
        
        ChangeNodeColour(graph, nodeId, newColor);
        
    }
    
    public static void SetNewEdgeValue(Graph graph,  String nodeA, String nodeB, Map<String, Integer> listOfVertexWithOccurents)
    {
        // Phai xu ly them truong hop: Khi ca hai NodeA va NodeB deu la Gate => Can can cu vao gia tri den tu father of A
        // Dang tam thoi de gia tri sai.
        // Se xu ly sau.
        
        if(graph.getEdge(nodeA+keySeparateInside+nodeB)==null)
        {
//            System.err.println("Edge "+ nodeA + keySeparateInside+nodeB +  " doesn't exist! Check the algorithm. (SetGraphAttribute.java)");
            return;
        }
        Edge edge = graph.getEdge(nodeA+keySeparateInside+nodeB);
        Integer newValueInteger= listOfVertexWithOccurents.get(nodeA)-GetGraphAttribute.GetSelfLoopValue(graph, nodeA);
        String newValueString = newValueInteger.toString();
        
        edge.setAttribute("ui.label", newValueString);
        
//        System.out.println(nodeA+keySeparateInside+nodeB+": " + newValueString);
        
    }
 
    public static void SetEdgeThickness(Graph graph, double yMin, double yMax)
    {
        Integer xMin=1,xMax=0;
        double yThick=yMax-yMin;
        Collection<Edge> edges = graph.getEdgeSet();    
        for(Edge edge:edges)
        {
            String currentWeightString = edge.getAttribute("ui.label");
            Integer currentWeightInteger = 0;
            if(ToolsHelper.isStringInteger(currentWeightString))
                currentWeightInteger = Integer.parseInt(currentWeightString);
            if(xMax<currentWeightInteger)
                xMax=currentWeightInteger;
            if(xMin>currentWeightInteger)
                xMin=currentWeightInteger;
        }
        Integer xThick = xMax-xMin;
        edges = graph.getEdgeSet();   
        
        for(Edge edge:edges)
        {
            String currentWeightString = edge.getAttribute("ui.label");
            Integer x=0;
            double y=0;
            
            Integer currentWeightInteger = 0;
            if(ToolsHelper.isStringInteger(currentWeightString))
                currentWeightInteger = Integer.parseInt(currentWeightString);
            x=currentWeightInteger;
            
            double temp;
            temp = (double) ((double)(x-xMin)/(double)xThick);
            temp=MathHelper.CubeCalculator(temp);
//            currentEdge.addAttribute("ui.style", "text-alignment: along; size:"+MainProgram.StartForm.cmbEdgeSize.getSelectedItem() + ";" + styleOffsetForAtoB+"fill-color: blue; text-color: blue;");
            y=yMin+yThick*temp;
            if (!CheckSelfLoopEdge(edge))
            {
                String node0Id=edge.getNode0().getId();
                String node1Id=edge.getNode1().getId(); 
                Edge reversEdge = graph.getEdge(node1Id+keySeparateInside+node0Id);
                if(reversEdge==null) // Chi co mot chieu                    
                    edge.addAttribute("ui.style",  "text-alignment: along; size:" + Double.toString(y) + "px;" + styleOffsetForAtoB + "fill-color: blue; text-color: blue;" );
                else  // Co hai chieu
                {
                    String bToAOffset = edge.getAttribute("ui.style");
                    if (bToAOffset.toUpperCase().contains("BLUE"))  // BtoA: blue ===> AtoB: Orange
                        edge.addAttribute("ui.style",  "text-alignment: along; size:" + Double.toString(y) + "px;" + styleOffsetForBtoA + "fill-color: orange; text-color: orange;" );
                    else // BtoA: != Blue => AtoB: Blue
                        edge.addAttribute("ui.style",  "text-alignment: along; size:" + Double.toString(y) + "px;" + styleOffsetForAtoB + "fill-color: blue; text-color: blue;" );
                        
                }
                    
            }
            else
                edge.addAttribute("ui.style", "text-alignment: justify; size:" + Double.toString(y) + "px;"+ "fill-color: blue; text-color: blue;" + styleOffsetForSelfLoop);
            
        }
        
    }
    private static boolean CheckSelfLoopEdge(Edge edge)
    {
        return (edge.getNode0().getId().equals(edge.getNode1().getId()));
    }
    
    
    public static Graph SetDefaultGraphAttribute(Graph graph, String nodeType, String startEndType,String nodeColour, Integer nodeSize, boolean showAvatar, boolean showNodeID)
    {
        Integer imageName =-1;
        for (Node node:graph){
            String tmpType ="";
            tmpType = "shape: "+nodeType+";";
            String currentID= node.getId();
            if(currentID.equals("START") || currentID.equals("END") )
            {
                    Node e1=graph.getNode("START");
                    if(e1!=null)
                    {
                        e1.addAttribute("ui.label", e1.getId());
                        tmpType = "shape: "+startEndType+";";
                        e1=graph.getNode("END");
                        e1.addAttribute("ui.label", e1.getId());
                        tmpType = "shape: "+startEndType+";";
                    }
            }
            imageName++;  
            String tmpSize="";
            Double width=0.0;
            if (nodeType.equals("box") || nodeType.equals("rounded-box"))
            {
                width = nodeSize + nodeSize*0.5;
                tmpSize = "size: "+width.toString()+"px, "+nodeSize.toString()+"px;";
            }
            else
                tmpSize = "size: "+nodeSize.toString()+"px, "+nodeSize.toString()+"px;";
            String tmpAvatar = "";
            String tmpColour="";
            tmpColour = "fill-color: "+nodeColour+";";
                
            if(showAvatar && !currentID.equals("START") && !currentID.equals("END"))
                tmpAvatar= "fill-mode: image-scaled; fill-image: url('.//css/images/avatar"+imageName+".png');";
            if(showNodeID)
                node.addAttribute("ui.label", node.getId()); // Display the node's label
            
            node.addAttribute("ui.style",tmpSize+tmpColour + tmpType+tmpAvatar );
        }
        return graph;
    }
    public static void ChangeNodeColour(Graph g, String id, String colorHexCode)
    {
        Node nodeID= g.getNode(id);
        if(nodeID!=null)
        {
            String tmpColor="fill-color: "+colorHexCode+";";
            nodeID.setAttribute("ui.style", tmpColor);
        }
        
    }
    public static String GetNodeColor(Graph g, String id)
    {
        Node nodeID= g.getNode(id);
        if(nodeID!=null)
        {
            String tmpColor= nodeID.getAttribute("ui.style");
            return tmpColor;
        }
        return "";
    }
    
    public static void ChangeNodeSize(Graph g, String id, Integer size)
    {
        Node nodeID= g.getNode(id);
        if (nodeID!=null)
        {
            String tmpSize = "size: "+size.toString()+"px, "+size.toString()+"px;";
            nodeID.setAttribute("ui.style", tmpSize);
        }
       
    }
    public static void ChangeNodeType(Graph g, String id, Integer size)
    {
        Node nodeID= g.getNode(id);
        if (nodeID!=null)
        {
            String tmpType = "size: "+size.toString()+"px, "+size.toString()+"px;";
            nodeID.setAttribute("ui.style", tmpType);
        }
       
    }
    
    public static void SetNodeAttrib(Graph g, String id, Integer nodeSize, String colorHexCode, String nodeType)
    {
        Node node= g.getNode(id);
        String nodeName= node.getId();
        
       
       // if(ICNBuilderV1.CheckNodeIsGate(nodeName))
       //     nodeName=GetVirtualNameOfGate(nodeName);
       
            
        node.addAttribute("ui.label", nodeName);
        
        String tmpType ="";
            tmpType = "shape: "+nodeType+";";
            
        String tmpSize="";
        Double width=0.0;
        if (nodeType.equals("box") || nodeType.equals("rounded-box"))
        {
            width = nodeSize + nodeSize*0.5;
            tmpSize = "size: "+width.toString()+"px, "+nodeSize.toString()+"px;";
        }
        else
            tmpSize = "size: "+nodeSize.toString()+"px, "+nodeSize.toString()+"px;";
        
        String tmpColour="";
            tmpColour = "fill-color: "+colorHexCode+";";
            
        node.addAttribute("ui.style",tmpSize+tmpColour + tmpType );
       
    }
    
    public static void SetNodeAttribute(Graph g, String id, Integer nodeSize, String colorHexCode, String outlineColorHexCode, String nodeType, String nodeImage)
    {
        try {
            Node node= g.getNode(id);
        String nodeName= node.getId();
        
        node.addAttribute("ui.label", nodeName);
        
        String tmpType ="";
            tmpType = "shape: "+nodeType+"; text-alignment: under; ";
            
        String tmpSize="";
        Double width=0.0;
        if (nodeType.equals("box") || nodeType.equals("rounded-box"))
        {
            width = nodeSize + nodeSize*0.5;
            tmpSize = "size: "+width.toString()+"px, "+nodeSize.toString()+"px;";
        }
        else
            tmpSize = "size: "+nodeSize.toString()+"px, "+nodeSize.toString()+"px;";
        
        String tmpColour="";
            tmpColour = "fill-color: "+colorHexCode+"; stroke-color: " + outlineColorHexCode + "; stroke-mode: plain; " ;
        
            String nodeImageSource ="";
        if (!nodeImage.equals(""))    
            nodeImageSource ="fill-mode: image-scaled; fill-image: url('"+ nodeImage + "'); ";
            
        node.addAttribute("ui.style",tmpSize+tmpColour + tmpType + nodeImageSource );
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
       
    }
    
    public static void SetNodeAttributeWithLabel(Graph g, String id, String label, Integer nodeSize, String colorHexCode, String outlineColorHexCode, String nodeType)
    {
        try {
            Node node= g.getNode(id);
            if(node==null)
            {
                g.addNode(id);
                node = g.getNode(id);
            }
        String nodeName= label;
        
        node.addAttribute("ui.label", nodeName);
        
        String tmpType ="";
            tmpType = "shape: "+nodeType+"; text-alignment: under; ";
            
        String tmpSize="";
        Double width=0.0;
        if (nodeType.equals("box") || nodeType.equals("rounded-box"))
        {
            width = nodeSize + nodeSize*0.5;
            tmpSize = "size: "+width.toString()+"px, "+nodeSize.toString()+"px;";
        }
        else
            tmpSize = "size: "+nodeSize.toString()+"px, "+nodeSize.toString()+"px;";
        
        String tmpColour="";
            tmpColour = "fill-color: "+colorHexCode+"; stroke-color: " + outlineColorHexCode + "; stroke-mode: plain; " ;
        
            String nodeImageSource ="";
       
            
        node.addAttribute("ui.style",tmpSize+tmpColour + tmpType );
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
       
    }
    
    public static void SetNodeAttributeTextInside(Graph g, String id, Integer nodeSize, String colorHexCode, String outlineColorHexCode, String nodeType, String nodeImage)
    {
        try {
            Node node= g.getNode(id);
        String nodeName= node.getId();
        
        node.addAttribute("ui.label", nodeName);
        
        String tmpType ="";
            tmpType = "shape: "+nodeType+"; text-alignment: center; ";
            
        String tmpSize="";
        Double width=0.0;
        if (nodeType.equals("box") || nodeType.equals("rounded-box"))
        {
            width = nodeSize + nodeSize*0.5;
            tmpSize = "size: "+width.toString()+"px, "+nodeSize.toString()+"px;";
        }
        else
            tmpSize = "size: "+nodeSize.toString()+"px, "+nodeSize.toString()+"px;";
        
        String tmpColour="";
            tmpColour = "fill-color: "+colorHexCode+"; stroke-color: " + outlineColorHexCode + "; stroke-mode: plain; " ;
        
            String nodeImageSource ="";
        if (!nodeImage.equals(""))    
            nodeImageSource ="fill-mode: image-scaled; fill-image: url('"+ nodeImage + "'); ";
            
        node.addAttribute("ui.style",tmpSize+tmpColour + tmpType + nodeImageSource );
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
       
    }
    
    public static void RemoveNode(Graph g, String id)
    {
         Node nodeID= g.getNode(id);
         if(nodeID!=null)
         {
             g.removeNode(id);
         }
    }
    
    
    public static void RemoveEdge(Graph g, String fromId, String toId)
    {
        if(g.getEdge(fromId+keySeparateInside+toId)!=null)
            g.removeEdge(fromId, toId);
        
    }
    public static void PrintEdgeValue(Graph g, String fromId, String toId)
    {
        if(g.getEdge(fromId+keySeparateInside+toId)!=null)
        {
            String edgeValue = g.getEdge(fromId+keySeparateInside+toId).getAttribute("ui.label");
            System.out.println(fromId+keySeparateInside+toId+": "+edgeValue);
        }
        
    }
    public static void AddEdge(Graph g, String fromId, String toId, boolean directed)
    {
        g.addEdge(fromId+keySeparateInside+toId, fromId, toId,directed);
        
    }
    public static boolean CheckEdgeExist(Graph g, String fromId, String toId)
    {
        if (g.getEdge(fromId+keySeparateInside+toId)==null)
            return false;
        return true;
    }
    public static String GetEdgeLabel(Graph g, String fromId, String toId)
    {
        Edge edge=g.getEdge(fromId+keySeparateInside+toId);
        if(edge==null)
            return "NULL";
        
        return edge.getAttribute("ui.label");
    }
    
    public static Integer GetEdgeWeight(Graph g, String fromId, String toId)
    {
        Integer result =0;
        Edge edge=g.getEdge(fromId+keySeparateInside+toId);
        if(edge==null)
            return 0;
        String weightString = edge.getAttribute("ui.label");
        if(!isStringInteger(weightString))
            {System.err.println("Edge weight is not an Integer value. " + fromId + " -->" + toId);}
        else
            result = Integer.parseInt(weightString);
        return result;
    }
    public static void UpdateEdgeOffset(Graph g, String  fromId, String toId, String offset)
    {
        Edge edge=g.getEdge(fromId+keySeparateInside+toId);
        if(edge==null)
            return;
         edge.addAttribute("ui.style", "text-alignment: justify; "  + offset); 
        
    }
    public static void UpdateEdgeWeight(Graph g, String fromId, String toId, Integer newValueInteger)
    {
        try {
            Edge edge=g.getEdge(fromId+keySeparateInside+toId);
            edge.setAttribute("ui.label", newValueInteger.toString());
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
    }
    
    public static void SetEdgeLabel(Graph g, String _edgeId, String _label)
    {
        try {
            Edge edge=g.getEdge(_edgeId);
            edge.setAttribute("ui.label", _label);
            
        } catch (Exception e) {
            MessageHelper.Error(e.toString());
        }
    }
    
    
    public static void ChangeEdgeColour(Graph g, String fromId, String toId, String colorHexCode)
    {
            Edge edge = g.getEdge(fromId+keySeparateInside+toId);
            String tmpColor="fill-color: "+colorHexCode+";";
            edge.setAttribute("ui.style", tmpColor);
    }
    public static void ChangeAllEdgeFormat(Graph g, String colorHexCode)
    {
        Collection<Edge> edges = g.getEdgeSet();    
        String tmpColor="fill-color: "+colorHexCode+"; ";
        String _offset = "";
        for(Edge edge:edges)
        {
            if(edge.getNode0() == edge.getNode1())
                _offset = Variables.styleOffsetForSelfLoop;
            else
                _offset = "";
            edge.addAttribute("ui.style", tmpColor + "text-alignment: justify; " + _offset);
        }
            
    }
    
    public static void InsertEdgeListToGraph(Graph graph, Map<String, Integer> _edgeWeightMap)
    {
        for (Map.Entry<String, Integer> entry : _edgeWeightMap.entrySet()) 
        {
            String _edgeID = entry.getKey();
            Integer value = entry.getValue();
            graph.addEdge(_edgeID, _edgeID.split(keySeparateInside)[0], _edgeID.split(keySeparateInside)[1], true);
            SetGraphAttribute.SetEdgeLabel(graph, _edgeID, String.valueOf(value));
        }
    }
    
    public static void SetEdgeAttribute(Graph g, String _edgeId, double _edgeSize, String _edgeColorHexCode)
    {
        Edge edge = g.getEdge(_edgeId); 
        if(edge == null)
            return;
        String tmpSize = "size: "+Double.toString(_edgeSize)+"px; ";
        String tmpColor="fill-color: "+_edgeColorHexCode+"; ";
        edge.setAttribute("ui.style", tmpSize+tmpColor);
    }
    
    public static void ChangeEdgeSize(Graph g, String fromId, String toId, double size)
    {
        Edge edge = g.getEdge(fromId+keySeparateInside+toId); 
        String tmpSize = "size: "+Double.toString(size)+"px;";
        edge.setAttribute("ui.style", tmpSize);
    }
    public static void ChangeEdgeType(Graph g, String fromId, String toId, String type)
    {
        Edge edge = g.getEdge(fromId+keySeparateInside+toId);
        String tmpShape = "shape: " + type;
        edge.setAttribute("ui.style", tmpShape);
    }
    
    public static void SwitchNodeLabel(Graph g, String id, boolean turnOn)
    {
        Node nodeId = g.getNode(id);
        if(nodeId!=null)
        {
            if(turnOn)
                nodeId.setAttribute("ui.label", id);
            else
                nodeId.setAttribute("ui.label", "");
        }
    }
    
    public static void SetNodeVirtualName(Graph g, Map<String, Integer> _nodeOccurenceMap)
    {
        for (Map.Entry<String, Integer> entry : _nodeOccurenceMap.entrySet()) {
                String nodeName = entry.getKey();
                Integer value = entry.getValue();
                
                Node nodeId = g.getNode(nodeName);
                nodeId.setAttribute("ui.label", nodeName + ":" + String.valueOf(value));
            }
    }
    
    public static void ShowNodeId(Graph g)
    {
        for(Node node:g)
        {
            String currentId=node.getId();
            String virtualName=currentId;
            node.setAttribute("ui.label", virtualName);
            
        }
    }
    public static void ChangeNodeIdName(Graph g, String oldId, String newId)            
    {
        try 
        {
            Node tempNewNode = g.getNode(newId);
            
            
            if (tempNewNode!=null)
            {
                MessageHelper.Info("Sorry, ID" + newId + " alread exist!");
                return;
            }
            Node node = g.getNode(oldId);
            if(node==null)
            {
                MessageHelper.Info("Please check ID" + oldId + " is not exist!");
                return;
            }


                Node oldNode = g.getNode(oldId);
                String attribute = oldNode.getAttribute("ui.style");
                g.addNode(newId);
                Node newNode = g.getNode(newId);
                newNode.addAttribute("ui.style", attribute);

                //String virtualName = GetVirtualNameOfGate(newId);
                String virtualName = newId;
                newNode.addAttribute("ui.label", virtualName);

                Iterator<Edge> edgeLeavingIterator = oldNode.getLeavingEdgeIterator();
                // Process for list Of Son
                while (edgeLeavingIterator.hasNext()) {
                    Edge thisEdge = edgeLeavingIterator.next();
                    String currentSonId = thisEdge.getNode1().getId();
                    String currentSonEdgeAttribute = thisEdge.getAttribute("ui.style");
                    String currentSonEdgeLabel = thisEdge.getAttribute("ui.label");

                    g.addEdge(newId+keySeparateInside+currentSonId, newId, currentSonId, true);
                    Edge tempEdge = g.getEdge(newId+keySeparateInside+currentSonId);
                    tempEdge.addAttribute("ui.label", currentSonEdgeLabel);
        //            tempEdge.addAttribute("ui.style", currentSonEdgeAttribute);
                }


                Iterator<Edge> edgeEnteringIterator = oldNode.getEnteringEdgeIterator();
                // Process for list Of Son
                while (edgeEnteringIterator.hasNext()) {
                    Edge thisEdge = edgeEnteringIterator.next();
                    String currentFatherId = thisEdge.getNode0().getId();
                    String currentFatherEdgeAttribute = thisEdge.getAttribute("ui.style");
                    String currentFatherEdgeLabel = thisEdge.getAttribute("ui.label");

                    g.addEdge(currentFatherId+keySeparateInside+newId, currentFatherId, newId, true);

                    Edge tempEdge = g.getEdge(currentFatherId+keySeparateInside+newId);
                    tempEdge.setAttribute("ui.label", currentFatherEdgeLabel);
        //            tempEdge.setAttribute("ui.style", currentFatherEdgeAttribute);
            }
        
        } catch (Exception e) {
            System.out.println("-----------------error---------------");
            System.out.println(e.toString());
            System.err.println(oldId +" newID: " + newId);
            System.out.println("-------------------------------------");
        }
       
            
    }
    public static String GetVirtualNameOfGate(String NodeId)
    {
        if(NodeId.contains("#"))
            return NodeId.substring(0,NodeId.lastIndexOf("#"));
        return "NotIdentify";
    }
    public static void ShowNodeOccurent(Graph g, Map<String, Integer> listOfVertexWithOccurents, boolean flagUnfold)
    {
        for(Node node:g)
        {
            String currentId=node.getId();
            String virtualName=currentId;
            
            if(flagUnfold) // Rename the activity to the original name.
            {
                if(!currentId.toUpperCase().contains("START") && !currentId.toUpperCase().contains("END"))
                    currentId=currentId.substring(0, currentId.indexOf("-"));
            }
            
            //if(!currentId.contains(":"))
            {
                Integer value = listOfVertexWithOccurents.get(currentId);
                if(value==null)
                    value=0;
                virtualName=virtualName+":"+value.toString();
                node.setAttribute("ui.label", virtualName);
            }
        }
    }
    
    public static void SetGlobalEdgeValue(Graph g)
    {
        Collection<Edge> edges = g.getEdgeSet();
         globalEdge = new HashMap<>();
            for(Edge edge:edges)
            {
                String node0 = edge.getNode0().getId();
                String node1 = edge.getNode1().getId();
                String attribute = edge.getAttribute("ui.label");
               globalEdge.put(node0+keySeparateInside+node1, attribute);
            }
    }
    
    public static void SwitchAllEdgeGlobalValue(Graph g, boolean state)
    {
         Collection<Edge> edges = g.getEdgeSet();
        if(!state)
            {
                for (Edge edge:edges)
                    edge.setAttribute("ui.label", "");
            }
            else
            {
                for (Edge edge:edges)
                {
                    String node0 = edge.getNode0().getId();
                    String node1 = edge.getNode1().getId();
                    String attribute = globalEdge.get(node0+keySeparateInside+node1);
                    edge.setAttribute("ui.label", attribute);
                }
            }
    }
    
    public static void ShowActivitiesInvolvements(Graph g)
    {
         Collection<Edge> edges = g.getEdgeSet();
        for (Edge edge:edges)
        {
            String node0 = edge.getNode0().getId();
            String node1 = edge.getNode1().getId();
            String attribute = WTNEdgeWithActivities.get(node0+keySeparateInside+node1);
            edge.setAttribute("ui.label", attribute);
        }
    }
    public static void ChangeBackgroundColor(Graph g, String colorHexCode)
    {
        g.addAttribute("ui.stylesheet", "graph { fill-color: "+ colorHexCode + "; }");
    }
    
    public static void ChangeColorForAll(Graph g, String _nodeColor, String _arcColor, String _andGateColor, String _orGateColor, String _loopGateColor)
    {
        for(Node node:g)
        {
            String currentId = node.getId();
            String nodeColorToChange ="";
            nodeColorToChange = _nodeColor;
            ChangeNodeColour(g, currentId, nodeColorToChange);
        }
        ChangeAllEdgeFormat(g, _arcColor);
    }
    
    public static void HightLightPersonId(Graph g, String syntax, String colorHexCode, Integer length, String[] containMemberStrings)
    {
        for(Node node:g)
        {
            String currentId=node.getId();
            Integer numberofContains=containMemberStrings.length;
            
            boolean checkContain=CheckContains(currentId, containMemberStrings);
            boolean checkLength = CheckLengthId(currentId, syntax, length);
            if(length == 0)
            {
                if(numberofContains==0)
                {
                    ChangeNodeColour(g, currentId, colorHexCode);
                }
                    
                else
                {
                     if(!checkContain)
                        ChangeNodeColour(g, currentId, colorHexCode);
                }
                   
            }
            else
            {
                if(numberofContains==0)
                {
                    if(checkLength)
                        ChangeNodeColour(g, currentId, colorHexCode);
                }
                else
                {
                    if(!checkContain && checkLength)
                        ChangeNodeColour(g, currentId, colorHexCode);
                }
            }
                
        }
    }
    
    public static boolean CheckLengthId(String id,String syntax, Integer length)
    {
        switch (syntax)
        {
            case "Equal":
                if(id.length()==length)
                    return true;
                break;
            case "Large than":
                if(id.length()>length)
                    return true;
                break;
            case "Less than":
                if(id.length()<length)
                    return true;
                break;
        }
        return false;
    }
    
    // CheckContains: If any member in memberString belong to id then return true
    public static boolean CheckContains(String id, String[] memberStrings)
    {
        Integer n = memberStrings.length;
        if(n==1 && memberStrings[0].equals(""))
            return false;
        for (Integer i=0;i<n;i++)
        {
            if(id.contains(memberStrings[i]))
                return true;
        }
        return false;
    }
    
    public static void ChangeEdgeColorForGroup(Graph g, String nodeId, String typeRelationShip,String colorHexCodeOfArc, String colorHexCodeOfFather, String colorHexCodeOfSon)
    {
        InitCurrentDisplayNode(g);
        Collection<Edge> edges = g.getEdgeSet();
        String meNode= nodeId;
        for (Edge edge:edges)
        {
            Node fatherNode = edge.getNode0();
            Node sonNode = edge.getNode1();
            if (fatherNode!=sonNode)
                switch (typeRelationShip)
                {
                    case "From Father": // That mean sonNode == nodeId
                        if(meNode.equals(sonNode.getId()))
                        {
                            ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                            ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                            StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                            StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                           
                            ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                        }
                        break;
                    case "To Son": // That mean 
                        if(meNode.equals(fatherNode.getId()))
                        {
                            ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                            ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                            StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                            StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                            ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                        }
                        break;
                    case "Both Father and Son": // That mean sonNode == nodeId || FatherNode==nodeID
                        if(meNode.equals(sonNode.getId()) || meNode.equals(fatherNode.getId()))
                        {
                            ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                            ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                            StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                            StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                            ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                        }
                        break;



                }
        }
    }
    public static Integer ComputeBetweenness(Graph g)
    {
        Integer max=0;
        Collection<Edge> edges = g.getEdgeSet();
        mapNodeWithNumberOfToSon = new HashMap<>();
        mapNodeWithNumberofFromFather = new HashMap<>();
        for (Edge edge:edges)
        {
            String idNode0 = edge.getNode0().getId();
            String idNode1 =  edge.getNode1().getId();
            
            if (idNode0.equals(idNode1))
                break;
            
            if(!mapNodeWithNumberOfToSon.containsKey(idNode0))
                mapNodeWithNumberOfToSon.put(idNode0,1);
            else
            {
                Integer currentValue = mapNodeWithNumberOfToSon.get(idNode0);
                mapNodeWithNumberOfToSon.put(idNode0, currentValue+1);
            }
            
            if(!mapNodeWithNumberofFromFather.containsKey(idNode1))
                mapNodeWithNumberofFromFather.put(idNode1, 1);
            else
            {
                Integer currentValue = mapNodeWithNumberofFromFather.get(idNode1);
                mapNodeWithNumberofFromFather.put(idNode1, currentValue+1);
            }
            Integer val0=0;
            if(mapNodeWithNumberofFromFather.containsKey(idNode0))
                val0 = mapNodeWithNumberofFromFather.get(idNode0);
            
            
            Integer val1=0;
            if(mapNodeWithNumberofFromFather.containsKey(idNode1))
                val1 = mapNodeWithNumberofFromFather.get(idNode1);
            
            if(max<val0)
                max=val0;
            
            if(max<val1)
                max=val1;
            
            
            Integer val2=0;Integer val3=0;
            
            if(mapNodeWithNumberOfToSon.containsKey(idNode0))
                val2=mapNodeWithNumberOfToSon.get(idNode0);
            
            if(mapNodeWithNumberOfToSon.containsKey(idNode1))
                val3=mapNodeWithNumberOfToSon.get(idNode1);
            
            if(max<val2)
                max=val2;
            
            if(max<val3)
                max=val3;
            
            
            
        }
        return max;
    }
    
    public static void ChangeEdgeColorForBetweenness(
            Graph g, 
            Integer numberOfArc,
            String typeRelationShip,
            String typeOfCompare,
            String colorHexCodeOfArc, 
            String colorHexCodeOfFather, 
            String colorHexCodeOfSon,
            Boolean firstime
            )
    {
        //Step 1. Caculate
        if (firstime)
            ComputeBetweenness(g);
        
        InitCurrentDisplayNode(g);
        Collection<Edge> edges = g.getEdgeSet();
        for (Edge edge:edges)
        {
            Node fatherNode = edge.getNode0();
            Node sonNode = edge.getNode1();

            Integer numberOfFromFather;
            numberOfFromFather = mapNodeWithNumberofFromFather.get(sonNode.getId());
            Integer numberOFToSon;
            numberOFToSon = mapNodeWithNumberOfToSon.get(fatherNode.getId());

            if (fatherNode!=sonNode)
                switch (typeRelationShip)
                {
                    case "From Father": // That mean Dispaly nodes with 
                                        //number of Father Largen| equal | less than number of Arc
                        switch (typeOfCompare)
                        {
                            case "Equal":

                                if(numberOfFromFather== numberOfArc)
                                {
                                    ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                                    ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                                    ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                                }
                                break;
                            case "Large than":
                                if(numberOfFromFather > numberOfArc)
                                {
                                    ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                                    ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                                    ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                                }
                                break;
                            case "Less than":
                                if(numberOfFromFather < numberOfArc)
                                {
                                    ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                                    ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                                    ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                                }
                                break;
                        }


                        break;
                    case "To Son": // That mean 

                        switch (typeOfCompare)
                        {
                            case "Equal":

                                if(numberOFToSon== numberOfArc)
                                {
                                    ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                                    ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                                    ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                                }
                                break;
                            case "Large than":
                                if(numberOFToSon > numberOfArc)
                                {
                                    ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                                    ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                                    ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                                }
                                break;
                            case "Less than":
                                if(numberOFToSon < numberOfArc)
                                {
                                    ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                                    ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                                    ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                                }
                                break;
                        }


                        break;
                    case "Both Father and Son": // That mean sonNode == nodeId || FatherNode==nodeID
                        switch (typeOfCompare)
                        {
                            case "Equal":

                                if((numberOFToSon== numberOfArc)&&(numberOfFromFather== numberOfArc))
                                {
                                    ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                                    ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                                    ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                                }
                                break;
                            case "Large than":
                                if((numberOFToSon > numberOfArc)&&(numberOfFromFather > numberOfArc))
                                {
                                    ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                                    ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                                    ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                                }
                                break;
                            case "Less than":
                                if((numberOFToSon < numberOfArc)&&(numberOfFromFather < numberOfArc))
                                {
                                    ChangeNodeColour(g, fatherNode.getId(), colorHexCodeOfFather);
                                    ChangeNodeColour(g, sonNode.getId(), colorHexCodeOfSon);
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,fatherNode.getId());
                                    StoreNodeToMapCurrentDisplay(mapStoreCurrentDisplayNode,sonNode.getId());
                                    ChangeEdgeColour(g, fatherNode.getId(), sonNode.getId(), colorHexCodeOfArc);
                                }
                                break;
                        }

                        break;
                }
        }
            
        
    }
    public static void InitCurrentDisplayNode(Graph g)
    {
        mapStoreCurrentDisplayNode = new HashMap<>();
        for (Node node:g)
        {
            String currentId= node.getId();
            mapStoreCurrentDisplayNode.put(currentId, "");
        }
    }
    public static void StoreNodeToMapCurrentDisplay(Map<String, String> currentMap, String nodeID)
    {
            currentMap.put(nodeID, "keep");
        
    }
    public static void RemoveUnDisplayNode(Graph g)
    {
        if (mapStoreCurrentDisplayNode.size()==0 || mapStoreCurrentDisplayNode ==null)
            return;
        
        for (Node node:g)
        {
            String currentID= node.getId();
            String key=mapStoreCurrentDisplayNode.get(currentID);
            if(!key.equals("keep"))
                g.removeNode(node);
            
        }
    }
   
    public static Integer GetNumberOccurent(Graph graph, String currentNodeId, Map<String, Integer> listOfVertexWithOccurents)
    {
        return listOfVertexWithOccurents.get(currentNodeId);
    }
    
    
}
