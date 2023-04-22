//@Author: NGUYỄN TRẦN TẤN QUY PTITHCM
package com.nhom13.main;

import com.nhom13.DAO.BANDAO;
import com.nhom13.DAO.HAISANDAO;
import com.nhom13.DAO.HOADONDAO;
import com.nhom13.DAO.MONANDAO;
import com.nhom13.DAO.NHANVIENDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class Satistics extends javax.swing.JFrame {
    
    public Satistics() {
        initComponents();
        initStaticsBill();
        initStaticsRevenue();
        initStaticsEmployee();
        initStaticsTable();
        initStaticsSeaFood();
        initComboYear();
        initComboMonth();
        initBarChart("BIỂU ĐỒ DOANH THU ", Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));
    }

    private void initStaticsBill() {
        int total = 0;
        int totalToday = 0;
        try {
            HOADONDAO dao = new HOADONDAO();
            total = dao.getCountBill();
            totalToday = dao.getCountBillToday();
        } catch (Exception ex) {
        }

        lblTotalBill.setText(String.valueOf(total));
        lblTotalBillToday.setText(String.valueOf(totalToday));
    }

    private void initStaticsRevenue() {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        int total = 0;
        int totalToday = 0;
        try {
            HOADONDAO dao = new HOADONDAO();
            total = dao.getTotalRevenue();
            totalToday = dao.getTotalRevenueToday();
        } catch (Exception ex) {
        }

        lblTotalRevenue.setText(formatter.format(total));
        lblTotalRevenueToday.setText(formatter.format(totalToday));
    }

    private void initStaticsEmployee() {
        int total = 0;
        try {
            NHANVIENDAO dao = new NHANVIENDAO();
            total = dao.getTotalEmployee();
        } catch (Exception ex) {
        }

        lblTotalEmployee.setText(String.valueOf(total) + " Nhân viên");
    }

    private void initStaticsTable() {
        int total = 0;
        try {
            BANDAO dao = new BANDAO();
            total = dao.getTotalTableWithCustomers();
        } catch (Exception ex) {
        }

        lblTotalTable.setText(String.valueOf(total) + " Bàn");
    }

    private void initStaticsSeaFood() {
        int total = 0;
        int totalSeaFoodOutOfStock = 0;
        try {
            HAISANDAO dao = new HAISANDAO();
            total = dao.getTotalSeaFoodInventory();
            totalSeaFoodOutOfStock = dao.getTotalSeaFood() - total;
        } catch (Exception ex) {
        }

        lblSeaFoodInventory.setText(String.valueOf(total) + " Loại hải sản");
        lblSeaFoodOutOfStock.setText(String.valueOf(totalSeaFoodOutOfStock) + " Loại hải sản");
    }

    private void initBarChart(String titleChart, int Year, int Month) {
        titleChart += String.format("THÁNG %d NĂM %d", Month + 1, Year);
        JFreeChart barChart = ChartFactory.createBarChart(titleChart, "NGÀY", "Tổng tiền", createDatasetRevenue(Year, Month), PlotOrientation.VERTICAL, false, false, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(650, 400));
        pnChart.setLayout(new BorderLayout());
        pnChart.add(chartPanel, BorderLayout.NORTH);
    }
    
    private void initPieChart(String titleChart, int Year, int Month) {
        titleChart += String.format("THÁNG %d NĂM %d", Month + 1, Year);
        JFreeChart pieChart = ChartFactory.createPieChart(titleChart, createDatasetSeaFood(Year, Month));
        
        PiePlot plot = (PiePlot) pieChart.getPlot();
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1}");
        plot.setLabelGenerator(labelGenerator);
        
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(650, 400));
        pnChart.setLayout(new BorderLayout());
        pnChart.add(chartPanel, BorderLayout.NORTH);
    }
    
    private void initLineChart(String titleChart, int Year, int Month) {
        titleChart += String.format("THÁNG %d NĂM %d", Month + 1, Year);
        JFreeChart lineChart = ChartFactory.createLineChart(titleChart, "Số Bàn", "Doanh Thu", createDatasetTable(Year, Month));
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(650, 400));
        pnChart.setLayout(new BorderLayout());
        pnChart.add(chartPanel, BorderLayout.NORTH);
    }
    
    private CategoryDataset createDatasetRevenue(int Year, int Month) {
        Map<String, Integer> result = new HashMap<>();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try {
            HOADONDAO dao = new HOADONDAO();
            result = dao.getTotalRevenueByMonth(Year, Month);
        } catch (Exception ex) {
        }

        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            dataset.addValue(entry.getValue(), "Tổng Tiền", entry.getKey());
        }
        
        return dataset;
    }

    private PieDataset createDatasetSeaFood(int Year, int Month) {
        Map<String, Integer> result = new HashMap<>();
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            MONANDAO dao = new MONANDAO();
            result = dao.getTop5SeaFood(Year, Month);
        } catch (Exception ex) {
        }

        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            dataset.setValue(entry.getKey(), Double.valueOf(entry.getValue()));
        }
        return dataset;
    }
    
    private DefaultCategoryDataset createDatasetTable(int Year, int Month) {
        Map<String, Integer> result = new HashMap<>();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        try {
            BANDAO dao = new BANDAO();
            result = dao.getTop5Table(Year, Month);
        } catch (Exception ex) {
        }

        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            dataset.addValue(entry.getValue(), "Tổng Tiền", String.format("Bàn %s", entry.getKey()));
        }
        
        return dataset;
    }
    
    private void initComboMonth() {
        List<Integer> result = new ArrayList<>();
        try {
            HOADONDAO dao = new HOADONDAO();
            result = dao.showMonth();
        } catch (Exception ex) {
        }

        for (int Month : result) {
            cbMonth.addItem(formatMonth(String.valueOf(Month)));
        }
    }

    private void initComboYear() {
        List<Integer> result = new ArrayList<>();
        try {
            HOADONDAO dao = new HOADONDAO();
            result = dao.showYear();
        } catch (Exception ex) {
        }

        for (int Year : result) {
            cbYear.addItem(String.valueOf(Year));
        }
    }

    public String formatMonth(String month) {
        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM");
        try {
            return monthDisplay.format(monthParse.parse(month));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return "";
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
        pnGif = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTotalBill = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblTotalBillToday = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        lblTotalEmployee = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        lblTotalRevenue = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lblTotalRevenueToday = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lblTotalTable = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        lblSeaFoodOutOfStock = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        lblSeaFoodInventory = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        pnChart = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbMonth = new javax.swing.JComboBox<>();
        cbYear = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        btnStaticsRevenue = new javax.swing.JButton();
        btnStaticsSeaFood = new javax.swing.JButton();
        btnStaticsTable = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thống kê doanh thu");
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

        panelDoanhThu.setBackground(new java.awt.Color(0, 204, 204));
        panelDoanhThu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelDoanhThu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelDoanhThuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelDoanhThuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelDoanhThuMouseExited(evt);
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

        pnGif.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));

        jLabel5.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tổng số hóa đơn");

        lblTotalBill.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        lblTotalBill.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalBill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalBill.setText("100");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/invoice.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Hôm nay:");

        lblTotalBillToday.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        lblTotalBillToday.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalBillToday.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalBillToday.setText("10");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotalBillToday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                        .addGap(12, 12, 12)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTotalBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalBillToday, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));

        jLabel17.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Tổng số nhân viên");

        lblTotalEmployee.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        lblTotalEmployee.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalEmployee.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalEmployee.setText("3 Nhân viên");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/management.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lblTotalEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(153, 0, 153));

        jLabel25.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Tổng doanh thu");

        lblTotalRevenue.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        lblTotalRevenue.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalRevenue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalRevenue.setText("100");

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Money.png"))); // NOI18N

        jLabel35.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Hôm nay:");

        lblTotalRevenueToday.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        lblTotalRevenueToday.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalRevenueToday.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalRevenueToday.setText("10");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalRevenueToday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblTotalRevenue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTotalRevenue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalRevenueToday, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 0));

        jLabel23.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Tổng số bàn có khách");

        lblTotalTable.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        lblTotalTable.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalTable.setText("3 Bàn");

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/table.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lblTotalTable, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalTable, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(51, 51, 51));

        jLabel38.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Hải sản đã hết hàng");

        lblSeaFoodOutOfStock.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        lblSeaFoodOutOfStock.setForeground(new java.awt.Color(255, 255, 255));
        lblSeaFoodOutOfStock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSeaFoodOutOfStock.setText("10 Loại hải sản");

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/octopus1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lblSeaFoodOutOfStock, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSeaFoodOutOfStock, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(204, 0, 0));

        jLabel41.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Hải sản tồn kho");

        lblSeaFoodInventory.setFont(new java.awt.Font("SF Mono", 1, 14)); // NOI18N
        lblSeaFoodInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblSeaFoodInventory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSeaFoodInventory.setText("10 Loại hải sản");

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/octopus1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lblSeaFoodInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSeaFoodInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnChart.setBackground(new java.awt.Color(255, 255, 255));
        pnChart.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout pnChartLayout = new javax.swing.GroupLayout(pnChart);
        pnChart.setLayout(pnChartLayout);
        pnChartLayout.setHorizontalGroup(
            pnChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnChartLayout.setVerticalGroup(
            pnChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setFont(new java.awt.Font("SF Mono", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("BẢNG ĐIỀU KHIỂN");

        cbMonth.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                cbMonthComponentRemoved(evt);
            }
        });
        cbMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMonthItemStateChanged(evt);
            }
        });
        cbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMonthActionPerformed(evt);
            }
        });

        cbYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbYearItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        btnStaticsRevenue.setFont(new java.awt.Font("SF Mono", 1, 12)); // NOI18N
        btnStaticsRevenue.setText("THỐNG KÊ DOANH THU");
        btnStaticsRevenue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStaticsRevenueActionPerformed(evt);
            }
        });

        btnStaticsSeaFood.setFont(new java.awt.Font("SF Mono", 1, 12)); // NOI18N
        btnStaticsSeaFood.setText("THỐNG KÊ HẢI SẢN");
        btnStaticsSeaFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStaticsSeaFoodActionPerformed(evt);
            }
        });

        btnStaticsTable.setFont(new java.awt.Font("SF Mono", 1, 12)); // NOI18N
        btnStaticsTable.setText("THỐNG KÊ BÀN");
        btnStaticsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStaticsTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnStaticsRevenue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnStaticsSeaFood, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnStaticsTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnStaticsRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnStaticsSeaFood, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnStaticsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout pnGifLayout = new javax.swing.GroupLayout(pnGif);
        pnGif.setLayout(pnGifLayout);
        pnGifLayout.setHorizontalGroup(
            pnGifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGifLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnGifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnGifLayout.createSequentialGroup()
                        .addGroup(pnGifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnGifLayout.createSequentialGroup()
                                .addGap(206, 206, 206)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnGifLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnGifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnGifLayout.setVerticalGroup(
            pnGifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGifLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnGifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnGifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnGifLayout.createSequentialGroup()
                        .addGroup(pnGifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(pnChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnGif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
            .addComponent(pnGif, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    }//GEN-LAST:event_panelDoanhThuMouseEntered

    private void panelDoanhThuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDoanhThuMouseExited
        panelDoanhThu.setBackground(new Color(0, 204, 204));
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

    private void cbMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMonthItemStateChanged
    
    }//GEN-LAST:event_cbMonthItemStateChanged

    private void cbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMonthActionPerformed

    }//GEN-LAST:event_cbMonthActionPerformed

    private void cbMonthComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_cbMonthComponentRemoved
        
    }//GEN-LAST:event_cbMonthComponentRemoved

    private void cbYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbYearItemStateChanged

    }//GEN-LAST:event_cbYearItemStateChanged

    private void btnStaticsSeaFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaticsSeaFoodActionPerformed
        int year = Integer.parseInt(cbYear.getSelectedItem().toString());
        int month = cbMonth.getSelectedIndex();
        
        pnChart.removeAll();
        initPieChart("TOP 5 MÓN ĂN BÁN CHẠY NHẤT ", year, month);
        pnChart.revalidate();
    }//GEN-LAST:event_btnStaticsSeaFoodActionPerformed

    private void btnStaticsRevenueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaticsRevenueActionPerformed
        int year = Integer.parseInt(cbYear.getSelectedItem().toString());
        int month = cbMonth.getSelectedIndex();
        
        pnChart.removeAll();
        initBarChart("BIỂU ĐỒ DOANH THU ", year, month);
        pnChart.revalidate();
    }//GEN-LAST:event_btnStaticsRevenueActionPerformed

    private void btnStaticsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaticsTableActionPerformed
        int year = Integer.parseInt(cbYear.getSelectedItem().toString());
        int month = cbMonth.getSelectedIndex();
        
        pnChart.removeAll();
        initLineChart("BIỂU ĐỒ TOP 5 BÀN MANG LẠI DOANH THU CAO NHẤT ", year, month);
        pnChart.revalidate();
    }//GEN-LAST:event_btnStaticsTableActionPerformed

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
            java.util.logging.Logger.getLogger(Satistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Satistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Satistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Satistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Satistics().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStaticsRevenue;
    private javax.swing.JButton btnStaticsSeaFood;
    private javax.swing.JButton btnStaticsTable;
    private javax.swing.JComboBox<String> cbMonth;
    private javax.swing.JComboBox<String> cbYear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblSeaFoodInventory;
    private javax.swing.JLabel lblSeaFoodOutOfStock;
    private javax.swing.JLabel lblTotalBill;
    private javax.swing.JLabel lblTotalBillToday;
    private javax.swing.JLabel lblTotalEmployee;
    private javax.swing.JLabel lblTotalRevenue;
    private javax.swing.JLabel lblTotalRevenueToday;
    private javax.swing.JLabel lblTotalTable;
    private javax.swing.JPanel panelDSBan;
    private javax.swing.JPanel panelDatBan;
    private javax.swing.JPanel panelDoanhThu;
    private javax.swing.JPanel panelHaiSan;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JPanel panelPhieuNhap;
    private javax.swing.JPanel panelThongTin;
    private javax.swing.JPanel panelThucDon;
    private javax.swing.JPanel panelTrangChu;
    private javax.swing.JPanel pnChart;
    private javax.swing.JPanel pnGif;
    // End of variables declaration//GEN-END:variables
}
