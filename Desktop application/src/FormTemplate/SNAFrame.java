/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormTemplate;

import Helper.Common.Variables;
import SNAHelper.FormatGraph;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import static SNAHelper.Filter.FilterCentrality;
import static SNAHelper.FormatGraph.HightLightByNodeId;
import javax.swing.JComboBox;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author DinhLam Pham
 */
public class SNAFrame extends javax.swing.JFrame {

    public static String viewingType="";
    /**
     * Creates new form SNA
     */
    public SNAFrame() {
        initComponents();
        Initialize();
       
        
    }
    public void InitCmbNode(JComboBox jComboBox, Graph graph)
    {
        jComboBox.removeAllItems();
        for(Node node:graph)
        {
            jComboBox.addItem(node.getId().toString());
        }
    }
    
    public void Initialize() 
    {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2 + 100);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        
        slideDegreeMin.setValue(0);
        slideDegreeMax.setValue(100);
        
        
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
        jLabel7 = new javax.swing.JLabel();
        slideDegreeMin = new javax.swing.JSlider();
        jLabel8 = new javax.swing.JLabel();
        slideDegreeMax = new javax.swing.JSlider();
        lblDegreeMin = new javax.swing.JLabel();
        lblDegreeMax = new javax.swing.JLabel();
        cmbCentralityType = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        cbAutoRefresh = new javax.swing.JCheckBox();
        btnRefreshDegreeCentrality = new javax.swing.JToggleButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblCurrentNodeName = new javax.swing.JLabel();
        btnHighlightByNodeName = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cbOutgoingEdge = new javax.swing.JCheckBox();
        cbIncomingEdge = new javax.swing.JCheckBox();
        cbShowOtherNode = new javax.swing.JCheckBox();
        cbShowLabel = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        cmbNodeLevel = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cmbEdgeLevel = new javax.swing.JComboBox<>();
        btnClose = new javax.swing.JButton();
        cbShowRelativeNode = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Highlight by percentage"));

        jLabel7.setText("min");

