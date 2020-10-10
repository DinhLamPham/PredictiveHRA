/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphVizHelper;

import static GraphVizHelper.Variables.*;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.*;

/**
 *
 * @author phamdinhlam
 */
public class ReadGraphSettingXML {
    private static String GetElementByTageName(Element element, String tagName)
    {
        String result="";
        try {
            result = element.getElementsByTagName(tagName).item(0).getTextContent();
        } catch (Exception e) {
            return "NULL";
        }
       
        return result;
    }
    
    public static void ReadGraphVizSettingToVariables() throws ParserConfigurationException, SAXException, IOException
    {
        
        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(new File(GraphVizHelper.Variables.settingINIFullPath));

        //Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

       
        //Get all GraphViz Variable elements
        NodeList nList = document.getElementsByTagName(graphVizXMLTag);

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
         Node node = nList.item(temp);
         System.out.println("");    //Just a separator
         if (node.getNodeType() == Node.ELEMENT_NODE)
         {
            //Print each employee's detail
             Element eElement = (Element) node;
             String tmpShape = GetElementByTageName(eElement,"shape");
             String tmpSize = GetElementByTageName(eElement,"size"); 
             String tmpBorderColor = GetElementByTageName(eElement,"bordercolor");  
             String tmpFillColor =  GetElementByTageName(eElement,"fillcolor");  
             String tmpNodesep = GetElementByTageName(eElement,"nodesep"); 
             String tmpRanksep = GetElementByTageName(eElement,"ranksep"); 
             String tmpRankdir = GetElementByTageName(eElement,"rankdir"); 
             switch (eElement.getAttribute("id"))
             {
                 case "NormalNode":
                     Variables.nodeShape = tmpShape;
                     Variables.nodeSizeString = tmpSize;
                     Variables.nodeColor = tmpBorderColor;
                     Variables.nodeFillColor = tmpFillColor;
                    break;
                case "Or-Gate":
                     Variables.orGateShape = tmpShape;
                     Variables.orGateSizeString = tmpSize;
                     Variables.orGateColor = tmpBorderColor;
                     Variables.orGateFillColor = tmpFillColor;
                    break;
                case "And-Gate":
                     Variables.andGateShape = tmpShape;
                     Variables.andGateSizeString = tmpSize;
                     Variables.andGateColor = tmpBorderColor;
                     Variables.andGateFillColor = tmpFillColor;
                    break;
                case "Loop-Gate":
                     Variables.loopGateShape = tmpShape;
                     Variables.loopGateSizeString = tmpSize;
                     Variables.loopGateColor = tmpBorderColor;
                     Variables.loopGateFillColor = tmpFillColor;
                    break;
                case "Cluster":
                    Variables.nodesep = tmpNodesep;
                    Variables.ranksep = tmpRanksep;
                    Variables.rankdir = tmpRankdir;
                    break;
                case "Left-Cluster":
                    Variables.leftSubGraphFillcolor = tmpFillColor;
                    break;
                case "Right-Cluster":
                    Variables.rightSubGraphFillColor = tmpFillColor;
                    break;
             }
         }
        }
    }
   
}
