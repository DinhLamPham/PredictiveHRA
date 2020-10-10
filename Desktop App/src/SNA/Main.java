/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNA;

import static SNA.GenerateRandomGraph.BigGraph;
import java.util.Objects;
import java.util.Random;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author DinhLam Pham
 */
public class Main {
    
    
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph g = new SingleGraph("Graph of Log");
        g.setAttribute("ui.quality");
        g.setAttribute("ui.antialias");
        g.setAttribute("ui.stylesheet", "url('style/SNAStyle.css')");
        g.display();
        Random rand = new Random(); 
        Integer size = 50;
        for(Integer i=0;i<size;i++)
        {
//            pause(200);
            String nodeId = i.toString();
            g.addNode(nodeId);
            g.getNode(i).addAttribute("ui.label", i.toString());
            
            
            double colorval = (double) i / (double) size;
            System.out.println(colorval);
            g.getNode(i).setAttribute("ui.color", colorval);
            if(i%5==0)
            {
                g.getNode(i).addAttribute("ui.class", "supply_balanced");
                g.getNode(i).addAttribute("ui.class", "show_text");
                
                
            }
        }
        for(Integer i=0;i<(size-1)/2;i++)
            for(Integer j=i+1;j<size; j++)
            if(!Objects.equals(i, j) && g.getEdge(i.toString()+j.toString())==null)
            {
                g.addEdge(i.toString()+j.toString(), i.toString(), j.toString());
                 g.getEdge(i.toString()+j.toString()).setAttribute("ui.color", rand.nextDouble());
                if(j%15==0)
                    g.getEdge(i.toString()+j.toString()).addAttribute("ui.class", "basic"); 
                
                
                
                
//                pause(50);                
            }
        for(Integer i=0; i<size; i++)
        {
            if(i%5==0)
            {
//                g.getNode(i).removeAttribute("ui.class");
                g.getNode(i).removeAttribute("ui.hide" );
            }
//            pause(200);
        }
        
        
        for(Integer i=0; i<size; i++)
        {
            if(i%5==0)
            {
                g.getNode(i).removeAttribute("ui.class");
                
            }
            pause(200);
        }
        
        
        
        for(Integer i=0;i<(size-1)/2;i++)
            for(Integer j=i+1;j<size; j++)
                if(j%15!=0)
                {
                    g.getNode(j.toString()).addAttribute("ui.hide");
                    g.getEdge(i.toString()+j.toString()).addAttribute("ui.hide"); 
                    pause(2);
                }
        
    }
    
    
     public static void pause(long millis) {
    try {
                Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
