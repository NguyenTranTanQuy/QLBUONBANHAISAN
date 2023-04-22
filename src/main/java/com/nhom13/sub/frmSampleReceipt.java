//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.sub;

public class frmSampleReceipt extends javax.swing.JFrame {

    public frmSampleReceipt() {
        initComponents();
        defaultReceipt();
    }

    private void defaultReceipt() {
        txtAreaSampleReceipt.setText("\n" + String.format("%16s", " ") + "HÓA ĐƠN CỦA BẠN" + String.format("%15s", " ") + "\n\n");
        txtAreaSampleReceipt.setText(txtAreaSampleReceipt.getText() + " Địa chỉ: 97 Man Thiện, phường Hiệp Phú,TP HCM\n");
        txtAreaSampleReceipt.setText(txtAreaSampleReceipt.getText() + " Số điện thoại: 09123456789\n");
        txtAreaSampleReceipt.setText(txtAreaSampleReceipt.getText() + " *********************************************\n\n");   
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaSampleReceipt = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preview Bill");

        jScrollPane1.setBorder(null);

        txtAreaSampleReceipt.setColumns(20);
        txtAreaSampleReceipt.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        txtAreaSampleReceipt.setLineWrap(true);
        txtAreaSampleReceipt.setRows(5);
        txtAreaSampleReceipt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 3));
        jScrollPane1.setViewportView(txtAreaSampleReceipt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(frmSampleReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmSampleReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmSampleReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmSampleReceipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmSampleReceipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea txtAreaSampleReceipt;
    // End of variables declaration//GEN-END:variables
}
