//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.main;

import com.nhom13.DAO.BANDAO;
import com.nhom13.DAO.CTHOADONDAO;
import com.nhom13.DAO.HAISANDAO;
import com.nhom13.DAO.HOADONDAO;
import com.nhom13.DAO.MONANDAO;
import com.nhom13.model.BAN;
import com.nhom13.model.HAISAN;
import com.nhom13.model.MONAN;
import com.nhom13.model.PHIEUNHAPMON;
import com.nhom13.sub.frmAddFood;
import com.nhom13.sub.frmSampleReceipt;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

public class Menu extends javax.swing.JFrame {

    private final DefaultTableModel tblModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private List<BAN> Tables = new ArrayList<>();
    private List<HAISAN> seaFood = new ArrayList<>();
    private List<MONAN> Food = new ArrayList<>();
    private List<PHIEUNHAPMON> tableBill = new ArrayList<>();
    private final frmAddFood addFood = new frmAddFood(this);

    public Menu() {
        initComponents();
        this.initTabbedPanel();
        this.initTableBill();
        this.initDefaultValue();
    }

    private void initDefaultValue() {
        this.fillTableBill(1);
        addFood.txtMAHS.setText(seaFood.get(0).getMAHS());
    }

    public void initTabbedPanel() {
        try {
            BANDAO banDao = new BANDAO();
            HAISANDAO hsDao = new HAISANDAO();
            MONANDAO maDao = new MONANDAO();

            Tables = banDao.getAllBAN();
            seaFood = hsDao.getAllHAISAN();
            Food = maDao.getAllMONAN();
        } catch (Exception e) {
        }

        for (BAN table : Tables) {
//            if (!table.getTRANGTHAI().equals("Trống")) {
            JTabbedPane tpFood = new JTabbedPane();
            tpFood.setFont(new Font("Source Code Pro", 1, 15));
            tpFood.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            tpFood.setTabPlacement(JTabbedPane.LEFT);

            tpTable.addTab(table.getTENBAN(), tpFood);

            for (HAISAN hs : seaFood) {
                JPanel pnFood = new JPanel();
                pnFood.setBackground(Color.white);
                GridLayout layout = new GridLayout(0, 2);
                layout.setHgap(5);
                layout.setVgap(10);
                pnFood.setLayout(layout);

                for (MONAN monAn : Food) {
                    if (monAn.getMAHS().equals(hs.getMAHS())) {
                        JPanel panel = this.initPanelFood(monAn, monAn.getHINHANH(), monAn.getTENMONAN().toUpperCase(), monAn.getGIA());
                        pnFood.add(panel);
                    }
                }

                JScrollPane jspFood = new JScrollPane();
                jspFood.setViewportView(pnFood);
                tpFood.addTab(hs.getTENHS().toUpperCase(), jspFood);
            }

            tpFood.addChangeListener((ChangeEvent e) -> {
                addFood.txtMAHS.setText(seaFood.get(tpFood.getSelectedIndex()).getMAHS());
            });

        }
//        }
        addEventTPTable(tpTable);
    }

    private void initTableBill() {
        String[] header = new String[]{"TÊN MÓN", "SỐ LƯỢNG", "THÀNH TIỀN"};

        tblModel.setColumnIdentifiers(header);
        tblBill.setModel(tblModel);
        
        if (tblBill.getColumnModel().getColumnCount() > 0) {
            tblBill.getColumnModel().getColumn(1).setMinWidth(80);
            tblBill.getColumnModel().getColumn(1).setMaxWidth(80);
            tblBill.getColumnModel().getColumn(2).setMaxWidth(100);
            tblBill.getColumnModel().getColumn(2).setMinWidth(100);
        }
    }

