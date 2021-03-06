/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormTemplate;

import Helper.Common.MessageHelper;
import Helper.Common.Variables;
import static Helper.Common.Variables.*;
import Helper.Dashboard.DetailPanel1Helper;
import Helper.Dashboard.DetailPanel3_newHelper;
import TableHelper.TableController;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author phamdinhlam
 */
public class DetailPanel1 extends javax.swing.JPanel {

    /**
     * Creates new form detailPanel1
     */
    public DetailPanel1() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblCurrentPage = new javax.swing.JLabel();
        lblLeftPage = new javax.swing.JLabel();
        lblRightPage = new javax.swing.JLabel();
        lblViewGraph = new javax.swing.JLabel();
        cmbTypeToVisualize = new javax.swing.JComboBox<>();
        lblTotalRow = new javax.swing.JLabel();
        lblShowInTable = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTraceSummary = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 204, 255)), "Trace information"));

        lblCurrentPage.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        lblCurrentPage.setForeground(new java.awt.Color(47, 130, 214));
        lblCurrentPage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCurrentPage.setText("...");

        lblLeftPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-circled_chevron_left_filled.png"))); // NOI18N
        lblLeftPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLeftPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblLeftPageMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLeftPageMouseClicked(evt);
            }
        });

        lblRightPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-circled_chevron_right_filled.png"))); // NOI18N
        lblRightPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblRightPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblRightPageMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRightPageMouseClicked(evt);
            }
        });

        lblViewGraph.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        lblViewGraph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-broadcasting_filled.png"))); // NOI18N
        lblViewGraph.setText("Graph");
        lblViewGraph.setToolTipText("Show selected traces in graph");
        lblViewGraph.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblViewGraph.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblViewGraphMousePressed(evt);
            }
        });

        cmbTypeToVisualize.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        cmbTypeToVisualize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activity", "Performer" }));
        cmbTypeToVisualize.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTypeToVisualizeItemStateChanged(evt);
            }
        });
        cmbTypeToVisualize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTypeToVisualizeActionPerformed(evt);
            }
        });
        cmbTypeToVisualize.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbTypeToVisualizePropertyChange(evt);
            }
        });

        lblTotalRow.setFont(new java.awt.Font("Lucida Grande", 0, 8)); // NOI18N
        lblTotalRow.setForeground(new java.awt.Color(47, 130, 214));
        lblTotalRow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalRow.setText("... rows");

        lblShowInTable.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        lblShowInTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-view_details.png"))); // NOI18N
        lblShowInTable.setText("Table");
        lblShowInTable.setToolTipText("Show selected trace in table");
        lblShowInTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblShowInTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblShowInTableMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(lblLeftPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCurrentPage, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalRow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRightPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTypeToVisualize, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblViewGraph)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblShowInTable)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblShowInTable, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblViewGraph)
                            .addComponent(cmbTypeToVisualize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCurrentPage)
                        .addGap(5, 5, 5)
                        .addComponent(lblTotalRow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblLeftPage, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRightPage, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        tblTraceSummary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Trace", "Activity", "Performer", "Duration"
            }
        ));
        tblTraceSummary.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblTraceSummaryPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblTraceSummary);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lblLeftPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLeftPageMouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_lblLeftPageMouseClicked

    private void lblRightPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRightPageMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lblRightPageMouseClicked

    private void lblRightPageMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRightPageMousePressed
        // TODO add your handling code here:
        DetailPanel1Helper.LoadPageInTraceTable("next");
    }//GEN-LAST:event_lblRightPageMousePressed

    private void lblLeftPageMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLeftPageMousePressed
        // TODO add your handling code here:
        DetailPanel1Helper.LoadPageInTraceTable("prev");
    }//GEN-LAST:event_lblLeftPageMousePressed

    private void lblViewGraphMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblViewGraphMousePressed
        // TODO add your handling code here:
            switch (DetailPanel1.cmbTypeToVisualize.getSelectedIndex()){
                case 0: {  // Activity
                    DetailPanel1Helper.VisualizeTraceInTableToGraph(tblTraceSummary, Variables.posOfAct);
                }
                    break;
                case 1: { // Performer
                    DetailPanel1Helper.VisualizeTraceInTableToGraph(tblTraceSummary, Variables.posOfPer);
                }
                    break;
            }
    }//GEN-LAST:event_lblViewGraphMousePressed

    private void cmbTypeToVisualizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTypeToVisualizeItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTypeToVisualizeItemStateChanged

    private void cmbTypeToVisualizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTypeToVisualizeActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbTypeToVisualizeActionPerformed

    private void cmbTypeToVisualizePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbTypeToVisualizePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTypeToVisualizePropertyChange

    private void tblTraceSummaryPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblTraceSummaryPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblTraceSummaryPropertyChange

    private void lblShowInTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShowInTableMousePressed
        // TODO add your handling code here:
        try {
            Integer currentRow = DetailPanel1.tblTraceSummary.getSelectedRow();
            if(currentRow == -1)
                return;
            String traceId = DetailPanel1.tblTraceSummary.getValueAt(currentRow, 0).toString();
            DetailPanel3_newHelper.FillTraceToTable(globalXESLogListStrings.get(Integer.parseInt(traceId)), 
                    DetailPanel3_new.tblTracedetail);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_lblShowInTableMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> cmbTypeToVisualize;
    public static javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblCurrentPage;
    private javax.swing.JLabel lblLeftPage;
    private javax.swing.JLabel lblRightPage;
    public static javax.swing.JLabel lblShowInTable;
    public static javax.swing.JLabel lblTotalRow;
    public static javax.swing.JLabel lblViewGraph;
    public static javax.swing.JTable tblTraceSummary;
    // End of variables declaration//GEN-END:variables
}
