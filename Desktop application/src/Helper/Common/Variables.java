/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Common;
import FormTemplate.SNAFrame;
import Helper.Dashboard.GraphUIProperty;
import Helper.Dashboard.MainLogic;
import Helper.Dashboard.NodeClickListener;
import com.sun.javafx.PlatformUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JTable;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;

/**
 *
 * @author phamdinhlam
 */
public class Variables {
    
    public static String predictNextFunctionId = "PredictNext";
    public static String predictESNFunctionId = "PredictESN";
    public static String predictESNInputFileName = "_inputForPredictESN.txt";
    public static List<String> predictedESNLogList = new LinkedList<>();
    public static JTable predictESNJTable = new JTable();
    public static Integer stepInForPredictESN = 0;
//    public static Integer stepOutForPredictESN = 0;
    
    
    public static String getActPerStatisticsFuncId = "GetActPerStatistics";
    public static String getTracesStatisticsFuncId = "GetTracesStatistics";
    public static String getTraceDetailFuncId = "GetTracesDetail";
    
    
    public static View graphViewerinPanel=null;
    public static Graph globalGraph = new SingleGraph("My Graph");
    
    public static Graph steadyGraph;
    public static Map<String, Integer> nodeOccurenceMap = new HashMap<>();
    public static Map<String, Integer> edgeWeightMap = new HashMap<>();
    public static NodeClickListener clisten=null;
    public static MainLogic MLogic; 
    public static GraphUIProperty gUIProp;
    
    
    public static String styleOffsetForSelfLoop = "text-offset: 25, -25;";
    public static String styleOffsetForAtoB="text-offset: 0, -30;";
    public static String styleOffsetForBtoA="text-offset: 0, 30;";
    
    
    public static List<String> globalXESLogListStrings = new LinkedList<String>();
    public static boolean flagAnalizeSuccess = false;
    public static String logName = "";
    public static String newFileExtension = ".txt";
    
    public static  String[] eventKeyToGet;
    public static  String activityCode = "concept:name";
    public static  String performerCode = "org:resource";
    public static  String timeStampCode = "time:timestamp";    
    public static  String keyWordSeparate = "!@#";
    public static  String keySeparateInside = "!!";
    
    public static Integer posOfAct = 0, posOfPer = 1, posOfTime = 2;
    
    public static String traceTableName = "currentTraceInfo";
    public static String[] traceTableHeader = {"Trace", "Activity", "Performer", "Duration" };
    public static JTable globalTraceTable = null;
    public static List<String> globalCurrentViewingTraceList = null;
    
    public static Integer maxEventInTrace = 0;
    public static Integer minEventInTrace = 0;
    public static Integer averageEventInTrace = 0;
    public static Integer maxEventID = 0;
    public static Integer tableSizeLimit = 15000;
    public static Integer currentPage = 0;
    public static Integer totalPage = -1;
    public static String currentViewTable = "Trace";
    
    public static String[] activityTableHeader = {"LogName", "ActivityName", "InTotalTrace", "Occurence", "Duration"};
    public static String activitiesTableName = "Activities";
    public static JTable globalActivityTable = null;
    public static Set<String> globalActivitySet = null;
    public static Map<String, Duration> globalActivityDuration;
    public static Map<String, Set<Integer>> globalActWithTraceMap;
    public static String[] activityWithTraceTableHeader = {"LogName", "ActivityName", "Members"};
    public static String activityWithTraceTableName = "ActivityWithTrace";
    
    public static String[] performerTableHeader = {"LogName", "PerformerName", "InTotalTrace", "Occurence", "Duration"};
    public static JTable globalPerformerTable = null;
    public static String performersTableName = "Performers";
    public static Set<String> globalPerformerSet = null;
    public static Map<String, Duration> globalPerformerDuration;
    public static Map<String, Set<Integer>> globalPerWithTraceMap;
    public static String[] performerWithTraceTableHeader = {"LogName", "PerformerName", "Members"};
    public static String performerWithTraceTableName = "PerformerWithTrace";
    
    public static String[] tblInputForPredictionHeader = {"Order", "Activity", "Performer", "Time"};
    
    public static JFrame graphPropertiesFrame = null;
    public static JFrame functionManagerFrame = null;
    
