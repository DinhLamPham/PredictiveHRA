/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.GraphStream;

import GraphStreamCoreHelper.SetGraphAttribute;
import Helper.Common.MessageHelper;
import static Helper.ICN.ICNBuilderV1.CheckNodeIsGate;
import Helper.Common.SortAlgorithm;
import static Helper.Common.SortAlgorithm.SortByLevel;
import Helper.Common.ToolsHelper;
import Helper.ICN.ICNBuilderV1;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import static Helper.ICN.ICNBuilderV1.keyConnectNode;
/**
 *
 * @author phamdinhlam
 */
public class ICNChecker {
    private static Map<String, Map<String, Integer>> listOfNodeProblem;
    private static Integer countNodeProblem=0;
    private static boolean flagRunChecker=false;
    private static String keySeperateRoom = "@#@";
    
    public ICNChecker()
    {
//        listOfNodeProblem = new HashMap<>();
//        countNodeProblem =0;
//        flagRunChecker=false;
    }
    public static boolean HighLightNodeNotProcess(Graph graph, Map<String, Integer> listOfVertexWithOccurents)
    {
        listOfNodeProblem = new HashMap<>();
        countNodeProblem =0;
        HashMap<String, Integer> fatherList = new HashMap<>(); 
        HashMap<String, Integer> sonList = new HashMap<>(); 
        boolean isICNModel=true;
        for(Node node:graph)
        {
            String currentNodeId = node.getId();
            sonList= new HashMap<>();
            fatherList = new HashMap<>();                    
            ICNBuilderV1.FindFatherListAndSonList(graph, currentNodeId, listOfVertexWithOccurents, fatherList, sonList);
            if(!CheckNodeIsGate(currentNodeId))
            {
                if(sonList.size()>1 || fatherList.size()>1)
                {
                    countNodeProblem++;
                    listOfNodeProblem.put(currentNodeId+";Open-Gate",sonList);
                    SetGraphAttribute.ChangeNodeColour(graph, currentNodeId, "#db0611");
                    isICNModel=false;
                }
                    
            }
        }
        if(!isICNModel)
            System.out.println("List Of Problem: " + listOfNodeProblem.toString());
        return isICNModel;
    }
    private static Integer CheckNormalNodeInQueue(LinkedList<String> queue)
    {
        Integer position = -1;
        for(Integer i = 0;i<queue.size();i++)
        {
            String currentNode = queue.get(i);
            if(!ICNBuilderV1.CheckNodeIsGate(currentNode))
            {
                position = i;
                break;
            }
        }
        return position;
    }
    
    private static Integer CheckOpenGateInQueue(LinkedList<String> queue)
    {
        Integer position = -1;
        for(Integer i = 0;i<queue.size();i++)
        {
            String currentNode = queue.get(i);
            if(ICNBuilderV1.CheckNodeIsOpenGate(currentNode))
            {
                position = i;
                break;
            }
        }
        return position;
    }
    private static boolean CheckAllFatherArrivedToCloseGate(Graph graph, Map<String, Integer> queueVertexWithLevel, String closeGateId)
    {
        Node closeGate = graph.getNode(closeGateId);
        Iterator<Edge> iter = closeGate.getEnteringEdgeIterator();
        while (iter.hasNext()) {
            Edge next = iter.next();
            String currentFatherId=next.getNode0().getId();
            Integer currentFatherLevel = queueVertexWithLevel.get(currentFatherId);
            if(currentFatherLevel==null) // Chua xuat hien => Chua ghe tham => Phai doi.
                return false;
        }
        return true;
        
    }
    private static Integer GetMaxLevelOfFather(Graph graph, Map<String, Integer> queueVertexWithLevel, String sonId)
    {
        Integer max=-1;
        Node nodeSon=graph.getNode(sonId);
        Iterator<Edge> iter = nodeSon.getEnteringEdgeIterator();
        while (iter.hasNext()) {
            Edge next = iter.next();
            String fatherId = next.getNode0().getId();
            Integer fatherLevel = queueVertexWithLevel.get(fatherId);
            if(max<fatherLevel)
                max=fatherLevel;
        }
        return max;
        
    }
    
