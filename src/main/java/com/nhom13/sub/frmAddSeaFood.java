//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.sub;

import com.nhom13.DAO.CTPHIEUNHAPDAO;
import com.nhom13.model.CTPHIEUNHAP;
import com.nhom13.main.ImportGoods;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class frmAddSeaFood extends javax.swing.JFrame {

    private ImportGoods importGoods;

    public frmAddSeaFood() {
        initComponents();
    }

    public frmAddSeaFood(ImportGoods importGoods) {
        initComponents();
        this.importGoods = importGoods;
    }

    private void addNoteDetail() {
        CTPHIEUNHAP noteDetail = getInfoNoteDetailFromNote();
        try {
            CTPHIEUNHAPDAO dao = new CTPHIEUNHAPDAO();
            dao.addCTPhieuNhap(noteDetail);
        } catch (Exception ex) {
        }
    }

    private void editNoteDetail() {
        CTPHIEUNHAP noteDetail = getInfoNoteDetailFromNote();
        try {
            CTPHIEUNHAPDAO dao = new CTPHIEUNHAPDAO();
            dao.editCTPhieuNhap(noteDetail);
        } catch (Exception ex) {
        }
    }

    private void deleteNoteDetail() {
        CTPHIEUNHAP noteDetail = getInfoNoteDetailFromNote();
        try {
            CTPHIEUNHAPDAO dao = new CTPHIEUNHAPDAO();
            dao.deleteCTPhieuNhap(noteDetail.getIDPN(), noteDetail.getMAHS());
        } catch (Exception ex) {
        }
    }

    private CTPHIEUNHAP getInfoNoteDetailFromNote() {
        CTPHIEUNHAP noteDetail = new CTPHIEUNHAP();

        noteDetail.setIDPN(Integer.parseInt(txtIDPN.getText()));
        noteDetail.setMAHS(txtMAHS.getText());
        noteDetail.setSOLUONG(Integer.parseInt(txtSOLUONG.getText()));
        noteDetail.setDONGIA(Integer.parseInt(txtGIA.getText()));
        noteDetail.setDONVI(cbUnit.getSelectedItem().toString());

        return noteDetail;
    }

    private void enabledTxt(List<JTextField> txts) {
        txts.forEach(txt -> txt.setEnabled(true));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfLink = new javax.swing.JTextField();
        pnAddFood = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIDPN = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMAHS = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSOLUONG = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtGIA = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        cbUnit = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("NHẬP THÊM HẢI SẢN");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        pnAddFood.setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Source Code Pro", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(153, 0, 153));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/clipboard.png"))); // NOI18N
        lblTitle.setText(" NHẬP HÀNG");

        jLabel2.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel2.setText("IDPN:");

        txtIDPN.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel5.setText("Mã hải sản:");

        jLabel13.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel13.setText("Số lượng");

        jLabel15.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel15.setText("Đơn giá:");

        jLabel17.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        jLabel17.setText("Đơn vị:");

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
                .addGap(0, 15, Short.MAX_VALUE))
        );

        cbUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kilogam", "Con", " " }));

        javax.swing.GroupLayout pnAddFoodLayout = new javax.swing.GroupLayout(pnAddFood);
        pnAddFood.setLayout(pnAddFoodLayout);
        pnAddFoodLayout.setHorizontalGroup(
            pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAddFoodLayout.createSequentialGroup()
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAddFoodLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
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
                                    .addComponent(txtGIA)
                                    .addComponent(cbUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pnAddFoodLayout.createSequentialGroup()
                                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMAHS, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                    .addComponent(txtSOLUONG)
                                    .addComponent(txtIDPN))))))
                .addContainerGap(15, Short.MAX_VALUE))
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnAddFoodLayout.setVerticalGroup(
            pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAddFoodLayout.createSequentialGroup()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtIDPN, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMAHS, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSOLUONG, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGIA, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(pnAddFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnAddFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnAddFood, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        StringBuilder sb = new StringBuilder();

        if (txtMAHS.getText().isEmpty() || txtMAHS.getText().isBlank()) {
            sb.append("- Không được để trống tên món ăn\n");
        }
        if (txtSOLUONG.getText().isEmpty() || txtSOLUONG.getText().isBlank()) {
            sb.append("- Không được để trống số lượng\n");
        }
        if (txtGIA.getText().isEmpty() || txtGIA.getText().isBlank()) {
            sb.append("- Không được để trống giá\n");
        }
        if (sb.length() > 0) {
            JOptionPane.showMessageDialog(this, sb.toString(), "Không hợp lệ!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        addNoteDetail();
        dispose();
        importGoods.fillTableCTPhieuNhap(Integer.parseInt(txtIDPN.getText()));
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn lưu chi tiết hải sản này không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            StringBuilder sb = new StringBuilder();

            if (txtMAHS.getText().isEmpty() || txtMAHS.getText().isBlank()) {
                sb.append("- Không được để trống tên món ăn\n");
            }
            if (txtSOLUONG.getText().isEmpty() || txtSOLUONG.getText().isBlank()) {
                sb.append("- Không được để trống số lượng\n");
            }
            if (txtGIA.getText().isEmpty() || txtGIA.getText().isBlank()) {
                sb.append("- Không được để trống giá\n");
            }
            if (sb.length() > 0) {
                JOptionPane.showMessageDialog(this, sb.toString(), "Không hợp lệ!",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.editNoteDetail();
            dispose();
            importGoods.fillTableCTPhieuNhap(Integer.parseInt(txtIDPN.getText()));
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        btnSave.setEnabled(true);
        enabledTxt(Arrays.asList(txtMAHS, txtSOLUONG, txtGIA));
        cbUnit.setEnabled(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa chi tiết hải sản này không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            deleteNoteDetail();
            dispose();
            importGoods.fillTableCTPhieuNhap(Integer.parseInt(txtIDPN.getText()));
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAdd;
    public javax.swing.JButton btnDelete;
    public javax.swing.JButton btnEdit;
    public javax.swing.JButton btnSave;
    public javax.swing.JComboBox<String> cbUnit;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnAddFood;
    public javax.swing.JTextField tfLink;
    public javax.swing.JTextField txtGIA;
    public javax.swing.JTextField txtIDPN;
    public javax.swing.JTextField txtMAHS;
    public javax.swing.JTextField txtSOLUONG;
    // End of variables declaration//GEN-END:variables
}