    public static String tblActInMySQL = "Activities";
    public static String tblPerInMySQL = "Performers";
    public static String tblXESLogInMySQL = "XESLog";
    public static String[] tblXESLogHeader = {"logId", "logName", "trace"};
    
    public static String tblSlectingTraceInMySQL = "SelectingTrace";
    public static String[] headerOfTblSlectingTraceInMySQL = {"trace"};
    
    public static String tblSlectingEventInMySQL = "SelectingEvent";
    public static String[] headerOfTblSlectingEventInMySQL = {"name", "type"};
    
    public static String tblFunctionListName = "FunctionList";  // Run column value: notyet/ running/ finished
    public static String tblFunctionListRunColumn = "run";
    public static String tblFunctionListOutputPathColumn = "output";
    public static String pythonNoyetStatus = "notyet";  // notyet  / running / finished
    public static String[] tblFunctionListHeader = {"id", "name", "parameter", "describer"};
    
    public static String[] tblTrainedModelHeader = {"id", "name", "feature", "predicttype", "stepin", "stepout", 
        "name_to_int_set", "int_to_name_set", "val_accuracy"};
    public static String tblTrainedModelName = "model";

    
    
    public static String pythonFolder = Paths.get("").toAbsolutePath().toString() + "/Python/Main/";
    public static String pythonRunningFile = "";
    
    public static Integer delayTime=1000;
    
    public static Map<String, Set<String>> activityAffiliationMap = new HashMap<>();
    public static String[] tblAffiliationActHeader = new String[] {"LogName", "ActivityName", "Members"};
    public static String tblAffiliationActName = "affiliation_activity";

    public static Map<String, Set<String>> performerAffiliationMap = new HashMap<>();
    public static String[] tblAffiliationPerHeader = new String[] {"LogName", "PerformerName", "Members"};
    public static String tblAffiliationPerName = "affiliation_performer";
    
    public static Map<String, Set<String>> activityRelationMap = new HashMap<>();
    public static String[] tblRelationActHeader = new String[] {"LogName", "ActivityName", "Members"};
    public static String tblRelationActName = "relation_activity";
    
    
    public static String[] newTraceTableHeader = new String[] {"Activity", "Performer", "Time"};
    
    public static String defaultTimeInsert = Instant.now().toString();
    
    public static FormTemplate.SNAFrame frmSNA = null;
    public static double[] degreeRange = new double[2];
    public static double[] closenessRange = new double[2];
    public static double[] betweennessRange = new double[2];
    
    public static GraphVizHelper.SetupGraphvizProperties frmGraphviz;
    public static String defaultGraphvizFileName = "graphVizPNG";
   
    
    
    public static String getFolderPathInPredictESNFunc(String _outputFileName)
    {
        Path currentRelativePath = Paths.get("");
        String currentWD = currentRelativePath.toAbsolutePath().toString();
        String folderToStore = "";
        
        if (PlatformUtil.isWindows())
            folderToStore = "\\PredictESN\\";
        else
            folderToStore = "/PredictESN/";
        String outputFilePath = currentWD + folderToStore + _outputFileName;
        
        return  outputFilePath;
    }
    
    public static String getFolderPathInGetTraceStatisticsFunc(String _outputFileName)
    {
        Path currentRelativePath = Paths.get("");
        String currentWD = currentRelativePath.toAbsolutePath().toString();
        String folderToStore = "";
        
        if (PlatformUtil.isWindows())
            folderToStore = "\\GetTracesStatistics\\";
        else
            folderToStore = "/GetTracesStatistics/";
        String outputFilePath = currentWD + folderToStore + _outputFileName;
        
        return  outputFilePath;
    }
    
    
    public static String getFolderPathGraphvizFile(String _outputFileName)
    {
        Path currentRelativePath = Paths.get("");
        String currentWD = currentRelativePath.toAbsolutePath().toString();
        String folderToStore = "";
        
        if (PlatformUtil.isWindows())
            folderToStore = "\\tmp\\";
        else
            folderToStore = "/tmp/";
        String outputFilePath = currentWD + folderToStore + _outputFileName;
        
        return  outputFilePath;
    }
}