    public static Map<String, Integer> AssignLevelGraphBFS(Graph graph, String rootId)
    {
        LinkedList<String> queueVertexVisited = new LinkedList<>();
        Map<String, Integer> result_queueVertexWithLevel = new HashMap<>();
        queueVertexVisited.add(rootId);
         result_queueVertexWithLevel.put(rootId, 0);
        while(!queueVertexVisited.isEmpty())
        {
            String currentNodeId = queueVertexVisited.getFirst();
            Integer currentProcessLevel = result_queueVertexWithLevel.get(currentNodeId);
            // Lap lai trong khi level bang nhau cua nhung thang dau danh sach.
            while(!queueVertexVisited.isEmpty() && result_queueVertexWithLevel.get(queueVertexVisited.getFirst())==currentProcessLevel)
            {
                currentNodeId = queueVertexVisited.getFirst();
                currentProcessLevel = result_queueVertexWithLevel.get(currentNodeId);
                //Kiem tra xem Vi tri dau co phai la Gate khong. 
                //Neu la Gate thi kiem tra xem dang sau con Node thuong khong, uu tien node thuong truoc
                  if(ICNBuilderV1.CheckNodeIsCloseGate(currentNodeId))
                {
                    boolean checkAllArrived = CheckAllFatherArrivedToCloseGate(graph, result_queueVertexWithLevel, currentNodeId);
                    if(!checkAllArrived) // Neu tat ca chua den thi chua duoc di. Uu tien thang sau di truoc. don tu duoi len
                    {
                        Integer sizeOfQueue = queueVertexVisited.size();
                        Integer swapPosition =0;
                        if(sizeOfQueue>=2)
                            while (swapPosition<sizeOfQueue-1)
                            {
                                SwapPosAPosBinQueue(queueVertexVisited, swapPosition, swapPosition+1);
                                swapPosition++;
                            }
                                                
                        currentNodeId = queueVertexVisited.getFirst();
                        currentProcessLevel = result_queueVertexWithLevel.get(currentNodeId);
                    }
                    else
                    {
                        Integer maxLevelOfFather=GetMaxLevelOfFather(graph, result_queueVertexWithLevel, currentNodeId);
                        //KHI CAC CHA DEN HET ROI THI CAP NHAT LEVEL CUA CON BANG MAX CUA CHA + 1
                        result_queueVertexWithLevel.put(currentNodeId, maxLevelOfFather+1);
                    }
                }
                
                currentNodeId = queueVertexVisited.pop();
                Node currentNode = graph.getNode(currentNodeId);
                Iterator<Edge> iter = currentNode.getLeavingEdgeIterator();
                
                Integer currentFatherLevel = result_queueVertexWithLevel.get(currentNodeId);
                Integer newSonLevel = currentFatherLevel + 1;
                // Dua danh sach con vao Hang doi. Put Son to Queue List.
                while (iter.hasNext()) {
                    Edge edge = iter.next();
                    String currentSonId = edge.getNode1().getId();
                    Integer  currentSonLevel = newSonLevel;
                    
                    boolean checkVisitSon = result_queueVertexWithLevel.containsKey(currentSonId);
                    
                    if(checkVisitSon==false)
                    {
                        queueVertexVisited.add(currentSonId);
                        result_queueVertexWithLevel.put(currentSonId, currentSonLevel);
                    }
                }
            }
        }
        result_queueVertexWithLevel = SortByLevel(result_queueVertexWithLevel);
        
        return result_queueVertexWithLevel;
    }
    private static void SwapABinQueue(LinkedList<String> queue, String aString, String bString)
    {
        Integer aPosition = queue.indexOf(aString);
        Integer bPosition = queue.indexOf(bString);
        if(aPosition==-1 || bPosition==-1)
            return; // Khong ton tai a hoac b trong hang doi
        queue.set(aPosition, bString);
        queue.set(bPosition, aString);
    }
    private static void SwapPosAPosBinQueue(LinkedList<String> queue, Integer posA, Integer posB)
    {
        String aString="",bString="";
        if (posA>-1)
            aString=queue.get(posA);
        if(posB>-1)
            bString=queue.get(posB);
        
        queue.set(posA, bString);
        queue.set(posB, aString);
                
    }
    public static LinkedList<String> GetListOfIllogicalCloseGate(Graph graph, Map<String, Integer> queueVertexWithLevel)
    {
        LinkedList<String> listOfIllogicalCloseGate = new LinkedList<>();
        Collection<Edge> edges = graph.getEdgeSet();
        for (Edge edge:edges)
        {
            String node0Id = edge.getNode0().getId();
            String node1Id = edge.getNode1().getId();
            Integer node0Level = queueVertexWithLevel.get(node0Id);
            Integer node1Level = queueVertexWithLevel.get(node1Id);
            if(node0Level>node1Level) // Bat thuong
            {
                listOfIllogicalCloseGate.add(node1Id);
            }
        }
        
        return listOfIllogicalCloseGate;
    }
    
