/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessingBar;

import Helper.Common.MessageHelper;
import FormTemplate.LeftPanel;
import static ProcessingBar.ProgressPanel.progressBar;
import static ProcessingBar.ProgressPanel.lblJobName;
import static ProcessingBar.ProgressPanel.lblProgressValue;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRootPane;

/**
 *
 * @author phamdinhlam
 */
public class RunProgress implements Runnable{
    @Override
    public void run() {
        JFrame frame = new JFrame("Processing");
        DisplayProgressFrame(frame);
        try {
            while (ProgressHelper.running) {                
                progressBar.setValue(ProgressHelper.progressCurrentValue);
                LeftPanel.systemProcessingBar.setValue(ProgressHelper.progressCurrentValue);
                
                lblJobName.setText(ProgressHelper.jobTitle);
                String currentValueString = ProgressHelper.progressCurrentValue.toString();
                lblProgressValue.setText(currentValueString);
                
                
                if(ProgressHelper.progressCurrentValue >= 100 || ProgressHelper.running==false)
                    ProgressHelper.FinishProgress(frame);
                if(ProgressHelper.finishJob)
                     ProgressHelper.FinishProgress(frame);
                
                Thread.sleep(ProgressHelper.sleepInteger);   
            }
        } catch (Exception e) {
        }
        
    }
    public static void DisplayProgressFrame(JFrame frame)
    {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 60);
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        frame.setAlwaysOnTop(true);
        ProgressPanel progressPanel = new ProgressPanel();
        
        frame.add(progressPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        ProgressHelper.progressPanelFrame = frame;
    }
}