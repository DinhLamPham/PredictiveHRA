/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper.Python;

import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class ErrorHandle {
    public static void ShowError(String _errorString)
    {
        
        JFrame frmError = new frmShowPythonError();
        frmError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmError.pack();
        frmError.setLocationRelativeTo(null);
        frmShowPythonError.txtErrorContent.setText(_errorString);
        frmError.setVisible(true);
    }
}
