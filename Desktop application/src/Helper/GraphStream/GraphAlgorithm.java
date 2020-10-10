/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.GraphStream;

import GraphStreamCoreHelper.SetGraphAttribute;
import Helper.Common.MessageHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.graphstream.algorithm.BetweennessCentrality;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import static Helper.ICN.ICNBuilderV1.keyConnectNode;
import Helper.Common.ToolsHelper;
//import Helper.WTNBuilder;
import static Helper.Common.Variables.keySeparateInside;
import static Helper.Common.Variables.keyWordSeparate;

/**
 *
 * @author phamdinhlam
 */
public class GraphAlgorithm {
    private static HashMap<Integer, List<String>> listOfPathFromAToB = new HashMap<>();// List of Path From A to B
    private static Integer pathSize=0;
    
    public static String activityCode="concept:name";
    public static String performerCode="org:resource";
    public static String timeStampCode="time:timestamp";
    public static String identityCode="identity:id";
    public static String docTypeCode="doctype";

    
    public static String[] eventKeyToGet = new String[]{identityCode, activityCode, performerCode, timeStampCode, docTypeCode};

    public GraphAlgorithm() {
        listOfPathFromAToB = new HashMap<>();
    }
    
    private static Integer GetAtoBValue(Graph graph, Edge edge)
    {
        Integer value=0;
        value = Integer.parseInt(edge.getAttribute("ui.label"));
        return value;
    }
    
    private static Integer GetBtoAValue(Graph graph, Edge edge)
    {
        Integer value=0;
        String node0Id= edge.getNode0().getId();
        String node1Id = edge.getNode1().getId();
        Edge btoAedge = graph.getEdge(node1Id+keyConnectNode+node0Id);
        value = Integer.parseInt(btoAedge.getAttribute("ui.label"));
        return value;
    }
    