        slideDegreeMin.setValue(0);
        slideDegreeMin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slideDegreeMinStateChanged(evt);
            }
        });
        slideDegreeMin.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                slideDegreeMinPropertyChange(evt);
            }
        });

        jLabel8.setText("max");

        slideDegreeMax.setValue(100);
        slideDegreeMax.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slideDegreeMaxStateChanged(evt);
            }
        });

        lblDegreeMin.setText("0");

        lblDegreeMax.setText("100");

        cmbCentralityType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Degree centrality", "Closeness centrality", "Betweenness centrality" }));
        cmbCentralityType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCentralityTypeActionPerformed(evt);
            }
        });

        jLabel1.setText("Highlight type");

        cbAutoRefresh.setSelected(true);
        cbAutoRefresh.setText("Auto refresh");

        btnRefreshDegreeCentrality.setText("Refresh");
        btnRefreshDegreeCentrality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshDegreeCentralityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(slideDegreeMin, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                            .addComponent(slideDegreeMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDegreeMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDegreeMin, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCentralityType, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbAutoRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefreshDegreeCentrality)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmbCentralityType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbAutoRefresh)
                            .addComponent(btnRefreshDegreeCentrality))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(slideDegreeMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDegreeMin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slideDegreeMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(lblDegreeMax))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Manual highlight"));

        jLabel2.setText("Node name");

        lblCurrentNodeName.setText("...");

        btnHighlightByNodeName.setText("High light");
        btnHighlightByNodeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHighlightByNodeNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblCurrentNodeName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHighlightByNodeName)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblCurrentNodeName)
                    .addComponent(btnHighlightByNodeName))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbOutgoingEdge.setSelected(true);
        cbOutgoingEdge.setText("Outgoing edge");

        cbIncomingEdge.setSelected(true);
        cbIncomingEdge.setText("Incoming edge");

        cbShowOtherNode.setText("Other node");

        cbShowLabel.setSelected(true);
        cbShowLabel.setText("Show label");
        cbShowLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbShowLabelActionPerformed(evt);
            }
        });

        jLabel5.setText("Node");

        cmbNodeLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cmbNodeLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNodeLevelActionPerformed(evt);
            }
        });

        jLabel6.setText("Edge");

        cmbEdgeLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cmbEdgeLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEdgeLevelActionPerformed(evt);
            }
        });

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        cbShowRelativeNode.setText("Relative node");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbOutgoingEdge)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbIncomingEdge)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbShowOtherNode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbShowRelativeNode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbShowLabel))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbNodeLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEdgeLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbOutgoingEdge)
                    .addComponent(cbIncomingEdge)
                    .addComponent(cbShowOtherNode)
                    .addComponent(cbShowLabel)
                    .addComponent(cbShowRelativeNode))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(cmbNodeLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(cmbEdgeLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void cmbCentralityTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCentralityTypeActionPerformed
        try {
            // TODO add your handling code here:
            Object currentSelectType = cmbCentralityType.getSelectedItem();
            if(currentSelectType == null)
                return;
            switch(currentSelectType.toString())
            {
                case "Normal":
                    viewingType = "Normal";
                    FormatGraph.SetDefaultStyle(Variables.globalGraph);
                    break;
                
                case "Degree centrality":
                    viewingType = "Degree";
                    FormatGraph.FormatNodeOnDegree(Variables.globalGraph, viewingType);
                    break;
                case "Closeness centrality":
                    viewingType = "Closeness";
                    FormatGraph.FormatNodeOnDegree(Variables.globalGraph, viewingType);
                    break;
                case "Betweenness centrality":
                    viewingType = "Betweenness";
                    FormatGraph.FormatNodeOnDegree(Variables.globalGraph, viewingType);
                    break;
            }
               
        } catch (InterruptedException ex) {
            Logger.getLogger(SNAFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmbCentralityTypeActionPerformed

    private void cmbNodeLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNodeLevelActionPerformed
        // TODO add your handling code here:
        FormatGraph.SetNodeFormatLevel(Variables.globalGraph, cmbNodeLevel.getSelectedItem().toString());
    }//GEN-LAST:event_cmbNodeLevelActionPerformed

    private void cmbEdgeLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEdgeLevelActionPerformed
        // TODO add your handling code here:
        FormatGraph.SetEdgeFormatLevel(Variables.globalGraph, cmbEdgeLevel.getSelectedItem().toString());
    }//GEN-LAST:event_cmbEdgeLevelActionPerformed

    private void cbShowLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbShowLabelActionPerformed
        // TODO add your handling code here:
        FormatGraph.GraphLabelToggle(Variables.globalGraph, cbShowLabel.isSelected());
    }//GEN-LAST:event_cbShowLabelActionPerformed

    private void slideDegreeMinPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_slideDegreeMinPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_slideDegreeMinPropertyChange

    private void slideDegreeMaxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slideDegreeMaxStateChanged
        // TODO add your handling code here:
        Integer maxVal = slideDegreeMax.getValue();
        lblDegreeMax.setText(maxVal.toString());
        
        if(slideDegreeMin.getValue()>maxVal)
            slideDegreeMin.setValue(maxVal);
        
        if(cbAutoRefresh.isSelected())
            RefreshFilter();
    }//GEN-LAST:event_slideDegreeMaxStateChanged

    private void slideDegreeMinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slideDegreeMinStateChanged
        // TODO add your handling code here:
        Integer minVal = slideDegreeMin.getValue();
        lblDegreeMin.setText(minVal.toString());
        
        if(slideDegreeMax.getValue()<minVal)
            slideDegreeMax.setValue(minVal);
        if(cbAutoRefresh.isSelected())
            RefreshFilter();
    }//GEN-LAST:event_slideDegreeMinStateChanged

    private void btnRefreshDegreeCentralityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshDegreeCentralityActionPerformed
        // TODO add your handling code here:
        RefreshFilter();
        
    }//GEN-LAST:event_btnRefreshDegreeCentralityActionPerformed

    private void btnHighlightByNodeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHighlightByNodeNameActionPerformed
        // TODO add your handling code here:
        HightLightByNodeId(Variables.globalGraph, lblCurrentNodeName.getText(), cbShowRelativeNode.isSelected(),
                cbShowOtherNode.isSelected(), cbIncomingEdge.isSelected(), cbOutgoingEdge.isSelected());
    }//GEN-LAST:event_btnHighlightByNodeNameActionPerformed

    public void RefreshFilter()
    {
        FilterCentrality(viewingType, Variables.globalGraph, slideDegreeMin.getValue(), slideDegreeMax.getValue(), 
                cbShowOtherNode.isSelected(), cbOutgoingEdge.isSelected(), cbIncomingEdge.isSelected());
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SNAFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SNAFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SNAFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SNAFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SNAFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHighlightByNodeName;
    private javax.swing.JToggleButton btnRefreshDegreeCentrality;
    private javax.swing.JCheckBox cbAutoRefresh;
    private javax.swing.JCheckBox cbIncomingEdge;
    private javax.swing.JCheckBox cbOutgoingEdge;
    private javax.swing.JCheckBox cbShowLabel;
    private javax.swing.JCheckBox cbShowOtherNode;
    private javax.swing.JCheckBox cbShowRelativeNode;
    public static javax.swing.JComboBox cmbCentralityType;
    private javax.swing.JComboBox<String> cmbEdgeLevel;
    private javax.swing.JComboBox<String> cmbNodeLevel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JLabel lblCurrentNodeName;
    private javax.swing.JLabel lblDegreeMax;
    private javax.swing.JLabel lblDegreeMin;
    private javax.swing.JSlider slideDegreeMax;
    public static javax.swing.JSlider slideDegreeMin;
    // End of variables declaration//GEN-END:variables
}
