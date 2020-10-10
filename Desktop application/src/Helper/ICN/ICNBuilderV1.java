/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.ICN;

import Helper.Common.MessageHelper;

import Helper.Common.SortAlgorithm;
import GraphStreamCoreHelper.GetGraphAttribute;
import GraphStreamCoreHelper.SetGraphAttribute;
import Helper.Common.Variables;
import static Helper.Common.Variables.steadyGraph;
import Helper.GraphStream.ICNChecker;
import Helper.GraphStream.GraphAlgorithm;
import java.text.DecimalFormat;
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

/**
 *
 * @author phamdinhlam
 */
public class ICNBuilderV1 {
    public static String orOpenName ="OR-OPEN";
    public static String orCloseName ="OR-CLOSE";
    public static String andOpenName ="AND-OPEN";
    public static String andCloseName ="AND-CLOSE";
    public static String loopOpenName ="LOOP-OPEN";
    public static String loopCloseName ="LOOP-CLOSE";
    public static String orNodeColor="#b5cca7";
    public static String andNodeColor="#a8a826";
    public static String loopNodeColor="#f4d742";
    public static String orNodeType="circle";
    public static String andNodeType="circle";
    public static String loopNodeType="diamond";
    public static Integer nodeSize=15;
    
    public static Integer orOpenId=0;
    public static Integer orCloseId=0;
    public static Integer andOpenId=0;
    public static Integer andCloseId=0;
    public static Integer loopOpenId=0;
    public static Integer loopCloseId=0;
    public static String keyConnectNode = "!!";
    public static String keySeperateRoom = "@#@";
    private static Map<Integer, String> houseOfSon;
    private static Map<Integer, String> houseOfFather;
    
    public static Integer _countOr=0;
    public static Integer _countAnd=0;
    public static Integer _countLoop=0;
    
    public static Map<String, Integer> listOfOrOpen = new HashMap<>();
    public static Map<String, Integer> listOfOrClose = new HashMap<>();
    public static Map<String, Integer> listOfAndOpen = new HashMap<>();
    public static Map<String, Integer> listOfAndClose = new HashMap<>();
    public static Map<String, Integer> listOfLoopOpen = new HashMap<>();
    public static Map<String, Integer> listOfLoopClose = new HashMap<>();
    
    public static Map<String, Integer> queueVertexWithLevel = new HashMap<>();
    
    
    
    public static String FindGateIdAsBridge(Graph graph, String node0Id, String node1Id, Map<String, Integer> gateList)
    {
        
        for (Map.Entry<String, Integer> entry : gateList.entrySet()) {
            String key = entry.getKey();
            
            Edge node0ToGate = graph.getEdge(node0Id+keyConnectNode+key);
            Edge gateToNode1 = graph.getEdge(key+keyConnectNode+node1Id);
            if( node0ToGate!=null && gateToNode1!=null)
                return key;
        }
        return "";
    }
    public  static String ScanGate(Graph graph, String node0Id, String node1Id)
    {
        String result="";
        result = FindGateIdAsBridge(graph, node0Id, node1Id, listOfOrOpen);
        if(result.equals(""))
            result = FindGateIdAsBridge(graph, node0Id, node1Id, listOfOrClose);
        
        if(result.equals(""))
            result = FindGateIdAsBridge(graph, node0Id, node1Id, listOfAndOpen);
        if(result.equals(""))
            result = FindGateIdAsBridge(graph, node0Id, node1Id, listOfAndClose);
        
        if(result.equals(""))
            result = FindGateIdAsBridge(graph, node0Id, node1Id, listOfLoopOpen);
        if(result.equals(""))
            result = FindGateIdAsBridge(graph, node0Id, node1Id, listOfLoopClose);
        
        return result;
    }
    
