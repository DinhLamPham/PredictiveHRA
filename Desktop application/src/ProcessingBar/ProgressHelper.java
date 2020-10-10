/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessingBar;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JRootPane;

/**
 *
 * @author phamdinhlam
 */
public class ProgressHelper {
    public static Integer progressCurrentValue =0;
    public static Integer progressMaxValue =100;
    public static Integer progressMinValue =0;
    public static Integer progressStep=0;
    
    public static Integer sleepInteger = 0;
    public static String jobTitle="";
    
    public static boolean running = true;
    public static boolean finishJob = false;
    
    public static JFrame progressPanelFrame;
    
    
    public static void RunProgress(String _jobName)
    {
        jobTitle = _jobName;
        running = true;
        progressCurrentValue = 0;
        progressMaxValue = 1000;
        finishJob=false;
        StartThread();
    }
    
    private static void StartThread()
    {
        ProcessingBar.RunProgress threadProgress = new ProcessingBar.RunProgress();
        Thread progressThread = new Thread(threadProgress);
        progressThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProgressHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void FinishProgress(JFrame frame)
    {
        try {
            jobTitle = "Done";
            running = false;
            progressCurrentValue = 0;
            progressMaxValue = 0;
            finishJob=true;
            Thread.sleep(100);
            frame.dispose();
        } 
        catch (Exception e) {
        }
    }
    
    
    public static Long CaculateProgress(double _currentValue, double _totalValue)
    {
        Long result;
        double percent = (_currentValue/_totalValue)*100;
        result=Math.round(percent);
        return result;
    }
    public static void SynchronizeProgress(double _currentValue, double _totalValue)
    {
        progressCurrentValue = CaculateProgress(_currentValue, _totalValue).intValue();
        try {
            Thread.sleep(sleepInteger);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProgressHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
