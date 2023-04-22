//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.main;

import com.nhom13.DAO.HAISANDAO;
import com.nhom13.model.HAISAN;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SeaFood extends javax.swing.JFrame {

    private final DefaultTableModel tblModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private List<HAISAN> seaFood = new ArrayList<>();

    public SeaFood() {
        initComponents();
        initTable();
        fillFullTable();
    }

    private void initTable() {
        String[] header = new String[]{"MÃ HẢI SẢN", "TÊN HẢI SẢN", "GIÁ", "SỐ LƯỢNG", "ĐƠN VỊ"};

        tblSeaFood.getTableHeader().setFont(new Font("Source Code Pro", 1, 12));
        tblSeaFood.getTableHeader().setPreferredSize(new Dimension(0, 40));

        tblModel.setColumnIdentifiers(header);
        tblSeaFood.setModel(tblModel);
        if (tblSeaFood.getColumnModel().getColumnCount() > 0) {
            tblSeaFood.getColumnModel().getColumn(0).setMinWidth(140);
            tblSeaFood.getColumnModel().getColumn(0).setMaxWidth(140);
            tblSeaFood.getColumnModel().getColumn(2).setMinWidth(80);
            tblSeaFood.getColumnModel().getColumn(2).setMaxWidth(80);
            tblSeaFood.getColumnModel().getColumn(3).setMinWidth(90);
            tblSeaFood.getColumnModel().getColumn(3).setMaxWidth(90);
            tblSeaFood.getColumnModel().getColumn(4).setMinWidth(80);
            tblSeaFood.getColumnModel().getColumn(4).setMaxWidth(80);
        }
    }

    private void fillFullTable() {
        try {
            HAISANDAO dao = new HAISANDAO();
            seaFood = dao.getAllHAISAN();
        } catch (Exception e) {
        }
        fillPartialTable();
    }

    private void fillPartialTable() {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        List<String> mountAndUnit = new ArrayList<>();
        tblModel.setRowCount(0);
        for (HAISAN haisan : seaFood) {
            try {
                HAISANDAO dao = new HAISANDAO();
                mountAndUnit = dao.getMountAndUnitOfSeaFood(haisan.getMAHS());
            } catch (Exception e) {
            }
            tblModel.addRow(new String[]{haisan.getMAHS(), haisan.getTENHS(), formatter.format(haisan.getGIA()), mountAndUnit.get(0), mountAndUnit.get(1)});
        }
        tblModel.fireTableDataChanged();
    }

    private void showDetail() {
        int index = tblSeaFood.getSelectedRow();

        HAISAN hs = seaFood.get(index);

        txtMAHS.setText(hs.getMAHS().trim());
        txtTENHS.setText(hs.getTENHS().trim());
        txtGIA.setText(String.valueOf(hs.getGIA()));
        
        ImageIcon icon = new ImageIcon(hs.getHINHANH());
        if (icon.toString().equals("")) {
            String RESOURCE = "/image/Logo_Large.png";
            URL url = getClass().getResource(RESOURCE);
            if (url == null) {
                try {
                    throw new Exception("ERR cannot find resource: " + RESOURCE);
                } catch (Exception ex) {
                    Logger.getLogger(SeaFood.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Image image = Toolkit.getDefaultToolkit().getImage(url).getScaledInstance(265, 237, Image.SCALE_SMOOTH);
            lblHINHANH.setIcon(new ImageIcon(image));
        } else {
            lblHINHANH.setIcon(icon);
            Image img = icon.getImage().getScaledInstance(265, 237, Image.SCALE_SMOOTH);
            lblHINHANH.setIcon(new ImageIcon(img));
            tfLink.setText(icon.toString());
        }
    }

    private void addSeaFood() {
        HAISAN hs = getInfoSeafoodFromNote();
        try {
            HAISANDAO dao = new HAISANDAO();
            dao.addHAISAN(hs);
        } catch (Exception ex) {
            Logger.getLogger(SeaFood.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editSeaFood() {
        HAISAN hs = getInfoSeafoodFromNote();

        int index = tblSeaFood.getSelectedRow();
        
        if (seaFood.get(index).getMAHS().equals(hs.getMAHS())) {
            try {
                HAISANDAO dao = new HAISANDAO();
                dao.editHAISAN(hs);
            } catch (Exception ex) {
                Logger.getLogger(SeaFood.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            boolean isSuccess = true;
            try {
                HAISANDAO dao = new HAISANDAO();
                isSuccess = dao.addHAISAN(hs);
            } catch (Exception ex) {
                Logger.getLogger(SeaFood.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (isSuccess) {
                try {
                    HAISANDAO dao = new HAISANDAO();
                    dao.deleteHAISAN(seaFood.get(index).getMAHS());
                } catch (Exception ex) {
                    Logger.getLogger(SeaFood.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void deleteSeaFood() {
        int index = tblSeaFood.getSelectedRow();
        String maHS = seaFood.get(index).getMAHS();
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xoá hải sản này không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            try {
                HAISANDAO dao = new HAISANDAO();
                dao.deleteHAISAN(maHS);
                this.reloadCard();
            } catch (Exception ex) {
                System.out.println("Đã xảy ra lỗi trong quá trình xóa hãy thử lại sau!");
            }
        } else {
            this.reloadCard();
        }
    }

    private void reloadCard() {
        txtMAHS.setText("");
        txtTENHS.setText("");
        txtGIA.setText("");
        String RESOURCE = "/image/Logo_Large.png";
        URL url = getClass().getResource(RESOURCE);
        if (url == null) {
            try {
                throw new Exception("ERR cannot find resource: " + RESOURCE);
            } catch (Exception ex) {
                System.out.println("Đường dẫn ảnh bị lỗi!");
            }
        }
        Image image = Toolkit.getDefaultToolkit().getImage(url).getScaledInstance(265, 237, Image.SCALE_SMOOTH);
        lblHINHANH.setIcon(new ImageIcon(image));
    }

    private void disableButton(List<JButton> btns) {
        btns.forEach(btn -> btn.setEnabled(false));
    }

    private void enableButton(List<JButton> btns) {
        btns.forEach(btn -> btn.setEnabled(true));
    }

    private void disableTxt(List<JTextField> txts) {
        txts.forEach(txt -> txt.setEnabled(false));
    }

    private void enableTxt(List<JTextField> txts) {
        txts.forEach(txt -> txt.setEnabled(true));
    }

    private HAISAN getInfoSeafoodFromNote() {
        HAISAN hs = new HAISAN();
        hs.setMAHS(txtMAHS.getText().replaceAll("\\s", ""));
        hs.setTENHS(txtTENHS.getText());
        hs.setGIA(Integer.parseInt(txtGIA.getText()));
        hs.setHINHANH(tfLink.getText());
        return hs;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfLink = new javax.swing.JTextField();
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
        pnSeaFood = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        spSeaFoodTable = new javax.swing.JScrollPane();
        tblSeaFood = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        cbSearch = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtMAHS = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTENHS = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtGIA = new javax.swing.JTextField();
        lblHINHANH = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnSortByName = new javax.swing.JButton();
        btnSortByPrice = new javax.swing.JButton();
        btnSortByQuatity = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hải sản");
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

        panelHaiSan.setBackground(new java.awt.Color(0, 204, 204));
        panelHaiSan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelHaiSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelHaiSanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelHaiSanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelHaiSanMouseExited(evt);
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

        jPanel2.add(panelThucDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 180, 50));

        panelDatBan.setBackground(new java.awt.Color(51, 51, 51));
        panelDatBan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelDatBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDatBanMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelDatBanMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDatBanMouseEntered(evt);
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

        jPanel2.add(panelPhieuNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 180, 50));

        panelThongTin.setBackground(new java.awt.Color(51, 51, 51));
        panelThongTin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelThongTin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelThongTinMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelThongTinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelThongTinMouseExited(evt);
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

        pnSeaFood.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Source Code Pro", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("QUẢN LÝ HẢI SẢN");

        spSeaFoodTable.setAutoscrolls(true);

        tblSeaFood.setFont(new java.awt.Font("Source Code Pro", 0, 14)); // NOI18N
        tblSeaFood.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        tblSeaFood.setGridColor(new java.awt.Color(51, 51, 51));
        tblSeaFood.setRowHeight(40);
        tblSeaFood.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tblSeaFood.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblSeaFood.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSeaFood.setShowGrid(true);
        tblSeaFood.getTableHeader().setResizingAllowed(false);
        tblSeaFood.getTableHeader().setReorderingAllowed(false);
        tblSeaFood.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSeaFoodMouseClicked(evt);
            }
        });
        spSeaFoodTable.setViewportView(tblSeaFood);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cbSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã hải sản", "Tên hải sản", "Giá" }));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THÔNG TIN HẢI SẢN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Source Code Pro", 1, 24), new java.awt.Color(102, 0, 153))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Source Code Pro", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setLabelFor(txtMAHS);
        jLabel13.setText("Mã hải sản:");

        jLabel15.setFont(new java.awt.Font("Source Code Pro", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setLabelFor(txtTENHS);
        jLabel15.setText("Tên hải sản:");

        jLabel17.setFont(new java.awt.Font("Source Code Pro", 0, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setLabelFor(txtGIA);
        jLabel17.setText("Giá:");

        lblHINHANH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHINHANH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/image_default.png"))); // NOI18N
        lblHINHANH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHINHANHMouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Source Code Pro", 0, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setLabelFor(lblHINHANH);
        jLabel22.setText("Hình ảnh:");

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
        btnDelete.setText("Xoá");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Source Code Pro", 0, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plus.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGIA)
                            .addComponent(txtMAHS)
                            .addComponent(txtTENHS)
                            .addComponent(lblHINHANH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnAdd)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMAHS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTENHS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGIA, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblHINHANH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(14, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(txtSearch)
                    .addComponent(cbSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSortByName.setText("Sắp xếp theo tên");
        btnSortByName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByNameActionPerformed(evt);
            }
        });

        btnSortByPrice.setText("Sắp xếp theo giá");
        btnSortByPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByPriceActionPerformed(evt);
            }
        });

        btnSortByQuatity.setText("Sắp xếp theo số lượng");
        btnSortByQuatity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortByQuatityActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnSeaFoodLayout = new javax.swing.GroupLayout(pnSeaFood);
        pnSeaFood.setLayout(pnSeaFoodLayout);
        pnSeaFoodLayout.setHorizontalGroup(
            pnSeaFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSeaFoodLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnSeaFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnSeaFoodLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(pnSeaFoodLayout.createSequentialGroup()
                        .addComponent(btnSortByName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(40, 40, 40)
                        .addComponent(btnSortByQuatity)
                        .addGap(40, 40, 40)
                        .addComponent(btnSortByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(spSeaFoodTable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnSeaFoodLayout.setVerticalGroup(
            pnSeaFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSeaFoodLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spSeaFoodTable, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnSeaFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnSortByQuatity, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSortByName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSortByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnSeaFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                    .addComponent(pnSeaFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
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

    }//GEN-LAST:event_panelHaiSanMouseClicked

    private void panelHaiSanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHaiSanMouseExited

    }//GEN-LAST:event_panelHaiSanMouseExited

    private void panelHaiSanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHaiSanMouseEntered

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
                JOptionPane.showMessageDialog(SeaFood.this, "Chưa chọn đường dẫn tới ảnh!!");
            }
        }
    }//GEN-LAST:event_lblHINHANHMouseClicked

    private void tblSeaFoodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSeaFoodMouseClicked
        showDetail();
        disableButton(Arrays.asList(btnAdd, btnSave));
        enableButton(Arrays.asList(btnEdit, btnDelete));
        disableTxt(Arrays.asList(txtMAHS, txtTENHS, txtGIA));
    }//GEN-LAST:event_tblSeaFoodMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        StringBuilder sb = new StringBuilder();

        if (txtMAHS.getText().isEmpty() || txtMAHS.getText().isBlank()) {
            sb.append("- Không được để trống mã hải sản\n");
        }
        if (txtTENHS.getText().isEmpty() || txtTENHS.getText().isBlank()) {
            sb.append("- Không được để trống tên hải sản\n");
        }
        if (txtGIA.getText().isEmpty() || txtGIA.getText().isBlank()) {
            sb.append("- Không được để trống giá\n");
        }
        if (sb.length() > 0) {
            JOptionPane.showMessageDialog(this, sb.toString(), "Không hợp lệ!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.addSeaFood();
        this.fillFullTable();
        this.reloadCard();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa hải sản này không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            this.deleteSeaFood();
            this.fillFullTable();
            enableButton(Arrays.asList(btnAdd));
            disableButton(Arrays.asList(btnEdit, btnSave, btnDelete));
            enableTxt(Arrays.asList(txtMAHS, txtTENHS, txtGIA));
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        enableTxt(Arrays.asList(txtMAHS, txtTENHS, txtGIA));
        enableButton(Arrays.asList(btnSave));
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        int click = JOptionPane.showConfirmDialog(null, "Bạn có muốn lưu không?", "Thông báo", 2);
        if (click == JOptionPane.YES_OPTION) {
            this.editSeaFood();
            this.fillFullTable();
            this.reloadCard();

            enableTxt(Arrays.asList(txtMAHS, txtTENHS, txtGIA));
            enableButton(Arrays.asList(btnAdd));
            disableButton(Arrays.asList(btnSave, btnEdit, btnDelete));
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSortByNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByNameActionPerformed
        try {
            HAISANDAO dao = new HAISANDAO();

            seaFood = dao.sortAllHAISANByTENHS();
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi hãy thử lại sau !");
        }
        this.fillPartialTable();
        this.reloadCard();
        enableTxt(Arrays.asList(txtMAHS, txtTENHS, txtGIA));
        enableButton(Arrays.asList(btnAdd));
        disableButton(Arrays.asList(btnSave, btnEdit, btnDelete));
    }//GEN-LAST:event_btnSortByNameActionPerformed

    private void btnSortByPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByPriceActionPerformed
        try {
            HAISANDAO dao = new HAISANDAO();

            seaFood = dao.sortAllHAISANByGIA();
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi hãy thử lại sau !");
        }

        this.fillPartialTable();
        this.reloadCard();
        enableTxt(Arrays.asList(txtMAHS, txtTENHS, txtGIA));
        enableButton(Arrays.asList(btnAdd));
        disableButton(Arrays.asList(btnSave, btnEdit, btnDelete));
    }//GEN-LAST:event_btnSortByPriceActionPerformed

    private void btnSortByQuatityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortByQuatityActionPerformed
        try {
            HAISANDAO dao = new HAISANDAO();

            seaFood = dao.sortAllHAISANBySOLUONG();
        } catch (Exception e) {
            System.out.println("Xảy ra lỗi hãy thử lại sau !");
        }

        this.fillPartialTable();
        this.reloadCard();
        enableTxt(Arrays.asList(txtMAHS, txtTENHS, txtGIA));
        enableButton(Arrays.asList(btnAdd));
        disableButton(Arrays.asList(btnSave, btnEdit, btnDelete));
    }//GEN-LAST:event_btnSortByQuatityActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (txtSearch.getText().replaceAll("\\s", "").equals("")) {
            this.fillFullTable();

        } else if (cbSearch.getSelectedItem().toString().equals("Mã hải sản")) {
            try {
                HAISANDAO dao = new HAISANDAO();
                seaFood = dao.searchAllHAISANByMAHS(txtSearch.getText());
            } catch (Exception e) {
                System.out.println("Xảy ra lỗi hãy thử lại sau !");
            }
            this.fillPartialTable();

        } else if (cbSearch.getSelectedItem().toString().equals("Tên hải sản")) {
            try {
                HAISANDAO dao = new HAISANDAO();
                seaFood = dao.searchAllHAISANByTENHS(txtSearch.getText());
            } catch (Exception e) {
                System.out.println("Xảy ra lỗi hãy thử lại sau !");
            }
            this.fillPartialTable();

        } else if (cbSearch.getSelectedItem().toString().equals("Giá")) {
            try {
                HAISANDAO dao = new HAISANDAO();
                seaFood = dao.searchAllHAISANByGIA(txtSearch.getText());
            } catch (Exception e) {
                System.out.println("Xảy ra lỗi hãy thử lại sau !");
            }
            this.fillPartialTable();
        }

        this.reloadCard();
        enableTxt(Arrays.asList(txtMAHS, txtTENHS, txtGIA));
        enableButton(Arrays.asList(btnAdd));
        disableButton(Arrays.asList(btnSave, btnEdit, btnDelete));
    }//GEN-LAST:event_btnSearchActionPerformed

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
            java.util.logging.Logger.getLogger(SeaFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SeaFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SeaFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SeaFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SeaFood().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSortByName;
    private javax.swing.JButton btnSortByPrice;
    private javax.swing.JButton btnSortByQuatity;
    private javax.swing.JComboBox<String> cbSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblHINHANH;
    private javax.swing.JPanel panelDSBan;
    private javax.swing.JPanel panelDatBan;
    private javax.swing.JPanel panelDoanhThu;
    private javax.swing.JPanel panelHaiSan;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPanel panelPhieuNhap;
    private javax.swing.JPanel panelThongTin;
    private javax.swing.JPanel panelThucDon;
    private javax.swing.JPanel panelTrangChu;
    private javax.swing.JPanel pnSeaFood;
    private javax.swing.JScrollPane spSeaFoodTable;
    private javax.swing.JTable tblSeaFood;
    private javax.swing.JTextField tfLink;
    private javax.swing.JTextField txtGIA;
    private javax.swing.JTextField txtMAHS;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTENHS;
    // End of variables declaration//GEN-END:variables
}
