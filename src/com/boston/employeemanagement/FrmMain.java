/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boston.employeemanagement;

/**
 *
 * @author Mthunzi
 */
public class FrmMain extends javax.swing.JFrame {

    /**
     * Creates new form FrmMain
     */
    public FrmMain() {
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

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuItemExit = new javax.swing.JMenuItem();
        mnuEmployee = new javax.swing.JMenu();
        mnuItemEmployeeInfo = new javax.swing.JMenuItem();
        mnuItemSalaryInfo = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(218, 129, 129));

        jMenu1.setText("File");

        mnuItemExit.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        mnuItemExit.setText("Exit");
        mnuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(mnuItemExit);

        jMenuBar1.add(jMenu1);

        mnuEmployee.setText("Employees");

        mnuItemEmployeeInfo.setFont(new java.awt.Font("Ebrima", 1, 13)); // NOI18N
        mnuItemEmployeeInfo.setText("Employee Information");
        mnuItemEmployeeInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemEmployeeInfoActionPerformed(evt);
            }
        });
        mnuEmployee.add(mnuItemEmployeeInfo);

        mnuItemSalaryInfo.setFont(new java.awt.Font("Ebrima", 1, 13)); // NOI18N
        mnuItemSalaryInfo.setText("Salary Information");
        mnuItemSalaryInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSalaryInfoActionPerformed(evt);
            }
        });
        mnuEmployee.add(mnuItemSalaryInfo);

        jMenuBar1.add(mnuEmployee);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 905, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuItemEmployeeInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemEmployeeInfoActionPerformed
        // TODO add your handling code here:
        EmployeeInformation Employee = new EmployeeInformation();
        Employee.show();
        
    }//GEN-LAST:event_mnuItemEmployeeInfoActionPerformed

    private void mnuItemSalaryInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSalaryInfoActionPerformed
        // TODO add your handling code here:
        SalaryInformation Salary = new SalaryInformation();
        Salary.show();
    }//GEN-LAST:event_mnuItemSalaryInfoActionPerformed

    private void mnuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_mnuItemExitActionPerformed

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
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMain().setVisible(true);
            }
        });
        
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu mnuEmployee;
    private javax.swing.JMenuItem mnuItemEmployeeInfo;
    private javax.swing.JMenuItem mnuItemExit;
    private javax.swing.JMenuItem mnuItemSalaryInfo;
    // End of variables declaration//GEN-END:variables
}
