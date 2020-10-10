package Helper.Timer;


import FormTemplate.DetailPanel3_new;
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
import TableHelper.TableController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Schedule a task that executes once every second.
 */

public class SchedulerForLoadPredictNextFunctionResultToTable 
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
    

    public SchedulerForLoadPredictNextFunctionResultToTable(JLabel _Label, String _funcName, JTable _ouputTable) {
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
                        String[] eventlist = output.split(Variables.keyWordSeparate);
                        // 1 - feature. 2: feature => Update table model.
                        if(eventlist[0].split(Variables.keySeparateInside).length == 1) // One feature output
                        {
                            String[] tblOutputHeader = {"Activity"};
                            if (DetailPanel3_new.cmbPredictType.getSelectedItem().equals("Performer"))
                                tblOutputHeader = new String[]{"Performer"};
                            TableController.InitializeBlankTableWithHeader(tblOutput, tblOutputHeader);
                            DefaultTableModel myModel = (DefaultTableModel) tblOutput.getModel();
                            for(Integer i=0; i<eventlist.length; i++)
                            {
                                String[] data = {eventlist[i]};
                                myModel.addRow(data);

                            }
                        }
                        else  // More than one feature. in this case, the total ouput feature is two
                        {
                            String[] tblOutputHeader = {"Activity", "Performer"};
                            TableController.InitializeBlankTableWithHeader(tblOutput, tblOutputHeader);
                            DefaultTableModel myModel = (DefaultTableModel) tblOutput.getModel();
                            for(Integer i=0; i<eventlist.length; i++)
                            {
                                String[] data = {eventlist[i].split(Variables.keySeparateInside)[0],
                                                 eventlist[i].split(Variables.keySeparateInside)[1]};
                                myModel.addRow(data);
                            }
                            
                        }
                        
                        
                        Query.UpdateTbl(tblFunctionListName, "id", funcToScan, tblFunctionListRunColumn, "displayed");
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
