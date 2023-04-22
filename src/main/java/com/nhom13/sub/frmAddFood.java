//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.sub;

import com.nhom13.DAO.MONANDAO;
import com.nhom13.model.MONAN;
import com.nhom13.main.Menu;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class frmAddFood extends javax.swing.JFrame {

    private Menu menu;

    public frmAddFood() {
        initComponents();
    }

    public frmAddFood(Menu menu) {
        initComponents();
        this.menu = menu;
    }

    private void addFood() {
        MONAN monAn = getInfoFoodFromNote();
        try {
            MONANDAO dao = new MONANDAO();
            dao.addMONAN(monAn);
        } catch (Exception ex) {
        }
    }

    private void editFood() {
        MONAN monAn = getInfoFoodFromNote();
        try {
            MONANDAO dao = new MONANDAO();
            dao.editMONAN(monAn);
        } catch (Exception ex) {
        }
    }

    private void deleteFood() {
        MONAN monAn = getInfoFoodFromNote();
        int IDMA = monAn.getID();
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xoá hải sản này không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            try {
                MONANDAO dao = new MONANDAO();
                dao.deleteMONAN(IDMA);
                
                dispose();
                menu.tpTable.removeAll();
                menu.initTabbedPanel();
                menu.tpTable.revalidate();
            } catch (Exception ex) {

            }
        }
    }

    private MONAN getInfoFoodFromNote() {
        MONAN monAn = new MONAN();
        System.out.println(Integer.parseInt(txtIDMA.getText()));
        monAn.setID(Integer.parseInt(txtIDMA.getText()));
        monAn.setTENMONAN(txtTENMA.getText());
        monAn.setGIA(Integer.parseInt(txtGIA.getText()));
        monAn.setMAHS(txtMAHS.getText());
        monAn.setHINHANH(tfLink.getText());
        return monAn;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfLink = new javax.swing.JTextField();
        pnAddFood = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIDMA = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTENMA = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtGIA = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtMAHS = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        lblHINHANH = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("NHẬP THÊM MÓN ĂN");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        pnAddFood.setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Source Code Pro", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(153, 0, 153));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/seafood.png"))); // NOI18N
        lblTitle.setText("THÔNG TIN MÓN ĂN");

        jLabel2.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel2.setText("ID:");

        txtIDMA.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel5.setText("Tên món ăn:");

        jLabel13.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel13.setText("Giá:");

        jLabel15.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel15.setText("Mã hải sản:");

        txtMAHS.setEnabled(false);

        jLabel17.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel17.setText("Hình ảnh:");

        lblHINHANH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHINHANH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/image_default.png"))); // NOI18N
        lblHINHANH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHINHANHMouseClicked(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnAdd.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plus.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save-file.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/writing.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnAdd)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addGap(18, 18, 18)
                .addComponent(btnEdit)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnAddFoodLayout = new javax.swing.GroupLayout(pnAddFood);
        pnAddFood.setLayout(pnAddFoodLayout);
        pnAddFoodLayout.setHorizontalGroup(
            pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnAddFoodLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnAddFoodLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnAddFoodLayout.createSequentialGroup()
                        .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnAddFoodLayout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                        .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnAddFoodLayout.createSequentialGroup()
                                .addComponent(lblHINHANH)
                                .addContainerGap())
                            .addComponent(txtMAHS)))
                    .addGroup(pnAddFoodLayout.createSequentialGroup()
                        .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIDMA)
                            .addComponent(txtTENMA)
                            .addComponent(txtGIA)))))
        );
        pnAddFoodLayout.setVerticalGroup(
            pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAddFoodLayout.createSequentialGroup()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtIDMA, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTENMA, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGIA, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMAHS, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAddFoodLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnAddFoodLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHINHANH, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 389, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnAddFood, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnAddFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        StringBuilder sb = new StringBuilder();

        if (txtTENMA.getText().isEmpty() || txtTENMA.getText().isBlank()) {
            sb.append("- Không được để trống tên món ăn\n");
        }
        if (txtGIA.getText().isEmpty() || txtGIA.getText().isBlank()) {
            sb.append("- Không được để trống giá\n");
        }
        if (sb.length() > 0) {
            JOptionPane.showMessageDialog(this, sb.toString(), "Không hợp lệ!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        this.addFood();
        dispose();
        menu.tpTable.removeAll();
        menu.initTabbedPanel();
        menu.tpTable.revalidate();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        StringBuilder sb = new StringBuilder();

        if (txtTENMA.getText().isEmpty() || txtTENMA.getText().isBlank()) {
            sb.append("- Không được để trống tên món ăn\n");
        }
        if (txtGIA.getText().isEmpty() || txtGIA.getText().isBlank()) {
            sb.append("- Không được để trống giá\n");
        }
        if (sb.length() > 0) {
            JOptionPane.showMessageDialog(this, sb.toString(), "Không hợp lệ!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.editFood();
        
        dispose();
        menu.tpTable.removeAll();
        menu.initTabbedPanel();
        menu.tpTable.revalidate();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        btnSave.setEnabled(true);
        txtTENMA.setEnabled(true);
        txtGIA.setEnabled(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        this.deleteFood();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void lblHINHANHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHINHANHMouseClicked
        if (btnSave.isEnabled() || btnAdd.isEnabled()) {
            JFileChooser open = new JFileChooser();
            open.setDialogTitle("Chọn ảnh");
            int file = open.showOpenDialog(null);
            if (file == JFileChooser.APPROVE_OPTION) {
                tfLink.setText(open.getCurrentDirectory().toString() + "\\" + open.getSelectedFile().getName());
                ImageIcon icon = new ImageIcon(tfLink.getText());
                lblHINHANH.setIcon(icon);
                Image img = icon.getImage().getScaledInstance(lblHINHANH.getWidth(), lblHINHANH.getHeight(), Image.SCALE_SMOOTH);
                lblHINHANH.setIcon(new ImageIcon(img));
            } else {
                JOptionPane.showMessageDialog(frmAddFood.this, "Chưa chọn đường dẫn tới ảnh!!");
            }
        }
    }//GEN-LAST:event_lblHINHANHMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAdd;
    public javax.swing.JButton btnDelete;
    public javax.swing.JButton btnEdit;
    public javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JLabel lblHINHANH;
    public javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnAddFood;
    public javax.swing.JTextField tfLink;
    public javax.swing.JTextField txtGIA;
    public javax.swing.JTextField txtIDMA;
    public javax.swing.JTextField txtMAHS;
    public javax.swing.JTextField txtTENMA;
    // End of variables declaration//GEN-END:variables
}
