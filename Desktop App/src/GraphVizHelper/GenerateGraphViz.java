package GraphVizHelper;

// GenerateGraphViz.java - a simple API to call dot from Java programs

/*$Id$*/
/*
 ******************************************************************************
 *                                                                            *
 *                    (c) Copyright Laszlo Szathmary                          *
 *                                                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation; either version 2.1 of the License, or        *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public    *
 * License for more details.                                                  *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program; if not, write to the Free Software Foundation,    *
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.                              *
 *                                                                            *
 ******************************************************************************
 */

import Helper.Common.MessageHelper;
import Helper.ICN.ICNBuilderV1;
import com.sun.javafx.PlatformUtil;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * <dl>
 * <dt>Purpose: GenerateGraphViz Java API
 <dd>
 *
 * <dt>Description:
 * <dd> With this Java class you can simply call dot
 *      from your Java programs.
 * <dt>Example usage:
 * <dd>
 * <pre>
    GenerateGraphViz gv = new GenerateGraphViz();
    gv.addln(gv.startDirect_graph());
    gv.addln("A -> B;");
 *    gv.addln("A -> C;");
 *    gv.addln(gv.end_graph());
 *    System.out.println(gv.getDotSource());
 *
 *    String type = "gif";
 *    String representationType="dot";
 *    File out = new File("out." + type);   // out.gif in this example
 *    gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, representationType), out );
 * </pre>
 * </dd>
 *
 * </dl>
 *
 * @version v0.6.1, 2016/04/10 (April) -- Patch of Markus Keunecke is added.
 * The eclipse project configuration was extended with the maven nature.
 * @version v0.6, 2013/11/28 (November) -- Patch of Olivier Duplouy is added. Now you
 * can specify the representation type of your graph: dot, neato, fdp, sfdp, twopi, circo
 * @version v0.5.1, 2013/03/18 (March) -- Patch of Juan Hoyos (Mac support)
 * @version v0.5, 2012/04/24 (April) -- Patch of Abdur Rahman (OS detection + start subgraph +
 * read config file)
 * @version v0.4, 2011/02/05 (February) -- Patch of Keheliya Gallaba is added. Now you
 * can specify the type of the output file: gif, dot, fig, pdf, ps, svg, png, etc.
 * @version v0.3, 2010/11/29 (November) -- Windows support + ability to read the graph from a text file
 * @version v0.2, 2010/07/22 (July) -- bug fix
 * @version v0.1, 2003/12/04 (December) -- first release
 * @author  Laszlo Szathmary (<a href="jabba.laci@gmail.com">jabba.laci@gmail.com</a>)
 */
public class GenerateGraphViz
{
    /**
     * Detects the client's operating system.
     */
    private final static String osName = System.getProperty("os.name").replaceAll("\\s","");

    /**
     * The image size in dpi. 96 dpi is normal size. Higher values are 10% higher each.
     * Lower values 10% lower each.
     *
     * dpi patch by Peter Mueller
     */
    private final int[] arrayDpiSizes = Variables.dpiSizes;

    /**
     * Define the index in the image size array.
     */
    private int dpiPos = Variables.currentDpiPos ;

    /**
     * Increase the image size (dpi).
     */
    public void increaseDpi() {
        if ( this.dpiPos < (this.arrayDpiSizes.length - 1) ) {
            ++this.dpiPos;
        }
        System.out.println("Position: " + this.dpiPos + "Value: "+ arrayDpiSizes[dpiPos]);
    }

    /**
     * Decrease the image size (dpi).
     */
    public void decreaseDpi() {
        if (this.dpiPos > 0) {
            --this.dpiPos;
        }
    }

    public int getImageDpi() {
        return this.arrayDpiSizes[this.dpiPos];
    }

    /**
     * The source of the graph written in dot language.
     */
    private StringBuilder graph = new StringBuilder();

    private String tempDir;

    private String executable;

    /**
     * Convenience Constructor with default OS specific pathes
     * creates a new GraphViz object that will contain a graph.
     * Windows:
     * executable = c:/Program Files (x86)/Graphviz 2.28/bin/dot.exe
     * tempDir = c:/temp
     * MacOs:
     * executable = /usr/local/bin/dot
     * tempDir = /tmp
     * Linux:
     * executable = /usr/bin/dot
     * tempDir = /tmp
     */
    public GenerateGraphViz() {
            Path currentRelativePath = Paths.get("");
            String currentPathString = currentRelativePath.toAbsolutePath().toString();
            
            // MAC OS
            this.tempDir = currentPathString + "/tmp";
            this.executable = currentPathString + "/Lib/GraphViz2.40.1/bin/dot";
            if(PlatformUtil.isWindows())
            {
                this.tempDir = currentPathString + "\\tmp";
                this.executable = currentPathString + "c:/Program Files (x86)/Graphviz 2.28/bin/dot.exe";
            }

        
    }