    public static void ICNStatistics()
    {

        Integer countOr=0,countAnd=0,countLoop=0,countActivities=0;
        
        for (Map.Entry<String, Integer> entry : listOfOrOpen.entrySet()) 
        {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value==1)
                countOr++;
        }
        for (Map.Entry<String, Integer> entry : listOfAndOpen.entrySet()) 
        {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value==1)
                countAnd++;
        }
        for (Map.Entry<String, Integer> entry : listOfLoopOpen.entrySet()) 
        {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value==1)
                countLoop++;
        }
        _countOr=countOr;
        _countAnd=countAnd;
        _countLoop=countLoop;
        
    }
    
    public static boolean CheckNodeIsGate(String nodeId)
    {
//        if(!nodeId.toUpperCase().contains("OR") || !nodeId.toUpperCase().contains("AND") || !nodeId.toUpperCase().contains("LOOP"))
//            return false;
        
        if(nodeId.toUpperCase().contains("OPEN"))
            return true;
        if(nodeId.toUpperCase().contains("CLOSE"))
            return true;
        if(nodeId.toUpperCase().contains("LOOP"))
            return true;
        
        return false;
    }
    
    public static String RemoveCharacterSP10(String inputString)
    {
        String outputString=inputString.replaceAll("-", "");
        outputString=outputString.replaceAll("_", "");
        
        return outputString;
             
    }
    public static String RemoveCharacterSP3(String inputString)
    {
        String outputString=inputString;
        outputString = outputString.replaceAll("#", "");
        return outputString;
             
    }
    
    public static boolean CheckNodeIsCloseGate(String nodeId)
    {
        return nodeId.toUpperCase().contains("CLOSE");
    }
    public static boolean CheckNodeIsOpenGate(String nodeId)
    {
        return nodeId.toUpperCase().contains("OPEN");
    }
    
    public static boolean CheckNodeIsOrOpenGate(String nodeId)
    {
        nodeId=RemoveCharacterSP10(nodeId);
        return (nodeId.toUpperCase().contains("OROPEN"));
    }
    public static boolean CheckNodeIsOrCloseGate(String nodeId)
    {
        nodeId=RemoveCharacterSP10(nodeId);
        return (nodeId.toUpperCase().contains("ORCLOSE"));
    }
    
    public static boolean CheckNodeIsAndOpenGate(String nodeId)
    {
        nodeId=RemoveCharacterSP10(nodeId);
        return (nodeId.toUpperCase().contains("ANDOPEN"));
    }
    public static boolean CheckNodeIsANDCloseGate(String nodeId)
    {
        nodeId=RemoveCharacterSP10(nodeId);
        return (nodeId.toUpperCase().contains("ANDCLOSE"));
    }
    
    public static boolean CheckNodeIsLoopOpenGate(String nodeId)
    {
        nodeId=RemoveCharacterSP10(nodeId);
        return (nodeId.toUpperCase().contains("LOOPOPEN"));
    }
    public static boolean CheckNodeIsLoopCloseGate(String nodeId)
    {
        nodeId=RemoveCharacterSP10(nodeId);
        return (nodeId.toUpperCase().contains("LOOPCLOSE"));
    }
    
    public static boolean CheckNodeIsOrGate(String nodeId)
    {
        nodeId=RemoveCharacterSP10(nodeId);
        boolean reusult=false;
        if (nodeId.toUpperCase().contains("OROPEN") || nodeId.toUpperCase().contains("ORCLOSE") )
            reusult = true;
        return reusult;
    }
    public static boolean CheckNodeIsAndGate(String nodeId)
    {
        nodeId=RemoveCharacterSP10(nodeId);
        boolean reusult=false;
        if (nodeId.toUpperCase().contains("ANDOPEN") || nodeId.toUpperCase().contains("ANDCLOSE") )
            reusult = true;
        return reusult;
        
    }
    public static boolean CheckNodeIsLoopGate(String nodeId)
    {
        nodeId=RemoveCharacterSP10(nodeId);
        boolean reusult=false;
        if (nodeId.toUpperCase().contains("LOOPOPEN") || nodeId.toUpperCase().contains("LOOPCLOSE") )
            reusult = true;
        return reusult;
        
    }
    
    
    // This function fix comlexility case
    public static void FixICNProblem(Graph graph, Map<String, Integer> listOfVertexWithOccurents)
    {
        HashMap<String, Integer> fatherList = new HashMap<>(); 
        HashMap<String, Integer> sonList = new HashMap<>(); 
        
        for(Node node:graph)
        {
            String currentNodeId = node.getId();
            fatherList = new HashMap<>();
            sonList = new HashMap<>();
            ICNBuilderV1.FindFatherListAndSonList(graph, currentNodeId, listOfVertexWithOccurents, fatherList, sonList);
            
            Integer maxRoom = 0;
            if(fatherList.size()>sonList.size())
                maxRoom=fatherList.size();
            else
                maxRoom=sonList.size();
        // Process for the normal node with wrong number of Father and Son
            if(!CheckNodeIsGate(currentNodeId))
            {
                if(sonList.size()>1) // Has more than 1 son => Connect by one Close Gate
                    ProcessICNForSonList(graph, currentNodeId, sonList, listOfVertexWithOccurents, maxRoom);
                if(fatherList.size()>1)// Has more than 1 Father => Connect by One Close Gate
                    ProcessICNForFatherList(graph, currentNodeId, fatherList, listOfVertexWithOccurents, maxRoom);
            }
        // Process for the Gate  with the wrong number of Incoming and Outgoing
        }
        
    }   
    private static void ProcessICNForSonList(Graph graph, String currentNodeId, HashMap<String, Integer> sonList, Map<String, Integer> listOfVertexWithOccurents, Integer maxRoom )
    {
        Map<Integer, String> listRoomOfSon = new HashMap<>();  
        ICNBuilderV1.AssignRoom(sonList, listOfVertexWithOccurents, listRoomOfSon);
        Integer[] arrayListRoom = new Integer[maxRoom]; //Danh sach cac phong voi ten phong
        Integer[] arrayCountGuestInroom = new Integer[maxRoom];// Danh sach Dem so khach trong cac phong
        String[] arrayGuestInRoom = new String[maxRoom]; // Danh sach khach trong cac phong
        Integer countNotEmptyRoom=0;
        for (Map.Entry<Integer, String> entry : listRoomOfSon.entrySet()) 
        {
              Integer roomNumber = entry.getKey();
              String guestList = entry.getValue();

              countNotEmptyRoom++;
              arrayListRoom[countNotEmptyRoom-1]=roomNumber;
              arrayGuestInRoom[countNotEmptyRoom-1]=guestList;
              arrayCountGuestInroom[countNotEmptyRoom-1]=guestList.split(keySeperateRoom).length;
        }
        // Case 1: Room number = 1                      
        if(countNotEmptyRoom==1) // ==> Connect All Guest By And
        {
              String[] currentGuestList = arrayGuestInRoom[0].split(keySeperateRoom);
              andOpenId++;
              Integer gateId=andOpenId;
              String gateType="And-Open";
              String[] listOfNodeToConnect= currentGuestList;
              CreateNormalGate(graph,listOfVertexWithOccurents, currentNodeId, listOfNodeToConnect, gateId, gateType);
        }

      //---------------------Number Of room >=2:
      //1. Di xem cac phong. PHong nao co tren 2 nguoi thi ket noi bang And-Open
      //2. Xem cac phong don con lai ket noi voi nhau bang Or-Open hoac And-
      //3. Ket noi cac Gate moi lai voi Grandfather <CurrentID>
        if(countNotEmptyRoom>=2) // Case 3: Room number >2
        {
        Integer lengOfArrayContainsGuest = arrayCountGuestInroom.length;
        String[] arrayGateOfRoom = new String[lengOfArrayContainsGuest];
        String[] arrayListOfNewGate = new String[lengOfArrayContainsGuest];
        Integer numberOfNewGate=0;
        Integer numberOfSingleRoom=0;
        //--------Di duyet cac phong, Phong nao co tren 2 nguoi thi ket noi bang AND-OPEN   
        for(Integer i=0; i<lengOfArrayContainsGuest;i++)
        {
            arrayGateOfRoom[i]= ""; //Gia vo la khong co gate nao ca
            if(arrayCountGuestInroom[i]!=null)
                if(arrayCountGuestInroom[i]>1) // Ket noi tat ca trong phong bang AND-OPEN
                {
                    String gateType="And-Open";
                    String nodeConnectGuestList = currentNodeId;
                    String[] guestList = arrayGuestInRoom[i].split(keySeperateRoom);
                    // Ket noi bang And-OPEN
                    String newGateCreated = ConnectAllGuestInRoom(graph, nodeConnectGuestList, guestList, listOfVertexWithOccurents, gateType);
                    arrayGateOfRoom[i]=newGateCreated;
                    numberOfNewGate++;
                    arrayListOfNewGate[numberOfNewGate-1]=newGateCreated;
                }
                else
                    numberOfSingleRoom++;
        }

        // Kiem tra xem co bao nhieu Phong Single room. Ket noi lai voi nhau bang OR-Open  
        if(numberOfSingleRoom==1) // => Coi nguoi nay la 1 gate
        {
            for(Integer i=0; i<arrayGateOfRoom.length;i++)
            {
                if(arrayGateOfRoom[i].equals(""))
                {
                    numberOfNewGate++;
                    String newGateCreated = arrayGuestInRoom[i].split(keySeperateRoom)[0];
                    arrayGateOfRoom[i]=newGateCreated;
                    arrayListOfNewGate[numberOfNewGate-1]= newGateCreated;
                    break; // Vi chi co 1 phong don la truong hop nay thoi.
                }
            }
        }
        // Ket noi tat ca cac SingleRoom bang 1 Or Gate. Khong dung!!!
        // TRUONG HOP NAY SAU NAY CAN MO RONG RA THEM
        // TRUOC MAT XU LY NHU SAU:
        // 1. Tim gia tri xuat hien nho nhat trong cac singleroom
        // 2. Neu gia tri nho nhat nay >= so lan xuat hien cuar CurrentID => AND - OPEN
        //    Nguoc lai => OR - OPEN
        
        if(numberOfSingleRoom>1) 
        {
            
            
            Integer grandFatherSelfLoopValue = GetGraphAttribute.GetSelfLoopValue(graph, currentNodeId);
            Integer numberOccurentOfGrandFather = listOfVertexWithOccurents.get(currentNodeId) - grandFatherSelfLoopValue;

            String[] listOfSingleNodeToConncet = new String[numberOfSingleRoom];
            Integer pos=0;
            for(Integer i=0; i<arrayGateOfRoom.length;i++)
            {
                if(arrayGuestInRoom[i]!=null)
                {
                    pos++;
                    // Single room nen chi co duy nhat 1 khach: [0]
                    String guestName =arrayGuestInRoom[i].split(keySeperateRoom)[0]; 
                    listOfSingleNodeToConncet[pos-1] = guestName;
                }
            }
            // Gia vo thang dau tien la thang xuat hien it nhat
            Integer minOccurentOfList = listOfVertexWithOccurents.get(listOfSingleNodeToConncet[0])- GetGraphAttribute.GetSelfLoopValue(graph, listOfSingleNodeToConncet[0]);
            for (Integer t=0; t<listOfSingleNodeToConncet.length; t++)
            {
                String currentNode = listOfSingleNodeToConncet[t];
                Integer numberOfCurrentNodeOccurent = listOfVertexWithOccurents.get(currentNode) - GetGraphAttribute.GetSelfLoopValue(graph, currentNode);
                if(numberOfCurrentNodeOccurent<minOccurentOfList)
                    minOccurentOfList=numberOfCurrentNodeOccurent;
            }
            Integer gateId=0;
            String gateType="";
            String gateName="";
            if(minOccurentOfList>numberOccurentOfGrandFather) // Ket noi bang AND-OPEN
            {
                  andOpenId++;
                  gateId=andOpenId;
                  gateType="And-Open";
                  gateName=andOpenName+"#"+gateId.toString();
                  listOfAndOpen.put(gateName,1);
            }
            else //Ket noi bang OR-OPEN
            {
                  orOpenId++;
                  gateId=orOpenId;
                  gateType="Or-Open";
                  gateName=orOpenName+"#"+gateId.toString();
                  listOfOrOpen.put(gateName,1);
            }
            // Ket noi cac single room bang 1 OPEN-GATE
            CreateNormalGate(graph, listOfVertexWithOccurents, currentNodeId, listOfSingleNodeToConncet, gateId, gateType);
            // Them Gate moi vao danh sach cac gate cua cac phong.
            numberOfNewGate++;
            arrayListOfNewGate[numberOfNewGate-1]=gateName;
        }


        // Truong hop newGate =1 => Khong can ket noi them voi Grand Fater
        // NewGate >1 => Ket noi tat ca cac new Gate lai voi nhau voi thang Grand Father.
        if(numberOfNewGate>1)    
        {
            Integer numberOccurentOfGrandFather = listOfVertexWithOccurents.get(currentNodeId) - GetGraphAttribute.GetSelfLoopValue(graph, currentNodeId);

            // Consider the first Gate [0] has the minimum occurent.
            Integer minOccurentOfListNewGate = listOfVertexWithOccurents.get(arrayListOfNewGate[0]) - GetGraphAttribute.GetSelfLoopValue(graph, arrayListOfNewGate[0]);
            for (Integer t=0; t<arrayListOfNewGate.length;t++)
            {
                String currentGate = arrayListOfNewGate[t];
                Integer numberOcurrentOfCurrentGate = listOfVertexWithOccurents.get(currentGate) - GetGraphAttribute.GetSelfLoopValue(graph, currentGate);
                if(numberOcurrentOfCurrentGate<minOccurentOfListNewGate)
                    minOccurentOfListNewGate=numberOcurrentOfCurrentGate;
            }
            Integer gateId=0;
            String gateType="";
            if(minOccurentOfListNewGate>=numberOccurentOfGrandFather)
            {
                andOpenId++;
                gateId=andOpenId;
                gateType="And-Open";
            }
            else
            {
                orOpenId++;
                gateId=orOpenId;
                gateType="Or-Open";
            }
            CreateNormalGate(graph, listOfVertexWithOccurents, currentNodeId, arrayListOfNewGate, gateId, gateType);
        }

      }
                    //---------------------------------------------
    }
    
    
    private static void ProcessICNForFatherList(Graph graph, String currentNodeId, HashMap<String, Integer> fatherList, Map<String, Integer> listOfVertexWithOccurents, Integer maxRoom )
    {
        Map<Integer, String> listRoomOfFather = new HashMap<>(); 
        ICNBuilderV1.AssignRoom(fatherList, listOfVertexWithOccurents, listRoomOfFather);
        Integer[] arrayListRoom = new Integer[maxRoom]; //Danh sach cac phong voi ten phong
        Integer[] arrayCountGuestInroom = new Integer[maxRoom];// Danh sach Dem so khach trong cac phong
        String[] arrayGuestInRoom = new String[maxRoom]; // Danh sach khach trong cac phong
        Integer countNotEmptyRoom=0;
        for (Map.Entry<Integer, String> entry : listRoomOfFather.entrySet()) 
        {
              Integer roomNumber = entry.getKey();
              String guestList = entry.getValue();

              countNotEmptyRoom++;
              arrayListRoom[countNotEmptyRoom-1]=roomNumber;
              arrayGuestInRoom[countNotEmptyRoom-1]=guestList;
              arrayCountGuestInroom[countNotEmptyRoom-1]=guestList.split(keySeperateRoom).length;
        }
        // Case 1: Room number = 1                      
        if(countNotEmptyRoom==1) // ==> Connect All Guest By And-CLOSE
        {
              String[] currentGuestList = arrayGuestInRoom[0].split(keySeperateRoom);
              andCloseId++;
              Integer gateId=andCloseId;
              String gateType="And-Close";
              String[] listOfNodeToConnect= currentGuestList;
              CreateNormalGate(graph,listOfVertexWithOccurents, currentNodeId, listOfNodeToConnect, gateId, gateType);
        }

      //---------------------Number Of room >=2:
      //1. Di xem cac phong. PHong nao co tren 2 nguoi thi ket noi bang And-Open
      //2. Xem cac phong don con lai ket noi voi nhau bang Or-Open hoac And-
      //3. Ket noi cac Gate moi lai voi Grandfather <CurrentID>
        if(countNotEmptyRoom>=2) 
        {
        Integer lengOfArrayContainsGuest = arrayCountGuestInroom.length;
        String[] arrayGateOfRoom = new String[lengOfArrayContainsGuest];
        String[] arrayListOfNewGate = new String[lengOfArrayContainsGuest];
        Integer numberOfNewGate=0;
        Integer numberOfSingleRoom=0;
        //--------Di duyet cac phong, Phong nao co tren 2 nguoi thi ket noi bang AND-CLOSE   
        for(Integer i=0; i<lengOfArrayContainsGuest;i++)
        {
            arrayGateOfRoom[i]= ""; //Gia vo la khong co gate nao ca
            if(arrayGuestInRoom[i]!=null)
                if(arrayCountGuestInroom[i]>1) // Ket noi tat ca trong phong bang AND-CLOSE
                {
                    String gateType="And-Close";
                    String nodeConnectGuestList = currentNodeId;
                    String[] guestList = arrayGuestInRoom[i].split(keySeperateRoom);
                    // Ket noi bang And-OPEN
                    String newGateCreated = ConnectAllGuestInRoom(graph, nodeConnectGuestList, guestList, listOfVertexWithOccurents, gateType);
                    arrayGateOfRoom[i]=newGateCreated;
                    numberOfNewGate++;
                    arrayListOfNewGate[numberOfNewGate-1]=newGateCreated;
                }
                else
                    numberOfSingleRoom++;
        }
        boolean flagFatherContainsOrGate=false;
        for (Map.Entry<String, Integer> entry : fatherList.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(CheckNodeIsOrGate(key))
                flagFatherContainsOrGate=true;
        }

        // Kiem tra xem co bao nhieu Phong Single room. Ket noi lai voi nhau bang OR-Open  
        if(numberOfSingleRoom==1) // => Coi nguoi nay la 1 gate
        {
            for(Integer i=0; i<arrayGateOfRoom.length;i++)
            {
                if(arrayGateOfRoom[i].equals(""))
                {
                    numberOfNewGate++;
                    String newGateCreated = arrayGuestInRoom[i].split(keySeperateRoom)[0];
                    arrayGateOfRoom[i]=newGateCreated;
                    arrayListOfNewGate[numberOfNewGate-1]= newGateCreated;
                    break; // Vi chi co 1 phong don la truong hop nay thoi.
                }
            }
        }
        
        // Ket noi tat ca cac SingleRoom bang 1 Or Gate. Khong dung!!!
        // TRUONG HOP NAY SAU NAY CAN MO RONG RA THEM
        // TRUOC MAT XU LY NHU SAU:
        // 1. Tim gia tri xuat hien nho nhat trong cac singleroom
        // 2. Neu gia tri nho nhat nay >= so lan xuat hien cuar CurrentID => AND - OPEN
        //    Nguoc lai => OR - OPEN
        if(numberOfSingleRoom>1) 
        {
            Integer numberOccurentOfGrandFather = listOfVertexWithOccurents.get(currentNodeId) - GetGraphAttribute.GetSelfLoopValue(graph, currentNodeId);

            String[] listOfSingleNodeToConncet = new String[numberOfSingleRoom];
            Integer pos=0;
            for(Integer i=0; i<arrayGateOfRoom.length;i++)
            {
                if(arrayGuestInRoom[i]!=null)
                    if(arrayGateOfRoom[i].equals(""))
                    {
                        pos++;
                        // Single room nen chi co duy nhat 1 khach: [0]
                        listOfSingleNodeToConncet[pos-1]=arrayGuestInRoom[i].split(keySeperateRoom)[0];
                    }
            }
            // Gia vo thang dau tien la thang xuat hien it nhat
            Integer minOccurentOfList = listOfVertexWithOccurents.get(listOfSingleNodeToConncet[0]) - GetGraphAttribute.GetSelfLoopValue(graph, listOfSingleNodeToConncet[0]);
            for (Integer t=0; t<listOfSingleNodeToConncet.length; t++)
            {
                String currentNode = listOfSingleNodeToConncet[t];
                Integer numberOfCurrentNodeOccurent = listOfVertexWithOccurents.get(currentNode) - GetGraphAttribute.GetSelfLoopValue(graph, currentNode);
                if(numberOfCurrentNodeOccurent<minOccurentOfList)
                    minOccurentOfList=numberOfCurrentNodeOccurent;
            }
            Integer gateId=0;
            String gateType="";
            String gateName="";
            
                    
                    
            if(minOccurentOfList>numberOccurentOfGrandFather &&!flagFatherContainsOrGate) // Ket noi bang AND-Close
            {
                  andCloseId++;
                  gateId=andCloseId;
                  gateType="And-Close";
                  gateName=andCloseName+"#"+gateId.toString();
                  listOfAndClose.put(gateName,1);
            }
            else //Ket noi bang OR-Close
            {
                  orCloseId++;
                  gateId=orCloseId;
                  gateType="Or-Close";
                  gateName=orCloseName+"#"+gateId.toString();
                  listOfOrClose.put(gateName,1);
            }
            // Ket noi cac single room bang 1 OPEN-GATE
            CreateNormalGate(graph, listOfVertexWithOccurents, currentNodeId, listOfSingleNodeToConncet, gateId, gateType);
            // Them Gate moi vao danh sach cac gate cua cac phong.
            numberOfNewGate++;
            arrayListOfNewGate[numberOfNewGate-1]=gateName;
        }


        // Truong hop newGate =1 => Khong can ket noi them voi Grand Fater
        // NewGate >1 => Ket noi tat ca cac new Gate lai voi nhau voi thang Grand Father.
        if(numberOfNewGate>1)    
        {
            Integer numberOccurentOfGrandFather = listOfVertexWithOccurents.get(currentNodeId) - GetGraphAttribute.GetSelfLoopValue(graph, currentNodeId);

            // Consider the first Gate [0] has the minimum occurent.
            Integer minOccurentOfListNewGate = listOfVertexWithOccurents.get(arrayListOfNewGate[0]) - GetGraphAttribute.GetSelfLoopValue(graph, arrayListOfNewGate[0]);
            for (Integer t=0; t<arrayListOfNewGate.length;t++)
            {
                String currentGate = arrayListOfNewGate[t];
                Integer numberOcurrentOfCurrentGate = listOfVertexWithOccurents.get(currentGate) - GetGraphAttribute.GetSelfLoopValue(graph, currentGate);
                if(numberOcurrentOfCurrentGate<minOccurentOfListNewGate)
                    minOccurentOfListNewGate=numberOcurrentOfCurrentGate;
            }
            Integer gateId=0;
            String gateType="";
            if(minOccurentOfListNewGate>numberOccurentOfGrandFather && !flagFatherContainsOrGate)
            {
                andCloseId++;
                gateId=andCloseId;
                gateType="And-Close";
            }
            else
            {
                orOpenId++;
                gateId=orOpenId;
                gateType="Or-Close";
            }
            CreateNormalGate(graph, listOfVertexWithOccurents, currentNodeId, arrayListOfNewGate, gateId, gateType);
        }

      }
                    //---------------------------------------------
    }
    
    
    
    private static String ConnectAllGuestInRoom(Graph graph,String nodeConnectGuestList, String[] guestList, Map<String, Integer> listOfVertexWithOccurents, String gateType)
    {
        if(guestList.length<=1)
            return "";
        
        andOpenId++;
        Integer gateId=andOpenId;
        String gateName = "";
        String nodeColor="";
        String nodeType="";
        Integer numberOfOcurrent = listOfVertexWithOccurents.get(guestList[0]);
        
        switch (gateType)
        {
            case "And-Open":
                gateName=andOpenName+"#"+gateId.toString();
                nodeColor=andNodeColor;
                nodeType=andNodeType;
                listOfAndOpen.put(gateName,1);
                break;
            case "Or-Open":
                gateName=orOpenName+"#"+gateId.toString();
                nodeColor=orNodeColor;
                nodeType=orNodeType;
                listOfOrOpen.put(gateName,1);
                break;
            case "And-Close":
                gateName=andCloseName+"#"+gateId.toString();
                nodeColor=andNodeColor;
                nodeType=andNodeType;
                listOfAndClose.put(gateName,1);
                break;
            case "Or-Close":
                gateName=orCloseName+"#"+gateId.toString();
                nodeColor=orNodeColor;
                nodeType=orNodeType;
                listOfOrClose.put(gateName,1);
                break;
        }
        
        graph.addNode(gateName);
                
        SetGraphAttribute.SetNodeAttrib(graph, gateName, nodeSize, nodeColor, nodeType);
        listOfVertexWithOccurents.put(gateName, numberOfOcurrent);
        try {
            
            for(Integer i=0;i<guestList.length;i++)
            {
                String currentGuestId = guestList[i];
                String currentEdgeLabel="";
                Edge edge=null;
                if(gateType.toUpperCase().equals("OPEN"))
                {
                    //Ghi thong tin gia tri cua Label tren Edge cu. Sau do xoa Edge nay di.
                    currentEdgeLabel = graph.getEdge(nodeConnectGuestList+keyConnectNode+currentGuestId).getAttribute("ui.label");
                    graph.removeEdge(nodeConnectGuestList+keyConnectNode+currentGuestId);
                    // Tao Edge moi ket noi cong moi <gateName> voi khach hien tai.
                    graph.addEdge(gateName+keyConnectNode+currentGuestId, gateName, currentGuestId, true);
                    edge = graph.getEdge(gateName+keyConnectNode+currentGuestId);
                }
                if(gateType.toUpperCase().equals("CLOSE"))
                {
                    //Ghi thong tin gia tri cua Label tren Edge cu. Sau do xoa Edge nay di.
                    currentEdgeLabel = graph.getEdge(currentGuestId+keyConnectNode+nodeConnectGuestList).getAttribute("ui.label");
                    graph.removeEdge(currentGuestId+keyConnectNode+nodeConnectGuestList);
                    
                    // Tao Edge moi ket noi cong moi <gateName> voi khach hien tai.
                    graph.addEdge(currentGuestId+keyConnectNode+gateName, currentGuestId, gateName,true);
                    edge = graph.getEdge(currentGuestId+keyConnectNode+gateName);
                }

                if(currentEdgeLabel.equals(""))
                {
                    String thisGuestList = "";
                    for(Integer m=0; m<guestList.length; m++)
                        thisGuestList+=guestList[i] + "\n";
                    
                    MessageHelper.Warning("The log is not a formal workflow process model. I can not create gate to connect activity together. Activity: "
                            + nodeConnectGuestList +"\n. Activity List to be connect: \n" 
                            + thisGuestList);
                    
                }
                if(edge!=null)
                    edge.setAttribute("ui.label", currentEdgeLabel);

            }
            
            
        } catch (Exception e) {
            MessageHelper.Warning(e.toString());
        }
        
        
        return gateName;
        
    }
    private static Integer SumOfAllRoom(Map<String, Integer> listOfVertexWithOccurents, String[] currentGuestList)
    {
        Integer result=0;
        for(Integer i=0;i<currentGuestList.length;i++)
        {
            String currenetGest = currentGuestList[i];
            result+=listOfVertexWithOccurents.get(currenetGest);
        }
        return result;
    }
    
    private static void ProcessForTheOpenGate(Graph graph, String currentNodeId, HashMap<String, Integer> sonList,Map<String, Integer> listOfVertexWithOccurents)
    {
        //-----------------------Processing for the open node---------------------------------------------
            
            String fatherId=currentNodeId;
                        
            Integer sizeOfSonList = sonList.size();
            if(sizeOfSonList<=1) //We don't care about Father has only Son.
                return;
            
            String NodeXID=fatherId;
            // ------------Step 2: Put Son with the same occurent to the same room in the house------------
            houseOfSon = new HashMap<>();
            PutVertexToHouseOccurents(sonList,listOfVertexWithOccurents,houseOfSon);
            
            //-------------Step 3: Sort the room number in the house
            houseOfSon=SortAlgorithm.SortByRoomNumber(houseOfSon);
            
            //---------------Step 4: Build ICN Model base the roomnumber  in the House
            //--------Consider How many room are there in the house
            
            Integer numberOfRoom = houseOfSon.size();
            Integer[] arrayListRoom = new Integer[numberOfRoom];
            Integer[] arrayCountGuestInroom = new Integer[numberOfRoom];
            String[] arrayGuestInRoom = new String[numberOfRoom];
            Integer count=0;
            
            //Fill the roomNumber to arrayListRoom, list of Guest for each room to arrayGuestInRoom
            for (Map.Entry<Integer, String> entry : houseOfSon.entrySet()) {
                Integer roomNumber = entry.getKey();
                String guestList = entry.getValue();
                
                count++;
                arrayListRoom[count-1]=roomNumber;
                arrayGuestInRoom[count-1]=guestList;
                arrayCountGuestInroom[count-1]=guestList.split(keySeperateRoom).length;
                
            }
            // Cac phong co hon hai con chung bo thi tao AND-OPEN.
            for (Integer i=0; i<count;i++)
            {
                if(arrayCountGuestInroom[i]>=2)
                {
                    String[] currentGuestList = arrayGuestInRoom[i].split(keySeperateRoom);
                    Integer gateId=0;
                    String gateType;
                    //---------Neu tong cua Gest = Father ==> OR
                    if(Objects.equals(listOfVertexWithOccurents.get(NodeXID), SumOfAllRoom(listOfVertexWithOccurents, currentGuestList)))
                    {
                        orOpenId++;
                        gateId = orOpenId;
                        gateType = "Or-Open";
                    }
                    else // tong cua Gest <> Father =>tao AND. 
                    {
                        andOpenId++;
                        gateId=andOpenId;
                        gateType="And-Open";
                    }
                    
                    String[] listOfNodeToConnect= currentGuestList;
                    CreateNormalGate(graph,listOfVertexWithOccurents, NodeXID, listOfNodeToConnect, gateId, gateType);
                    arrayGuestInRoom[i]=""; //DONE. Remove all guest out of room.
                }
            }
            
            //----------------------------------------------------------------------
            // Xu ly Or Open cho tat cac cac phong con lai. Cac phong con lai chi co duy nhat 1 khach
            Integer guestConnectByOrCount=0;
            String[] listOfNodeToConnect= new String[count];
            for (Integer i=0;i<count;i++)
            {
                String[] currentGuestList = arrayGuestInRoom[i].split(keySeperateRoom);
                if(currentGuestList.length>0 && !currentGuestList[0].equals("")) // Co' duy nhat 1 khach. nhung thang bang 0 do da xu ly: ""
                {
                    guestConnectByOrCount++;
                    listOfNodeToConnect[guestConnectByOrCount-1]=currentGuestList[0];
                }
            }
            
            // Truong hop nay can xem xet ttinh dung dan???
            // Co the kiem tra tong cac node con lai
            boolean flagOpenGateIsStandard=false;
            Integer n = guestConnectByOrCount;
            Integer sum=0;
            if(n>0) // Co phong co khach don de ket noi lai voi nhau
                for(Integer j=0;j<n;j++)
                {
                    String currentNode=listOfNodeToConnect[j];
                    if(!currentNode.equals("") && currentNode.length()>0 && currentNode!=null)
                        sum+=listOfVertexWithOccurents.get(currentNode);
                }
            if(listOfVertexWithOccurents.get(NodeXID)==sum)        
                flagOpenGateIsStandard=true;
            
            if (flagOpenGateIsStandard)
            {
                orOpenId++;
                Integer gateId=orOpenId;
                String gateType="Or-Open";
                CreateNormalGate(graph, listOfVertexWithOccurents, NodeXID, listOfNodeToConnect, gateId, gateType);
            }

            //-------------------------------------------------------------------
    }
    private static void ProcessForTheCloseGate(Graph graph, String currentNodeId, HashMap<String, Integer> fatherList,Map<String, Integer> listOfVertexWithOccurents)
    {
        //-----------------------Processing for the open node---------------------------------------------
            String sonId=currentNodeId;
                        
            Integer sizeOfFatherList = fatherList.size();
            if(sizeOfFatherList<=1) //We don't care about Son has only Father
                return;
            
            String NodeXID=sonId;
            // ------------Step 2: Put Father with the same occurent to the same room in the house------------
            houseOfFather = new HashMap<>();
            PutVertexToHouseOccurents(fatherList,listOfVertexWithOccurents,houseOfFather);
            
            //-------------Step 3: Sort the room number in the house
            houseOfFather=SortAlgorithm.SortByRoomNumber(houseOfFather);
            
            //---------------Step 4: Build ICN Model base the roomnumber  in the House
            //--------Consider How many room are there in the house
            
            Integer numberOfRoom = houseOfFather.size();
            Integer[] arrayListRoom = new Integer[numberOfRoom];
            Integer[] arrayCountGuestInroom = new Integer[numberOfRoom];
            String[] arrayGuestInRoom = new String[numberOfRoom];
            Integer count=0;
            
            //Fill the roomNumber to arrayListRoom, list of Guest for each room to arrayGuestInRoom
            for (Map.Entry<Integer, String> entry : houseOfFather.entrySet()) {
                Integer roomNumber = entry.getKey();
                String guestList = entry.getValue();
                
                count++;
                arrayListRoom[count-1]=roomNumber;
                arrayGuestInRoom[count-1]=guestList;
                arrayCountGuestInroom[count-1]=guestList.split(keySeperateRoom).length;
                
            }
            // Cac phong co hon hai Bo chung thi tao AND-CLOSE.
            for (Integer i=0; i<count;i++)
            {
                if(arrayCountGuestInroom[i]>=2)
                {
                    String[] currentGuestList = arrayGuestInRoom[i].split(keySeperateRoom);
                    andCloseId++;
                    Integer gateId=andCloseId;
                    String gateType="And-Close";
                    String[] listOfNodeToConnect= currentGuestList;

                    CreateNormalGate(graph,listOfVertexWithOccurents, NodeXID, listOfNodeToConnect, gateId, gateType);
                    
                    arrayGuestInRoom[i]=""; //DONE. Remove all guest out of room.
                }
            }
            
            //----------------------------------------------------------------------
            // Xu ly Or Open cho tat cac cac phong con lai. Cac phong con lai chi co duy nhat 1 khach
            Integer guestConnectByOrCount=0;
            String[] listOfNodeToConnect= new String[count];
            for (Integer i=0;i<count;i++)
            {
                String[] currentGuestList = arrayGuestInRoom[i].split(keySeperateRoom);
                if(currentGuestList.length>0 ) // Co' duy nhat 1 khach. nhung thang bang 0 do da xu ly: ""
                {
                    guestConnectByOrCount++;
                    listOfNodeToConnect[guestConnectByOrCount-1]=currentGuestList[0];
                }
            }
            
            // Remove Node = ""
            boolean flagGuestConnectByOrCount=true;
            String[] realListOfNodeToConnect = new String[count];
            Integer n=0;
            for(Integer k=0; k<listOfNodeToConnect.length;k++)
            {
                if(!listOfNodeToConnect[k].equals(""))
                {
                    n++;
                    realListOfNodeToConnect[n-1]=listOfNodeToConnect[k];
                }
            }
            // Truong hop nay can xem xet ttinh dung dan???
            // Co the kiem tra tong cac node con lai
            
            boolean flagOpenGateIsStandard=false;
            Integer sum=0;
            if(n>0)
                for(Integer j=0;j<n;j++)
                {
                    String currentNode=realListOfNodeToConnect[j];
                    if(!currentNode.equals("") && currentNode.length()>0)
                        sum+=listOfVertexWithOccurents.get(currentNode);
                }
            if(listOfVertexWithOccurents.get(NodeXID)==sum)        
                flagOpenGateIsStandard=true;
            
            if (flagOpenGateIsStandard)
            {
                orCloseId++;
                Integer gateId=orCloseId;
                String gateType="Or-Close";
                CreateNormalGate(graph, listOfVertexWithOccurents, NodeXID, realListOfNodeToConnect, gateId, gateType);
            }


            //-------------------------------------------------------------------
    }
    public static void Initialize()
    {
        orOpenId=0;
        orCloseId=0;
        andOpenId=0;
        andCloseId=0;
        loopOpenId=0;
        loopCloseId=0;
    }
    
    public static void BuildICN(Graph graph, Map<String, Integer> listOfVertexWithOccurents, String _colorOfAndGate, String _colorOfOrGate, String _colorOfLoopGate)
    {
        
        List<String> nodeOrderToVisitList = GraphAlgorithm.CreateListOrderToVisitBFS(graph, "START", "END");
        Integer listNodeLength = nodeOrderToVisitList.size();
        for (Integer i=0;i<listNodeLength;i++)
        {
            String currentNodeId=nodeOrderToVisitList.get(i);
            
            HashMap<String, Integer> sonList = new HashMap<>(); 
            HashMap<String, Integer> fatherList = new HashMap<>(); 
            if(CheckNodeIsGate(currentNodeId))
                continue;
            
            FindFatherListAndSonList(graph, currentNodeId, listOfVertexWithOccurents, fatherList, sonList);
            
            // Xu ly cac truong hop chinh tac truoc.            
            ProcessTriangle(graph, currentNodeId, sonList, listOfVertexWithOccurents);  // NGuy hiem voi truong hop khac. Large bank transaction thi okay.
            ProcessForTheOpenGate(graph, currentNodeId, sonList,listOfVertexWithOccurents);
            ProcessForTheCloseGate(graph, currentNodeId, fatherList, listOfVertexWithOccurents);
        }
        
        // Xu ly cho truong hop phuc tap. CAN NANG CAP.
         FixICNProblem(graph, listOfVertexWithOccurents);
         
         //==================== Tim Loop gate tu Illogical Vertex, doi ten===========
         // Luu y: Chi xep hang Level duoc sau khi da tao Gate.
         queueVertexWithLevel = ICNChecker.AssignLevelGraphBFS(graph, "START");
         FindLoopGate(graph, Variables.nodeOccurenceMap, queueVertexWithLevel);
         //========================================================================
         
         
         
        
    }
    
    private static void PutVertexToHouseOccurents(HashMap<String, Integer> sonList,Map<String, Integer> listOfVertexWithOccurents,Map<Integer, String> houseOfSon)
    {
        for (Map.Entry<String, Integer> entry : sonList.entrySet()) {
            String sonId = entry.getKey();
            Integer currentSonOccurent = listOfVertexWithOccurents.get(sonId);
            
            // Put Cow to his room algorithm
            Integer roomNumber = currentSonOccurent;
            if(!houseOfSon.containsKey(roomNumber))
                houseOfSon.put(roomNumber, sonId);
            else
                houseOfSon.put(roomNumber, houseOfSon.get(roomNumber)+keySeperateRoom+sonId);
        }
    }
    
    public static void AssignRoom(HashMap<String, Integer> guestList,Map<String, Integer> listOfVertexWithOccurents,Map<Integer, String> roomList)
    {
        for (Map.Entry<String, Integer> entry : guestList.entrySet()) {
            String guestId = entry.getKey();
            Integer currentguestOccurent = listOfVertexWithOccurents.get(guestId);
            
            // Put Cow to his room algorithm
            Integer roomNumber = currentguestOccurent;
            if(!roomList.containsKey(roomNumber))
                roomList.put(roomNumber, guestId);
            else
                roomList.put(roomNumber, roomList.get(roomNumber)+keySeperateRoom+guestId);
        }
        SortAlgorithm.SortByRoomNumber(roomList);
    }
    // Return the maximium occurence of the member in group listOfNodeToConnect
    private static Integer CalculateOccurenceForAndCloseGate(Graph graph, Map<String, Integer> listOfVertexWithOccurents, String[] listOfNodeToConnect)
    {
        Integer result=0;
        for(Integer i=0;i<listOfNodeToConnect.length;i++)
        {
            String currentId = listOfNodeToConnect[i];
            Integer currentOccurence=listOfVertexWithOccurents.get(currentId) - GetGraphAttribute.GetSelfLoopValue(graph, currentId) ;
            if(result<currentOccurence)
                result=currentOccurence;
        }
        
        return result;
    }
    
    public static String CreateNormalGate(Graph graph,Map<String, Integer> listOfVertexWithOccurents,String NodeXId,String[] listOfNodeToConnect,Integer gateId,String gateType)
    {
        if(listOfNodeToConnect.length==1 && listOfNodeToConnect[0]==null)
            return "NULL";
        String gateName="";
        String gateAttrib="";
        String nodeColor="";
        String nodeType="";
        switch (gateType.toUpperCase())
        {
            case "AND-OPEN":
                gateName=andOpenName+"#"+gateId.toString();
                nodeColor=andNodeColor;
                nodeType=andNodeType;
                listOfAndOpen.put(gateName,1);
                break;
            case "OR-OPEN":
                gateName=orOpenName+"#"+gateId.toString();
                nodeColor=orNodeColor;
                nodeType=orNodeType;
                listOfOrOpen.put(gateName,1);
                break;
            case "LOOP-OPEN": // Tinh chat cua Loop Nen doi ten nguoc nhau giua OPEN va CLOSE
                gateName=loopCloseName+"#"+gateId.toString();
                nodeColor=loopNodeColor;
                nodeType=loopNodeType;
                listOfLoopClose.put(gateName,1);
                break;
            case "AND-CLOSE":
                gateName=andCloseName+"#"+gateId.toString();
                nodeColor=andNodeColor;
                nodeType=andNodeType;
                listOfAndClose.put(gateName,1);
                break;
            case "OR-CLOSE":
                gateName=orCloseName+"#"+gateId.toString();
                nodeColor=orNodeColor;
                nodeType=orNodeType;
                listOfOrClose.put(gateName,1);
                break;
            case "LOOP-CLOSE":  // Tinh chat cua Loop Nen doi ten nguoc nhau giua OPEN va CLOSE
                gateName=loopOpenName+"#"+gateId.toString();
                nodeColor=loopNodeColor;
                nodeType=loopNodeType;
                listOfLoopOpen.put(gateName,1);
                break;
        }
        
        //Create Gate and set attribute. Create arc from NodeX -> Gate and set Attribute
        
        graph.addNode(gateName);
        queueVertexWithLevel.put(gateName, queueVertexWithLevel.get(NodeXId));
        
//        O day phai cap nhap ca Adjacency List
        
        SetGraphAttribute.SetNodeAttrib(graph, gateName, nodeSize, nodeColor, nodeType);
        
        Integer numberOccurence = listOfVertexWithOccurents.get(NodeXId)-GetGraphAttribute.GetSelfLoopValue(graph, NodeXId);
        if(gateType.toUpperCase().equals("AND-CLOSE"))
            numberOccurence = CalculateOccurenceForAndCloseGate(graph, listOfVertexWithOccurents, listOfNodeToConnect);
        
        listOfVertexWithOccurents.put(gateName, numberOccurence);
        Edge edge = null;
        if(gateType.toUpperCase().contains("OPEN"))
        {
            graph.addEdge(NodeXId+keyConnectNode+gateName, NodeXId, gateName,true);
            edge = graph.getEdge(NodeXId+keyConnectNode+gateName);
        }
        
        if(gateType.toUpperCase().contains("CLOSE"))
        {
            graph.addEdge(gateName+keyConnectNode+NodeXId, gateName, NodeXId,true);
            edge = graph.getEdge(gateName+keyConnectNode+NodeXId);
        }
        Integer nodeXIdOccurent=listOfVertexWithOccurents.get(NodeXId);
        
        String nodeAId=edge.getNode0().getId(), nodeBId=edge.getNode1().getId();
        
        
        
        if(!CheckNodeIsGate(nodeAId) || !CheckNodeIsGate(nodeBId))
            SetGraphAttribute.SetNewEdgeValue(graph, nodeAId, nodeBId, listOfVertexWithOccurents);
        else
            edge.setAttribute("ui.label", nodeXIdOccurent.toString());
        
        
        
//----------------Create relationship between gate and Son. Delete arc from Father to Son-------------------------
        for(Integer i=0; i<listOfNodeToConnect.length;i++)
        {
            String currentNodeId = listOfNodeToConnect[i];
            if(currentNodeId==null)
                continue;
            String newEdgeLabel="";
            try {
            //Delete arc from NodeX-> Son    
            
            if(gateType.toUpperCase().contains("OPEN"))
            {
                newEdgeLabel = SetGraphAttribute.GetEdgeLabel(graph, NodeXId, currentNodeId);
                SetGraphAttribute.RemoveEdge(graph, NodeXId, currentNodeId);
            }

            if(gateType.toUpperCase().contains("CLOSE"))//Delete arc from Son -> Nodex. Close gate
            {
                newEdgeLabel = SetGraphAttribute.GetEdgeLabel(graph, currentNodeId, NodeXId);
                SetGraphAttribute.RemoveEdge(graph, currentNodeId, NodeXId);
            }
           } catch (Exception e) {
                MessageHelper.Warning("Edge doesn't exist! \n"+e.toString());
           }

            //Create arc from Gate to Son. Set attribute
            
            String originalEdgeLabel=null;
            
            try {
               Edge newEdge = null;
               if(gateType.toUpperCase().contains("OPEN"))
               {
                   SetGraphAttribute.AddEdge(graph, gateName, currentNodeId, true);
                   newEdge = graph.getEdge(gateName+keyConnectNode+currentNodeId);
               }

               if(gateType.toUpperCase().contains("CLOSE"))
               {
                   SetGraphAttribute.AddEdge(graph, currentNodeId, gateName, true);
                   newEdge = graph.getEdge(currentNodeId+keyConnectNode+gateName);
                   
               }
               /* New approach.... */
                Integer edgeVal = listOfVertexWithOccurents.get(currentNodeId);
                if(edgeVal==null) edgeVal=0;
                newEdge.setAttribute("ui.label", edgeVal.toString());
               
//               if(newEdge!=null)
//               {
//                   String node0Id = newEdge.getNode0().getId();
//                   String node1Id = newEdge.getNode1().getId();
//                   if(!CheckNodeIsGate(node0Id) || !CheckNodeIsGate(node1Id))
//                        SetGraphAttribute.SetNewEdgeValue(graph, node0Id, node1Id, listOfVertexWithOccurents);
//                   else
//                   {
////                       newEdgeLabel = GetGraphAttribute.GetFatherEdgeValueOfGate(graph, node0Id).toString();
//                       System.err.println("Get Value from father: " + node0Id+keyConnectNode+node1Id + ": "+newEdgeLabel);
//                       newEdge.addAttribute("ui.label", newEdgeLabel);
//                   }
//               }



           } catch (Exception e) {
               MessageHelper.Warning(e.toString());
           }
        }
        return gateName;
    }
    
    private static void ProcessTriangle(Graph graph, String currentNodeId, HashMap<String, Integer> sonList,Map<String, Integer> listOfVertexWithOccurents)
    {
        //Only Process for Son List is Okay (I Think)
        Integer sizeOfSonList = sonList.size();
        if(sizeOfSonList<2)
            return;
        
        String[] listStringSon = new String[sizeOfSonList];
        String[] triangleListForOpen = new String[2];
        String[] triangleListForClose = new String[2];
        String aTriangle = currentNodeId;
        String bTriangle = "";
        String cTriangle = "";
        Integer count=0;
        
        for (Map.Entry<String, Integer> entry : sonList.entrySet()) {
            count++;
            String key = entry.getKey();
            listStringSon[count-1]=key;
        }
        for(Integer i=0;i<count-1;i++)
        {
            bTriangle=listStringSon[i];
            cTriangle=listStringSon[i+1];
            
            // exist Arc from B -> C  ==>  A->B, A->C and B->C
            // A is Or-Open, C is Or-Close.  (Chi ton tai tu B-> C va khong ton tai tu C->B
            if((graph.getEdge(bTriangle+keyConnectNode+cTriangle)!=null) && (graph.getEdge(cTriangle+keyConnectNode+bTriangle)==null)) //=> C is close Gate
            {
                triangleListForOpen[0]=bTriangle;
                triangleListForOpen[1]=cTriangle;
                orOpenId++;
                Integer gateId=orOpenId;
                String gateType="Or-Open";
                CreateNormalGate(graph, listOfVertexWithOccurents, aTriangle, triangleListForOpen, gateId, gateType);
                
                String gateName=orOpenName+"#"+gateId.toString();
                
                triangleListForClose[0]=gateName;
                triangleListForClose[1]=bTriangle;
                orCloseId++;
                Integer gateCloseId=orCloseId;
                gateType="Or-Close";
                CreateNormalGate(graph, listOfVertexWithOccurents, cTriangle, triangleListForClose, gateCloseId, gateType);
            }
            // Chi ton tai tu C->B. Khong ton tai tu B->C   =========>  A is open, B is Close Gate
            if((graph.getEdge(cTriangle+keyConnectNode+bTriangle)!=null)&&(graph.getEdge(bTriangle+keyConnectNode+cTriangle)==null)) // A is open, B is Close Gate
            {
                triangleListForOpen[0]=cTriangle;
                triangleListForOpen[1]=bTriangle;
                orOpenId++;
                Integer gateId=orOpenId;
                String gateType="Or-Open";
                CreateNormalGate(graph, listOfVertexWithOccurents, aTriangle, triangleListForOpen, gateId, gateType);
                
                String gateName=orOpenName+"#"+gateId.toString();
                
                triangleListForClose[0]=gateName;
                triangleListForClose[1]=cTriangle;
                orCloseId++;
                Integer gateCloseId=orCloseId;
                gateType="Or-Close";
                CreateNormalGate(graph, listOfVertexWithOccurents, bTriangle, triangleListForClose, gateCloseId, gateType);
            }
        }
        
    }
    public static void FindFatherListAndSonList(Graph graph, String currentNodeId, Map<String, Integer> listOfVertexWithOccurents, HashMap<String, Integer> fatherList, HashMap<String, Integer> sonList )
    {
        // -----------Find father List---------------
            String sonId=currentNodeId;
            String fatherId = currentNodeId;
            
            Node sonNode=graph.getNode(sonId);
            Node fatherNode=graph.getNode(fatherId);
            
            //Tim danh sach list Of son
            
            Iterator<Edge> iterLeavingEdge = fatherNode.getLeavingEdgeIterator();
            while (iterLeavingEdge.hasNext()) 
            {
                Edge nextEdge = iterLeavingEdge.next();
                String currentSonId = nextEdge.getNode1().getId();
                if(!currentSonId.equals(fatherId))
                    sonList.put(currentSonId, listOfVertexWithOccurents.get(currentSonId));
            }
            
            Iterator<Edge> iterEnteringEdge = sonNode.getEnteringEdgeIterator();
            while (iterEnteringEdge.hasNext()) 
            {
                Edge nextEdge = iterEnteringEdge.next();
                String currentFatherId = nextEdge.getNode0().getId();
                if(!currentFatherId.equals(sonId))
                    fatherList.put(currentFatherId, listOfVertexWithOccurents.get(currentFatherId));
            }
            
    }
    public static void FillProportionalToGate(Graph graph,Map<String, Integer> listOfVertexWithOccurents)
    {
       for(Node node:graph)
        {
            HashMap<String, Integer> sonList = new HashMap<>(); 
            HashMap<String, Integer> fatherList = new HashMap<>(); 
            String currentNodeId=node.getId();
            boolean isGateOpen = false;
            boolean isGateClose = false;
            if(currentNodeId.toUpperCase().contains("OPEN"))
                isGateOpen=true;
            if(currentNodeId.toUpperCase().contains("CLOSE"))
                isGateClose=true;
            
            
            if(!isGateOpen && !isGateClose)
                continue;
            
            FindFatherListAndSonList(graph, currentNodeId, listOfVertexWithOccurents, fatherList, sonList);
            Integer occurentOfGate = listOfVertexWithOccurents.get(currentNodeId); 
            if(isGateOpen)
            {
                String node0Id = currentNodeId;
                for (Map.Entry<String, Integer> entry : sonList.entrySet()) {
                    String key = entry.getKey();
                    String node1Id = key;
                    Edge edge = graph.getEdge(node0Id+keyConnectNode+node1Id);
                    String attribute = edge.getAttribute("ui.label");
                    
                    try {
                        if(isStringInteger(attribute))
                        {
                            Integer occurentOfLabel = Integer.parseInt(attribute);
                            Double proportional = occurentOfLabel.doubleValue()/occurentOfGate.doubleValue();

                            DecimalFormat decimalFormat = new DecimalFormat("0.000");
                            String proportionalString=decimalFormat.format(proportional);
                            String newAttribute = attribute + "(p:"+ proportionalString + ")";
                            edge.setAttribute("ui.label", newAttribute);
                        }
                        
                    } catch (Exception e) {
                        MessageHelper.Warning(e.toString());
                    }
                }
            }
            if(isGateClose)
            {
                String node1Id = currentNodeId;
                for (Map.Entry<String, Integer> entry : fatherList.entrySet()) {
                    String key = entry.getKey();
                    String node0Id = key;
                    Edge edge = graph.getEdge(node0Id+keyConnectNode+node1Id);
                    String attribute = edge.getAttribute("ui.label");
                    
                    
                    try {
                        if(isStringInteger(attribute))
                        {
                            Integer occurentOfLabel = Integer.parseInt(attribute);
                            Double proportional = occurentOfLabel.doubleValue()/occurentOfGate.doubleValue();

                            DecimalFormat decimalFormat = new DecimalFormat("0.000");
                            String proportionalString=decimalFormat.format(proportional);
                            String newAttribute = attribute + "(p:"+ proportionalString + ")";
                            edge.setAttribute("ui.label", newAttribute);
                        }
                        
                    } catch (Exception e) {
                        MessageHelper.Warning(e.toString());
                    }
                }
                
            }
        }
    }
    public static boolean isStringInteger(String number)
    {
        try{
            Integer.parseInt(number);
        }catch(Exception e ){
            return false;
        }
        return true;
    }
    public static String FindNearestOpenFather(Graph graph, String sonId)
    {
        LinkedList<String> queueOfSon = new LinkedList<>();
        Map<String, Integer> listVisitedVertex = new HashMap<>();
        queueOfSon.add(sonId);
        listVisitedVertex.put(sonId, 1);
        
        while (queueOfSon.size()>0)
        {
            String currentSonId = queueOfSon.pop();
            Node sonNode = graph.getNode(currentSonId);
            
            Iterator<Edge> edge = sonNode.getEnteringEdgeIterator();
            while (edge.hasNext()) 
            {
                Edge next = edge.next();
                String currentFatherId = next.getNode0().getId();
                if(currentFatherId.toUpperCase().contains("OPEN"))
                    return currentFatherId;
                
                if(!listVisitedVertex.containsKey(currentFatherId))
                {
                    queueOfSon.add(currentFatherId);
                    listVisitedVertex.put(currentFatherId, 1);
                }
            }
        }
        return "NULL";
    }
    public static void FindLoopGate(Graph graph, Map<String, Integer> listOfVertexWithOccurents,Map<String, Integer> _queueVertexWithLevel)
    {
        Collection<Edge> edges = graph.getEdgeSet();
        for (Edge edge:edges)
        {
            String node0Id = edge.getNode0().getId();
            String node1Id = edge.getNode1().getId();
            Integer node0Level = _queueVertexWithLevel.get(node0Id);
            Integer node1Level = _queueVertexWithLevel.get(node1Id);
            if(node0Level == null)
                node0Level = 0;
            if(node1Level == null)
                node1Level = 0;
            
//            if(node0Level==null | node1Level==null) // Truong hop them cac node vao chua co Level.
//                continue;
            if(node0Level!=0 && node1Level!=0 && node0Level>node1Level && node1Id.toUpperCase().contains("OR")) // Bat thuong
            {
                String openGateId = FindNearestOpenFather(graph, node0Id);
                if(openGateId.contains("NULL")) // Khong tim thay Open Gate as a Father.
                    continue;
                String closeGateId = node1Id;
                boolean aTob = false, bToa = false;
                
                aTob = GraphAlgorithm.isReachable(graph,  openGateId, closeGateId);
                bToa = GraphAlgorithm.isReachable(graph,  closeGateId, openGateId);
                if(aTob && bToa) // Tim duoc 1 cap LOOP => Highlight
                {
                    //Cho vao danh sach moi
//                    loopOpenId++;
//                    String newOpenName = loopOpenName +"#"+loopOpenId.toString();
//                    listOfLoopOpen.put(newOpenName, 1);
//                    loopCloseId++;
//                    String newCloseName = loopCloseName +"#"+loopCloseId.toString();
//                    listOfLoopClose.put(newCloseName, 1);
                    
                    loopOpenId++;loopCloseId++;
                    // Cho vao danh sach moi. Doi ten Open <-> Close
                    String newOpenName = loopCloseName +"#"+loopCloseId.toString();
                    listOfLoopOpen.put(newOpenName, 1);
                    
                    String newCloseName = loopOpenName +"#"+loopOpenId.toString();
                    listOfLoopClose.put(newCloseName, 1);



                    
                    // Change name 
                    SetGraphAttribute.ChangeNodeIdName(graph, openGateId, newCloseName);
                    SetGraphAttribute.ChangeNodeIdName(graph, closeGateId, newOpenName );
                    
                    Integer occurentOfNewOpen = listOfVertexWithOccurents.get(openGateId);
                    Integer occurentOfNewClose = listOfVertexWithOccurents.get(closeGateId);
                    listOfVertexWithOccurents.put(newOpenName, occurentOfNewOpen);
                    listOfVertexWithOccurents.put(newCloseName, occurentOfNewClose);
                    
                    SetGraphAttribute.ChangeNodeColour(graph, newOpenName, loopNodeColor);
                    SetGraphAttribute.ChangeNodeColour(graph, newCloseName, loopNodeColor);
                    
                    //Them node LOOP va level vao danh sach
                    _queueVertexWithLevel.put(newCloseName, _queueVertexWithLevel.get(openGateId));
                    _queueVertexWithLevel.put(newOpenName, _queueVertexWithLevel.get(closeGateId));
                    

                    //Xoa khoi danh sach cu
                    listOfOrOpen.put(openGateId, 0);
                    listOfOrClose.put(closeGateId, 0);
                    graph.removeNode(openGateId);
                    graph.removeNode(closeGateId);
                }
            }
        }
    }
    public static void ProcessForSelfLoop(Graph graph, Map<String, Integer> listOfVertexWithOccurents)
    {
        Collection<Edge> edges = graph.getEdgeSet();
        for (Edge edge:edges)
        {
            Node node0 = edge.getNode0();
            Node node1 = edge.getNode1();
            String node0Id = node0.getId().toString();
            String node1Id = node1.getId().toString();
            
            if(node0Id.equals(node1Id)) // Self loop
            {
                
                HashMap<String, Integer> sonList = new HashMap<>(); 
                HashMap<String, Integer> fatherList = new HashMap<>(); 
                FindFatherListAndSonList(graph, node0Id, listOfVertexWithOccurents, fatherList, sonList);
                
                // --------------------Create LOOP - OPEN--------------
                String loopOpenGateName;
                String loopCloseGateName;
                Integer edgeValue;
                String edgeSelfLoopValue = graph.getEdge(node0Id+keyConnectNode+node0Id).getAttribute("ui.label");
                edgeValue = Integer.parseInt(edgeSelfLoopValue);
                loopOpenGateName = CreateLoopOpenForSelfLoop(graph, listOfVertexWithOccurents, node0Id, sonList);
                //-------------------- Create LOOP - CLOSE -------------------------
                loopCloseGateName = CreateLoopCloseForSelfLoop(graph, listOfVertexWithOccurents, node0Id, fatherList);
                
                //Delete self arc.
                graph.removeEdge(edge);
                
                //Connect Loop-Open to Loop-Close
                graph.addEdge(loopOpenGateName+keyConnectNode+loopCloseGateName, loopOpenGateName, loopCloseGateName, true);
                Edge newEdge = graph.getEdge(loopOpenGateName+keyConnectNode+loopCloseGateName);
                newEdge.setAttribute("ui.label", edgeValue.toString());
                
            }
        }
    }
    private static String CreateLoopCloseForSelfLoop(Graph graph, Map<String, Integer> listOfVertexWithOccurents,String currentNodeId, HashMap<String, Integer> sonList)
    {
        String gateName="";
        loopOpenId++;
        Integer gateId=loopOpenId;
        String gateType="Loop-Close";
        
        String[] listOfNodeToConnect= new String[sonList.size()];
        Integer count=0;
        for (Map.Entry<String, Integer> entry : sonList.entrySet()) {
            String key = entry.getKey();
            count++;
            listOfNodeToConnect[count-1] = key;
        }
        gateName = CreateNormalGate(graph,listOfVertexWithOccurents, currentNodeId, listOfNodeToConnect, gateId, gateType);


        return gateName;
    }
    private static String CreateLoopOpenForSelfLoop(Graph graph, Map<String, Integer> listOfVertexWithOccurents,String currentNodeId, HashMap<String, Integer> fatherList)
    {
        
        String gateName="";
        loopCloseId++;
        Integer gateId=loopCloseId;
        String gateType="Loop-Open";
        
        String[] listOfNodeToConnect= new String[fatherList.size()];
        Integer count=0;
        for (Map.Entry<String, Integer> entry : fatherList.entrySet()) {
            String key = entry.getKey();
            count++;
            listOfNodeToConnect[count-1] = key;
        }
        gateName = CreateNormalGate(graph,listOfVertexWithOccurents, currentNodeId, listOfNodeToConnect, gateId, gateType);

        return gateName;
    }
  
    
   
    
}
