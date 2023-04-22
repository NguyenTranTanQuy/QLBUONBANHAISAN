//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.main;

import com.nhom13.DAO.CTPHIEUNHAPDAO;
import com.nhom13.DAO.PHIEUNHAPDAO;
import com.nhom13.model.CTPHIEUNHAP;
import com.nhom13.model.PHIEUNHAP;
import com.nhom13.sub.frmAddSeaFood;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ImportGoods extends javax.swing.JFrame {

    private final DefaultTableModel tblModelPN = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final DefaultTableModel tblModelCTPN = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private List<PHIEUNHAP> Notes = new ArrayList<>();
    private List<CTPHIEUNHAP> noteDetails = new ArrayList<>();
    private final frmAddSeaFood addSeaFood = new frmAddSeaFood(this);

    public ImportGoods() {
        initComponents();
        initTablePhieuNhap();
        fillTablePhieuNhap();
    }

    private void initTablePhieuNhap() {
        String[] header = new String[]{"IDPN", "NGÀY NHẬP HÀNG"};
        
        tblModelPN.setColumnIdentifiers(header);
        tblPhieuNhap.setModel(tblModelPN);
    }

    private void fillTablePhieuNhap() {
        try {
            PHIEUNHAPDAO dao = new PHIEUNHAPDAO();
            Notes = dao.getAllPhieuNhap();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tblModelPN.setRowCount(0);
        for (PHIEUNHAP note : Notes) {
            tblModelPN.addRow(new String[]{String.valueOf(note.getID()), note.getNGAYNHAP()});
        }
        tblModelPN.fireTableDataChanged();
    }

    private void initTableCTPhieuNhap() {
        String[] header = new String[]{"IDPN", "MÃ HẢI SẢN", "SỐ LƯỢNG", "ĐƠN GIÁ", "ĐƠN VỊ"};        
        tblModelCTPN.setColumnIdentifiers(header);
        tblCTPhieuNhap.setModel(tblModelCTPN);
    }

    public void fillTableCTPhieuNhap(int IDPN) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        int totalValue = 0;
        try {
            CTPHIEUNHAPDAO dao = new CTPHIEUNHAPDAO();
            noteDetails = dao.getALLCTPhieuNhap(IDPN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tblModelCTPN.setRowCount(0);
        for (CTPHIEUNHAP noteDetail : noteDetails) {
            totalValue += (noteDetail.getDONGIA() * noteDetail.getSOLUONG());
            tblModelCTPN.addRow(new String[]{String.valueOf(noteDetail.getIDPN()), noteDetail.getMAHS(), String.valueOf(noteDetail.getSOLUONG()), formatter.format(noteDetail.getDONGIA()) + " VNĐ", noteDetail.getDONVI()});
        }
        txtTotalValue.setText(formatter.format(totalValue) + " VNĐ");
        tblModelCTPN.fireTableDataChanged();
    }

    private void addNote() {
        int IDPN;
        try {
            IDPN = Notes.get(Notes.size() - 1).getID() + 1;
        } catch (Exception e) {
            IDPN = 1;
        }

        try {
            PHIEUNHAPDAO dao = new PHIEUNHAPDAO();
            dao.addPhieuNhap(IDPN);
        } catch (Exception ex) {
        }
    }

    private void deleteNote() {
        int index = tblPhieuNhap.getSelectedRow();
        int IDPN = Notes.get(index).getID();
        try {
            PHIEUNHAPDAO dao = new PHIEUNHAPDAO();
            dao.deletePhieuNhap(IDPN);
        } catch (Exception ex) {
        }
    }

    private void showNoteDetail() {
        int index = tblCTPhieuNhap.getSelectedRow();
        CTPHIEUNHAP noteDetail = noteDetails.get(index);
        addSeaFood.setTitle("CHỈNH SỬA THÔNG TIN CHI TIẾT");
        addSeaFood.lblTitle.setText("CHỈNH SỬA HÀNG NHẬP");
        addSeaFood.txtMAHS.setText(noteDetail.getMAHS());
        addSeaFood.txtSOLUONG.setText(String.valueOf(noteDetail.getSOLUONG()));
        addSeaFood.txtGIA.setText(String.valueOf(noteDetail.getDONGIA()));
        addSeaFood.cbUnit.setSelectedItem(noteDetail.getDONVI());
        enabledButton(Arrays.asList(addSeaFood.btnEdit, addSeaFood.btnDelete));
        disabledTxt(Arrays.asList(addSeaFood.txtMAHS, addSeaFood.txtSOLUONG, addSeaFood.txtGIA));
        addSeaFood.cbUnit.setEnabled(false);
    }

    private void reloadCardNoteDetail() {
        addSeaFood.txtMAHS.setText("");
        addSeaFood.txtSOLUONG.setText("");
        addSeaFood.txtGIA.setText("");
        addSeaFood.cbUnit.setSelectedIndex(0);
    }

    private void Enabled() {
        btnShowDetail.setEnabled(true);
        btnAddSeaFood.setEnabled(true);
        tblCTPhieuNhap.setEnabled(true);
    }

    private void Disabled() {
        btnShowDetail.setEnabled(false);
        tblCTPhieuNhap.setEnabled(false);
        btnAddSeaFood.setEnabled(false);
        btnEditSeaFood.setEnabled(false);
        tblModelCTPN.setRowCount(0);
    }

    private void enabledButton(List<JButton> btns) {
        btns.forEach(btn -> btn.setEnabled(true));
    }

    private void enabledTxt(List<JTextField> txts) {
        txts.forEach(txt -> txt.setEnabled(true));
    }

    private void disabledTxt(List<JTextField> txts) {
        txts.forEach(txt -> txt.setEnabled(false));
    }

    private void disabledButton(List<JButton> btns) {
        btns.forEach(btn -> btn.setEnabled(false));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panelTrangChu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelHaiSan = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        panelThucDon = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panelDatBan = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelDoanhThu = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        panelNhanVien = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        panelDSBan = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        panelPhieuNhap = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        panelThongTin = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        pnImportGoods = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnShowDetail = new javax.swing.JButton();
        pnPhieuNhap = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();
        btnAddNote = new javax.swing.JButton();
        btnDeleteNote = new javax.swing.JButton();
        pnCTPhieuNhap = new javax.swing.JPanel();
        btnAddSeaFood = new javax.swing.JButton();
        btnEditSeaFood = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblCTPhieuNhap = new javax.swing.JTable();
        txtTotalValue = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nhập hàng ");
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelTrangChu.setBackground(new java.awt.Color(51, 51, 51));
        panelTrangChu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelTrangChuMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelTrangChuMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelTrangChuMouseEntered(evt);
            }
        });
        panelTrangChu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/home.png"))); // NOI18N
        panelTrangChu.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jLabel3.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Trang chủ");
        panelTrangChu.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 110, 50));

        jPanel2.add(panelTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 180, 50));

        panelHaiSan.setBackground(new java.awt.Color(51, 51, 51));
        panelHaiSan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelHaiSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelHaiSanMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelHaiSanMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelHaiSanMouseEntered(evt);
            }
        });
        panelHaiSan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/octopus1.png"))); // NOI18N
        panelHaiSan.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jLabel31.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Hải sản");
        panelHaiSan.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 110, 50));

        jPanel2.add(panelHaiSan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 180, 50));

        panelThucDon.setBackground(new java.awt.Color(51, 51, 51));
        panelThucDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelThucDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelThucDonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelThucDonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelThucDonMouseExited(evt);
            }
        });
        panelThucDon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/menu.png"))); // NOI18N
        panelThucDon.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jLabel6.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Thực đơn");
        panelThucDon.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 110, 50));

        jPanel2.add(panelThucDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 180, 50));

        panelDatBan.setBackground(new java.awt.Color(51, 51, 51));
        panelDatBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelDatBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDatBanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDatBanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelDatBanMouseExited(evt);
            }
        });
        panelDatBan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/table.png"))); // NOI18N
        panelDatBan.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jLabel9.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Đặt bàn");
        panelDatBan.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 110, 50));

        jPanel2.add(panelDatBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 180, 50));

        panelDoanhThu.setBackground(new java.awt.Color(51, 51, 51));
        panelDoanhThu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelDoanhThu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDoanhThuMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelDoanhThuMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDoanhThuMouseEntered(evt);
            }
        });
        panelDoanhThu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/statistics.png"))); // NOI18N
        panelDoanhThu.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jLabel18.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Thống kê");
        panelDoanhThu.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 110, 50));

        jPanel2.add(panelDoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 180, 50));

        panelNhanVien.setBackground(new java.awt.Color(51, 51, 51));
        panelNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseExited(evt);
            }
        });
        panelNhanVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/management.png"))); // NOI18N
        panelNhanVien.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jLabel21.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Nhân viên");
        panelNhanVien.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 110, 50));

        jPanel2.add(panelNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 180, 50));

        panelDSBan.setBackground(new java.awt.Color(51, 51, 51));
        panelDSBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelDSBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDSBanMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelDSBanMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDSBanMouseEntered(evt);
            }
        });
        panelDSBan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/table.png"))); // NOI18N
        panelDSBan.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jLabel27.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("DS Bàn");
        panelDSBan.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 110, 50));

        jPanel2.add(panelDSBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 180, 50));

        panelPhieuNhap.setBackground(new java.awt.Color(0, 204, 204));
        panelPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelPhieuNhapMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelPhieuNhapMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelPhieuNhapMouseExited(evt);
            }
        });
        panelPhieuNhap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/fill.png"))); // NOI18N
        panelPhieuNhap.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jLabel33.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Nhập hàng");
        panelPhieuNhap.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 110, 50));

        jPanel2.add(panelPhieuNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 180, 50));

        panelThongTin.setBackground(new java.awt.Color(51, 51, 51));
        panelThongTin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelThongTin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelThongTinMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelThongTinMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelThongTinMouseEntered(evt);
            }
        });
        panelThongTin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/info.png"))); // NOI18N
        panelThongTin.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jLabel29.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Thông tin");
        panelThongTin.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 110, 50));

        jPanel2.add(panelThongTin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 180, 50));

        pnImportGoods.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/importGoods.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Source Code Pro", 1, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("IMPORT");

        jLabel8.setFont(new java.awt.Font("Source Code Pro", 1, 36)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("GOODS");

        btnShowDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/right-arrow.png"))); // NOI18N
        btnShowDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowDetailActionPerformed(evt);
            }
        });

        pnPhieuNhap.setBackground(new java.awt.Color(255, 255, 255));

        tblPhieuNhap.setFont(new java.awt.Font("Source Code Pro", 1, 12)); // NOI18N
        tblPhieuNhap.setForeground(new java.awt.Color(51, 51, 51));
        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDPN", "NGÀY NHẬP HÀNG"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblPhieuNhap.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblPhieuNhap.setGridColor(new java.awt.Color(51, 51, 51));
        tblPhieuNhap.setRowHeight(40);
        tblPhieuNhap.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tblPhieuNhap.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblPhieuNhap.setShowGrid(true);
        tblPhieuNhap.getTableHeader().setResizingAllowed(false);
        tblPhieuNhap.getTableHeader().setReorderingAllowed(false);
        tblPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhieuNhap);
        tblPhieuNhap.getTableHeader().setFont(new Font("Source Code Pro", 1, 12));
        tblPhieuNhap.getTableHeader().setPreferredSize(new Dimension(0, 40));

        btnAddNote.setBackground(new java.awt.Color(51, 51, 51));
        btnAddNote.setFont(new java.awt.Font("Source Code Pro", 1, 12)); // NOI18N
        btnAddNote.setForeground(new java.awt.Color(255, 255, 255));
        btnAddNote.setText("THÊM PHIẾU NHẬP");
        btnAddNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNoteActionPerformed(evt);
            }
        });

        btnDeleteNote.setBackground(new java.awt.Color(51, 51, 51));
        btnDeleteNote.setFont(new java.awt.Font("Source Code Pro", 1, 12)); // NOI18N
        btnDeleteNote.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteNote.setText("XÓA PHIẾU NHẬP");
        btnDeleteNote.setEnabled(false);
        btnDeleteNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteNoteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnPhieuNhapLayout = new javax.swing.GroupLayout(pnPhieuNhap);
        pnPhieuNhap.setLayout(pnPhieuNhapLayout);
        pnPhieuNhapLayout.setHorizontalGroup(
            pnPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPhieuNhapLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnPhieuNhapLayout.createSequentialGroup()
                        .addComponent(btnAddNote, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteNote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnPhieuNhapLayout.setVerticalGroup(
            pnPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPhieuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(pnPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddNote, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteNote, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        pnCTPhieuNhap.setBackground(new java.awt.Color(255, 255, 255));

        btnAddSeaFood.setBackground(new java.awt.Color(51, 51, 51));
        btnAddSeaFood.setFont(new java.awt.Font("Source Code Pro", 1, 12)); // NOI18N
        btnAddSeaFood.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSeaFood.setText("THÊM CHI TIẾT HẢI SẢN NHẬP");
        btnAddSeaFood.setEnabled(false);
        btnAddSeaFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSeaFoodActionPerformed(evt);
            }
        });

        btnEditSeaFood.setBackground(new java.awt.Color(51, 51, 51));
        btnEditSeaFood.setFont(new java.awt.Font("Source Code Pro", 1, 12)); // NOI18N
        btnEditSeaFood.setForeground(new java.awt.Color(255, 255, 255));
        btnEditSeaFood.setText("CHỈNH SỬA CHI TIẾT HẢI SẢN");
        btnEditSeaFood.setEnabled(false);
        btnEditSeaFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSeaFoodActionPerformed(evt);
            }
        });

        tblCTPhieuNhap.setFont(new java.awt.Font("Source Code Pro", 1, 12)); // NOI18N
        tblCTPhieuNhap.setForeground(new java.awt.Color(51, 51, 51));
        tblCTPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDPN", "MÃ HẢI SẢN", "SỐ LƯỢNG", "ĐƠN GIÁ", "ĐƠN VỊ"
            }
        ));
        tblCTPhieuNhap.setEnabled(false);
        tblCTPhieuNhap.setGridColor(new java.awt.Color(51, 51, 51));
        tblCTPhieuNhap.setRowHeight(40);
        tblCTPhieuNhap.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tblCTPhieuNhap.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblCTPhieuNhap.setShowGrid(true);
        tblCTPhieuNhap.getTableHeader().setResizingAllowed(false);
        tblCTPhieuNhap.getTableHeader().setReorderingAllowed(false);
        tblCTPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblCTPhieuNhap);
        tblCTPhieuNhap.getTableHeader().setFont(new Font("Source Code Pro", 1, 12));
        tblCTPhieuNhap.getTableHeader().setPreferredSize(new Dimension(0, 40));

        javax.swing.GroupLayout pnCTPhieuNhapLayout = new javax.swing.GroupLayout(pnCTPhieuNhap);
        pnCTPhieuNhap.setLayout(pnCTPhieuNhapLayout);
        pnCTPhieuNhapLayout.setHorizontalGroup(
            pnCTPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCTPhieuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddSeaFood)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditSeaFood, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnCTPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnCTPhieuNhapLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnCTPhieuNhapLayout.setVerticalGroup(
            pnCTPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCTPhieuNhapLayout.createSequentialGroup()
                .addGap(354, 354, 354)
                .addGroup(pnCTPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddSeaFood, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditSeaFood, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
            .addGroup(pnCTPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnCTPhieuNhapLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(47, Short.MAX_VALUE)))
        );

        txtTotalValue.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        txtTotalValue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalValue.setText("0 VNĐ");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 0, 153));
        jLabel11.setText("TỔNG GIÁ TRỊ:");

        javax.swing.GroupLayout pnImportGoodsLayout = new javax.swing.GroupLayout(pnImportGoods);
        pnImportGoods.setLayout(pnImportGoodsLayout);
        pnImportGoodsLayout.setHorizontalGroup(
            pnImportGoodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnImportGoodsLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnImportGoodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnImportGoodsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(74, Short.MAX_VALUE))
                    .addGroup(pnImportGoodsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnImportGoodsLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(pnPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnShowDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnCTPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnImportGoodsLayout.setVerticalGroup(
            pnImportGoodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnImportGoodsLayout.createSequentialGroup()
                .addGroup(pnImportGoodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnImportGoodsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnImportGoodsLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnImportGoodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTotalValue, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnImportGoodsLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel5)))
                .addGroup(pnImportGoodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnImportGoodsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnImportGoodsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnCTPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(pnImportGoodsLayout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(btnShowDetail)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnImportGoods, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnImportGoods, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTrangChuMouseClicked
        dispose();
        MainPage home = new MainPage();
        home.setVisible(true);
    }//GEN-LAST:event_panelTrangChuMouseClicked

    private void panelThucDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelThucDonMouseClicked
        dispose();
        Menu menu = new Menu();
        menu.setVisible(true);
    }//GEN-LAST:event_panelThucDonMouseClicked

    private void panelDatBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDatBanMouseClicked
        dispose();
        Reservation reservation = new Reservation();
        reservation.setVisible(true);
    }//GEN-LAST:event_panelDatBanMouseClicked

    private void panelDoanhThuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuMouseClicked
        dispose();
        Satistics satistics = new Satistics();
        satistics.setVisible(true);
    }//GEN-LAST:event_panelDoanhThuMouseClicked

    private void panelNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNhanVienMouseClicked
        dispose();
        PersonalManagement staff = new PersonalManagement();
        staff.setVisible(true);
    }//GEN-LAST:event_panelNhanVienMouseClicked

    private void panelDSBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDSBanMouseClicked
        dispose();
        ListTable listTable = new ListTable();
        listTable.setVisible(true);
    }//GEN-LAST:event_panelDSBanMouseClicked

    private void panelThongTinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelThongTinMouseClicked
        dispose();
        Information information = new Information();
        information.setVisible(true);
    }//GEN-LAST:event_panelThongTinMouseClicked

    private void panelTrangChuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTrangChuMouseEntered
        panelTrangChu.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelTrangChuMouseEntered

    private void panelTrangChuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTrangChuMouseExited
        panelTrangChu.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_panelTrangChuMouseExited

    private void panelThucDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelThucDonMouseEntered
        panelThucDon.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelThucDonMouseEntered

    private void panelThucDonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelThucDonMouseExited
        panelThucDon.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_panelThucDonMouseExited

    private void panelDatBanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDatBanMouseEntered
        panelDatBan.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelDatBanMouseEntered

    private void panelDatBanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDatBanMouseExited
        panelDatBan.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_panelDatBanMouseExited

    private void panelDoanhThuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuMouseEntered
        panelDoanhThu.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelDoanhThuMouseEntered

    private void panelDoanhThuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuMouseExited
        panelDoanhThu.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_panelDoanhThuMouseExited

    private void panelNhanVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNhanVienMouseEntered
        panelNhanVien.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelNhanVienMouseEntered

    private void panelNhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelNhanVienMouseExited
        panelNhanVien.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_panelNhanVienMouseExited

    private void panelDSBanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDSBanMouseEntered
        panelDSBan.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelDSBanMouseEntered

    private void panelDSBanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDSBanMouseExited
        panelDSBan.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_panelDSBanMouseExited

    private void panelThongTinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelThongTinMouseEntered
        panelThongTin.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelThongTinMouseEntered

    private void panelThongTinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelThongTinMouseExited
        panelThongTin.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_panelThongTinMouseExited

    private void panelHaiSanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHaiSanMouseClicked
        dispose();
        SeaFood seaFood = new SeaFood();
        seaFood.setVisible(true);
    }//GEN-LAST:event_panelHaiSanMouseClicked

    private void panelHaiSanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHaiSanMouseExited
        panelHaiSan.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_panelHaiSanMouseExited

    private void panelHaiSanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHaiSanMouseEntered
        panelHaiSan.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelHaiSanMouseEntered

    private void panelPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPhieuNhapMouseClicked

    }//GEN-LAST:event_panelPhieuNhapMouseClicked

    private void panelPhieuNhapMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPhieuNhapMouseExited
        panelPhieuNhap.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelPhieuNhapMouseExited

    private void panelPhieuNhapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPhieuNhapMouseEntered

    }//GEN-LAST:event_panelPhieuNhapMouseEntered

    private void btnAddSeaFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSeaFoodActionPerformed
        reloadCardNoteDetail();
        addSeaFood.setVisible(true);
        addSeaFood.lblTitle.setText("NHẬP HÀNG");
        enabledTxt(Arrays.asList(addSeaFood.txtMAHS, addSeaFood.txtSOLUONG, addSeaFood.txtGIA));
        addSeaFood.cbUnit.setEnabled(true);
        disabledButton(Arrays.asList(addSeaFood.btnSave, addSeaFood.btnEdit, addSeaFood.btnDelete));
        addSeaFood.btnAdd.setEnabled(true);
    }//GEN-LAST:event_btnAddSeaFoodActionPerformed

    private void tblPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuNhapMouseClicked
        Disabled();
        txtTotalValue.setText("0 VNĐ");
        btnDeleteNote.setEnabled(true);
        btnShowDetail.setEnabled(true);
    }//GEN-LAST:event_tblPhieuNhapMouseClicked

    private void btnShowDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowDetailActionPerformed
        int index = tblPhieuNhap.getSelectedRow();
        int IDPN = Notes.get(index).getID();
        Enabled();
        initTableCTPhieuNhap();
        fillTableCTPhieuNhap(IDPN);

        addSeaFood.txtIDPN.setText(String.valueOf(IDPN));
    }//GEN-LAST:event_btnShowDetailActionPerformed

    private void btnDeleteNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteNoteActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn phiếu nhập này không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            deleteNote();
            fillTablePhieuNhap();
            Disabled();
        }
    }//GEN-LAST:event_btnDeleteNoteActionPerformed

    private void btnAddNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNoteActionPerformed
        addNote();
        fillTablePhieuNhap();
        btnDeleteNote.setEnabled(false);
        Disabled();
    }//GEN-LAST:event_btnAddNoteActionPerformed

    private void tblCTPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTPhieuNhapMouseClicked
        btnEditSeaFood.setEnabled(true);
    }//GEN-LAST:event_tblCTPhieuNhapMouseClicked

    private void btnEditSeaFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSeaFoodActionPerformed
        showNoteDetail();
        addSeaFood.btnAdd.setEnabled(false);
        addSeaFood.btnSave.setEnabled(false);
        addSeaFood.setVisible(true);
    }//GEN-LAST:event_btnEditSeaFoodActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImportGoods.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ImportGoods().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNote;
    private javax.swing.JButton btnAddSeaFood;
    private javax.swing.JButton btnDeleteNote;
    private javax.swing.JButton btnEditSeaFood;
    private javax.swing.JButton btnShowDetail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel panelDSBan;
    private javax.swing.JPanel panelDatBan;
    private javax.swing.JPanel panelDoanhThu;
    private javax.swing.JPanel panelHaiSan;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPanel panelPhieuNhap;
    private javax.swing.JPanel panelThongTin;
    private javax.swing.JPanel panelThucDon;
    private javax.swing.JPanel panelTrangChu;
    private javax.swing.JPanel pnCTPhieuNhap;
    private javax.swing.JPanel pnImportGoods;
    private javax.swing.JPanel pnPhieuNhap;
    private javax.swing.JTable tblCTPhieuNhap;
    private javax.swing.JTable tblPhieuNhap;
    private javax.swing.JTextField txtTotalValue;
    // End of variables declaration//GEN-END:variables

}
