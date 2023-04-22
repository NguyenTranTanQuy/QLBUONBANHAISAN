//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.main;

import com.nhom13.DAO.BANDAO;
import com.nhom13.DAO.BANDATDAO;
import com.nhom13.model.BAN;
import com.nhom13.model.BANDAT;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Reservation extends javax.swing.JFrame {

    private final DefaultTableModel tblModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private List<BANDAT> reservationTables = new ArrayList<>();
    private List<BAN> Tables = new ArrayList<>();

    public Reservation() {
        initComponents();
        initDefaultValue();
        initLabelTime();
        initTableReservation();
        fillTableReservation();
    }

    private void initDefaultValue() {
        try {
            BANDAO dao = new BANDAO();
            Tables = dao.getAllBAN();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String[] nameTable = new String[Tables.size() + 1];
        for (int i = 0; i < Tables.size(); i++) {
            nameTable[i] = Tables.get(i).getTENBAN();
        }
        spBanSo.setModel(new SpinnerListModel(nameTable));
    }

    private void initLabelTime() {
        Timer timer = new Timer(1000, (ActionEvent e) -> {
            try {
                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa - dd/MM/yyyy");
                Date date = new Date();
                lblTime.setText(dateFormat.format(date));
            } catch (Exception ex) {
            }
        });
        timer.start();
    }

    private void initTableReservation() {
        String[] header = new String[]{"ID", "TÊN KHÁCH HÀNG", "SDT", "NGÀY", "GIỜ", "SL", "BÀN SỐ", "TRẠNG THÁI", "GHI CHÚ"};

        tblReservation.getTableHeader().setFont(new Font("Source Code Pro", 1, 12));
        tblReservation.getTableHeader().setPreferredSize(new Dimension(0, 40));

        tblModel.setColumnIdentifiers(header);
        tblReservation.setModel(tblModel);
        if (tblReservation.getColumnModel().getColumnCount() > 0) {
            tblReservation.getColumnModel().getColumn(0).setMinWidth(40);
            tblReservation.getColumnModel().getColumn(0).setMaxWidth(40);
            tblReservation.getColumnModel().getColumn(2).setMinWidth(80);
            tblReservation.getColumnModel().getColumn(2).setMaxWidth(80);
            tblReservation.getColumnModel().getColumn(3).setMinWidth(70);
            tblReservation.getColumnModel().getColumn(3).setMaxWidth(70);
            tblReservation.getColumnModel().getColumn(4).setMinWidth(60);
            tblReservation.getColumnModel().getColumn(4).setMaxWidth(60);
            tblReservation.getColumnModel().getColumn(5).setMinWidth(40);
            tblReservation.getColumnModel().getColumn(5).setMaxWidth(40);
            tblReservation.getColumnModel().getColumn(6).setMinWidth(60);
            tblReservation.getColumnModel().getColumn(6).setMaxWidth(60);
            tblReservation.getColumnModel().getColumn(7).setMinWidth(90);
            tblReservation.getColumnModel().getColumn(7).setMaxWidth(90);
            tblReservation.getColumnModel().getColumn(8).setMinWidth(60);
            tblReservation.getColumnModel().getColumn(8).setMaxWidth(60);
        }
    }

    private void fillTableReservation() {
        try {
            BANDATDAO dao = new BANDATDAO();
            reservationTables = dao.getAllBanDat();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tblModel.setRowCount(0);
        for (BANDAT reservationTable : reservationTables) {
            tblModel.addRow(new String[]{String.valueOf(reservationTable.getID()), reservationTable.getTENKHACHHANG(), reservationTable.getSDT(), reservationTable.getNGAYDAT(), reservationTable.getTHOIGIANDAT(), String.valueOf(reservationTable.getSOLUONGNGUOI()), reservationTable.getBANSO(), reservationTable.getTRANGTHAI(), reservationTable.getGHICHU()});
        }
        tblModel.fireTableDataChanged();
    }

    private void addBanDat() {
        BANDAT reservationTable = getInfoTableFromNote();

        try {
            BANDATDAO dao = new BANDATDAO();
            dao.addBanDat(reservationTable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void editBanDat() {
        BANDAT reservationTable = getInfoTableFromNote();
        int index = tblReservation.getSelectedRow();
        int IDBD = reservationTables.get(index).getID();
        reservationTable.setID(IDBD);
        try {
            BANDATDAO dao = new BANDATDAO();
            dao.editBanDat(reservationTable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteBanDat() {
        int index = tblReservation.getSelectedRow();
        int IDBD = reservationTables.get(index).getID();

        try {
            BANDATDAO dao = new BANDATDAO();
            dao.deleteBanDat(IDBD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showDetail() {
        int index = tblReservation.getSelectedRow();
        BANDAT reservationTable = reservationTables.get(index);

        txtTenKH.setText(reservationTable.getTENKHACHHANG());
        txtSDT.setText(reservationTable.getSDT());
        ((JTextField) txtNgayDat.getDateEditor().getUiComponent()).setText(reservationTable.getNGAYDAT());
        txtGioDat.setText(reservationTable.getTHOIGIANDAT());
        spSoLuong.setValue(reservationTable.getSOLUONGNGUOI());
        spBanSo.setValue(reservationTable.getBANSO());
        cbTrangThai.setSelectedItem(reservationTable.getTRANGTHAI());
        taGhiChu.setText(reservationTable.getGHICHU());
    }

    private void reloadCard() {
        txtTenKH.setText("");
        txtSDT.setText("");
        ((JTextField) txtNgayDat.getDateEditor().getUiComponent()).setText("");
        txtGioDat.setText("");
        spSoLuong.setValue(1);
        spBanSo.setValue(Tables.get(0).getTENBAN());
        cbTrangThai.setSelectedIndex(0);
        taGhiChu.setText("");
    }

    private BANDAT getInfoTableFromNote() {
        BANDAT reservationTable = new BANDAT();

        reservationTable.setTENKHACHHANG(txtTenKH.getText());
        reservationTable.setSDT(txtSDT.getText());
        reservationTable.setNGAYDAT(((JTextField) txtNgayDat.getDateEditor().getUiComponent()).getText());
        reservationTable.setTHOIGIANDAT(txtGioDat.getText());
        reservationTable.setSOLUONGNGUOI(Integer.parseInt(spSoLuong.getValue().toString()));
        reservationTable.setBANSO(spBanSo.getValue().toString());
        reservationTable.setTRANGTHAI(cbTrangThai.getSelectedItem().toString());
        reservationTable.setGHICHU(taGhiChu.getText());

        return reservationTable;
    }

    private void Enabled() {
        enabledButton(Arrays.asList(btnAddTime));
        enabledTxt(Arrays.asList(txtTenKH, txtSDT, txtGioDat));
        txtNgayDat.setEnabled(true);
        spSoLuong.setEnabled(true);
        spBanSo.setEnabled(true);
        cbTrangThai.setEnabled(true);
        taGhiChu.setEnabled(true);
    }

    private void Disabled() {
        disabledButton(Arrays.asList(btnAddTime));
        disabledTxt(Arrays.asList(txtTenKH, txtSDT, txtGioDat));
        txtNgayDat.setEnabled(false);
        spSoLuong.setEnabled(false);
        spBanSo.setEnabled(false);
        cbTrangThai.setEnabled(false);
        taGhiChu.setEnabled(false);
    }

    private void disabledButton(List<JButton> btns) {
        btns.forEach(btn -> btn.setEnabled(false));
    }

    private void enabledButton(List<JButton> btns) {
        btns.forEach(btn -> btn.setEnabled(true));
    }

    private void disabledTxt(List<JTextField> txts) {
        txts.forEach(txt -> txt.setEnabled(false));
    }

    private void enabledTxt(List<JTextField> txts) {
        txts.forEach(txt -> txt.setEnabled(true));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timePicker = new com.raven.swing.TimePicker();
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
        pnReservation = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReservation = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        spSoLuong = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taGhiChu = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        cbTrangThai = new javax.swing.JComboBox<>();
        spBanSo = new javax.swing.JSpinner();
        txtGioDat = new javax.swing.JTextField();
        btnAddTime = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtNgayDat = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        lblTime = new javax.swing.JLabel();

        timePicker.setBackground(new java.awt.Color(51, 51, 51));
        timePicker.setForeground(new java.awt.Color(51, 51, 51));
        timePicker.setDisplayText(txtGioDat);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đặt bàn");
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

        jPanel2.add(panelTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 180, 50));

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

        jPanel2.add(panelHaiSan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 180, 50));

        panelThucDon.setBackground(new java.awt.Color(51, 51, 51));
        panelThucDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelThucDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelThucDonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelThucDonMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelThucDonMouseEntered(evt);
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

        jPanel2.add(panelThucDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 180, 50));

        panelDatBan.setBackground(new java.awt.Color(0, 204, 204));
        panelDatBan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        jPanel2.add(panelDatBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 180, 50));

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

        jPanel2.add(panelDoanhThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 180, 50));

        panelNhanVien.setBackground(new java.awt.Color(51, 51, 51));
        panelNhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelNhanVienMouseEntered(evt);
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

        jPanel2.add(panelNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 180, 50));

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

        jPanel2.add(panelDSBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 180, 50));

        panelPhieuNhap.setBackground(new java.awt.Color(51, 51, 51));
        panelPhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelPhieuNhapMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelPhieuNhapMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelPhieuNhapMouseEntered(evt);
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

        jPanel2.add(panelPhieuNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 180, 50));

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

        jPanel2.add(panelThongTin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 180, 50));

        pnReservation.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/reserved.jpg"))); // NOI18N

        tblReservation.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tblReservation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "TÊN KHÁCH HÀNG", "SDT", "NGÀY", "GIỜ", "SL", "BÀN SỐ", "TRẠNG THÁI", "GHI CHÚ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblReservation.setGridColor(new java.awt.Color(51, 51, 51));
        tblReservation.setRowHeight(40);
        tblReservation.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tblReservation.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblReservation.setShowGrid(true);
        tblReservation.getTableHeader().setResizingAllowed(false);
        tblReservation.getTableHeader().setReorderingAllowed(false);
        tblReservation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblReservationMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblReservation);
        if (tblReservation.getColumnModel().getColumnCount() > 0) {
            tblReservation.getColumnModel().getColumn(0).setMinWidth(40);
            tblReservation.getColumnModel().getColumn(0).setMaxWidth(40);
            tblReservation.getColumnModel().getColumn(2).setMinWidth(80);
            tblReservation.getColumnModel().getColumn(2).setMaxWidth(80);
            tblReservation.getColumnModel().getColumn(3).setMinWidth(80);
            tblReservation.getColumnModel().getColumn(3).setMaxWidth(80);
            tblReservation.getColumnModel().getColumn(4).setMinWidth(80);
            tblReservation.getColumnModel().getColumn(4).setMaxWidth(80);
            tblReservation.getColumnModel().getColumn(5).setMinWidth(30);
            tblReservation.getColumnModel().getColumn(5).setMaxWidth(30);
            tblReservation.getColumnModel().getColumn(6).setMinWidth(50);
            tblReservation.getColumnModel().getColumn(6).setMaxWidth(50);
            tblReservation.getColumnModel().getColumn(7).setMinWidth(80);
            tblReservation.getColumnModel().getColumn(7).setMaxWidth(80);
            tblReservation.getColumnModel().getColumn(8).setMinWidth(60);
            tblReservation.getColumnModel().getColumn(8).setMaxWidth(60);
        }

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Nhập thông tin bàn đặt ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Source Code Pro", 1, 14), new java.awt.Color(0, 204, 204))); // NOI18N

        jLabel11.setText("Tên khách hàng:");
        jLabel11.setPreferredSize(new java.awt.Dimension(85, 40));

        jLabel13.setText("Số điện thoại: ");

        jLabel15.setText("Bàn số:");

        jLabel17.setText("Thời gian đặt:");

        jLabel20.setText("Số lương người:");

        spSoLuong.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel22.setText("Ghi chú:");

        taGhiChu.setColumns(20);
        taGhiChu.setRows(5);
        jScrollPane2.setViewportView(taGhiChu);

        jLabel23.setText("Trạng thái");

        cbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Không", "Đã đặt cọc" }));

        spBanSo.setModel(new javax.swing.SpinnerListModel(new String[] {""}));

        txtGioDat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGioDatActionPerformed(evt);
            }
        });

        btnAddTime.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        btnAddTime.setText("+");
        btnAddTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTimeActionPerformed(evt);
            }
        });

        jLabel8.setText("Ngày đặt:");

        txtNgayDat.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayDat.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgayDat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spBanSo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtGioDat, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddTime, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(cbTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGioDat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayDat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spBanSo)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CHỨC NĂNG", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Source Code Pro", 1, 36), new java.awt.Color(102, 0, 153))); // NOI18N

        btnAdd.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plus.png"))); // NOI18N
        btnAdd.setText("THÊM");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save-file.png"))); // NOI18N
        btnSave.setText("LƯU");
        btnSave.setEnabled(false);
        btnSave.setPreferredSize(new java.awt.Dimension(72, 70));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/writing.png"))); // NOI18N
        btnEdit.setText("SỬA");
        btnEdit.setEnabled(false);
        btnEdit.setPreferredSize(new java.awt.Dimension(72, 70));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        btnDelete.setText("XÓA");
        btnDelete.setEnabled(false);
        btnDelete.setPreferredSize(new java.awt.Dimension(72, 70));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReload.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        btnReload.setText("LÀM MỚI");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        lblTime.setFont(new java.awt.Font("Source Code Pro", 1, 18)); // NOI18N
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTime.setText("00 : 00 : 00 AM ../../..");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout pnReservationLayout = new javax.swing.GroupLayout(pnReservation);
        pnReservation.setLayout(pnReservationLayout);
        pnReservationLayout.setHorizontalGroup(
            pnReservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnReservationLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnReservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnReservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnReservationLayout.setVerticalGroup(
            pnReservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnReservationLayout.createSequentialGroup()
                .addGroup(pnReservationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnReservationLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnReservationLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnReservation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnReservation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    }//GEN-LAST:event_panelDatBanMouseEntered

    private void panelDatBanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDatBanMouseExited
        panelDatBan.setBackground(new Color(0, 204, 204));
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
        dispose();
        ImportGoods importGoods = new ImportGoods();
        importGoods.setVisible(true);
    }//GEN-LAST:event_panelPhieuNhapMouseClicked

    private void panelPhieuNhapMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPhieuNhapMouseExited
        panelPhieuNhap.setBackground(new Color(51, 51, 51));
    }//GEN-LAST:event_panelPhieuNhapMouseExited

    private void panelPhieuNhapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelPhieuNhapMouseEntered
        panelPhieuNhap.setBackground(new Color(0, 204, 204));
    }//GEN-LAST:event_panelPhieuNhapMouseEntered

    private void btnAddTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTimeActionPerformed
        timePicker.showPopup(this, 200, 150);
    }//GEN-LAST:event_btnAddTimeActionPerformed

    private void txtGioDatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGioDatActionPerformed

    }//GEN-LAST:event_txtGioDatActionPerformed

    private void tblReservationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblReservationMouseClicked
        showDetail();
        disabledButton(Arrays.asList(btnSave));
        enabledButton(Arrays.asList(btnEdit, btnDelete));
        Disabled();
    }//GEN-LAST:event_tblReservationMouseClicked

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        reloadCard();
        Enabled();
        fillTableReservation();
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        StringBuilder sb = new StringBuilder();

        if (txtTenKH.getText().isEmpty() || txtTenKH.getText().isBlank()) {
            sb.append("- Không được để trống tên khách hàng\n");
        }
        if (txtSDT.getText().isEmpty() || txtSDT.getText().isBlank()) {
            sb.append("- Không được để trống số điện thoại\n");
        }
        if (((JTextField) txtNgayDat.getDateEditor().getUiComponent()).getText().replaceAll("\\s", "").equals("")) {
            sb.append("- Không được bỏ trống ngày đặt bàn\n");
        }
        if (sb.length() > 0) {
            JOptionPane.showMessageDialog(this, sb.toString(), "Không hợp lệ!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.addBanDat();
        this.fillTableReservation();
        this.reloadCard();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        enabledButton(Arrays.asList(btnSave));
        Enabled();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn lưu không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            editBanDat();
            fillTableReservation();
            reloadCard();

            Enabled();
            disabledButton(Arrays.asList(btnSave, btnEdit, btnDelete));
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            deleteBanDat();
            fillTableReservation();
            reloadCard();

            Enabled();
            disabledButton(Arrays.asList(btnSave, btnEdit, btnDelete));
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reservation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Reservation().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddTime;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel panelDSBan;
    private javax.swing.JPanel panelDatBan;
    private javax.swing.JPanel panelDoanhThu;
    private javax.swing.JPanel panelHaiSan;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPanel panelPhieuNhap;
    private javax.swing.JPanel panelThongTin;
    private javax.swing.JPanel panelThucDon;
    private javax.swing.JPanel panelTrangChu;
    private javax.swing.JPanel pnReservation;
    private javax.swing.JSpinner spBanSo;
    private javax.swing.JSpinner spSoLuong;
    private javax.swing.JTextArea taGhiChu;
    private javax.swing.JTable tblReservation;
    private com.raven.swing.TimePicker timePicker;
    private javax.swing.JTextField txtGioDat;
    private com.toedter.calendar.JDateChooser txtNgayDat;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    // End of variables declaration//GEN-END:variables
}