    /**
     * Configurable Constructor with path to executable dot and a temp dir
     *
     * @param executable absolute path to dot executable
     * @param tempDir absolute path to temp directory
     */
    public GenerateGraphViz(String executable, String tempDir) {
        this.executable = executable;
        this.tempDir = tempDir;
    }

    /**
     * Returns the graph's source description in dot language.
     * @return Source of the graph in dot language.
     */
    public String getDotSource() {
        return this.graph.toString();
    }

    /**
     * Adds a string to the graph's source (without newline).
     */
    public void add(String line) {
        this.graph.append(line);
    }

    /**
     * Adds a string to the graph's source (with newline).
     */
    public void addln(String line) {
        this.graph.append(line + "\n");
    }

    /**
     * Adds a newline to the graph's source.
     */
    public void addln() {
        this.graph.append('\n');
    }

    public void clearGraph(){
        this.graph = new StringBuilder();
    }
    
    /**
     * Adds list of level for nodes 
     * * Returns void
     * @param List<String> _nodeLevelList: List string of Node with the same level, separate by " " 
     */
    public void AddNodeLevel(List<String> _nodeLevelList) {
        for(Integer i=0; i<_nodeLevelList.size();i++)
        {
            String currentLine = "{rank=same;#currentList}";
            currentLine= currentLine.replaceAll("#currentList", _nodeLevelList.get(i)) ;
            this.graph.append(currentLine);
        }
    }

    /**
     * Returns the graph as an image in binary format.
     * @param dot_source Source of the graph to be drawn.
     * @param type Type of the output image to be produced, e.g.: gif, dot, fig, pdf, ps, svg, png.
     * @param representationType Type of how you want to represent the graph:
     * <ul>
     * 	<li>dot</li>
     * 	<li>neato</li>
     * 	<li>fdp</li>
     * 	<li>sfdp</li>
     * 	<li>twopi</li>
     * 	<li>circo</li>
     * </ul>
     * @see http://www.graphviz.org under the Roadmap title
     * @return A byte array containing the image of the graph.
     */
    public byte[] getGraph(String dot_source, String type, String representationType)
    {
        File dot;
        byte[] img_stream = null;

        try {
            dot = writeDotSourceToFile(dot_source);
            if (dot != null)
            {
                img_stream = get_img_stream(dot, type, representationType);
                if (dot.delete() == false) {
                    System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
                }
                return img_stream;
            }
            return null;
        } catch (java.io.IOException ioe) { return null; }
    }

    /**
     * Writes the graph's image in a file.
     * @param img   A byte array containing the image of the graph.
     * @param file  Name of the file to where we want to write.
     * @return Success: 1, Failure: -1
     */
    public int writeGraphToFile(byte[] img, String file)
    {
        File to = new File(file);
        return writeGraphToFile(img, to);
    }

    /**
     * Writes the graph's image in a file.
     * @param img   A byte array containing the image of the graph.
     * @param to    A File object to where we want to write.
     * @return Success: 1, Failure: -1
     */
    public int writeGraphToFile(byte[] img, File to)
    {
        try {
            FileOutputStream fos = new FileOutputStream(to);
            fos.write(img);
            fos.close();
            fos.flush();
        } 
        catch (java.io.IOException ioe) 
        {   MessageHelper.Error(ioe.toString());
            return -1; 
        }
        return 1;
    }

    /**
     * It will call the external dot program, and return the image in
     * binary format.
     * @param dot Source of the graph (in dot language).
     * @param type Type of the output image to be produced, e.g.: gif, dot, fig, pdf, ps, svg, png.
     * @param representationType Type of how you want to represent the graph:
     * <ul>
     * 	<li>dot</li>
     * 	<li>neato</li>
     * 	<li>fdp</li>
     * 	<li>sfdp</li>
     * 	<li>twopi</li>
     * 	<li>circo</li>
     * </ul>
     * @see http://www.graphviz.org under the Roadmap title
     * @return The image of the graph in .gif format.
     */
    private byte[] get_img_stream(File dot, String type, String representationType)
    {
        File img;
        byte[] img_stream = null;

        try {
            img = File.createTempFile("graph_", "." + type, new File(this.tempDir));
            Runtime rt = Runtime.getRuntime();

            // patch by Mike Chenault
            // representation type with -K argument by Olivier Duplouy
            String[] args = { executable, "-T" + type, "-K" + representationType, "-Gdpi=" + arrayDpiSizes[this.dpiPos], dot.getAbsolutePath(), "-o", img.getAbsolutePath() };
            Process p = rt.exec(args);
            p.waitFor();

            FileInputStream in = new FileInputStream(img.getAbsolutePath());
            img_stream = new byte[in.available()];
            in.read(img_stream);
            // Close it if we need to
            if( in != null ) {
                in.close();
            }

            if (img.delete() == false) {
                System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
            }
        }
        catch (java.io.IOException ioe) {
            System.err.println("Error:    in I/O processing of tempfile in dir " + tempDir + "\n");
            System.err.println("       or in calling external command");
            ioe.printStackTrace();
        }
        catch (java.lang.InterruptedException ie) {
            System.err.println("Error: the execution of the external program was interrupted");
            ie.printStackTrace();
        }

        return img_stream;
    }

