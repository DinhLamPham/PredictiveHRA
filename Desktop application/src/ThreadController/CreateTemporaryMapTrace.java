/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadController;

import Helper.Common.MessageHelper;
import static Helper.Dashboard.DetailPanel1Helper.SaveTemproraryMemberTraceMap;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DinhLam Pham
 */
public class CreateTemporaryMapTrace implements Runnable{

    private static Integer fromId=-1;
    private static Integer toId;
    @Override
    public void run() {
        if(fromId == -1 )
            MessageHelper.Warning("Unknow range for calculating trace Map...!");
        else
            try {
                SaveTemproraryMemberTraceMap(fromId, toId);
        } catch (SQLException ex) {
            Logger.getLogger(CreateTemporaryMapTrace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public CreateTemporaryMapTrace(Integer _from, Integer _to)
    {
        fromId  = _from; toId = _to;
    }
    
}
