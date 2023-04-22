//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.main;

import com.nhom13.DAO.TAIKHOANDAO;
import com.nhom13.model.TAIKHOAN;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class MainPage extends javax.swing.JFrame {

    public MainPage() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public MainPage(TAIKHOAN Account) {
        initComponents();
        this.setLocationRelativeTo(null);
        try {
            TAIKHOANDAO dao = new TAIKHOANDAO();

            lblHello.setText("Xin chào:"
                    + " " + dao.getNameStaffByMaNV(Account.getMANV()));
        } catch (Exception ex) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnContainer = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnManagerment = new javax.swing.JPanel();
        lblProject = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();
        lblHello = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TRANG CHỦ");
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        setResizable(false);

        pnContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 5));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Logo_Large.png"))); // NOI18N

        pnManagerment.setBackground(new java.awt.Color(255, 255, 255));

        lblProject.setFont(new java.awt.Font("Roboto Mono", 1, 24)); // NOI18N
        lblProject.setForeground(new java.awt.Color(0, 204, 204));
        lblProject.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProject.setText("QUẢN LÝ BUÔN BÁN HẢI SẢN");

        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(51, 51, 51));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/check-out.png"))); // NOI18N
        btnLogout.setText("ĐĂNG XUẤT");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        btnLogout.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLogoutKeyPressed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExit.setForeground(new java.awt.Color(51, 51, 51));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/power.png"))); // NOI18N
        btnExit.setText("THOÁT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        btnExit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnExitKeyPressed(evt);
            }
        });

        btnStart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnStart.setForeground(new java.awt.Color(51, 51, 51));
        btnStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/octopus.png"))); // NOI18N
        btnStart.setText("BẮT ĐẦU");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        btnStart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnStartKeyPressed(evt);
            }
        });

        lblHello.setFont(new java.awt.Font("Source Code Pro", 3, 18)); // NOI18N
        lblHello.setForeground(new java.awt.Color(153, 0, 204));
        lblHello.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnManagermentLayout = new javax.swing.GroupLayout(pnManagerment);
        pnManagerment.setLayout(pnManagermentLayout);
        pnManagermentLayout.setHorizontalGroup(
            pnManagermentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnManagermentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(lblHello, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblProject, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnManagermentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnManagermentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(181, 181, 181))
        );
        pnManagermentLayout.setVerticalGroup(
            pnManagermentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnManagermentLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblProject, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblHello, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout pnContainerLayout = new javax.swing.GroupLayout(pnContainer);
        pnContainer.setLayout(pnContainerLayout);
        pnContainerLayout.setHorizontalGroup(
            pnContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnContainerLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnManagerment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnContainerLayout.setVerticalGroup(
            pnContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnManagerment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnContainer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất hay không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            dispose();
            Login account = new Login();
            account.setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        dispose();
        SeaFood seaFood = new SeaFood();
        seaFood.setVisible(true);
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnStartKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnStartKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dispose();
            SeaFood seaFood = new SeaFood();
            seaFood.setVisible(true);
        }
    }//GEN-LAST:event_btnStartKeyPressed

    private void btnLogoutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLogoutKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất hay không?", "Thông báo", 2);
            if (click == JOptionPane.YES_OPTION) {
                dispose();
                Login account = new Login();
                account.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnLogoutKeyPressed

    private void btnExitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExitKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn thoát chương trình hay không?", "Thông báo", 2);
            if (click == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }//GEN-LAST:event_btnExitKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel lblHello;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblProject;
    private javax.swing.JPanel pnContainer;
    private javax.swing.JPanel pnManagerment;
    // End of variables declaration//GEN-END:variables
}