    public static Integer RemoveDeliberateNoises(Graph graph)
    {
        List<String> listOfEdgeToDelete = new LinkedList<>();
        Collection<Edge> edges = graph.getEdgeSet();
        Map<Edge, String> potentialEdgeToRemove = new HashMap<>();
        for(Edge edge:edges)
        {
            String node0ID = edge.getNode0().getId();
            String node1ID = edge.getNode1().getId();
            if(node0ID.equals(node1ID)) //Do not consider Selfoop
                continue;
            Edge abEdge = edge;
            Edge baEdge = graph.getEdge(node1ID+keyConnectNode+node0ID);
            if(baEdge != null) // Exist the backward Direction
            {
               if(!potentialEdgeToRemove.containsKey(abEdge) && !potentialEdgeToRemove.containsKey(baEdge))
                   potentialEdgeToRemove.put(abEdge, "");
            }
        }
        
        Integer countPair=0;
        boolean flagCheckRemoveArc=true;
        
        for (Map.Entry<Edge, String> entry : potentialEdgeToRemove.entrySet()) 
        {
            Edge edge = entry.getKey();
            String node0ID = edge.getNode0().getId();
            String node1ID = edge.getNode1().getId();
            Edge abEdge = edge;
            Edge baEdge = graph.getEdge(node1ID+keyConnectNode+node0ID);

            if(baEdge != null) // Exist the backward Direction
            {
                String abEdgeStringString = abEdge.getAttribute("ui.label");
                String baEdgeValueString = baEdge.getAttribute("ui.label");
                Integer abEdgeValueInteger=0, baEdgeValueInteger=0;
                
                if(ToolsHelper.isStringInteger(abEdgeStringString))
                    abEdgeValueInteger=Integer.parseInt(abEdgeStringString);

                if(ToolsHelper.isStringInteger(baEdgeValueString))
                    baEdgeValueInteger=Integer.parseInt(baEdgeValueString);

                //Compare ab, ba value with  total incoming and outgoing
                boolean flag1,flag2,flag3,flag4,remove=true;
                
                Integer totalIncomingOfNodeA, totalOutgoingOfNodeA, totalIncomingOfNodeB, totalOutgoingOfNodeB;
                totalIncomingOfNodeA = GetTotalIncomingValue(graph, node0ID);
                totalOutgoingOfNodeA = GetTotalOutgoingValue(graph, node0ID);
                totalIncomingOfNodeB = GetTotalIncomingValue(graph, node1ID);
                totalOutgoingOfNodeB = GetTotalOutgoingValue(graph, node1ID);
                
                flag1 = abEdgeValueInteger.equals(totalIncomingOfNodeA);
                flag2 = abEdgeValueInteger.equals(totalOutgoingOfNodeB);
                flag3 = baEdgeValueInteger.equals(totalOutgoingOfNodeA);
                flag4 = baEdgeValueInteger.equals(totalIncomingOfNodeB);
                
                if(flag1 || flag2 || flag3 || flag4)
                    remove=false;
                
                if(!remove)
                {
//                    System.err.println("Do not remove " + abEdge.getId());
                    continue;
                }
                //----------------
                    // Dem so canh ra vao moi dinh A, B de xem co nen xoa hay khong
                Integer countIncomingAInteger = CountIncomingEdge(graph, node0ID);
                Integer countOutgoingAInteger = CountOutgoingEdge(graph, node0ID);

                Integer countIncomingBInteger = CountIncomingEdge(graph, node1ID);
                Integer countOutgoingBInteger = CountOutgoingEdge(graph, node1ID);

                boolean removeABfromA=false;  // assume that we cannot delete AB from A
                if((countIncomingAInteger+countOutgoingAInteger>2)) /// Xoa duoc ABkhoi dinh A
                    removeABfromA=true;

                boolean removeABfromB=false;  // assume that we cannot delete AB from A
                if((countIncomingBInteger+countOutgoingBInteger>2)) /// Xoa duoc ABkhoi dinh B
                    removeABfromB=true;

                if(removeABfromA && removeABfromB)
                {

                    SetGraphAttribute.RemoveEdge(graph, node0ID, node1ID);
                    SetGraphAttribute.RemoveEdge(graph, node1ID, node0ID);
                    countPair++;
                    flagCheckRemoveArc=true;
                    listOfEdgeToDelete.add(node0ID+keyConnectNode+node1ID);
                }
            }
        }
//        WTNBuilder._listOfEdgeDeliberateNoise = listOfEdgeToDelete;
//        WTNBuilder.flagRemoveDeliberateNoise = true;
        
        return countPair;
    }
    
    private static Integer CountIncomingEdge(Graph graph, String nodeAId)
    {
        Integer result=0;
        Node nodeA = graph.getNode(nodeAId);
        Collection<Edge> incomingCollection = nodeA.getEnteringEdgeSet();
        result = incomingCollection.size();
        return result;
    }
    
    private static Integer CountOutgoingEdge(Graph graph, String nodeAId)
    {
        Integer result=0;
        Node nodeA = graph.getNode(nodeAId);
        Collection<Edge> outgoingCollection = nodeA.getLeavingEdgeSet();
        result = outgoingCollection.size();
        return result;
    }
    
    
    
    public static Integer GetTotalIncomingValue(Graph graph, String nodeId)
    {
        Integer totalIncomingValue =0;
        Node node = graph.getNode(nodeId);
        Iterator<Edge> iterator = node.getEnteringEdgeIterator();
        while (iterator.hasNext()) 
        {
            Edge next = iterator.next();
            String node0Id=next.getNode0().getId();
            String node1Id=next.getNode1().getId();
            if(node0Id.equals(node1Id))  // Khong xet truong hop BB. (Khong tinh selfloop edge)
                continue;
            Integer tempValue = 0;
            if(ToolsHelper.isStringInteger(next.getAttribute("ui.label")))
                tempValue = Integer.parseInt(next.getAttribute("ui.label"));

            totalIncomingValue+=tempValue;
        }
        return totalIncomingValue;
    }
    
