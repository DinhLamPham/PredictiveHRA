package Helper.Timer;


import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import static Helper.Common.Variables.delayTime;
import static Helper.Common.Variables.pythonNoyetStatus;
import static Helper.Common.Variables.tblFunctionListRunColumn;
import Helper.MySQL.Query;
import java.awt.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.graphstream.graph.Node;
import static Helper.MySQL.Query.GetColumnValueFromFunctionList;
import java.io.File;
import javax.swing.JLabel;
import static Helper.Common.Variables.tblFunctionListName;

/**
 * Schedule a task that executes once every second.
 */

public class SchedulerForLoadImageResult 
{
    Toolkit toolkit;
    Timer timer;
    
    public static Integer step=-2;
    public static Integer totalStep = -1;
    private static Integer sleepingTime=0;
    public static boolean flagNewTrace = false;
    private static String funcToScan = "";
    private static Helper.Common.PanelImage panelImage1;
    private static Helper.Common.PanelImage panelImage2;
    private JLabel myLabel;
    

    public SchedulerForLoadImageResult(JLabel _Label, String _funcName, Helper.Common.PanelImage _panelImage1, Helper.Common.PanelImage _panelImage2) {
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        funcToScan = _funcName;
        panelImage1 = _panelImage1;
        panelImage2 = _panelImage2;
        myLabel = _Label;
        timer.schedule(new RemindTask(),
	               sleepingTime,    //initial delay
	              delayTime);  //subsequent rate
    }
    
    class RemindTask extends TimerTask {
        @Override
        public void run() 
        {
            try {
                    String funcStatus = GetColumnValueFromFunctionList(funcToScan, "run");
                    if(funcStatus.toUpperCase().equals("FINISHED"))
                    {
                        String output = GetColumnValueFromFunctionList(funcToScan, "output");
                        String file1, file2="", fullFilePath1, fullFilePath2=""; File f1=null, f2=null;
                        if(output.contains(Variables.keySeparateInside))
                        {
                            file1 = output.split(Variables.keySeparateInside)[0];
                            file2 = output.split(Variables.keySeparateInside)[1];
                            fullFilePath1 = Variables.getFolderPathInGetTraceStatisticsFunc(file1);
                            fullFilePath2 = Variables.getFolderPathInGetTraceStatisticsFunc(file2);
                            f1 = new File(fullFilePath1);  f2 = new File(fullFilePath2);
                        }
                        else
                        {
                            file1 = output;
                            fullFilePath1 = Variables.getFolderPathInGetTraceStatisticsFunc(file1);
                            f1 = new File(fullFilePath1);
                        }
                        
                        if(f1.exists())
                        {
                            if(output.contains(Variables.keySeparateInside))
                            {
                                panelImage1.setImage(fullFilePath1);
                                panelImage2.setImage(fullFilePath2);
                            }
                            else
                                panelImage1.setImage(fullFilePath1);
                            
                            Query.UpdateTbl(tblFunctionListName, "id", funcToScan, tblFunctionListRunColumn, "displayed");
                            myLabel.setEnabled(true);
                        }
                        else
                            MessageHelper.Warning("File is not exist. Please check the path: " + output);
                        
                        StopTimer();
                    }
//                        
            } catch (Exception e) {
                MessageHelper.Error(e.toString());
                StopTimer();
            }
        }
        private void StopTimer()
        {
            timer.cancel(); 
        }
        
    }
}