    private void initBill() {
        int total = 0;
        frmSampleReceipt sampleReceipt = new frmSampleReceipt();
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        Date date = new Date();
        SimpleDateFormat formatTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + String.format("%21s", " ") + tpTable.getTitleAt(tpTable.getSelectedIndex()).toUpperCase() + "               \n\n");
        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + " ---------------------------------------------\n");
        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + " Tên món" + "                  " + "Số Lượng" + "  " + "Thành Tiền\n");
        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + " ---------------------------------------------\n");

        for (PHIEUNHAPMON pnMon : tableBill) {
            total += pnMon.getThanhTien();
            sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + String.format(" %-27s", pnMon.getTenMonAn()) + String.format("%-10s", String.valueOf(pnMon.getSoLuong())) + formatter.format(pnMon.getThanhTien()) + "\n");
        }

        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + " ---------------------------------------------\n");
        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + String.format("%-32s", " Tổng cộng:") + formatter.format(total) + " VNĐ\n");
        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + String.format("%-32s", " Giảm giá:") + txtDiscount.getText() + "%\n");
        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + String.format("%-32s", " Thành tiền:") + formatter.format(total * (1 - Double.parseDouble(txtDiscount.getText()) / 100)) + " VNĐ\n");
        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + " ---------------------------------------------\n");
        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + " Ngày in hóa đơn: " + formatTime.format(date) + "\n");
        sampleReceipt.txtAreaSampleReceipt.setText(sampleReceipt.txtAreaSampleReceipt.getText() + " Chúc quý khách vui vẻ, hẹn gặp lại!\n");
        sampleReceipt.setVisible(true);
    }

    private void fillTableBill(int soBan) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        int total = 0;
        try {
            MONANDAO dao = new MONANDAO();

            tableBill = dao.getInformationBill(soBan);
        } catch (Exception e) {
        }
        tblModel.setRowCount(0);
        for (PHIEUNHAPMON pnMon : tableBill) {
            total += pnMon.getThanhTien();
            tblModel.addRow(new String[]{pnMon.getTenMonAn(), String.valueOf(pnMon.getSoLuong()), formatter.format(pnMon.getThanhTien())});
        }
        txtTotal.setText(formatter.format(total) + " VNĐ");
        tblModel.fireTableDataChanged();
    }

    private void showDetail(MONAN monAn) {
        addFood.txtIDMA.setText(String.valueOf(monAn.getID()));
        addFood.txtTENMA.setText(monAn.getTENMONAN());
        addFood.txtGIA.setText(String.valueOf(monAn.getGIA()));
        addFood.txtMAHS.setText(monAn.getMAHS());

        ImageIcon icon = new ImageIcon(monAn.getHINHANH());
        if (icon.toString().equals("")) {
            String RESOURCE = "/image/avatar.png";
            URL url = getClass().getResource(RESOURCE);
            if (url == null) {
                try {
                    throw new Exception("ERR cannot find resource: " + RESOURCE);
                } catch (Exception ex) {
                    Logger.getLogger(SeaFood.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Image image = Toolkit.getDefaultToolkit().getImage(url).getScaledInstance(237, 237, Image.SCALE_SMOOTH);
            addFood.lblHINHANH.setIcon(new ImageIcon(image));
        } else {
            addFood.lblHINHANH.setIcon(icon);
            Image img = icon.getImage().getScaledInstance(237, 237, Image.SCALE_SMOOTH);
            addFood.lblHINHANH.setIcon(new ImageIcon(img));
            addFood.tfLink.setText(icon.toString());
        }
    }

    private JPanel initPanelFood(MONAN monAn, String imageFood, String name, int price) {
        JPanel panel = new javax.swing.JPanel();
        JLabel lb = new javax.swing.JLabel();
        JPanel pnPriceFood = new javax.swing.JPanel();
        JPanel pnDetail = new javax.swing.JPanel();
        JLabel lblName = new javax.swing.JLabel();
        JLabel lblPrice = new javax.swing.JLabel();
        JButton btnPlus = new javax.swing.JButton();
        JButton btnMinus = new javax.swing.JButton();
        JButton btnSetting = new javax.swing.JButton();
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setPreferredSize(new java.awt.Dimension(200, 280));

        lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        ImageIcon icon = new ImageIcon(imageFood);
        if (icon.toString().equals("")) {
            String RESOURCE = "/image/avatar.png";
            URL url = getClass().getResource(RESOURCE);
            if (url == null) {
                try {
                    throw new Exception("ERR cannot find resource: " + RESOURCE);
                } catch (Exception ex) {
                    Logger.getLogger(SeaFood.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Image image = Toolkit.getDefaultToolkit().getImage(url).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lb.setIcon(new ImageIcon(image));
        } else {
            lb.setIcon(icon);
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lb.setIcon(new ImageIcon(img));
        }

        pnPriceFood.setBackground(new java.awt.Color(255, 255, 255));
        pnPriceFood.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 3));
        pnPriceFood.setPreferredSize(new java.awt.Dimension(210, 110));

        pnDetail.setBackground(new java.awt.Color(255, 255, 255));

        lblName.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblName.setText(name);

        lblPrice.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrice.setText(formatter.format(price) + " VNĐ");

        javax.swing.GroupLayout pnDetailLayout = new javax.swing.GroupLayout(pnDetail);
        pnDetail.setLayout(pnDetailLayout);
        pnDetailLayout.setHorizontalGroup(
                pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnDetailLayout.createSequentialGroup()
                                .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                        .addComponent(lblPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        pnDetailLayout.setVerticalGroup(
                pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnDetailLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnPlus.setBackground(new java.awt.Color(51, 51, 51));
        btnPlus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPlus.setForeground(new java.awt.Color(255, 255, 255));
        btnPlus.setText("+");
        btnPlus.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (tableBill.stream().anyMatch(x1 -> x1.getTenMonAn().equals(monAn.getTENMONAN()))) {
                try {
                    CTHOADONDAO dao = new CTHOADONDAO();
                    dao.addFoodToBill(tableBill.get(0).getID(), monAn.getID());
                } catch (Exception ex) {
                }
            } else {
                try {
                    CTHOADONDAO CTdao = new CTHOADONDAO();
                    HOADONDAO HDdao = new HOADONDAO();
                    int ID = tableBill.isEmpty() ? HDdao.getIDByNumberTable(tpTable.getSelectedIndex() + 1) : tableBill.get(0).getID();
                    if (ID == 0) {
                        try {
                            BANDAO banDao = new BANDAO();
                            banDao.setStatusBAN(tpTable.getSelectedIndex() + 1);
                        } catch (Exception ex) {
                        }
                        HDdao.addHOADON(0, tpTable.getSelectedIndex() + 1);
                        ID = tableBill.isEmpty() ? HDdao.getIDByNumberTable(tpTable.getSelectedIndex() + 1) : tableBill.get(0).getID();
                    }
                    CTdao.addCTHOADON(ID, monAn.getID());
                } catch (Exception ex) {
                }
            }
            fillTableBill(tpTable.getSelectedIndex() + 1);
        });

        btnMinus.setBackground(new java.awt.Color(51, 51, 51));
        btnMinus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMinus.setForeground(new java.awt.Color(255, 255, 255));
        btnMinus.setText("-");
        btnMinus.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (tableBill.stream().anyMatch(x1 -> x1.getTenMonAn().equals(monAn.getTENMONAN()))) {
                int quantity = 0;
                try {
                    CTHOADONDAO dao = new CTHOADONDAO();
                    quantity = dao.getQuantityFoodOfBill(tableBill.get(0).getID(), monAn.getID());
                } catch (Exception ex) {
                }
                if (quantity != 0) {
                    if (quantity == 1) {
                        try {
                            CTHOADONDAO dao = new CTHOADONDAO();
                            dao.deleteCTHOADON(tableBill.get(0).getID(), monAn.getID());
                        } catch (Exception ex) {
                        }
                    } else {
                        try {
                            CTHOADONDAO dao = new CTHOADONDAO();
                            dao.reduceFoodToBill(tableBill.get(0).getID(), monAn.getID());
                        } catch (Exception ex) {
                        }
                    }
                }
                fillTableBill(tpTable.getSelectedIndex() + 1);
            }
        });

        btnSetting.setBackground(new java.awt.Color(51, 51, 51));
        btnSetting.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/settings_.png"))); // NOI18N
        btnSetting.addActionListener((java.awt.event.ActionEvent evt) -> {
            showDetail(monAn);
            addFood.setTitle("CHỈNH SỬA THÔNG TIN MÓN ĂN");
            addFood.setVisible(true);

            this.disableTxt(Arrays.asList(addFood.txtTENMA, addFood.txtGIA));
            this.disableButton(Arrays.asList(addFood.btnAdd, addFood.btnSave));
            this.enableButton(Arrays.asList(addFood.btnDelete, addFood.btnEdit));
        });

        javax.swing.GroupLayout pnPriceFoodLayout = new javax.swing.GroupLayout(pnPriceFood);
        pnPriceFood.setLayout(pnPriceFoodLayout);
        pnPriceFoodLayout.setHorizontalGroup(
                pnPriceFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnPriceFoodLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnPriceFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(pnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnPriceFoodLayout.createSequentialGroup()
                                                .addComponent(btnPlus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnMinus, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)
                                                .addComponent(btnSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(6, 6, 6)))
                                .addGap(12, 12, 12))
        );
        pnPriceFoodLayout.setVerticalGroup(
                pnPriceFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnPriceFoodLayout.createSequentialGroup()
                                .addComponent(pnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnPriceFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnPriceFoodLayout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(btnPlus, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                                        .addGroup(pnPriceFoodLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(pnPriceFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnMinus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
        );

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(pnPriceFood, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(lb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(lb, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(pnPriceFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(210, 210, 210)
                                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(211, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(165, Short.MAX_VALUE))
        );

        return panel;
    }

    private void addEventTPTable(JTabbedPane tpTable) {
        tpTable.addChangeListener((ChangeEvent e) -> {
            fillTableBill(tpTable.getSelectedIndex() + 1);
            lblInfoTable.setText("THÔNG TIN " + (tpTable.getTitleAt(tpTable.getSelectedIndex() == -1 ? 0 : tpTable.getSelectedIndex())).toUpperCase());
        });
    }

    private void disableTxt(List<JTextField> txts) {
        txts.forEach(txt -> txt.setEnabled(false));
    }

    private void enableButton(List<JButton> btns) {
        btns.forEach(btn -> btn.setEnabled(true));
    }

    private void disableButton(List<JButton> btns) {
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
        pnContainer = new javax.swing.JPanel();
        pnReceipt = new javax.swing.JPanel();
        lblInfoTable = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBill = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        btnPay = new javax.swing.JButton();
        pnTableFood = new javax.swing.JPanel();
        btnAddFood = new javax.swing.JButton();
        tpTable = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thực đơn");
        setBackground(new java.awt.Color(255, 255, 255));
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

        panelThucDon.setBackground(new java.awt.Color(0, 204, 204));
        panelThucDon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        pnContainer.setBackground(new java.awt.Color(255, 255, 255));

        pnReceipt.setBackground(new java.awt.Color(255, 255, 255));
        pnReceipt.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblInfoTable.setFont(new java.awt.Font("Source Code Pro", 1, 24)); // NOI18N
        lblInfoTable.setForeground(new java.awt.Color(153, 0, 153));
        lblInfoTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfoTable.setText("THÔNG TIN BÀN SỐ 1");

        tblBill.setFont(new java.awt.Font("Source Code Pro", 0, 14)); // NOI18N
        tblBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TÊN MÓN", "SỐ LƯỢNG", "THÀNH TIỀN"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblBill.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblBill.setRowHeight(40);
        tblBill.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tblBill.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tblBill.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblBill.getTableHeader().setResizingAllowed(false);
        tblBill.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblBill);
        if (tblBill.getColumnModel().getColumnCount() > 0) {
            tblBill.getColumnModel().getColumn(1).setMinWidth(80);
            tblBill.getColumnModel().getColumn(1).setMaxWidth(80);
            tblBill.getColumnModel().getColumn(2).setMinWidth(100);
            tblBill.getColumnModel().getColumn(2).setMaxWidth(100);
        }
        tblBill.getTableHeader().setFont(new Font("Source Code Pro", 1, 12));
        tblBill.getTableHeader().setPreferredSize(new Dimension(0, 40));

        jLabel22.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bill.png"))); // NOI18N
        jLabel22.setText("TỔNG CỘNG:");

        txtTotal.setFont(new java.awt.Font("SF Mono", 1, 18)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setText("0");
        txtTotal.setEnabled(false);
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("PT GIẢM GIÁ:");

        txtDiscount.setFont(new java.awt.Font("SF Mono", 1, 18)); // NOI18N
        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDiscount.setText("0");

        btnPay.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        btnPay.setText("THANH TOÁN");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnReceiptLayout = new javax.swing.GroupLayout(pnReceipt);
        pnReceipt.setLayout(pnReceiptLayout);
        pnReceiptLayout.setHorizontalGroup(
            pnReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnReceiptLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnReceiptLayout.createSequentialGroup()
                        .addComponent(lblInfoTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnReceiptLayout.createSequentialGroup()
                        .addGroup(pnReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnReceiptLayout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotal))
                            .addGroup(pnReceiptLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        pnReceiptLayout.setVerticalGroup(
            pnReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnReceiptLayout.createSequentialGroup()
                .addGroup(pnReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblInfoTable, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(btnPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnReceiptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnTableFood.setBackground(new java.awt.Color(255, 255, 255));
        pnTableFood.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAddFood.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Add.png"))); // NOI18N
        btnAddFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFoodActionPerformed(evt);
            }
        });
        pnTableFood.add(btnAddFood, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 560, 50, 50));

        tpTable.setBackground(new java.awt.Color(255, 255, 255));
        tpTable.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tpTable.setFont(new java.awt.Font("Source Code Pro", 1, 14)); // NOI18N
        pnTableFood.add(tpTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 620));

        javax.swing.GroupLayout pnContainerLayout = new javax.swing.GroupLayout(pnContainer);
        pnContainer.setLayout(pnContainerLayout);
        pnContainerLayout.setHorizontalGroup(
            pnContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnTableFood, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnReceipt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnContainerLayout.setVerticalGroup(
            pnContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnReceipt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnContainerLayout.createSequentialGroup()
                        .addComponent(pnTableFood, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        PersonalManagement nv = new PersonalManagement();
        nv.setVisible(true);
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
        SeaFood hs = new SeaFood();
        hs.setVisible(true);
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

    private void btnAddFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFoodActionPerformed
        addFood.setTitle("NHẬP THÔNG TIN MÓN ĂN");
        addFood.setVisible(true);
        addFood.txtIDMA.setText(String.valueOf(Food.get(Food.size() - 1).getID() + 1));
        addFood.txtTENMA.setText("");
        addFood.txtGIA.setText("");
        addFood.tfLink.setText("/image/avatar.png");
        this.enableButton(Arrays.asList(addFood.btnAdd));
        this.disableButton(Arrays.asList(addFood.btnSave, addFood.btnDelete, addFood.btnEdit));
    }//GEN-LAST:event_btnAddFoodActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        try {
            BANDAO dao = new BANDAO();
            dao.resetStatusBAN(tpTable.getSelectedIndex() + 1);
        } catch (Exception ex) {
        }

        try {
            HOADONDAO dao = new HOADONDAO();
            dao.resetStatusHD(tpTable.getSelectedIndex() + 1, txtDiscount.equals("") ? 0 : parseInt(txtDiscount.getText()));
        } catch (Exception ex) {
        }

        initBill();

        txtDiscount.setText("0");
        fillTableBill(tpTable.getSelectedIndex() + 1);
    }//GEN-LAST:event_btnPayActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFood;
    private javax.swing.JButton btnPay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInfoTable;
    private javax.swing.JPanel panelDSBan;
    private javax.swing.JPanel panelDatBan;
    private javax.swing.JPanel panelDoanhThu;
    private javax.swing.JPanel panelHaiSan;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPanel panelPhieuNhap;
    private javax.swing.JPanel panelThongTin;
    private javax.swing.JPanel panelThucDon;
    private javax.swing.JPanel panelTrangChu;
    private javax.swing.JPanel pnContainer;
    private javax.swing.JPanel pnReceipt;
    private javax.swing.JPanel pnTableFood;
    private javax.swing.JTable tblBill;
    public javax.swing.JTabbedPane tpTable;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