    public static void PrintIncomingAndOutgoingOfNode(Graph graph, Map<String, Integer> listOfVertexWithOccurents,String currentNodeId)
    {
        for(Node node:graph)
        {
            String nodeId = node.getId();
            if(nodeId.equals("START") || nodeId.equals("END") || !nodeId.equals(currentNodeId))
                continue;
            
            Integer totalIncoming=0, totalOutgoing=0;
            HashMap<String, Integer> sonList = new HashMap<>(); 
            HashMap<String, Integer> fatherList = new HashMap<>(); 
            ICNBuilderV1.FindFatherListAndSonList(graph, nodeId, listOfVertexWithOccurents, fatherList, sonList);
            
            
            for (Map.Entry<String, Integer> entry : fatherList.entrySet()) 
            {
                
                String fatherId = entry.getKey();
                Integer value=0;
                String arcValue = graph.getEdge(fatherId+keyConnectNode+nodeId).getAttribute("ui.label");
                if(ToolsHelper.isStringInteger(arcValue))
                    value=Integer.parseInt(arcValue);
                totalIncoming+=value;

            }
            for (Map.Entry<String, Integer> entry : sonList.entrySet()) 
            {
                String sonId = entry.getKey();
                Integer value = 0;
                String arcValue = graph.getEdge(nodeId+keyConnectNode+sonId).getAttribute("ui.label");
                if(ToolsHelper.isStringInteger(arcValue))
                    value=Integer.parseInt(arcValue);
                totalOutgoing+=value;
            }
//            boolean checkEqualIncomingAndOutgoing=Objects.equals(totalIncoming, totalOutgoing);
//            // In ra Node cu the (From node currentId) 
//            // Hoac in ra nhung Node co Incoming <> outGoing
//            if(!checkEqualIncomingAndOutgoing || currentNodeId.equals(""))
            {   
                
                System.out.println("Node - " + nodeId + ": "+listOfVertexWithOccurents.get(nodeId).toString());
                System.out.println("-----------From father:---------");

                for (Map.Entry<String, Integer> entry : fatherList.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                String edgeId=key+keyConnectNode+nodeId;
                System.out.println(graph.getEdge(edgeId)+": "+graph.getEdge(edgeId).getAttribute("ui.label"));

            }
            System.out.println("-----------From son:---------");

            for (Map.Entry<String, Integer> entry : sonList.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                String edgeId=nodeId+keyConnectNode+key;
                System.out.println(graph.getEdge(edgeId)+": "+graph.getEdge(edgeId).getAttribute("ui.label"));
            } 
            }
        }
    }
    public static void RemoveNoiseInOrGroup(Graph graph)
    {
//        Dang sai logic o day.
//        Van de la khong duoc duyet ngau nhien Node khi kiem tra noise de remove. Phai di tu START
//                => BFS de duyet do thi???
        List<String> listVertexOrder = new LinkedList<>();
        listVertexOrder = GraphAlgorithm.CreateListOrderToVisitBFS(graph, "START", "END");
        Integer listSize = listVertexOrder.size();
        Integer count=0;
        for(Integer k=0;k<listSize;k++)
        {
            Node node = graph.getNode(listVertexOrder.get(k));
            Iterator<Edge> iter = node.getLeavingEdgeIterator();
            String currentNodeString = node.getId();
            
            List<String> sonList = new LinkedList<>();
            while (iter.hasNext()) 
            {
                Edge next = iter.next();
                String currentSon=next.getNode1().getId();
                sonList.add(currentSon);
            }
            if(sonList.size()>2) //Kiem tra xem cac node con neu co ket noi 1 chieu thi xoa
            {
                for(Integer i=0; i<sonList.size()-1; i++)
                    for(Integer j=i+1;j<sonList.size();j++)
                {
                    String son1 = sonList.get(i);
                    String son2 = sonList.get(j);
                    if(son1.equals("END") || son2.equals("END"))
                        continue;
                    
                    boolean son1Toson2 = false;
                    boolean son2Toson1 = false;
                    if(graph.getEdge(son1+keyConnectNode+son2)!=null)
                        son1Toson2 = true;
                    if(graph.getEdge(son2+keyConnectNode+son1)!=null)
                        son2Toson1 = true;
                    if(son1Toson2 && !son2Toson1)
                    {
                        boolean son2IsPotentialCloseGate = false;
                        son2IsPotentialCloseGate = CheckActivityIsBecomeCloseGateOfOneGroup(graph, currentNodeString, son2);
                        if(!son2IsPotentialCloseGate) // Neu Son2 khong co the la Close-Gate thi moi xoa
                        {
                            graph.removeEdge(son1+keyConnectNode+son2);
                            count++;
                        }
                    }
                        
                    if(son2Toson1 && !son1Toson2)
                    {
                        boolean son1IsPotentialCloseGate = false;
                        son1IsPotentialCloseGate = CheckActivityIsBecomeCloseGateOfOneGroup(graph, currentNodeString, son1);
                        if(!son1IsPotentialCloseGate) // Neu Son1 khong co the la Close-Gate thi moi xoa
                        {
                            graph.removeEdge(son2+keyConnectNode+son1);
                            count++;
                        }
                    }
                }
            }
        }
        MessageHelper.Warning("Number edges removed: "+count.toString());
    }
    // Kiem tra xem mot node co the dong group lai hay khong. (Becoming to close gate ???)
    public static boolean CheckActivityIsBecomeCloseGateOfOneGroup(Graph graph, String grandFatherString, String potentialActivityString)
    {
        
        boolean result = false;
        boolean condition_1 = false;
        boolean condition_2 = false;
                
        // 2 dieu kien de tro thanh close Gate:
        //1. So luong Outgoing <=1;
        Iterator<Edge> iter = graph.getNode(potentialActivityString).getLeavingEdgeIterator();
        int count=0;
        while (iter.hasNext()) {
            Edge next = iter.next();
            count++;
        }
        if(count<=1)
            condition_1 = true;
        
        
        //2. Tat ca Children co the ket noi den. Hien tai moi chi xet 2 cap (Trong 1 group chi co 1 level Children)
        List<String> sonList = new LinkedList<>();
        Iterator<Edge> iterChildren = graph.getNode(grandFatherString).getLeavingEdgeIterator();
        boolean isCloseAble = true;
        while (iterChildren.hasNext()) {
            Edge next = iterChildren.next();
            String children = next.getNode1().getId();
            if(graph.getEdge(children+keyConnectNode+potentialActivityString)==null)
                isCloseAble=false;
        }
        if(isCloseAble)
            condition_2 = true;
        
        if(condition_1 && condition_2)
            return true;
        else 
            return false;
    }
    
    
   
}