    public static Integer GetTotalOutgoingValue(Graph graph, String nodeId)
    {
        Integer totalOutgoingValue =0;
        Node node = graph.getNode(nodeId);
        Iterator<Edge> iterator = node.getLeavingEdgeIterator();
        while (iterator.hasNext()) 
        {
            Edge next = iterator.next();
            String node0Id=next.getNode0().getId();
            String node1Id=next.getNode1().getId();
            if(node0Id.equals(node1Id))  // Khong xet truong hop BB. (Khong tinh selfloop edge)
                continue;
            Integer tempValue = 0;
            if(ToolsHelper.isStringInteger(next.getAttribute("ui.label")))
                tempValue = Integer.parseInt(next.getAttribute("ui.label"));

            totalOutgoingValue+=tempValue;
        }
        return totalOutgoingValue;
    }
    
    
    public static void GetAllPath(Graph graph, String fromV, String toV,HashMap<Integer, List<String>> outPutListPath)
    {
        HashMap<String,Boolean> isVisited = new HashMap<>();
        //Initialize the Isvisited map for every node in graph
        for(Node node:graph)
        {
            String nodeId=node.getId();
            isVisited.put(nodeId, Boolean.FALSE);
        }
        
        
        ArrayList<String> pathList = new ArrayList<>();
        //add source to path[]
        pathList.add(fromV);
        //Call recursive utility
        printAllPathsUtil(graph, fromV,toV, isVisited, pathList, outPutListPath);
        
    }
    // A recursive function to print all paths from 'u' to 'd'. isVisited[] keeps track of
    // vertices in current path.localPathList<> stores actual vertices in the current path
    public static void printAllPathsUtil(Graph graph, String u, String d, HashMap<String,Boolean> isVisited,List<String> localPathList, HashMap<Integer, List<String>> outPutListPath) 
    {
        // Mark the current node
        isVisited.put(u, Boolean.TRUE);
        if (u.equals(d)) 
        {
            AddVertextToListOfVertexFromAToB(graph, localPathList, outPutListPath);
        }
        else
        {
            // Recur for all the vertices adjacent to current vertex
            HashMap<String, Integer> edgeList = new  HashMap<>();
            
            String currentNodeId = u;
            Node currentNode = graph.getNode(currentNodeId);
            Iterator<Edge> edgeLeavingIterator = currentNode.getLeavingEdgeIterator();
            while (edgeLeavingIterator.hasNext()) {
                Edge next = edgeLeavingIterator.next();
                String node1Id = next.getNode1().getId();
                
                edgeList.put(node1Id, 1);
            }
            
            if(edgeList.size()>0) // The node "u" contains neightbour
                {
                    Iterator<String> keySetIterator = edgeList.keySet().iterator();
                    while(keySetIterator.hasNext())
                    {
                         String u_neighbour = keySetIterator.next();
    //                     Integer keyValue = edgeList.get(dest)
                        if(!isVisited.get(u_neighbour))
                           {
                               localPathList.add(u_neighbour);
                               printAllPathsUtil(graph, u_neighbour, d, isVisited, localPathList, outPutListPath);
                               localPathList.remove(u_neighbour);
                           }
                   }
                }
        }
        
        // Mark the current node
        isVisited.put(u, Boolean.FALSE);
    }
    private static void AddVertextToListOfVertexFromAToB(Graph graph, List<String> localPathList, HashMap<Integer, List<String>> outPutListPath)
    {
        pathSize++;
        List<String> tempPathList= new ArrayList<String>();
        Integer n = localPathList.size();
        for(Integer j=1;j<n-1;j++)
        {
            String currentVertex = localPathList.get(j);
            String currentNodeId= currentVertex;
            tempPathList.add(currentVertex);
            // Keep all neighbor Node on the Path From A -> B
            Node currentNode = graph.getNode(currentNodeId);
            
            Iterator<Node> iteratorNode = currentNode.getNeighborNodeIterator();
            while (iteratorNode.hasNext()) {
                Node node = iteratorNode.next();
                String neightBorNodeId = node.getId();
                if(!tempPathList.contains(neightBorNodeId))
                    tempPathList.add(neightBorNodeId);
            }
            
        }
        outPutListPath.put(pathSize, tempPathList);
        
    }
    public static void CopyGraph(Graph sourceGraph, Graph destGraph)
    {
        String graphAttribute = sourceGraph.getAttribute("ui.stylesheet");
        destGraph.addAttribute("ui.stylesheet", graphAttribute);
        
        Collection<Edge> edges = sourceGraph.getEdgeSet();
        for(Edge edge:edges)
        {
            String node0ID = edge.getNode0().getId();
            String node1ID = edge.getNode1().getId();
            Node node0 = sourceGraph.getNode(node0ID);
            Node node1 = sourceGraph.getNode(node1ID);
            
            // Copy node0, node1 to new Graph
            if(destGraph.getNode(node0ID)==null)
                destGraph.addNode(node0ID);
            if(destGraph.getNode(node1ID)==null)
                destGraph.addNode(node1ID);
            
            // Set new node Attribute
            Node newNode0 = destGraph.getNode(node0ID);
            Node newNode1 = destGraph.getNode(node1ID);
            
            String newNode0Attribute = node0.getAttribute("ui.style");
            String newNode1Attribute = node1.getAttribute("ui.style");
            newNode0.setAttribute("ui.style", newNode0Attribute);
            newNode0.addAttribute("ui.label", node0ID);
            
            newNode1.setAttribute("ui.style", newNode1Attribute);
            newNode1.addAttribute("ui.label", node1ID);
            // Copy edge to new graph and set Attribute
            String edgeAttribute = edge.getAttribute("ui.label");
            if(destGraph.getEdge(node0ID+keyConnectNode+node1ID)==null)
            {
                destGraph.addEdge(node0ID+keyConnectNode+node1ID, node0ID, node1ID, true);
                Edge newEdge = destGraph.getEdge(node0ID+keyConnectNode+node1ID);
                newEdge.setAttribute("ui.label", edgeAttribute);
            }
        }
    }
    public static void CreateAffiliationNetWork(List<String> listOfTraceToBuildAffiliationNetwork,
                            String codeLeftBetween, String leftNodeType, String LeftNodeColor, Integer LeftNodeSize, 
                            String codeRightBetween, String rightNodeType, String RightNodeColor, Integer RightNodesize,
                            Graph AffiliationGraph)
    {
        if(codeLeftBetween.equals(codeRightBetween))
        {
            MessageHelper.Warning("Can not create affiliation network between same type");
            return;
        }
        List<String> listOfLeftAffiliation = new LinkedList<>();
        List<String> listOfRightAffiliation  = new LinkedList<>();
        Integer posOfLeftAffiliationCode =0; Integer posOfRightAffiliationCode=0;
        for (Integer i=0;i<eventKeyToGet.length;i++)
        {
            if(eventKeyToGet[i].equals(codeLeftBetween))
                posOfLeftAffiliationCode=i;
            if(eventKeyToGet[i].equals(codeRightBetween))
                posOfRightAffiliationCode=i;
        }
        
        Integer numberOfTrace = listOfTraceToBuildAffiliationNetwork.size();
        for(Integer i=0;i<numberOfTrace;i++) 
        {
            String[] arrayCurrentTrace=listOfTraceToBuildAffiliationNetwork.get(i).split(keyWordSeparate);
            Integer lengthOfCurrentTrace=arrayCurrentTrace.length;
            for (Integer j=1;j<lengthOfCurrentTrace-1;j++) // We don't care about START and END
            {
                //Get the Left Node and Right Node in each Event in each Trace
                String[] arrayCurrentNodeOfTrace = arrayCurrentTrace[j].split(keySeparateInside);
                
                String leftNodeId = arrayCurrentNodeOfTrace[posOfLeftAffiliationCode];
                String rightNodeId = arrayCurrentNodeOfTrace[posOfRightAffiliationCode];
                
                // Add leftNode and rightNode to the list-Left and list-Right
                if(!listOfLeftAffiliation.contains(leftNodeId))
                    listOfLeftAffiliation.add(leftNodeId);
                if(!listOfRightAffiliation.contains(rightNodeId))
                    listOfRightAffiliation.add(rightNodeId);
                
                // Add Node to Graph
                if(AffiliationGraph.getNode(leftNodeId)==null)
                    AffiliationGraph.addNode(leftNodeId);
                if(AffiliationGraph.getNode(rightNodeId)==null)
                    AffiliationGraph.addNode(rightNodeId);
                if(AffiliationGraph.getEdge(leftNodeId+keyConnectNode+rightNodeId)==null)
                    AffiliationGraph.addEdge(leftNodeId+keyConnectNode+rightNodeId, leftNodeId, rightNodeId);
                
                //Set Node attrib
                Node leftNode = AffiliationGraph.getNode(leftNodeId);
                Node rightNode = AffiliationGraph.getNode(rightNodeId);
                Edge currentEdge = AffiliationGraph.getEdge(leftNodeId+keyConnectNode+rightNodeId);
                SetGraphAttribute.SetNodeAttrib(AffiliationGraph, leftNodeId, LeftNodeSize, LeftNodeColor, leftNodeType);
                SetGraphAttribute.SetNodeAttrib(AffiliationGraph, rightNodeId, RightNodesize, RightNodeColor, rightNodeType);
            }
        }
    }
    public static Boolean isReachable(Graph graph, String fromV, String toV) 
    {
        // Mark all the vertices as not visited(By default set    // as false)
        Map <String, Integer> visitedMap = new HashMap<>();
 
        // Create a queue for BFS
        LinkedList<String> queue = new LinkedList<>();
 
        // Mark the current node as visited and enqueue it
        for(Node node:graph)
        {
            String currentId=node.getId();
            visitedMap.put(currentId, 0);
        }
       
        visitedMap.put(fromV, 1);
        queue.add(fromV);
 
        // 'i' will be used to get all adjacent vertices of a vertex
        HashMap<String,Integer> listOfSon = new HashMap<>();
        while (!queue.isEmpty())
        {
            // Dequeue a vertex from queue and print it
            String currentNodeId = queue.poll();
            
            Node currentNode = graph.getNode(currentNodeId);
            Iterator<Edge> nodeIterator=null;
            if(currentNode!=null)
               nodeIterator = currentNode.getLeavingEdgeIterator();
            
            if(nodeIterator!=null)
            while (nodeIterator.hasNext()) {
                Edge next = nodeIterator.next();
                String sonId = next.getNode1().getId();
                listOfSon.put(sonId, 1);
                
            }
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            for (Map.Entry<String, Integer> entry : listOfSon.entrySet()) 
            {
                String key = entry.getKey();
                if(key.equals(toV))
                    return true;
                
                boolean realEdge=true;
                
                Edge edge = graph.getEdge(currentNodeId+keyConnectNode+key);
                if(edge==null)
                    realEdge=false;
                if(visitedMap.get(key)==0 && realEdge)
                {
                    visitedMap.put(key, 1);
                    queue.add(key);
                }
            }
            
        }
 
        // If BFS is complete without visited toV
        return false;
    }
    