    /**
     * Writes the source of the graph in a file, and returns the written file
     * as a File object.
     * @param str Source of the graph (in dot language).
     * @return The file (as a File object) that contains the source of the graph.
     */
    private File writeDotSourceToFile(String str) throws java.io.IOException
    {
        File temp;
        try {
            temp = File.createTempFile("graph_", ".dot.tmp", new File(tempDir));
            FileWriter fout = new FileWriter(temp);
            fout.write(str);
            fout.close();
        }
        catch (Exception e) {
            System.err.println("Error: I/O error while writing the dot source to temp file!");
            return null;
        }
        return temp;
    }

    /**
     * Returns a string that is used to start a graph.
     * @return A string to open a graph.
     */
    public String startDirect_graph() {
        return "digraph G {";
    }
    
    public String startUndirect_graph() {
        return "graph G {";
    }
    
    public String addDirectEdge_graph(String fromA, String toB){
        return fromA + " -> " + toB +";\n";
    }
    public String addNoDirectEdge_graph(String fromA, String toB){
        return fromA + " -- " + toB +";\n";
    }

    /**
     * Returns a string that is used to end a graph.
     * @return A string to close a graph.
     */
    public String end_graph() {
        return "}";
    }

    /**
     * Takes the cluster or subgraph id as input parameter and returns a string
     * that is used to start a subgraph.
     * @return A string to open a subgraph.
     */
    public String start_subgraph(int clusterid) {
        return "subgraph cluster_" + clusterid + " {";
    }

    /**
     * Returns a string that is used to end a graph.
     * @return A string to close a graph.
     */
    public String end_subgraph() {
        return "}";
    }
    
    /**
     * Returns a string that is used to label subgraph.
     * @return A string to label subgraph
     */
    public String label_subgraph(String label) {
        String output="label = \"" + label + "\"";
        return  output;
    }
    
    public String format_graph(String style, String color) {
        if(style==null)
            style="filled";
        if(color==null)
            color="lightgrey";
        
        String output= "style=\""+style+"\";\n";
        output = output+ "color=\"" + color + "\";";
        return  output;
    }
    public String NodeFormatForGraph(String nodesize, String shape, String color, String fillcolor) {
        if(shape==null)
            shape="box";
        if(color==null)
            color="lightgrey";
        if(fillcolor==null)
            color="lightgrey";
        
        String output= "node [style=\"rounded,filled\", width=\""+ nodesize +"\", shape=\""+shape+"\",color=\""+color+"\",fillcolor=\""+fillcolor+"\"];";
        return  output;
    }
    
    public static String SingleNodeFormat(String nodeName,String nodesize, String shape, String color, String fillcolor) {
        
        String output="\""+nodeName+"\" "+  "[style=\"rounded,filled\", width=\""+ nodesize +"\", shape=\""+shape+"\",color=\""+color+"\",fillcolor=\""+fillcolor+"\"];";
        return  output;
    }
    
     public static void InsertNodeToGraphViz(GenerateGraphViz gv, String nodeName)
    {
        String line="\""+nodeName+"\";"; //Default: Only node name without format.
        
        if(ICNBuilderV1.CheckNodeIsOrGate(nodeName))
            line = GenerateGraphViz.SingleNodeFormat(nodeName, Variables.orGateSizeString, 
                                                        Variables.orGateShape, Variables.orGateColor, Variables.orGateFillColor);
        
        if(ICNBuilderV1.CheckNodeIsAndGate(nodeName))
            line = GenerateGraphViz.SingleNodeFormat(nodeName, Variables.andGateSizeString, 
                                                        Variables.andGateShape, Variables.andGateColor, Variables.andGateFillColor);
        
        if(ICNBuilderV1.CheckNodeIsLoopGate(nodeName))
            line = GenerateGraphViz.SingleNodeFormat(nodeName, Variables.loopGateSizeString, 
                                                        Variables.loopGateShape, Variables.loopGateColor, Variables.loopGateFillColor);
        
        gv.add(line);
    }


    /**
     * Read a DOT graph from a text file.
     *
     * @param input Input text file containing the DOT graph
     * source.
     */
    public void readSource(String input)
    {
        StringBuilder sb = new StringBuilder();

        try
        {
            FileInputStream fis = new FileInputStream(input);
            DataInputStream dis = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            dis.close();
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        this.graph = sb;
    }

} // end of class GenerateGraphViz
