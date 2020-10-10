package Helper.Timer;


import FormTemplate.CenterPanel;
import static FormTemplate.CenterPanel.rsAllocation_tblTraceInfo;
import Helper.Common.MessageHelper;
import static Helper.Common.Variables.delayTime;
import static Helper.Common.Variables.getFolderPathInPredictESNFunc;
import static Helper.Common.Variables.predictESNJTable;
import static Helper.Common.Variables.predictedESNLogList;
import static Helper.Common.Variables.tblFunctionListRunColumn;
import Helper.MySQL.Query;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;
import java.util.LinkedList;
import static Helper.MySQL.Query.GetColumnValueFromFunctionList;
import java.io.File;
import javax.swing.JLabel;
import static Helper.Common.Variables.tblFunctionListName;
import Helper.Dashboard.CenterPanelHelper;
import TableHelper.TableController;
import TableHelper.xesLogToTable;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Schedule a task that executes once every second.
 */

public class SchedulerForLoadPredictESNFunctionResultToTable 
{
    Toolkit toolkit;
    Timer timer;
    
    public static Integer step=-2;
    public static Integer totalStep = -1;
    private static Integer sleepingTime=0;
    public static boolean flagNewTrace = false;
    private static String funcToScan = "";
    private JTable tblOutput;
    private JLabel myLabel;
    

    public SchedulerForLoadPredictESNFunctionResultToTable(JLabel _Label, String _funcName, JTable _ouputTable) {
        toolkit = Toolkit.getDefaultToolkit();
        timer = new Timer();
        funcToScan = _funcName;
        tblOutput = _ouputTable;
        TableController.ClearTable(tblOutput);
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
                        String fullFilePath = getFolderPathInPredictESNFunc(output);
                        File  f = new File(fullFilePath);
                        if(!f.exists())
                        {
                            MessageHelper.Warning("There is no trained model!");
                            StopTimer();
                            return;
                        }
                        
                        predictedESNLogList = new LinkedList<>();
                        predictedESNLogList = Helper.File.TXTLogHelper.ReadLogFromTXTFormat(fullFilePath);
                        if(predictedESNLogList.size()==0)
                        {
                            MessageHelper.Info("Predict result is empty! There is no trace fitted the probability condition");
                            StopTimer();
                            return;
                        }
                        MessageHelper.Info("Finished! Outputfile:\n" + fullFilePath);
                        TableController.ClearTable(tblOutput);
                        predictESNJTable = new JTable();
                        predictESNJTable = xesLogToTable.ToTraceTable(predictedESNLogList, 0, predictedESNLogList.size());
                        tblOutput.setModel(predictESNJTable.getModel());
                        
                        
                        tblOutput.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                        @Override
                        public void valueChanged(ListSelectionEvent e) 
                        {
                            if(!e.getValueIsAdjusting())
                                CenterPanelHelper.RefreshTraceDetailTable(rsAllocation_tblTraceInfo, predictedESNLogList);
                            }
                        });        
                        Query.UpdateTbl(tblFunctionListName, "id", funcToScan, tblFunctionListRunColumn, "displayed");
                        CenterPanel.CenterPanel.setSelectedComponent(CenterPanel.RsAllocation_Analysis);
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
            myLabel.setEnabled(true);
            timer.cancel(); 
        }
        
        
        
    }
}