    public static List<String> CreateListOrderToVisitBFS(Graph graph, String startNode, String endNode)
    {
        List<String> listVertexOrder = new LinkedList<>();
        // Visit Graph by BFS
        // Mark all node as not visit
        HashMap<String,Boolean> isVisited = new HashMap<>();
        //Initialize the Isvisited map for every node in graph
        for(Node node:graph)
        {
            String nodeId=node.getId();
            isVisited.put(nodeId, Boolean.FALSE);
        }
        
        List<String> queueVertex = new LinkedList<>();
        queueVertex.add(startNode);
        isVisited.put(startNode, Boolean.TRUE);
        while(queueVertex.size()>0)
        {
            String currentNodeId = queueVertex.remove(0);
            if(!isVisited.get(currentNodeId))
            {
                listVertexOrder.add(currentNodeId);
                isVisited.put(currentNodeId, Boolean.TRUE);
            }
            
            Node currentNode = graph.getNode(currentNodeId);
            Iterator<Edge> edgeLeavingIterator = currentNode.getLeavingEdgeIterator();
            while (edgeLeavingIterator.hasNext()) {
                Edge next = edgeLeavingIterator.next();
                String neighbourId = next.getNode1().getId();
                if(!isVisited.get(neighbourId))
                    queueVertex.add(neighbourId);
            }
        }
        
        return  listVertexOrder;
        
    }
}
